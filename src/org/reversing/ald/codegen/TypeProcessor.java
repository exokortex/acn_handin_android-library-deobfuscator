package org.reversing.ald.codegen;

import org.reversing.ald.data.Translation;
import spoon.processing.AbstractProcessor;
import spoon.refactoring.Refactoring;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtTypeReference;

import java.util.LinkedList;

public class TypeProcessor extends AbstractProcessor<CtClass<?>> {

    private Translation<String, String> translation;
    private PackageProcessor packageProcessor;

    public TypeProcessor(Translation<String, String> translation, PackageProcessor packageProcessor) {
        this.translation = translation;
        this.packageProcessor = packageProcessor;
    }

    private void renamePackage(CtPackage pack, String[] nameParts) {
        LinkedList<CtPackage> packages = new LinkedList<>();
        packages.add(pack);
        while (pack.getParent() instanceof CtPackage) {
            pack = (CtPackage) pack.getParent();
            packages.addFirst(pack);
        }
        for (int i = 0; i < nameParts.length - 1; i++) {
            packageProcessor.addPackageTranslation(packages.get(i), nameParts[i]);
        }
    }

    @Override
    public void process(CtClass<?> ctClass)
    {
        for (CtTypeReference t : ctClass.getReferencedTypes()) {
            CtType p = t.getDeclaration();

            if(p==null || p.isClass())
                continue;
            String newTypeName = translation.getSource(p.getQualifiedName());
            if (newTypeName != null) {
                String[] nameParts = newTypeName.split("\\.");
                Refactoring.changeTypeName(p, nameParts[nameParts.length - 1]);
            }
        }

        String newClassName = translation.getSource(ctClass.getQualifiedName());
        if (newClassName != null) {
            String[] nameParts = newClassName.split("\\.");
            renamePackage(ctClass.getPackage(), nameParts);
            Refactoring.changeTypeName(ctClass, nameParts[nameParts.length - 1]);
        }
    }
}