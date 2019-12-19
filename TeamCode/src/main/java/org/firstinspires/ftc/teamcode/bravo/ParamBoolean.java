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
	public Class getParamType(){ return boolean.class; };


	public Object adjust(Object src, int steps){
		if(steps==0) return src;
		return (steps<0) ? false : true;
	}

	public String getRangeString(){
		return falseString+" / "+trueString;
	}

	@Override
	public Object getValue() {
		return isTrue;
	}

	@Override
	public String getValueString(Object value){
		return ((boolean)value) ? trueString : falseString;
	}

	@Override
	public Param Clone() { return new ParamBoolean(this); }


}
