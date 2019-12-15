package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ParamDouble extends Param {

	// region constructors

	public ParamDouble(Config da){
		if(da!=null){
			_label = da.label();
			_min = da.min();
			_max = da.max();
			_step = da.step();
			_value = da.initial();
			_units = da.units();
			_displayScale = da.displayScale();
		}
		// fix / assign default
		if(_label==null || _label.isEmpty()) _label = "double";
		if(_units==null) _units="";
		if(_step == 0.0) _step = 0.1; else if(_step<0.0) _step = -_step;
		if(_max<_min){ double temp = _max; _max=_min;_min=temp; }
		if(_min==_max) _max = _min + _step * 100;
		if(_value < _min) _value = _min;
		if(_value > _max) _value = _max;
		if(_displayScale == 0) _displayScale=1.0;

		_format = "%."+determineDecimalPlacesToDisplay()+"f";
	}

	int determineDecimalPlacesToDisplay(){
		if(_step<=0.0) throw new IllegalStateException("step should be positive");
		int places = 0;
		double step = _step*_displayScale;
		while(step<1.0){
			step *= 10;
			places++;
		}
		return places;
	}

	ParamDouble(ParamDouble src) {
		_label = src._label;
		_min = src._min;
		_max = src._max;
		_step = src._step;
		_value = src._value;
		_format = src._format;
		_units = src._units;
		_displayScale = src._displayScale;
	}

	// endregion

	@Override
	public Object getValue() {
		return _value;
	}

	@Override
	public String getValueString() {
		return String.format(_format, _value * _displayScale) + _units;
	}

	@Override
	public String getRangeString(){
		String low = String.format(_format, _min*_displayScale);
		String high = String.format(_format, _max*_displayScale);
		return low+" to "+high;
	}

	@Override
	public void inc() {
		_value = Math.min(_value +_step,_max);
	}

	@Override
	public void dec() {
		_value = Math.max(_value -_step,_min);
	}

	@Override
	public Param Clone() { return new ParamDouble(this); }

	// region private fields

	double _min;
	double _max;
	double _step;
	double _value;
	double _displayScale;
	String _format;
	String _units;

	// endregion



}
