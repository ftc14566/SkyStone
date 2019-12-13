package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.teamcode.AutoBot;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodSignature {

	public MethodSignature(Method method, ConfigParam[] params){
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
		if(annotations.length != parameterTypes.length) throw new IllegalStateException("annotation length does not match params length");

		_params = new ConfigParam[parameterTypes.length];
		for(int i=0;i<_params.length;++i){
			Annotation annotation = FindFirstParamConfigAnnotation(annotations[i]);
			_params[i] = ConstructParamConfig(parameterTypes[i],annotation);
		}
	}

	Annotation FindFirstParamConfigAnnotation(Annotation[] annotations){
		for(int i=0;i<annotations.length;++i){
			Annotation a = annotations[i];
			Class<?> aType = a.annotationType();
			if(aType == DoubleAnnotation.class
					|| aType == BooleanAnnotation.class)
				return a;
		}
		return null;
	}

	ConfigParam ConstructParamConfig(Class<?> paramClass,Annotation annotation){
		if(annotation == null){
			// This will help the program to not crash but won't be very effective in programming
			if(paramClass==double.class) return ConfigDouble.Default;
			if(paramClass==int.class) return ConfigInt.Default;
			if(paramClass==boolean.class) return ConfigBoolean.Default;
			throw new IllegalStateException("Cannot auto-configure parameter of type:"+paramClass.getName());
		}

		Class<? extends Annotation> annotationType = annotation.annotationType();
		if(annotationType==DoubleAnnotation.class) return new ConfigDouble((DoubleAnnotation)annotation);
		if(annotationType==BooleanAnnotation.class) return new ConfigBoolean((BooleanAnnotation)annotation );
		if(annotationType==IntAnnotation.class) return new ConfigInt((IntAnnotation)annotation );

		throw new IllegalStateException("Unexpected annotation type.");
	}


	public void Execute(Object host){
		try{

			Object[] values = new Object[_params.length+1];

			values[0]=host;
			for(int i=0;i<_params.length;++i)
				values[i + 1] = _params[i].getValue();

			_method.invoke(values);

		} catch(IllegalAccessException ex){

		} catch(InvocationTargetException ex){

		}

	}

	public MethodSignature Clone(){
		// !!! clean up
		ConfigParam[] params = new ConfigParam[_params.length];
		for(int i=0;i<_params.length;++i)
			params[i] = _params[i].Clone();
		return new MethodSignature(_method,params);
	}

	ConfigParam[] _params;
	Method _method;

}
