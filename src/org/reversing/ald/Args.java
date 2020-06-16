package org.reversing.ald;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Args {
    @Parameter(description = "root of decompiled application")
    protected String appRoot;

    @Parameter(names = { "-l", "--lib" }, description = "root of library sourcecode")
    protected String libPath;

    @Parameter(names = { "-o", "--out" }, description = "output directory")
    protected String outPath;

    @Parameter(names = { "-c", "--config" }, description = "file containing configuration and the initial translation")
    protected String config;

    @Parameter(names = { "-t", "--translationOut" }, description = "file to write the translation map to")
    protected String translationOut;
}

