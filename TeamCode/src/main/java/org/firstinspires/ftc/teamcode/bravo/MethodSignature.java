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
		methodString = formatMethodSignature();
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
			Throwable cause = ex.getCause();
			String msg = cause.getMessage() + " - " + cause.toString();
			System.out.println(msg);
			throw new IllegalStateException(msg);

		} catch(IllegalAccessException ex) {
			System.out.println(ex.toString());
			throw new IllegalStateException("2:"+ex.toString());
		}
	}

	// region private methods

	private Object[] getInitialParamValues(){
		Object[] initialValues = new Object[params.length];
		for(int i=0;i<params.length;++i)
			initialValues[i] = params[i].getInitialValue();
		return initialValues;
	}

	private String formatMethodSignature(){
		StringBuilder buf = new StringBuilder();
		buf.append(method.getName());
		buf.append('(');
//		Class<?>[] parameterTypes = method.getParameterTypes();
		for(int index=0;index<params.length;++index){
			if(index!=0) buf.append(',');
			buf.append(params[index].getParamTypeString());
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


