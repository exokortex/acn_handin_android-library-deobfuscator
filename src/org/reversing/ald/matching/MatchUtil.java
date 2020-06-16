package org.reversing.ald.matching;

import spoon.reflect.reference.CtTypeReference;

import java.util.Objects;

public class MatchUtil {
    public static boolean typesEqual(CtTypeReference a, CtTypeReference b) {
        if (a.isPrimitive() != b.isPrimitive()) return false;
        if (a.isPrimitive()) return Objects.equals(a, b);
        // if both parameters are not of primitive type they are treated as if they were equal
        return true;
    }
}
