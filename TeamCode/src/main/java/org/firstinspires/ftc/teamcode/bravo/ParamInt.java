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
		intValue = (int)Math.round(value);
		intMin = (int)Math.round(min);
		intMax = (int)Math.round(max);
		intStep = (int)Math.round(step);
	}

	// endregion

	// keeps the double value in sync
	private void setIntValue(int newValue){
		intValue = newValue;
		value = intValue;
	}

	@Override
	public void inc() { setIntValue( Math.min(intMax, intValue + intStep) ); }

	@Override
	public void dec() { setIntValue( Math.max(intMin, intValue - intStep) ); }

	@Override
	public Object getValue() {
		return intValue;
	}

	@Override
	public String getValueString() {
		return intValue +units;
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
