package org.firstinspires.ftc.teamcode.bravo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;


public class MethodSignature {

	public MethodSignature(Method method){
		// part 1
		this.method = method;
		// part 2
		this.name = method.getName();
		this.params = Param.scanMethodParameters(method);
		// part 3
		methodString = formatMethodSignature(method);
	}

	public Object[] getInitialParamValues(){
		Object[] initialValues = new Object[params.length];
		for(int i=0;i<params.length;++i)
			initialValues[i] = params[i].getInitialValue();
		return initialValues;
	}

	public String getParamValueSummary(Object[] paramValues){
		String s = name+"(";
		for(int i = 0; i< params.length; ++i){
			if(i!=0) s+=",";
			s += params[i].getValueWithUnits(paramValues[i]);
		}
		return s+")";
	}

	private static String formatMethodSignature(Method method){
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

	public final Method method;
	public final String methodString;

	private final String name;
	public final Param[] params;

}


