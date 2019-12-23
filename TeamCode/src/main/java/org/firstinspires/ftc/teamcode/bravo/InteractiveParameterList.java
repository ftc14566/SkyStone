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

		signature = binding.getSignature();

		// clone values
		paramValues = binding.getParamValues();
	}

	public interface CallbackListener {
		void executeMethod(MethodBinding binding); //
		void saveMethodConfig(MethodBinding binding);
		void cancelMethodConfig();
	}

	// region interface InteractiveList

	@Override
	public void DisplayStatus(Telemetry telemetry){
		telemetry.addData("Mode","Enter Param Values for:"+ signature.methodString);

		int end = Math.min(topOfPageIndex +LinesPerPage, signature.params.length); // exclude end-index
		for(int i = topOfPageIndex; i<end; ++i)
			signature.params[i].addParamToTelemetry(telemetry, paramValues[i], i== curIndex);
		telemetry.update();
	}

	@Override protected int getLastIndex(){ return signature.params.length-1; }

	@Override
	public void DpadLeft_Pressed(){
		paramValues[curIndex] = signature.params[curIndex]
				.adjust(paramValues[curIndex],-1);
		trackPressedTime();
	}

	@Override
	public void DpadRight_Pressed(){
		paramValues[curIndex] = signature.params[curIndex]
				.adjust(paramValues[curIndex],1);
		trackPressedTime();
	}

	@Override
	public void B_Pressed() { listener.cancelMethodConfig(); }

	@Override
	public void A_Pressed() {
		listener.executeMethod( makeNewBinding() );
	}

	@Override
	public void LeftBumper_Pressed() {
		listener.saveMethodConfig( makeNewBinding() );
	}

	MethodBinding makeNewBinding(){ return new MethodBinding(signature,paramValues); }

	final double PreRepeatWaitTime = 1000; // wait 1 second before speeding up
	final double AutoPressTime = 50; // once every 70ms
	void trackPressedTime(){ _startRepeatingAtThisTime = currentTimeMillis() + PreRepeatWaitTime + AutoPressTime; }

	@Override
	public void doOtherWork(Gamepad gamepad){
		// When left or righ dpad held down, auto-inc/dec 10 times a second.s
		if(!gamepad.dpad_left && !gamepad.dpad_right) return;
		int stepCount = gamepad.dpad_right ? 1 : -1;
		double now = currentTimeMillis();
		Param cur =  signature.params[curIndex];
		while( _startRepeatingAtThisTime < now){
			_startRepeatingAtThisTime += AutoPressTime;
			paramValues[curIndex] = cur.adjust( paramValues[curIndex], stepCount );
		}
	}
	private double _startRepeatingAtThisTime;

	//endregion

	//region private fields


	private Object[] paramValues;
	private MethodSignature signature;

	CallbackListener listener;

	//endregion

}
