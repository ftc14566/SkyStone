package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

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
		if(paramClass==String.class) return new ParamString(cfg);
		// try generic value
		ParamValue paramValue = ParamValue.getValuesFor(paramClass);
		if(paramValue != null) return paramValue;
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
		if(label==null||label.isEmpty()) label = this.getParamTypeString();
	}

	public Param(Class parameterType){
		this.parameterType = parameterType;
		this.label = this.getParamTypeString();
		this.units = "";
	}

	// endregion

	public String getScaledValueWithUnits(Object value){ return getScaledValueString(value)+units; }
	abstract String getScaledValueString(Object value);
	abstract public Object adjust(Object value, int steps);

	abstract Object getInitialValue();
	abstract protected String getRangeString();
	Class getParamType(){ return parameterType; }
	String getParamTypeString(){ return Linq.getShortName(getParamType()); }
	abstract String getRawValueString(Object value);
	abstract Object parseRawValueString(String s);

	void addParamToTelemetry(Telemetry telemetry, Object value, boolean selected){
		String s = getScaledValueWithUnits(value);
		if(selected) s = "["+s+"] (" + getRangeString() + ")";
		telemetry.addData(label,s);
	}

	// region private fields
	private String units;
	private String label;
	private Class parameterType;
	// endregion

}
