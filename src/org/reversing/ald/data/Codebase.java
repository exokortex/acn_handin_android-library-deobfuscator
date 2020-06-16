package org.reversing.ald.data;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class Codebase {
    private static final Logger LOGGER = Logger.getLogger(Codebase.class.getName());

    private Launcher launcher;
    private Factory spoon;

    public void load(String path) throws IOException {
        launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource(path);
        launcher.buildModel();

        spoon = launcher.getFactory();
    }

    public void save(String path) {
    }

    public CtClass getClass(String name) {
        return spoon.Class().get(name);
    }

    public List<CtType<?>> getClasses() {
        return spoon.Class().getAll();
    }
}
