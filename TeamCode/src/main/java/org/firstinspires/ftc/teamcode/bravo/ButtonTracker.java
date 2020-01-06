package org.firstinspires.ftc.teamcode.bravo;

import com.qualcomm.robotcore.hardware.Gamepad;

public class ButtonTracker {
	public ButtonTracker(Gamepad gamepad){ this.gamepad = gamepad;}
	public boolean aPressed(){ return pressed(0,gamepad.a); }
	public boolean bPressed(){ return pressed(1,gamepad.b); }
	public boolean xPressed(){ return pressed(2,gamepad.x); }
	public boolean yPressed(){ return pressed(3,gamepad.y); }
	public boolean dpadDownPressed(){ return pressed(4,gamepad.dpad_down); }
	public boolean dpadUpPressed(){ return pressed(5,gamepad.dpad_up); }
	public boolean dpadLeftPressed(){ return pressed(6,gamepad.dpad_left); }
	public boolean dpadRightPressed(){ return pressed(7,gamepad.dpad_right); }
	public boolean backPressed(){ return pressed(8,gamepad.back); }
	public boolean startPressed(){ return pressed(9,gamepad.start); }
	public boolean guidePressed(){ return pressed(10,gamepad.guide); }
	public boolean leftBumperPressed(){ return pressed(11,gamepad.left_bumper); }
	public boolean rightBumperPressed(){ return pressed(12,gamepad.right_bumper); }
	private boolean pressed(int index, boolean newState){
		if(newState==_lastState[index]) return false;
		_lastState[index] = newState;
		return newState;
	}
	private boolean[] _lastState = new boolean[13];
	public Gamepad gamepad;
}
