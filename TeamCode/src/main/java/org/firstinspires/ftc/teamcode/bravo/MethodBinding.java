package org.firstinspires.ftc.teamcode.bravo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodBinding {

    public Method method;
    public Object[] paramValues;

    public void executeOn(Object host){
        try {
            method.invoke(host, paramValues);
        }catch(InvocationTargetException ex){
        } catch(IllegalAccessException ex) {
        }
    }

}
