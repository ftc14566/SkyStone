package org.firstinspires.ftc.teamcode.bravo;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.reflect.Method;

import static java.lang.System.currentTimeMillis;

public class InteractiveParameterList extends InteractiveList {

	// region consturctor

	public InteractiveParameterList(CallbackListener listener){
		_listener = listener;
	}

	// endregion

	@Override
	public void DisplayStatus(Telemetry telemetry){
		telemetry.addData("Mode","Enter Param Values for:"+ methodDescription);

		int end = Math.min(_topOfPageIndex+LinesPerPage, _signature.params.length); // exclude end-index
		for(int i = _topOfPageIndex; i<end; ++i)
			_signature.params[i].addParamToTelemetry(telemetry,paramValues[i], i==_curIndex);
		telemetry.update();
	}

	public void setMethodSignature(Method method){
		_curIndex = 0;
		_signature = new MethodSignature(method);
		this.paramValues = _signature.getInitialParamValues();
		methodDescription=MethodSignature.formatMethodSignature(method);
	}

	public void setMethodSignature(Method method, Object[] paramValues){
		_curIndex = 0;
		_signature = new MethodSignature(method);
		this.paramValues = paramValues;
		methodDescription=MethodSignature.formatMethodSignature(method);
	}

	public MethodSignature getMethodSignature(){
		return _signature;
	}

	public void execute(Object o){
		_signature.execute(o,paramValues);
	}

	// region button presses

	@Override
	public void DpadUp_Pressed(){
		if(_curIndex>0) _curIndex--;
		if(_topOfPageIndex<_curIndex+1-LinesPerPage) _topOfPageIndex=_curIndex+1-LinesPerPage;
	}

	@Override
	public void DpadDown_Pressed(){
		if(_curIndex< _signature.params.length-1) _curIndex++;
		if(_topOfPageIndex>_curIndex) _topOfPageIndex=_curIndex;
	}

	Param getCurParam(){ return _signature.params[_curIndex]; }

	@Override
	public void DpadLeft_Pressed(){
		paramValues[_curIndex] = _signature.params[_curIndex]
				.adjust(paramValues[_curIndex],-1);
		trackPressedTime();
	}

	@Override
	public void DpadRight_Pressed(){
		paramValues[_curIndex] = _signature.params[_curIndex]
				.adjust(paramValues[_curIndex],1);
		trackPressedTime();
	}

	@Override
	public void B_Pressed() { _listener.cancelMethodConfig(); }

	@Override
	public void A_Pressed() { _listener.executeMethod(); }

	@Override
	public void LeftBumper_Pressed() { _listener.saveMethodConfig(); }

	final double PreRepeatWaitTime = 1000; // wait 1 second before speeding up
	final double AutoPressTime = 70; // once every 70ms
	void trackPressedTime(){ _startRepeatingAtThisTime = currentTimeMillis() + PreRepeatWaitTime + AutoPressTime; }

	@Override
	public void doOtherWork(Gamepad gamepad){
		// When left or righ dpad held down, auto-inc/dec 10 times a second.s
		if(!gamepad.dpad_left && !gamepad.dpad_right) return;
		int stepCount = gamepad.dpad_right ? 1 : 1;
		double now = currentTimeMillis();
		Param cur = getCurParam();
		while( _startRepeatingAtThisTime < now){
			_startRepeatingAtThisTime += AutoPressTime;
			paramValues[_curIndex] = cur.adjust( paramValues[_curIndex], stepCount );
		}
	}
	private double _startRepeatingAtThisTime;

	//endregion

	//region interface

	public interface CallbackListener {
		void executeMethod(); //
		void saveMethodConfig();
		void cancelMethodConfig();
	}

	//endregion



	//region private fields

	private int _topOfPageIndex = 0;
	private int _curIndex = 0;
	private static final int LinesPerPage = 4;
	private String methodDescription; // used internally

	private MethodSignature _signature;
	public Object[] paramValues;

	CallbackListener _listener;

	//endregion

}
