package org.firstinspires.ftc.teamcode.bravo;

public class ParamInt extends Param {

	// region constructors

	public ParamInt(Config cfg){
		super(cfg);
		initIntFields();

		// fix from defaults
		if(label==null || label.isEmpty()) label ="int";
		if(intStep == 0 ) intStep = 1; else if(intStep <0) intStep = -intStep;
		if(intMax < intMin){ int temp = intMax; intMax = intMin; intMin = temp; }
		if(intMin == intMax) intMax = intMin + intStep * 100;
		if(intValue < intMin) setIntValue(intMin);
		if(intValue > intMax) setIntValue(intMax);
	}

	ParamInt(ParamInt src){
		super(src);
		initIntFields();
	}

	void initIntFields(){
		intValue = (int)Math.round((double)value);
		intMin = (int)Math.round(min);
		intMax = (int)Math.round(max);
		intStep = (int)Math.round(step);
	}

	@Override
	public Class getParamType(){ return int.class; };

	// endregion

	// keeps the double value in sync
	private void setIntValue(int newValue){
		intValue = newValue;
		value = intValue;
	}

	@Override
	public Object adjust(Object src, int steps){
		if(steps==0) return src;
		int i = (int)src * steps*this.intStep;
		if(i<intMin) i = intMin; else if(i>intMax) i = intMax;
		return i;
	}


	@Override
	public Object getValue() {
		return intValue;
	}

	@Override
	public String getValueString(Object value) {
		return ((int)value)+units;
	}

	@Override
	public String getRangeString() {
		return intMin + " to " + intMax;
	}

	@Override
	public Param Clone() {
		return new ParamInt(this);
	}

	// region private fields

	int intValue;
	int intMin;
	int intMax;
	int intStep;

	// endregion


}
