package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.*;
import com.qualcomm.hardware.motors.RevRobotics40HdHexMotor;

@TeleOp(name="TeleOP", group="Iterative Opmode")
public class TeleOP extends OpMode {

	private TeleBot bot;
	private Hardware hardware = new Hardware();

	@Override
	public void init() {

		hardware.init( hardwareMap );
		bot = new TeleBot(hardware);

	}

	/*
	 * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
	 */
	@Override
	public void init_loop() {

	}

	/*
	 * Code to run ONCE when the driver hits PLAY
	 */
	@Override
	public void start() {
	}

	/*
	 * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
	 */
	@Override
	public void loop() {
		//bot.Move(gamepad1.left_stick_y,gamepad1.left_stick_x);

		// move robot
		//TODO: Choose control style (Tank, standard, etc.)
		if(gamepad1.left_stick_y != 0)
			bot.RaiseElevator();
		else if(gamepad1.b)
			bot.LowerElevator();

		// other items...
	}

	public void Move(double safetyCap) {
		double leftSpeed = gamepad1.left_stick_y;
		double rightSpeed = gamepad1.right_stick_y;

		double leftWheels = Range.clip(leftSpeed, safetyCap, -safetyCap);
		double rightWheels = Range.clip(rightSpeed, safetyCap, -safetyCap);

	}

	/*
	 * Code to run ONCE after the driver hits STOP
	 */
	@Override
	public void stop() {
	}


}
