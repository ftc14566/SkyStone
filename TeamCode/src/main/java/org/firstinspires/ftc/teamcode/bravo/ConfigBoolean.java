package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ConfigBoolean implements ConfigParam {

	String _name;
	boolean _value;

	public ConfigBoolean(String name,boolean initialValue){
		_name = name;
		_value = initialValue;
	}

	public ConfigBoolean(BooleanAnnotation ba){
		_name=ba.label();
		_value = ba.initial();
	}

	@Override
	public void inc() {
		_value = true;
	}

	@Override
	public void dec() {
		_value = false;
	}

	@Override
	public Object getValue() {
		return _value;
	}

	@Override
	public Class<?> getValueClass() { return Boolean.class; }


	@Override
	public void show(Telemetry telemetry) {

	}

	@Override
	public ConfigParam Clone() {
		return new ConfigBoolean(_name,_value);
	}

	public static final ConfigBoolean Default = new ConfigBoolean("bool", false );

}
