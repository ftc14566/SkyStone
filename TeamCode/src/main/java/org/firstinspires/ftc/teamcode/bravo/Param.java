package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public abstract class Param {

	// region factory

	static public Param[] scanMethodParameters(Method method){

		// init Parameters from Annotations on method
		Class<?>[] parameterTypes = method.getParameterTypes();
		Annotation[][] annotations = method.getParameterAnnotations();

		if (annotations.length != parameterTypes.length)
			throw new IllegalStateException("annotation length does not match params length");

		Param[] params = new Param[parameterTypes.length];
		for (int i = 0; i < params.length; ++i)
			params[i] = Param.ConstructParamConfig(parameterTypes[i], annotations[i]);
		return params;
	}

	static private Param ConstructParamConfig(Class<?> paramClass, Annotation[] annotations){
		Config cfg = FindConfigAnnotation(annotations);
		if(paramClass==double.class) return new ParamDouble(cfg);
		if(paramClass==boolean.class) return new ParamBoolean(cfg);
		if(paramClass==int.class) return new ParamInt(cfg);
		throw new IllegalStateException("Cannot auto-configure parameter of type:"+paramClass.getName());
	}

	static private Config FindConfigAnnotation(Annotation[] annotations){
		for(int i=0;i<annotations.length;++i){
			Annotation a = annotations[i];
			Class<?> aType = a.annotationType();
			if(aType == Config.class)
				return (Config)a;
		}
		return null;
	}

	// endregion

	// region constructors

	public Param(Config cfg,Class parameterType){
		this.parameterType = parameterType;
		if(cfg!=null){
			label = cfg.label();
			units = cfg.units();
		}
		if(units==null) units="";
		if(label==null||label.isEmpty()) label = this.parameterType.getName();

	}

	// endregion

	public String getValueWithUnits(Object value){ return getValueString(value)+units; }
	abstract String getValueString(Object value);
	abstract public Object adjust(Object value, int steps);

	abstract Object getInitialValue();
	abstract protected String getRangeString();
	Class getParamType(){ return parameterType; }

	void addParamToTelemetry(Telemetry telemetry, Object value, boolean selected){
		String s = getValueWithUnits(value);
		if(selected) s = "["+s+"] (" + getRangeString() + ")";
		telemetry.addData(label,s);
	}

	// region private fields
	private String units;
	private String label;
	private Class parameterType;
	// endregion

}
