package org.firstinspires.ftc.teamcode.bravo;

import com.qualcomm.robotcore.hardware.Gamepad;

public class InteractiveList {

	boolean Changed(int index,boolean newState){
		boolean changed = newState != _lastState[index];
		_lastState[index] = _cur = newState;
		return changed;
	}
	boolean _cur;
	boolean[] _lastState = new boolean[13];

	public void TrackGamePad(Gamepad gamepad){
		if(Changed(0,gamepad.a)) if(_cur ) A_Pressed();
		if(Changed(1,gamepad.b)) if(_cur ) B_Pressed();
		if(Changed(2,gamepad.x)) if(_cur ) X_Pressed();
		if(Changed(3,gamepad.y)) if(_cur ) Y_Pressed();
		if(Changed(4,gamepad.dpad_down)) if(_cur ) DpadDown_Pressed();
		if(Changed(5,gamepad.dpad_up)) if(_cur ) DpadDown_Pressed();
		if(Changed(6,gamepad.dpad_left)) if(_cur ) DpadDown_Pressed();
		if(Changed(7,gamepad.dpad_right)) if(_cur ) DpadDown_Pressed();
		if(Changed(8,gamepad.back)) if(_cur ) Back_Pressed();
		if(Changed(9,gamepad.start)) if(_cur ) Start_Pressed();
		if(Changed(10,gamepad.guide)) if(_cur ) Guide_Pressed();
		if(Changed(11,gamepad.left_bumper)) if(_cur ) LeftBumper_Pressed();
		if(Changed(12,gamepad.right_bumper)) if(_cur ) RightBumper_Pressed();
	}

	protected void DpadUp_Pressed(){}
	protected void DpadDown_Pressed(){}
	protected void DpadLeft_Pressed(){}
	protected void DpadRight_Pressed(){}
	protected void A_Pressed(){}
	protected void B_Pressed(){}
	protected void X_Pressed(){}
	protected void Y_Pressed(){}
	protected void Back_Pressed(){}
	protected void Start_Pressed(){}
	protected void Guide_Pressed(){}
	protected void LeftBumper_Pressed(){}
	protected void RightBumper_Pressed(){}

}
