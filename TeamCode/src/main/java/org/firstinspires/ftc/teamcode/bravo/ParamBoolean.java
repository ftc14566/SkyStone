package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ParamBoolean extends Param {

	// region constructor

	public ParamBoolean(Config a){
		if(a!=null){
			_label = a.label();
			_value = a.isTrue();
			_trueString = a.trueString();
			_falseString = a.falseString();
		}
		// fix
		if(_label ==null|| _label.isEmpty()) _label = "boolean";
	}

	ParamBoolean(ParamBoolean a){
		_label = a._label;
		_value = a._value;
		_trueString = a._trueString;
		_falseString = a._falseString;

	}

	// endregion

	@Override
	public void inc() {
		_value = true;
	}

	@Override
	public void dec() {
		_value = false;
	}

	public String getRangeString(){
		return _falseString+" / "+_trueString;
	}

	@Override
	public Object getValue() {
		return _value;
	}

	@Override
	public String getValueString(){
		return _value ? _trueString : _falseString;
	}


	@Override
	public Param Clone() { return new ParamBoolean(this); }

	// region private fields
	boolean _value;
	String _trueString;
	String _falseString;
	// endregion



}
