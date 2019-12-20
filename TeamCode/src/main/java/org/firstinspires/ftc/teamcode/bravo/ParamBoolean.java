package org.firstinspires.ftc.teamcode.bravo;

public class ParamBoolean extends Param {

	// region constructor

	public ParamBoolean(Config cfg){
		super(cfg,boolean.class);
		initialValue = cfg.isTrue();
		trueString = cfg.trueString();
		falseString = cfg.falseString();
	}

	// endregion

	public Object adjust(Object src, int steps){
		if(steps==0) return src;
		return steps>0;
	}

	@Override
	String getScaledValueString(Object value){ return ((boolean)value) ? trueString : falseString; }

	protected String getRangeString(){
		return falseString+" / "+trueString;
	}

	@Override
	public Object getInitialValue() {
		return initialValue;
	}

	@Override
	String getRawValueString(Object value){
		return ((boolean)value) ? "true" : "false";
	}
	@Override
	Object parseRawValueString(String s){
		return s == "true";
	}


	// region used by boolean
	private boolean initialValue;
	private String trueString;
	private String falseString;

	// endregion


}
