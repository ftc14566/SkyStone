package org.firstinspires.ftc.teamcode.bravo;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.reflect.Method;

public class Bravo implements
		SelectStep.StepSequenceListener,
		SelectMethod.MethodListListener,
		SelectParamValue.EventListener
{

	public Bravo(TestBot bot){
		_bot = bot;

		// init modes
		_selectStep = new SelectStep(this);
		_selectMethod = new SelectMethod(this);
		_selectParam = new SelectParamValue(this);
		_mode = _selectStep;

		// provide steps
		final Class c = _bot.getClass();
		final Method[] methods = c.getDeclaredMethods();
		for(int i=0;i<methods.length;++i){
			MethodSignature sig = new MethodSignature(methods[i]);
			_selectMethod._items.add( sig );
		}
	}

	public void displayStatus(Telemetry telemetry){
		_mode.DisplayStatus(telemetry);
	}

	@Override
	public void stepSelected() {// step-index ->down-> select method
		MethodSignature sig = _selectStep.getCurrentSignature();
		if(sig != null) {
			_selectParam.SetMethod(sig.Clone());
			_mode = _selectParam;
		} else {
			_mode = _selectMethod;
		}
	}
	@Override
	public void cancelMethodSelection() {  // select method ->up-> step-index
		_mode = _selectStep;
	}


	@Override
	public void selectMethod() { // select method ->down-> config-params
		_selectParam._signature = _selectMethod.getCurrent();
		_mode = _selectParam;
	}

	@Override
	public void cancelMethodConfig() { // config-params ->up-> select method
		MethodSignature sig = _selectStep.getCurrentSignature();
		if(sig==null){
			_mode = _selectMethod;
		} else {
			_mode = _selectStep;
		}
	}

	@Override
	public void executeMethod() {
		_selectParam._signature.Execute(this._bot);
	}

	@Override
	public void saveMethodConfig() {
		_selectStep.setCurrentSignature( _selectParam._signature.Clone() );
		_mode = _selectStep;
	}

	// region button tracking

	void TrackGamePad(Gamepad gamepad){
		if(Changed(0,gamepad.a)) if(_cur ) _mode.A_Pressed();
		if(Changed(1,gamepad.b)) if(_cur ) _mode.B_Pressed();
		if(Changed(2,gamepad.x)) if(_cur ) _mode.X_Pressed();
		if(Changed(3,gamepad.y)) if(_cur ) _mode.Y_Pressed();
		if(Changed(4,gamepad.dpad_down)) if(_cur ) _mode.DpadDown_Pressed();
		if(Changed(5,gamepad.dpad_up)) if(_cur ) _mode.DpadUp_Pressed();
		if(Changed(6,gamepad.dpad_left)) if(_cur ) _mode.DpadLeft_Pressed();
		if(Changed(7,gamepad.dpad_right)) if(_cur ) _mode.DpadRight_Pressed();
		if(Changed(8,gamepad.back)) if(_cur ) _mode.Back_Pressed();
		if(Changed(9,gamepad.start)) if(_cur ) _mode.Start_Pressed();
		if(Changed(10,gamepad.guide)) if(_cur ) _mode.Guide_Pressed();
		if(Changed(11,gamepad.left_bumper)) if(_cur ) _mode.LeftBumper_Pressed();
		if(Changed(12,gamepad.right_bumper)) if(_cur ) _mode.RightBumper_Pressed();
	}

	boolean Changed(int index,boolean newState){
		boolean changed = newState != _lastState[index];
		_lastState[index] = _cur = newState;
		return changed;
	}

	boolean _cur;
	boolean[] _lastState = new boolean[13];

	// endregion

	// region mode fields

	SelectStep _selectStep;
	SelectMethod _selectMethod;
	SelectParamValue _selectParam;
	InteractiveList _mode;

	// endregion

	TestBot _bot;
}
