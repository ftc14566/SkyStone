package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ParamBoolean extends Param {

	// region constructor

	public ParamBoolean(Config cfg){
		super(cfg);

		// fix
		if(label==null|| label.isEmpty()) label = "boolean";
	}

	ParamBoolean(ParamBoolean a){ super(a); }

	// endregion

	@Override
	public void inc() {
		isTrue = true;
	}

	@Override
	public void dec() {
		isTrue = false;
	}

	public String getRangeString(){
		return falseString+" / "+trueString;
	}

	@Override
	public Object getValue() {
		return isTrue;
	}

	@Override
	public String getValueString(){
		return isTrue ? trueString : falseString;
	}

	@Override
	public Param Clone() { return new ParamBoolean(this); }


}
