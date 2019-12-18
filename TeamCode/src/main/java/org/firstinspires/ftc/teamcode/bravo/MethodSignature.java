package org.firstinspires.ftc.teamcode.bravo;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MethodSignature {

	// region constructors

	public MethodSignature(MethodSignature src){
		name = src.name;
		params = new Param[src.params.length];
		for(int i = 0; i< params.length; ++i)
			params[i] = params[i].Clone();
	}

	public MethodSignature(Method method){
		name = method.getName();
		initParameters(method);
	}

	// endregion

	// region building parameters

	void initParameters(Method method){

		// init Parameters from Annotations on method
		Class<?>[] parameterTypes = method.getParameterTypes();
		Annotation[][] annotations = method.getParameterAnnotations();

		if (annotations.length != parameterTypes.length)
			throw new IllegalStateException("annotation length does not match params length");

		params = new Param[parameterTypes.length];
		for (int i = 0; i < params.length; ++i)
			params[i] = ConstructParamConfig(parameterTypes[i], annotations[i], i);
	}

	Config FindConfigAnnotation(Annotation[] annotations){
		for(int i=0;i<annotations.length;++i){
			Annotation a = annotations[i];
			Class<?> aType = a.annotationType();
			if(aType == Config.class)
				return (Config)a;
		}
		return null;
	}

	Param ConstructParamConfig(Class<?> paramClass, Annotation[] annotations, int index){
		Config cfg = FindConfigAnnotation(annotations);
		if(paramClass==double.class) return new ParamDouble(cfg);
		if(paramClass==boolean.class) return new ParamBoolean(cfg);
		if(paramClass==int.class) return new ParamInt(cfg);
		throw new IllegalStateException("Cannot auto-configure parameter of type:"+paramClass.getName()+" "+getName()+" "+index);
	}

	// endregion

	public String getName(){
		return name;
	}

	public void execute(Object host){
		try{

			Object[] values = new Object[params.length];
			Class[] paramTypes = new Class[params.length];

			for(int i = 0; i< params.length; ++i){
				values[i] = params[i].getValue();
				paramTypes[i] = values[i].getClass();
			}

			Method method = host.getClass().getMethod(getName(), paramTypes);
			method.invoke(host,values);

		} catch(NoSuchMethodException ex){
		} catch(IllegalAccessException ex){

		} catch(InvocationTargetException ex){

		}

	}

	public String getVerbose(){
		String s = getName()+"(";
		for(int i = 0; i< params.length; ++i){
			if(i!=0) s+=",";
			s += params[i].getValueString();
		}
		return s+")";
	}

	public MethodSignature Clone(){
		return new MethodSignature(this);
	}

	Param[] params; // accessed by interactive parameter list
	private String name;

}
