package org.firstinspires.ftc.teamcode.bravo;

import android.content.Context;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Dictionary;

public class MethodExplorer implements
		InteractiveStepList.CallbackListener,
		InteractiveMethodList.CallbackListener,
		InteractiveParameterList.CallbackListener
{

	public MethodExplorer(HardwareMap hardwareMap){
		context = hardwareMap.appContext;

		// modes
		stepList = new InteractiveStepList(this);
		methodList = new InteractiveMethodList(this);
		paramList = new InteractiveParameterList(this); // does not depend on target
		currentMode = stepList;

	}

	public void setTarget(Object target){
		this.target = target;
		methodList.accessClass(target.getClass());
	}

	public void displayStatus(Telemetry telemetry){
		currentMode.DisplayStatus(telemetry);
	}

	// region callbacks

	@Override
	public void stepSelected() {// step-index ->down-> select method
		SavedMethodCall saved = stepList.getCurrentSignature();

		if(saved != null) {
			Method method = methodList.find(saved.methodKey);
			paramList.setMethodSignature( method, saved.paramValues );
			currentMode = paramList;
		} else {
			currentMode = methodList;
		}
	}
	String methodKeyForParams; // temporary, until we can find way to make step list reconstitue Methods on its own.

	@Override
	public void cancelMethodSelection() {  // select method ->up-> step-index

		currentMode = stepList;
	}


	@Override
	public void selectMethod() { // select method ->down-> config-params
		methodKeyForParams = methodList.getSelectedKey();
		paramList.setMethodSignature( methodList.find(methodKeyForParams) );
		currentMode = paramList;
	}

	@Override
	public void cancelMethodConfig() { // config-params ->up-> select method
		SavedMethodCall saved = stepList.getCurrentSignature();
		if(saved==null)
			currentMode = methodList;
		else
			currentMode = stepList;
	}

	@Override
	public void executeMethod() {
		paramList.execute(target);
	}

	@Override
	public void saveMethodConfig() {
		MethodSignature sig = paramList.getMethodSignature();
		SavedMethodCall saved = new SavedMethodCall();
		saved.display = sig.getParamValueSummary(paramList.paramValues);
		saved.methodKey = methodKeyForParams;
		saved.paramValues = paramList.paramValues;
		stepList.setCurrentSignature( saved );
		currentMode = stepList;
//		saveSteps();
	}

	// endregion

	// region load/save steps

	void saveSteps(){
		JsonBuilder builder = new JsonBuilder();
		// !!! builder.append( stepList.steps.toArray(new MethodSignature[0]) );
		try {
			OutputStream o = context.openFileOutput("steps.json", Context.MODE_PRIVATE);
			o.write(builder.toString().getBytes());
			o.close();
		}catch(Exception ex){}
	}

	void loadSteps(){
		JsonBuilder builder = new JsonBuilder();
// !!!		builder.append( stepList.steps.toArray(new MethodSignature[0]) );
		try {
			InputStream ii = context.openFileInput("steps.json");
			BufferedReader reader = new BufferedReader(new InputStreamReader(ii));
			StringBuilder out = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) out.append(line);
			reader.close();

			String source = out.toString();
			ArrayList<Object> stepsConfigs = new JsonParser(source).DeserializeArray();
			MethodSignature[] sigs = new MethodSignature[stepsConfigs.size()];
			for(int i=0;i<sigs.length;++i){
				Dictionary<String,Object> config = (Dictionary<String,Object>)stepsConfigs.get(i);
				String methodName = (String)config.get("name");
				ArrayList<Object> paramConfigs = (ArrayList<Object>)config.get("params");
				Param[] params = new Param[paramConfigs.size()];
				for(int j=0;j<params.length;++j){
					// !!! need to figureout which type of parameter it is.
				}
			}
			// Parse: array, object, boolean, string, double


		}catch(Exception ex){}

	}
	// endregion

	// region button tracking

	public void TrackGamePad(Gamepad gamepad){
		if(Changed(0,gamepad.a)) if(_cur) currentMode.A_Pressed();
		if(Changed(1,gamepad.b)) if(_cur) currentMode.B_Pressed();
		if(Changed(2,gamepad.x)) if(_cur) currentMode.X_Pressed();
		if(Changed(3,gamepad.y)) if(_cur) currentMode.Y_Pressed();
		if(Changed(4,gamepad.dpad_down)) if(_cur) currentMode.DpadDown_Pressed(); else currentMode.DpadDown_Released();
		if(Changed(5,gamepad.dpad_up)) if(_cur) currentMode.DpadUp_Pressed(); else currentMode.DpadUp_Released();
		if(Changed(6,gamepad.dpad_left)) if(_cur) currentMode.DpadLeft_Pressed(); else currentMode.DpadLeft_Released();
		if(Changed(7,gamepad.dpad_right)) if(_cur) currentMode.DpadRight_Pressed(); else currentMode.DpadRight_Released();
		if(Changed(8,gamepad.back)) if(_cur) currentMode.Back_Pressed();
		if(Changed(9,gamepad.start)) if(_cur) currentMode.Start_Pressed();
		if(Changed(10,gamepad.guide)) if(_cur) currentMode.Guide_Pressed();
		if(Changed(11,gamepad.left_bumper)) if(_cur) currentMode.LeftBumper_Pressed();
		if(Changed(12,gamepad.right_bumper)) if(_cur) currentMode.RightBumper_Pressed();
		currentMode.doOtherWork(gamepad);
	}

	private boolean Changed(int index,boolean newState){
		boolean changed = newState != _lastState[index];
		_lastState[index] = _cur = newState;
		return changed;
	}

	private boolean _cur;
	private boolean[] _lastState = new boolean[13];

	// endregion

	// region fields

	private InteractiveStepList stepList;
	private InteractiveMethodList methodList;
	private InteractiveParameterList paramList;
	private InteractiveList currentMode;
	private Context context;

	private Object target;

	// endregion

}
