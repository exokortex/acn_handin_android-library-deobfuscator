package org.reversing.ald.util;

import org.reversing.ald.data.Translation;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;

import java.util.List;

public class SignatureUtil
{
    public static String getSignature(CtMethod method, Translation translation)
    {
        String sig = "";//method.getSignature();
        List<CtParameter> methodParams = method.getParameters();
        for(CtParameter p : methodParams)
        {
            if(translation.getObfuscatedName(p.getSimpleName()) != null)
            {
                sig += translation.getObfuscatedName(p.getSimpleName()) + ",";
            }
            else
            {
                sig += p.getSimpleName() + ",";
            }
        }
        if(sig.length() > 0)
        {
            sig = sig.substring(0, sig.length()-2);
        }
        sig = "(" + sig + ")";
        sig = sig.substring(sig.indexOf("("));
        return sig + method.getType() + ";";
    }

    public static String getQualifiedName(CtMethod method, Translation translation)
    {
        return method.getDeclaringType().getQualifiedName() + "." + method.getSimpleName() + getSignature(method, translation);
    }
}
