package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp(name="Dean DriverOp", group="dean")
public class DeanDriverOp extends OpMode {
	private DeanBot bot;
	private Hardware hardware;

	@Override
	public void init() {

		hardware = new Hardware();
		hardware.init( hardwareMap );
		bot = new DeanBot(hardware);

	}


	@Override
	public void loop() {

		bot.driveAndStrafe(-gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x);
		bot.deanLift(gamepad2.dpad_up, gamepad2.dpad_down);
		bot.extend(gamepad2.y, gamepad2.a);

		bot.grab(gamepad2.x,bot.isBlockInFront());
		telemetry.addData("Bridge", hardware.bridgeMotor.getCurrentPosition());
		telemetry.update();
	}

	@Override
	public void stop() {
		telemetry.addData("Stopped","");
		telemetry.update();
	}

}
