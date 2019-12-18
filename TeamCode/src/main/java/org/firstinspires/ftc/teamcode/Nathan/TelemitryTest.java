package org.firstinspires.ftc.teamcode.Nathan;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TeleBot;

@TeleOp(name="TelemitryTest", group="Iterative Opmode")
public class TelemitryTest extends OpMode {
	private TeleBot bot;


	public DcMotor bridgeMotor;

	@Override
	public void init() {

		bridgeMotor = hardwareMap.get(DcMotor.class, "bridge");
		bridgeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		bridgeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		bridgeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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

	String motorState="forward";

	@Override
	public void loop() {
		telemetry.addData("Bridge", bridgeMotor.getCurrentPosition());

		/*switch(motorState){
			case "forward":
				if (bridgeMotor.isBusy()==false){
					bridgeMotor.setTargetPosition(-100);
					bridgeMotor.setPower(-0.25);
					motorState="backward";
				}
				break;
			case "backward":
				if (bridgeMotor.isBusy()==false){
					bridgeMotor.setTargetPosition(100);
					bridgeMotor.setPower(0.25);
					motorState="forward";
				}
				break;
		}*/

		if (bridgeMotor.isBusy()==false){
			bridgeMotor.setPower(0);
			bridgeMotor.setTargetPosition(0);
			bridgeMotor.setPower(-0.25);
		}
		telemetry.update();
		}


	@Override
	public void stop() {
	}

}
