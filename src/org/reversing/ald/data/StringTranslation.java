package org.reversing.ald.data;

public class StringTranslation extends Translation<String, String>
{
    public String translate(String obfuscatedName) {
        String res = obfToSource.get(obfuscatedName);
        return res == null ? obfuscatedName : res;
    }
}
