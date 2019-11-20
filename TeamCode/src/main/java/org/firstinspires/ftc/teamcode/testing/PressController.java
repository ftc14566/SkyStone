package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;

public class PressController {

	private ElapsedTime timer;

	public PressController(Gamepad gamepad){
		timer = new ElapsedTime();
		_gamepad = gamepad;
	}

	public SettingsSelector Listener;

	public void exec(){

		moveCurrentToLast();

		getCurrentState();

		if(whenPressed(ButtonA)) onAButtonPressed();
		if(whenPressed(ButtonB)) onBButtonPressed();
		if(whenPressed(ButtonX)) onXButtonPressed();
		if(whenPressed(ButtonY)) onYButtonPressed();

		if(whenPressed(PadLeft))  onPadLeftPressed();
		if(whenPressed(PadRight)) onPadRightPressed();

//		if(whenPressed(PadUp))    onPadUpPressed();
//		if(whenPressed(PadDown))  onPadDownPressed();
		onPadUpPressed( countPresses(PadUp) );
		onPadDownPressed( countPresses(PadDown) );


		if(whenPressed(LeftBumper))   onLeftBumperPressed();
		if(whenPressed(RightBumper))  onRightBumperPressed();
		if(whenPressed(LeftTrigger))  onLeftTriggerPressed();
		if(whenPressed(RightTrigger)) onRightTriggerPressed();

		if(whenPressed(Back))  onBackPressed();
		if(whenPressed(Guide)) onGuidePressed();
		if(whenPressed(Start)) onStartPressed();

	}

	private int countPresses(int index){
		// if button not pressed, no counts
		if(!newState[index]) return 0;
		// if button newly pressed, signal count and wait for 1 second
		if(!lastState[index]){
			incTime[index] = timer.seconds() + 1;
			return 1;
		}
		// but is being held down
		// inc total for every 200mS (past the initial 1 second) that button is held down
		int total = 0;
		while(incTime[index]<timer.seconds()){
			total++;
			incTime[index] += 0.2;
		}
		return total*2; // 5 presses per second is too slow, try 10
	}

	private boolean whenPressed(int index){
		return newState[index] && !lastState[index];
	}

	private void getCurrentState() {
		newState[ButtonA] = _gamepad.a;
		newState[ButtonB] = _gamepad.b;
		newState[ButtonX] = _gamepad.x;
		newState[ButtonY] = _gamepad.y;

		newState[PadLeft]  = _gamepad.dpad_left;
		newState[PadRight] = _gamepad.dpad_right;
		newState[PadUp]    = _gamepad.dpad_up;
		newState[PadDown]  = _gamepad.dpad_down;

		newState[LeftBumper]  = _gamepad.left_bumper;
		newState[RightBumper] = _gamepad.right_bumper;
		newState[LeftTrigger]   = _gamepad.left_trigger > 0.3;
		newState[RightTrigger]  = _gamepad.right_trigger > 0.3;

		newState[Back]  = _gamepad.back;
		newState[Guide] = _gamepad.guide;
		newState[Start] = _gamepad.start;
	}

	private void moveCurrentToLast() {
		boolean[] tmp = lastState; // reuse array so we don't have to keep new-ing one up
		lastState = newState;
		newState = tmp;
	}

	protected void onAButtonPressed(){ Listener.onAButtonPressed(); }
	protected void onBButtonPressed(){Listener.onBButtonPressed();}
	protected void onXButtonPressed(){Listener.onXButtonPressed();}
	protected void onYButtonPressed(){Listener.onYButtonPressed();}

	protected void onPadLeftPressed(){Listener.onPadLeftPressed();}
	protected void onPadRightPressed(){Listener.onPadRightPressed();}
	protected void onPadUpPressed(int count){if(count>0) Listener.onPadUpPressed(count);}
	protected void onPadDownPressed(int count){if(count>0) Listener.onPadDownPressed(count);}

	protected void onLeftBumperPressed(){Listener.onLeftBumperPressed();}
	protected void onRightBumperPressed(){Listener.onRightBumperPressed();}
	protected void onLeftTriggerPressed(){Listener.onLeftTriggerPressed();}
	protected void onRightTriggerPressed(){Listener.onRightTriggerPressed();}

	protected void onBackPressed(){Listener.onBackPressed();}
	protected void onStartPressed(){Listener.onStartPressed();}
	protected void onGuidePressed(){Listener.onGuidePressed();}

	private static final int ButtonA = 0;
	private static final int ButtonB = 1;
	private static final int ButtonX = 2;
	private static final int ButtonY = 3;

	private static final int PadUp    = 4;
	private static final int PadDown  = 5;
	private static final int PadLeft  = 6;
	private static final int PadRight = 7;

	private static final int LeftBumper = 8;
	private static final int RightBumper = 9;
	private static final int LeftTrigger = 10;
	private static final int RightTrigger = 11;

	private static final int Back = 12;
	private static final int Guide = 13;
	private static final int Start = 14;

	private Gamepad _gamepad;
	private boolean[] newState = new boolean[15];
	private boolean[] lastState = new boolean[15];
	private double[] incTime = new double[15];

}
