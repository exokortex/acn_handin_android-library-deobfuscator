package org.reversing.ald;

import com.beust.jcommander.JCommander;
import org.reversing.ald.codegen.MethodProcessor;
import org.reversing.ald.codegen.PackageProcessor;
import org.reversing.ald.codegen.TranslationPrinter;
import org.reversing.ald.codegen.TypeProcessor;
import org.reversing.ald.data.Codebase;
import org.reversing.ald.data.Translation;
import org.reversing.ald.matching.CodebaseMatcher;
import spoon.Launcher;
import spoon.SpoonAPI;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        Args arg = new Args();
        JCommander.newBuilder()
                .addObject(arg)
                .build()
                .parse(args);

        LOGGER.info("appRoot: " + arg.appRoot);
        LOGGER.info("libPath: " + arg.libPath);
        LOGGER.info("outPath: " + arg.outPath);
        LOGGER.info("config: " + arg.config);
        LOGGER.info("translationOut: " + arg.translationOut);

        Path configPath = Paths.get(arg.config);
        Path translationOutPath = Paths.get(arg.translationOut);

        final Translation<String, String> initialTranslation = new Translation<>();

        try (Stream<String> lines = Files.lines(configPath)) {
            lines.forEach(s -> {
                if (s.startsWith("#"))
                    return;
                String[] parts = s.split(" ");
                initialTranslation.addEntry(parts[0], parts[1]);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("Initial translation:");
        LOGGER.info(TranslationPrinter.translationToString(initialTranslation));

        LOGGER.info("Loading code...");
        Codebase obfuscated = new Codebase();
        Codebase library = new Codebase();
        obfuscated.load(arg.appRoot);
        library.load(arg.libPath);

        LOGGER.info("Creating translation...");
        CodebaseMatcher nm = new CodebaseMatcher(obfuscated, library, initialTranslation);
        Translation<String, String> finalTranslation = nm.createTranslation();
        LOGGER.info("Final translation:");
        String finalTranslationStr = TranslationPrinter.translationToString(finalTranslation);
        LOGGER.info(finalTranslationStr);
        Files.write(translationOutPath, finalTranslationStr.getBytes());

        SpoonAPI launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource(arg.appRoot);
        launcher.setSourceOutputDirectory(arg.outPath);
        launcher.addProcessor(new MethodProcessor(finalTranslation));
        PackageProcessor pp = new PackageProcessor();
        launcher.addProcessor(new TypeProcessor(finalTranslation, pp));
        LOGGER.info("Writing result...");
        launcher.run();
    }

}
