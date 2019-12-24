package org.firstinspires.ftc.teamcode.bravo;

import android.content.Context;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MethodExplorer implements
		InteractiveStepList.CallbackListener,
		InteractiveMethodList.CallbackListener,
		InteractiveParameterList.CallbackListener
{

	public MethodExplorer(Gamepad gamepad){

		this.tracker = new ButtonTracker(gamepad);

		// modes
		stepList = new InteractiveStepList(this);
		methodList = new InteractiveMethodList(this);
		paramList = new InteractiveParameterList(this);
		currentMode = stepList;

		// init steps
		stepList.setBindings(new MethodBinding[]{null});
	}

	public void bindToFile(ConfigFile file){
		this.file = file;
		MethodBinding[] steps = getBindingsFromFile();
		if(steps.length > 0)
			stepList.setBindings(steps);
	}

	public void setTarget(Object target){
		this.target = target;
		mgr = new MethodManager( target.getClass() );
		methodList.initMethods(mgr.getAllSignatures());
	}

	public void displayStatus(Telemetry telemetry){
		currentMode.DisplayStatus(telemetry);
	}

	// region callbacks

	@Override
	public void stepSelected(MethodBinding binding) {// stepSize-index ->down-> select method

		if(binding != null) {
			paramList.setBinding( binding );
			currentMode = paramList;
		} else {
			currentMode = methodList;
		}
	}

	@Override
	public void cancelMethodSelection() {  // select method ->up-> stepSize-index
		currentMode = stepList;
	}

	@Override
	public void selectMethod( MethodBinding binding ) { // select method ->down-> config-params
		paramList.setBinding( binding );
		currentMode = paramList;
	}

	@Override
	public void cancelMethodConfig() { // config-params ->up-> select method
		if( stepList.bindingSelected() )
			currentMode = stepList;
		else
			currentMode = methodList;
	}

	@Override
	public void executeMethod(MethodBinding binding) {
		binding.invoke(target);
	}

	@Override
	public void saveMethodConfig(MethodBinding binding) {
		stepList.setCurrentSignature( binding );
		currentMode = stepList;
		saveSteps();
	}

	// endregion

	// region load/save steps

	void saveSteps(){
		if(file==null) return;
		String s = new MethodSerializer().serialize( stepList.getBindings() );
		file.write(s);
	}
	MethodBinding[] getBindingsFromFile() {
		String s = file.read();
		if(s==null || s=="") return new MethodBinding[0];
		return new MethodSerializer().deserialize(s,mgr);
	}
	// endregion

	public void trackGamePad(){
		if(tracker.aPressed()) currentMode.A_Pressed();
		if(tracker.bPressed()) currentMode.B_Pressed();
		if(tracker.xPressed()) currentMode.X_Pressed();
		if(tracker.yPressed()) currentMode.Y_Pressed();
		if(tracker.dpadDownPressed()) currentMode.DpadDown_Pressed();
		if(tracker.dpadUpPressed()) currentMode.DpadUp_Pressed();
		if(tracker.dpadLeftPressed()) currentMode.DpadLeft_Pressed();
		if(tracker.dpadRightPressed()) currentMode.DpadRight_Pressed();
		if(tracker.backPressed()) currentMode.Back_Pressed();
		if(tracker.startPressed()) currentMode.Start_Pressed();
		if(tracker.guidePressed()) currentMode.Guide_Pressed();
		if(tracker.leftBumperPressed()) currentMode.LeftBumper_Pressed();
		if(tracker.rightBumperPressed()) currentMode.RightBumper_Pressed();
		currentMode.doOtherWork(tracker.gamepad);
	}

	// region fields
	private InteractiveStepList stepList;
	private InteractiveMethodList methodList;
	private InteractiveParameterList paramList;
	private InteractiveList currentMode;

	private MethodManager mgr;
	private Object target;
	private ConfigFile file;

	ButtonTracker tracker;

	// endregion

}
