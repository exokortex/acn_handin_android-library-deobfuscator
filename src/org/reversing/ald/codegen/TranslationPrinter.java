package org.reversing.ald.codegen;

import org.reversing.ald.data.Translation;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TranslationPrinter {
    public static void writeTranslation(Translation<String, String> tr, Writer out) throws IOException {
        out.write("# entries: ");
        out.write("" + tr.getAllObfuscated().size());
        out.write("\n");
        List<String> nameList = new ArrayList<>(tr.getAllObfuscated());
        Collections.sort(nameList);
        for (String obf : nameList) {
            // extra newline for classes
            if (!obf.contains("("))
                out.write("\n");
            out.write(obf);
            out.write(" ");
            out.write(tr.getSource(obf));
            out.write("\n");
        }
    }

    public static String translationToString(Translation tr) {
        StringWriter sw = new StringWriter();
        try {
            writeTranslation(tr, sw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }
}
