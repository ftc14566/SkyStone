package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@TeleOp(name="TeleOP", group="Iterative Opmode")
public class TeleOP extends OpMode {
	private TeleBot bot;
	private Hardware hardware;

	@Override
	public void init() {

		hardware = new Hardware();
		hardware.init( hardwareMap );
		bot = new TeleBot(hardware);
		telemetry.addLine("Why are you looking at me?");
		telemetry.addLine("I mean nothing");
		telemetry.addLine("LOOK AWAY!");
		telemetry.addLine("and tell Nathan you found me");
		telemetry.update();
	}

	/*
	 * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
	 */



	@Override
	public void init_loop() {
		//hardware.leftTowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		//hardware.rightTowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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

		// Drive
		bot.driveAndStraif(-gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x);
		//bot.Move(-gamepad1.left_stick_y,gamepad1.left_stick_x);
		//bot.MoveLR(-gamepad1.left_stick_y,gamepad1.left_stick_x,-gamepad1.right_stick_y,gamepad1.right_stick_x);

		// Lift
		bot.Lift(gamepad2.dpad_up, gamepad2.dpad_down);
		//bot.raiseElevators(gamepad2.left_stick_y, .25);
		//bot.towerDown(gamepad1.right_trigger);

		bot.Extend(gamepad2.y,gamepad2.a);

		bot.grab(gamepad2.x,gamepad2.b);


	}

/*
		telemetry.addData("Right Tower Position", hardware.rightTowerMotor.getCurrentPosition());
		telemetry.addData("Left Tower Position", hardware.leftTowerMotor.getCurrentPosition());

		telemetry.addData("Stick X",gamepad1.left_stick_x);
		telemetry.addData("Stick Y",gamepad1.left_stick_y);
		telemetry.addData("Spin",gamepad1.right_stick_x);
		telemetry.addLine("Left Color Sensor:");
		telemetry.addData("		Red",hardware.leftColorSensor.red());
		telemetry.addData("		Green",hardware.leftColorSensor.green());
		telemetry.addData("		Blue",hardware.leftColorSensor.blue());
		telemetry.addData("		Alpha (Light)", hardware.leftColorSensor.alpha());
		telemetry.addLine("Right Color Sensor:");
		telemetry.addData("		Red",hardware.rightColorSensor.red());
		telemetry.addData("		Green",hardware.rightColorSensor.green());
		telemetry.addData("		Blue",hardware.rightColorSensor.blue());
		telemetry.addData("		Alpha (Light)", hardware.rightColorSensor.alpha());

		telemetry.addData("lStick X",gamepad1.left_stick_x);
		telemetry.addData("lStick Y",gamepad1.left_stick_y);
		telemetry.addData("rStick X",gamepad1.right_stick_x);
		telemetry.addData("rStick Y",gamepad1.right_stick_x);

		telemetry.addData("up",gamepad1.dpad_up);
		telemetry.update();
*/

	@Override
	public void stop() {
	}

}
