package org.firstinspires.ftc.teamcode.bravo;

import java.lang.reflect.InvocationTargetException;

public class MethodBinding {

    // region constructors

    public MethodBinding(MethodSignature signature, Object[] paramValues){
        this.signature = signature;
        this.paramValues = paramValues;
    }

    // endregion

    public MethodSignature getSignature(){
        return signature;
    }
    public String getDisplay(){ return signature.getParamValueSummary(paramValues); }
    public Object[] getParamValues(){ return paramValues.clone(); }

    public void invoke(Object host){
        signature.invoke(host, paramValues);
    }

    // region private fields

    private MethodSignature signature;
    private Object[] paramValues;

    // endregion

}
