package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ParamInt implements Param {

	// region constructors

	public ParamInt(Config cfg){

		//public static final ParamInt Default = new ParamInt("int", 0, 0, 255, 1 );
		if(cfg!=null) {
			_label = cfg.label();
			_value = (int) Math.round(cfg.initial());
			_min = (int) Math.round(cfg.min());
			_max = (int) Math.round(cfg.max());
			_step = (int) Math.round(cfg.step());
			_units = cfg.units();
		}
		// fix from defaults
		if(_label==null || _label.isEmpty()) _label ="int";
		if(_step == 0 ) _step = 1; else if(_step<0) _step = -_step;
		if(_max < _min){ int temp = _max; _max = _min; _min = temp; }
		if(_min == _max) _max = _min + _step * 100;
		if(_value < _min) _value = _min;
		if(_value > _max) _value = _max;
	}

	ParamInt(ParamInt src){
		_label = src._label;
		_value = src._value;
		_min = src._min;
		_max = src._max;
		_step = src._step;
	}

	// endregion

	@Override
	public void inc() {
		_value = Math.min(_max,_value+_step);
	}

	@Override
	public void dec() {
		_value = Math.max(_min,_value-_step);
	}

	@Override
	public String getLabel() {
		return _label;
	}

	@Override
	public Object getValue() {
		return _value;
	}

	@Override
	public String getValueString() {
		return _value+_units;
	}

	@Override
	public String getRangeString() {
		return _min + " to " + _max;
	}


	@Override
	public Param Clone() {
		return new ParamInt(this);
	}

	// region private fields

	String _label;
	int _value;
	int _min;
	int _max;
	int _step;
	String _units;

	// endregion


}
