package org.firstinspires.ftc.teamcode.bravo;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static java.lang.System.currentTimeMillis;

public class SelectParamValue extends InteractiveList {

	// region consturctor

	public SelectParamValue(CallbackListener listener){
		_listener = listener;
	}

	// endregion

	@Override
	public void DisplayStatus(Telemetry telemetry){
		telemetry.addData("Mode","Enter Param Values for:"+ _signature._method.getName());

		int end = Math.min(_topOfPageIndex+LinesPerPage, _signature._params.length); // exclude end-index
		for(int i = _topOfPageIndex; i<end; ++i)
			_signature._params[i].addParamToTelemetry(telemetry,i==_curIndex);
		telemetry.update();
	}

	public void SetMethod(MethodSignature methodData){
		_curIndex = 0;
		_signature = methodData;
	}

	// region button presses

	@Override
	public void DpadUp_Pressed(){
		if(_curIndex>0) _curIndex--;
		if(_topOfPageIndex<_curIndex+1-LinesPerPage) _topOfPageIndex=_curIndex+1-LinesPerPage;
	}

	@Override
	public void DpadDown_Pressed(){
		if(_curIndex< _signature._params.length-1) _curIndex++;
		if(_topOfPageIndex>_curIndex) _topOfPageIndex=_curIndex;
	}

	Param getCurParam(){ return _signature._params[_curIndex]; }

	@Override
	public void DpadLeft_Pressed(){ getCurParam().dec(); trackPressedTime(); }

	@Override
	public void DpadRight_Pressed(){ getCurParam().inc(); trackPressedTime(); }

	@Override
	public void B_Pressed() { _listener.cancelMethodConfig(); }

	@Override
	public void A_Pressed() { _listener.executeMethod(); }

	@Override
	public void LeftBumper_Pressed() { _listener.saveMethodConfig(); }

	final double PreRepeatWaitTime = 1000; // wait 1 second before speeding up
	final double AutoPressTime = 100; // ms or 10 times per second
	void trackPressedTime(){ _startRepeatingAtThisTime = currentTimeMillis() + PreRepeatWaitTime + AutoPressTime; }

	@Override
	public void doOtherWork(Gamepad gamepad){
		// When left or righ dpad held down, auto-inc/dec 10 times a second.s
		if(!gamepad.dpad_left && !gamepad.dpad_right) return;
		boolean doInc = gamepad.dpad_right;
		double now = currentTimeMillis();
		Param cur = getCurParam();
		while( _startRepeatingAtThisTime < now){
			_startRepeatingAtThisTime += AutoPressTime;
			if(doInc) cur.inc(); else cur.dec();
		}
	}
	double _startRepeatingAtThisTime;
	boolean _incPressed;
	boolean _decPressed;

	//endregion

	//region interface

	public interface CallbackListener {
		void executeMethod();
		void saveMethodConfig();
		void cancelMethodConfig();
	}

	//endregion

	//region private fields

	int _topOfPageIndex = 0;
	int _curIndex = 0;
	static final int LinesPerPage = 4;
	MethodSignature _signature;

	CallbackListener _listener;

	//endregion

}
