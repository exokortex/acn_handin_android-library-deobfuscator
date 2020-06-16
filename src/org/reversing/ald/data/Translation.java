package org.reversing.ald.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents a translation table, like a phonebook
 */
public class Translation<L, R> {
    private static final Logger LOGGER = Logger.getLogger(Translation.class.getName());

    protected Map<R, L> sourceToObf = new HashMap<>();

    protected Map<L, R> obfToSource = new HashMap<>();

    public void addEntry(L obfuscated, R source) {
        if (obfToSource.containsKey(obfuscated)) {
            LOGGER.warning("obfuscated entry already existed for " + obfuscated);
        }
        if (sourceToObf.containsKey(source)) {
            LOGGER.warning("source entry already existed for " + source);
        }
        sourceToObf.put(source, obfuscated);
        obfToSource.put(obfuscated, source);
    }

    public R getSource(L obfuscatedName) {
        return obfToSource.get(obfuscatedName);
    }

    public L getObfuscatedName(R sourceName) {
        return sourceToObf.get(sourceName);
    }

    public Set<R> getSourceNames() {
        return sourceToObf.keySet();
    }

    public Set<L> getAllObfuscated() {
        return obfToSource.keySet();
    }

    public void clear() {
        sourceToObf.clear();
        obfToSource.clear();
    }

    @Override
    public Translation<L, R> clone() {
        Translation<L, R> clone = new Translation<>();
        clone.obfToSource.putAll(obfToSource);
        clone.sourceToObf.putAll(sourceToObf);
        return clone;
    }
}
