package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// Base class for different types of parameters
// Matches Config annotation directly.
// Pulling all derived requirements into the base class simplifies serialization/deserialization significantly

public abstract class Param {

	// from annotation
	public Param(Config cfg){
		if(cfg==null)return;
		label = cfg.label();
		units = cfg.units();
		value = cfg.value();
		min = cfg.min();
		max = cfg.max();
		step = cfg.step();
		displayScale = cfg.displayScale();
		isTrue = cfg.isTrue();
		trueString = cfg.trueString();
		falseString = cfg.falseString();
	}

	// copy constructor
	public Param(Param p){
		label = p.label;
		units = p.units;
		value = p.value;
		min = p.min;
		max = p.max;
		step = p.step;
		displayScale = p.displayScale;
		isTrue = p.isTrue;
		trueString = p.trueString;
		falseString = p.falseString;
	}

	abstract Object getValue();
	abstract String getValueString();
	abstract String getRangeString();
	abstract Param Clone();

	abstract public Object adjust(Object src, int steps);


	public void addParamToTelemetry(Telemetry telemetry, boolean selected){
		telemetry.addData(label,selected ? getValueAndRange() : getValueString());
	}

	String getValueAndRange(){ return "["+getValueString() + "] (" + getRangeString() + ")"; }

	// ! pushing all derived class functionality down into base class to simplify:
	//   initialization, serialization, deserialization
	// Besides, there are a very limited number of parameter types.

	// region used by all
	protected String label;
	protected String units;
	// endregion

	// region used by numerics
	protected double min;
	protected double max;
	protected double step;
	protected double displayScale;
	// endregion

	// region used by boolean
	protected boolean isTrue;
	protected String trueString;
	protected String falseString;

	public Object value;

}
