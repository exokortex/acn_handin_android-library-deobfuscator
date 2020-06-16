package org.reversing.ald.codegen;

import org.reversing.ald.data.Translation;
import org.reversing.ald.util.SignatureUtil;
import spoon.processing.AbstractProcessor;
import spoon.refactoring.Refactoring;
import spoon.reflect.declaration.CtMethod;

public class MethodProcessor extends AbstractProcessor<CtMethod<?>> {

    private Translation<String, String> translation;

    public MethodProcessor(Translation<String, String> translation) {
        this.translation = translation;
    }

    @Override
    public void process(CtMethod<?> ctMethod) {
        String newMethodName = translation.getSource(SignatureUtil.getQualifiedName(ctMethod, translation));
        if (newMethodName != null) {
            Refactoring.changeMethodName(ctMethod, newMethodName);
        }
    }
}