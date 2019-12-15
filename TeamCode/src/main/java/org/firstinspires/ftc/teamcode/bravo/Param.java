package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/* Base class for different types of parameters */
public abstract class Param {

	abstract void inc();
	abstract void dec();

	abstract Object getValue();
	abstract String getValueString();
	abstract String getRangeString();
	abstract Param Clone();

	public void addParamToTelemetry(Telemetry telemetry, boolean selected){
		telemetry.addData(_label,selected ? getValueAndRange() : getValueString());
	}

	String getValueAndRange(){ return "["+getValueString() + "] (" + getRangeString() + ")"; }

	protected String _label;

}
