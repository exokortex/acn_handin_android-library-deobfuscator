package org.reversing.ald.matching;

import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.reference.CtTypeReference;

import java.util.List;
import java.util.Objects;

/**
 * Signature for Methods and constructors
 */
public class ExecutableSignature
{
    private CtExecutable executable;
    CtTypeReference returnType;
    private List<CtParameter> params;

    public ExecutableSignature(CtExecutable executable) {
        this.executable = executable;
        returnType = executable.getType();
        params = executable.getParameters();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExecutableSignature that = (ExecutableSignature) o;
        if (!MatchUtil.typesEqual(returnType, that.returnType)) return false;
        if (params.size() != that.params.size()) return false;
        for (int i = 0; i < params.size(); i++) {
            if (!MatchUtil.typesEqual(params.get(i).getType(), that.params.get(i).getType()))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        // TODO improve
        return 0;
    }
}
