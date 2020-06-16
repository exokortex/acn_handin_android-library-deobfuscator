package org.reversing.ald.codegen;

import org.reversing.ald.data.Translation;
import org.reversing.ald.util.SignatureUtil;
import spoon.processing.AbstractProcessor;
import spoon.refactoring.Refactoring;
import spoon.reflect.declaration.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class PackageProcessor extends AbstractProcessor<CtPackage> {

    Map<CtPackage, String> renamings = new HashMap<>();

    public void addPackageTranslation(CtPackage pack, String newName) {
        renamings.put(pack, newName);
    }

    @Override
    public void process(CtPackage ctPackage) {
        String newName = renamings.get(ctPackage);
        if (newName != null)
            ctPackage.setSimpleName(newName);
    }
}