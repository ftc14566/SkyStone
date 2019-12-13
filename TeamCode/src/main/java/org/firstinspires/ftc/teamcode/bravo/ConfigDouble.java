package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ConfigDouble implements ConfigParam {

	public ConfigDouble(String name, double initialValue, double min, double max, double step ){
		_name = name;
		_value = initialValue;
		_min = min;
		_max = max;
		_step = step;
	}

	public ConfigDouble(DoubleAnnotation da){
		_name = da.label();
		_value = da.initial();
		_min = da.min();
		_max = da.max();
		_step = da.step();
	}

	public String _name;
	public double _min;
	public double _max;
	public double _step;
	public double _value;

	@Override
	public void inc() {
		_value = Math.min(_value +_step,_max);
	}

	@Override
	public void dec() {
		_value = Math.max(_value -_step,_min);
	}

	@Override
	public Object getValue() {
		return _value;
	}

	@Override
	public Class<?> getValueClass() {
		return double.class;
	}

	@Override
	public void show(Telemetry telemetry) {
		telemetry.addData(_name, "" + _value); // !!!
	}

	@Override
	public ConfigParam Clone() {
		return new ConfigDouble(_name,_value,_min,_max,_step);
	}

	final static ConfigDouble Default = new ConfigDouble("double", 0.0, 0.0, 1.0, 0.1);


}
