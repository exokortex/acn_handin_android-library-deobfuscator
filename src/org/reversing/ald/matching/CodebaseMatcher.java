package org.reversing.ald.matching;


import org.reversing.ald.data.Codebase;
import org.reversing.ald.data.Translation;
import org.reversing.ald.util.SignatureUtil;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;


/**
 * Generates translations between two codebases
 */
public class CodebaseMatcher {
    private static final Logger LOGGER = Logger.getLogger(CodebaseMatcher.class.getName());

    private Translation<String, String> strTranslation;
    private Translation<Object, Object> translation = new Translation<>();

    private Codebase obfuscated;
    private Codebase library;

    private Queue<Object> todoList = new LinkedList<>();
    private Set<Object> doneList = new HashSet<>();

    public CodebaseMatcher(Codebase obfuscated, Codebase library, Translation<String, String> initialTranslation) {
        this.obfuscated = obfuscated;
        this.library = library;
        this.strTranslation = initialTranslation.clone();

        strTranslation.getAllObfuscated().forEach(o -> {
            if (o.contains("("))
                return;
            addInitialTranslation(o, strTranslation.getSource(o));
        });
    }

    private void addInitialTranslation(String obfClassname, String sourceClassname) {
        CtClass obfClass = obfuscated.getClass(obfClassname);
        CtClass sourceClass = library.getClass(sourceClassname);

        if (obfClass == null) {
            LOGGER.warning("Could not find obfuscated class " + obfClassname);
            return;
        }
        if (sourceClass == null) {
            LOGGER.warning("Could not find source class " + sourceClassname);
            return;
        }
        if (doneList.contains(obfClass))
            return;
        LOGGER.info("Initial translation: "+obfClassname+":"+sourceClassname);
        translation.addEntry(obfClass, sourceClass);
    }

    public Translation<String, String> createTranslation() throws IOException {
        todoList.addAll(translation.getAllObfuscated());
        while (!todoList.isEmpty()) {
            Object obf = todoList.poll();
            Object src = translation.getSource(obf);
            doneList.add(obf);
            if (obf instanceof CtClass && src instanceof CtClass) {
                processClasses((CtClass)obf, (CtClass)src);
            } else if(obf instanceof CtExecutable && src instanceof CtExecutable) {
                processEcecutables((CtExecutable)obf, (CtExecutable)src);
            } else {
                LOGGER.warning("no handler for this class: "+obf.getClass().getCanonicalName());
            }
        }
        updateStringTranslation();
        return strTranslation;
    }

    private void updateStringTranslation() {
        for (Object obf : translation.getAllObfuscated()) {
            Object src = translation.getSource(obf);
            if (obf instanceof CtType && src instanceof CtType) {
                strTranslation.addEntry(((CtType)obf).getQualifiedName(), ((CtType)src).getQualifiedName());
            } else if(obf instanceof CtMethod && src instanceof CtMethod) {
                strTranslation.addEntry(SignatureUtil.getQualifiedName((CtMethod)obf, translation),
                                        ((CtMethod) src).getSimpleName());
            }
        }
    }

    private void addCorrelation(CtType obf, CtType src) {
        LOGGER.info("correlating types: "+obf.getQualifiedName()+" with "+src.getQualifiedName());
        queue_internal(obf, src);
    }

    private void addCorrelation(CtExecutable obf, CtExecutable src) {
        LOGGER.info("correlating executables: "+obf.getSignature()+" with "+src.getSignature());
        queue_internal(obf, src);
    }

    private void queue_internal(Object obf, Object source) {
        if (doneList.contains(obf))
            return;

        LOGGER.info("correlating: "+obf.getClass().getSimpleName()+" with "+source.getClass().getSimpleName());

        translation.addEntry(obf, source);
        todoList.add(obf);
    }

    private void processClasses(CtClass obfClass, CtClass sourceClass) {
        // superclasses
        CtTypeReference sc =  obfClass.getSuperclass();
        if(sc != null && sc.isClass())
        {
            LOGGER.info("Found superclass correlation");
            addCorrelation(sc.getDeclaration(), sourceClass.getSuperclass().getDeclaration());
        }

        // constructors and methods
        Collection<CtExecutableReference<?>> obfMethods = obfClass.getDeclaredExecutables();
        Collection<CtExecutableReference<?>> sourceMethods = sourceClass.getDeclaredExecutables();
        Map<ExecutableSignature, CtExecutable> sourceSignatures = new HashMap<>();
        sourceMethods.forEach(m -> {
            CtExecutable ex = m.getExecutableDeclaration();
            sourceSignatures.put(new ExecutableSignature(ex), ex);
        });

        Map<CtMethod, CtMethod> matchedMethods = new HashMap<>();

        for (CtExecutableReference method : obfMethods) {
            CtExecutable m = method.getExecutableDeclaration();
            ExecutableSignature ms = new ExecutableSignature(m);
            CtExecutable match = sourceSignatures.get(ms);

            if (match != null) {
                if (m instanceof CtMethod && match instanceof CtMethod)
                    matchedMethods.put((CtMethod)m, (CtMethod)match);
                addCorrelation(m, match);
                sourceSignatures.remove(ms);
            }
        }
        matchReferences(matchedMethods);
    }

    private void processEcecutables(CtExecutable obf, CtExecutable src)
    {
        List<CtParameter> obf_params = obf.getParameters();
        List<CtParameter> src_params = src.getParameters();
        for (int i = 0; i < obf_params.size(); i++) {
            CtParameter p = obf_params.get(i);
            if (p.getType().isPrimitive() || p.getType().isArray())
                continue;
            CtParameter pSource = src_params.get(i);
            if (MatchUtil.typesEqual(p.getType(), pSource.getType())) {
                addCorrelation(p.getType().getDeclaration(), pSource.getType().getDeclaration());
            }
        }
    }

    private void matchReferences(Map<CtMethod, CtMethod> matchedMethods)
    {
        //DeepRepresentationComparator comparer = new DeepRepresentationComparator();
        Map<CtTypeReference, Map<CtTypeReference, Integer>> translationProbs = new HashMap<>();
        for(CtMethod obfMethod : matchedMethods.keySet())
        {
            CtMethod sourceMethod = matchedMethods.get(obfMethod);
            Set<CtTypeReference<?>> reftypes = obfMethod.getReferencedTypes();
            Set<CtTypeReference<?>> sourcetypes = sourceMethod.getReferencedTypes();

            for(CtTypeReference r : reftypes)
            {
                if(r.isPrimitive() || r.getPosition() == SourcePosition.NOPOSITION)
                    continue;

                //System.out.println(r.getDirectChildren());
                if(!translationProbs.containsKey(r))
                {
                    translationProbs.put(r, new HashMap<>());
                }
                for(CtTypeReference rs : sourcetypes)
                {
                    if(rs.isPrimitive())
                        continue;
                    int compVal =  -1;
                    if(translationProbs.get(r).containsKey(rs))
                    {
                        translationProbs.get(r).put(rs, translationProbs.get(r).get(rs) + compVal);
                    }
                    else
                    {
                        translationProbs.get(r).put(rs,compVal);
                    }
                    //System.out.println(r.getQualifiedName() + ", " + rs.getSimpleName() + " = " + Integer.toString(compVal));
                    //if(compVal >= 32)
                    //{
                    //   translation.addEntry(r.getQualifiedName(), rs.getSimpleName());
                    //}
                }

            }

        }
        /*for(CtTypeReference r : translationProbs.keySet())
        {
            System.out.println(r.getQualifiedName());
            System.out.println(translationProbs.get(r).toString());
        }*/
    }

}
