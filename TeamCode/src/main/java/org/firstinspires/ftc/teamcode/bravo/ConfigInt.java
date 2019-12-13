package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ConfigInt implements ConfigParam {

	public String _name;
	int _value;
	int _min;
	int _max;
	int _step;

	public ConfigInt(String name,int initialValue,int min,int max, int step){
		_name = name;
		_value = initialValue;
		_min = min;
		_max = max;
	}

	@Override
	public void inc() {
		_value = Math.min(_max,_value+_step);
	}

	@Override
	public void dec() {
		_value = Math.max(_min,_value-_step);
	}

	@Override
	public Object getValue() {
		return 0;
	}

	@Override
	public Class<?> getValueClass() {
		return int.class;
	}

	@Override
	public void show(Telemetry telemetry) {

	}

	@Override
	public ConfigParam Clone() {

		return new ConfigInt(_name,_value,_min,_max,_step);
	}

}
