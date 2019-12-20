package org.firstinspires.ftc.teamcode.bravo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MethodSignature {

	// region constructor

	public MethodSignature(Method method){
		// part 1
		this.method = method;
		// part 2
		this.name = method.getName();
		this.params = Param.scanMethodParameters(method);
		// part 3
		methodString = formatMethodSignature(method);
	}

	// endregion

	public String getParamValueSummary(Object[] paramValues){
		String s = name+"(";
		for(int i = 0; i< params.length; ++i){
			if(i!=0) s+=",";
			s += params[i].getScaledValueWithUnits(paramValues[i]);
		}
		return s+")";
	}

	public MethodBinding createInitialBinding(){
		return new MethodBinding(this, getInitialParamValues() );
	}

	public void invoke(Object host, Object[] paramValues){
		try {
			method.invoke(host, paramValues);
		}catch(InvocationTargetException ex){
		} catch(IllegalAccessException ex) {
		}
	}

	// region private methods

	private Object[] getInitialParamValues(){
		Object[] initialValues = new Object[params.length];
		for(int i=0;i<params.length;++i)
			initialValues[i] = params[i].getInitialValue();
		return initialValues;
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

	// endregion

	public final String methodString;
	public final Param[] params;

	private final String name;
	private final Method method;

}


