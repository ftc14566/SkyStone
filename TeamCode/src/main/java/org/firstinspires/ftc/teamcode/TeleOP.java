package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
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


<<<<<<< HEAD
		bot.Move(-gamepad1.left_stick_y,gamepad1.left_stick_x);
		bot.SpinRight(gamepad1.right_stick_x);
		bot.raiseElevators(gamepad2.left_stick_y, .25);
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
=======
		bot.MoveLR(-gamepad1.left_stick_y,gamepad1.left_stick_x,-gamepad1.right_stick_y,gamepad1.right_stick_x);
		bot.Lift(gamepad1.dpad_up,gamepad1.dpad_down);
		//bot.SpinRight(gamepad1.right_stick_x);
		telemetry.addData("lStick X",gamepad1.left_stick_x);
		telemetry.addData("lStick Y",gamepad1.left_stick_y);
		telemetry.addData("rStick X",gamepad1.right_stick_x);
		telemetry.addData("rStick Y",gamepad1.right_stick_x);

		//telemetry.addData("Spin",gamepad1.right_stick_x);
		telemetry.addData("up",gamepad1.dpad_up);
		//telemetry.addLine("Left Color Sensor:");
		//telemetry.addData("		Red",hardware.leftColorSensor.red());
		//telemetry.addData("		Green",hardware.leftColorSensor.green());
		//telemetry.addData("		Blue",hardware.leftColorSensor.blue());
		//telemetry.addData("		Alpha (Light)", hardware.leftColorSensor.alpha());
		//telemetry.addLine("Right Color Sensor:");
		//telemetry.addData("		Red",hardware.rightColorSensor.red());
		//telemetry.addData("		Green",hardware.rightColorSensor.green());
		//telemetry.addData("		Blue",hardware.rightColorSensor.blue());
		//telemetry.addData("		Alpha (Light)", hardware.rightColorSensor.alpha());
>>>>>>> 302851b4aa4990f265bf8a23d4015394d40e7320
		telemetry.update();
	}


	@Override
	public void stop() {
	}

}
