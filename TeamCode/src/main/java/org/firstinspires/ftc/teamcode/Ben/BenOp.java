package org.firstinspires.ftc.teamcode.Ben;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.TeleBot;

@TeleOp(name="BenOp", group="Iterative Opmode")
public class BenOp extends OpMode {
	private BenBot bot;
	private Hardware hardware;

	@Override
	public void init() {
		
		hardware = new Hardware();
		hardware.init( hardwareMap );
		bot = new BenBot(hardware);

	}

	/*
	 * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
	 */
	
	public void telemetryAdd(double screen, boolean cycleButton) {
		if (cycleButton = true){
			screen += 1;
		}
		
		if(screen == 1) {
			telemetry.clear();
			telemetry.addData("Screen", screen);
			telemetry.addData("Right Tower Position", hardware.rightTowerMotor.getCurrentPosition());
			telemetry.addData("Left Tower Position", hardware.leftTowerMotor.getCurrentPosition());
			telemetry.update();
		}
		if(screen == 2) {
			telemetry.clear();
			telemetry.addData("Screen", screen);
			telemetry.addData("Stick X", gamepad1.left_stick_x);
			telemetry.addData("Stick Y", gamepad1.left_stick_y);
			telemetry.addData("Spin", gamepad1.right_stick_x);
			telemetry.update();
		}
		if(screen == 3) {
			telemetry.clear();
			telemetry.addData("Screen", screen);
			telemetry.addLine("Left Color Sensor:");
			telemetry.addData("		Red", hardware.leftColorSensor.red());
			telemetry.addData("		Green", hardware.leftColorSensor.green());
			telemetry.addData("		Blue", hardware.leftColorSensor.blue());
			telemetry.addData("		Alpha (Light)", hardware.leftColorSensor.alpha());
			telemetry.addLine("Right Color Sensor:");
			telemetry.addData("		Red", hardware.rightColorSensor.red());
			telemetry.addData("		Green", hardware.rightColorSensor.green());
			telemetry.addData("		Blue", hardware.rightColorSensor.blue());
			telemetry.addData("		Alpha (Light)", hardware.rightColorSensor.alpha());
			telemetry.update();
		}
		if(screen == 4) {
			telemetry.clear();
			telemetry.addData("Screen", screen);
			telemetry.addData("lStick X", gamepad1.left_stick_x);
			telemetry.addData("lStick Y", gamepad1.left_stick_y);
			telemetry.addData("rStick X", gamepad1.right_stick_x);
			telemetry.addData("rStick Y", gamepad1.right_stick_x);
			
			telemetry.addData("up", gamepad1.dpad_up);
			telemetry.update();
		}
		
		if (screen == 5){
			screen = 1;
		}
	}

	@Override
	public void init_loop() {
		hardware.leftTowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		hardware.rightTowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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

		bot.checkSpeed(gamepad1.left_trigger, -gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
		//bot.driveAndStrafe(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
		//bot.stairDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
		
		telemetryAdd(1, gamepad1.right_bumper);
		
		//bot.Lift(gamepad2.dpad_up, gamepad2.dpad_down);
		//bot.towerDown(gamepad2.right_trigger);
/////////////////I BROKE YOUR LIFT (TWO LINES ABOVE) -Nathan ///////////////////////////////////////
		//TODO bot.encodersWithGrabber(gamepad2.left_trigger, false);

		//bot.extend(gamepad2.y,gamepad2.a);

		//bot.grab(gamepad2.x,gamepad2.b);

		//bot.moveFoundation(gamepad1.right_bumper);

		//telemetry.addData("Right Foundation Position", hardware.rightFoundationServo.getPosition());
		//telemetry.addData("Left Foundation Position", hardware.leftFoundationServo.getPosition());
	}


	@Override
	public void stop() {
	}

}
