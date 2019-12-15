package org.firstinspires.ftc.teamcode.bravo;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MethodSignature {

	// region constructors

	public MethodSignature(Method method, Param[] params){
		_method = method;
		_params = params;
	}

	public MethodSignature(Method method){
		_method = method;
		initParameters();
	}

	void initParameters(){

		// init Parameters from Annotations on method
		Class<?>[] parameterTypes = _method.getParameterTypes();
		Annotation[][] annotations = _method.getParameterAnnotations();

		if (annotations.length != parameterTypes.length)
			throw new IllegalStateException("annotation length does not match params length");

		_params = new Param[parameterTypes.length];
		for (int i = 0; i < _params.length; ++i) {

			Annotation annotation = FindFirstParamConfigAnnotation(annotations[i]);
			_params[i] = ConstructParamConfig(parameterTypes[i], (Config)annotation,i);
		}
	}

	// endregion

	Annotation FindFirstParamConfigAnnotation(Annotation[] annotations){
		for(int i=0;i<annotations.length;++i){
			Annotation a = annotations[i];
			Class<?> aType = a.annotationType();
			if(aType == Config.class)
				return a;
		}
		return null;
	}

	Param ConstructParamConfig(Class<?> paramClass, Config cfg, int index){
		String label = "const:"+paramClass.getName();
		if(paramClass==double.class) return new ParamDouble(cfg);
		if(paramClass==boolean.class) return new ParamBoolean(cfg);
		if(paramClass==int.class) return new ParamInt(cfg);
		throw new IllegalStateException("Cannot auto-configure parameter of type:"+paramClass.getName()+" "+_method.getName()+" "+index);
	}


	public void Execute(Object host){
		try{

			Object[] values = new Object[_params.length];

			for(int i=0;i<_params.length;++i)
				values[i] = _params[i].getValue();

			_method.invoke(host,values);

		} catch(IllegalAccessException ex){

		} catch(InvocationTargetException ex){

		}

	}

	public String getVerbose(){
		String s = _method.getName()+"(";
		String separator="";
		for(int i=0;i<_params.length;++i){
			s += separator + _params[i].getValueString();
			separator=",";
		}
		return s+")";
	}

	public MethodSignature Clone(){
		// !!! clean up
		Param[] params = new Param[_params.length];
		for(int i=0;i<_params.length;++i)
			params[i] = _params[i].Clone();
		return new MethodSignature(_method,params);
	}

	Param[] _params;
	Method _method;

}
