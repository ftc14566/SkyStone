package org.firstinspires.ftc.teamcode.bravo;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static java.lang.System.currentTimeMillis;

public class InteractiveParameterList extends InteractiveList {

	// region consturctor

	public InteractiveParameterList(CallbackListener listener){
		this.listener = listener;
	}

	// endregion

	public void setBinding(MethodBinding binding){
		curIndex = 0;
		this.binding = binding;
		MethodSignature sig = methodManager.find(binding.method);
		title = sig.methodString;
		params = sig.params;
	}

	public interface CallbackListener {
		void executeMethod(MethodBinding binding); //
		void saveMethodConfig(MethodBinding binding);
		void cancelMethodConfig();
	}

	// region interface InteractiveList

	@Override
	public void DisplayStatus(Telemetry telemetry){
		telemetry.addData("Mode","Enter Param Values for:"+ title);

		int end = Math.min(topOfPageIndex +LinesPerPage, params.length); // exclude end-index
		for(int i = topOfPageIndex; i<end; ++i)
			params[i].addParamToTelemetry(telemetry,binding.paramValues[i], i== curIndex);
		telemetry.update();
	}

	@Override
	public void DpadUp_Pressed(){
		if(curIndex >0) curIndex--;
		if(topOfPageIndex < curIndex +1-LinesPerPage) topOfPageIndex = curIndex +1-LinesPerPage;
	}

	@Override
	public void DpadDown_Pressed(){
		if(curIndex < params.length-1) curIndex++;
		if(topOfPageIndex > curIndex) topOfPageIndex = curIndex;
	}

	@Override
	public void DpadLeft_Pressed(){
		binding.paramValues[curIndex] = params[curIndex]
				.adjust(binding.paramValues[curIndex],-1);
		trackPressedTime();
	}

	@Override
	public void DpadRight_Pressed(){
		binding.paramValues[curIndex] = params[curIndex]
				.adjust(binding.paramValues[curIndex],1);
		trackPressedTime();
	}

	@Override
	public void B_Pressed() { listener.cancelMethodConfig(); }

	@Override
	public void A_Pressed() {
		listener.executeMethod( binding );
	}

	@Override
	public void LeftBumper_Pressed() {
		listener.saveMethodConfig( binding );
	}

	final double PreRepeatWaitTime = 1000; // wait 1 second before speeding up
	final double AutoPressTime = 70; // once every 70ms
	void trackPressedTime(){ _startRepeatingAtThisTime = currentTimeMillis() + PreRepeatWaitTime + AutoPressTime; }

	@Override
	public void doOtherWork(Gamepad gamepad){
		// When left or righ dpad held down, auto-inc/dec 10 times a second.s
		if(!gamepad.dpad_left && !gamepad.dpad_right) return;
		int stepCount = gamepad.dpad_right ? 1 : 1;
		double now = currentTimeMillis();
		Param cur =  params[curIndex];
		while( _startRepeatingAtThisTime < now){
			_startRepeatingAtThisTime += AutoPressTime;
			binding.paramValues[curIndex] = cur.adjust( binding.paramValues[curIndex], stepCount );
		}
	}
	private double _startRepeatingAtThisTime;

	public void accessClass(MethodManager methodManager) {
		this.methodManager = methodManager;
	}

	//endregion

	//region private fields

	private int topOfPageIndex = 0;
	private int curIndex = 0;
	private static final int LinesPerPage = 4;
	private String title; // used internally

	private Param[] params;
	private MethodBinding binding;

	private MethodManager methodManager;

	CallbackListener listener;

	//endregion

}
