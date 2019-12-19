package org.firstinspires.ftc.teamcode.bravo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MethodSignature {

	// region constructors

	public MethodSignature(Method method){
		this.method = method;
		params = Param.scanMethodParameters(method);
	}

	// endregion

	public void execute(Object host, Object[] paramValues){
		try{
			method.invoke(host,paramValues);
		} catch(IllegalAccessException ex){

		} catch(InvocationTargetException ex){

		}
	}

	public String getParamValueSummary(Object[] paramValues){
		String s = method.getName()+"(";
		for(int i = 0; i< params.length; ++i){
			if(i!=0) s+=",";
			s += params[i].getValueWithUnits(paramValues[i]);
		}
		return s+")";
	}

	public Object[] getInitialParamValues(){
		Object[] initialValues = new Object[params.length];
		for(int i=0;i<params.length;++i)
			initialValues[i] = params[i].getInitialValue();
		return initialValues;
	}

	public static String formatMethodSignature(Method method){
		StringBuilder buf = new StringBuilder();
		buf.append(method.getName());
		buf.append('(');
		Class<?>[] parameterTypes = method.getParameterTypes();
		for(int index=0;index<parameterTypes.length;++index){
			if(index!=0) buf.append(',');
			buf.append(parameterTypes[index].getName());
		}
		buf.append(')');
		return buf.toString();
	}

	Param[] params; // accessed by interactive parameter list
	private Method method;

}
