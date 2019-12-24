package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.bravo.Config;

public class TestBot extends TestBotBase {

	public TestBot(LinearOpMode opMode){
		super(opMode);
		this.opMode = opMode;
		hardware = new Hardware(opMode.hardwareMap);
		blockGrabber = new BlockGrabber(hardware.grabberLeft,hardware.grabberRight);
	}

	// region servo

	public void blockGrabber(
			@Config(label="position", stringValue = "even",stringOptions = "grab,down,even,up") String position
	){

		blockGrabber.move(position);

		opMode.telemetry.addData("Servo Grabber To:", position );
		opMode.telemetry.addData("To return, press:", "gamepad1.b");
		opMode.telemetry.update();
		double endTime = opMode.time+2.0; // wait 2 seconds
		while(testModeIsActive() && opMode.time < endTime);

	}

	public void foundationGrabber(
			@Config(label="position", stringValue = "grab", stringOptions = "grab,up") String position
	){

		foundationGrabber.move(position);

		opMode.telemetry.addData("Foundation Grabber To:", position );
		opMode.telemetry.addData("To return, press:", "gamepad1.b");
		opMode.telemetry.update();
		double endTime = opMode.time+2.0; // wait 2 seconds
		while(testModeIsActive() && opMode.time < endTime);

	}

	public void moveServo(
			@Config(label="servo", stringOptions = "L-grab,R-Grab,L-Found,R-Found",stringValue = "L-grab") String selectServo,
			@Config(label="position", min=0.0, max=1.0, step=.01, displayScale = 100, value = 0.5, units = "%") double position,
			@Config(label="direction", isTrue=true, trueString = "forward", falseString = "backward") boolean directionForward,
			@Config(label="range-min", min=0.0, max=1.0, step=.01, displayScale = 100, value = 0.0, units = "%") double rangeMin,
			@Config(label="range-max", min=0.0, max=1.0, step=.01, displayScale = 100, value = 1.0, units = "%") double rangeMax
	){

		Servo s = null;
		switch(selectServo){
			case "L-grab": s=hardware.grabberLeft; break;
			case "R-grab": s=hardware.grabberRight; break;
			case "L-Found": s = hardware.leftFoundationServo; break;
			case "R-Found": s = hardware.rightFoundationServo; break;
			default: return;
		}

		// configure
		s.scaleRange(rangeMin,rangeMax);
		s.setDirection(directionForward ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);

		s.setPosition(position);

		opMode.telemetry.addData("Servo Position:", "%.0f", position*100);
		opMode.telemetry.addData("Servo Dir:", directionForward?"forward":"reverse");
		opMode.telemetry.addData("Servo Range:", "%.0f to %.0f", rangeMin*100,rangeMax*100);
		opMode.telemetry.addData("To return, press:", "gamepad1.b");
		opMode.telemetry.update();
		double endTime = opMode.time+2.0; // wait 2 seconds
		while(testModeIsActive() && opMode.time < endTime);

	}

	// endregion

	// region motor

	public void motorPower(
			@Config(label="motor", stringOptions = "FL,FR,RL,RR,L-Tower,R-Tower,Bridge") String motorStr,
			@Config(label="power", value = 0.2, min=-1.0, max=1.0, step=0.05, displayScale = 100, units = "%") double power,
			@Config(label="timeout", value = 30, min=5, max=120, step=5, units="sec") int timeout
	){
		DcMotor motor = pickMotor(motorStr);
		if(motor == null) return;
		super.motorRunUsingEncoder(motor,power,timeout);
	}

	public void motorPosition(
			@Config(label="motor", stringOptions = "FL,FR,RL,RR,L-Tower,R-Tower,Bridge") String motorStr,
			@Config(label="target pos", min=-5000, max=5000, step = 100, value = 100) int targetPosition,
			@Config(label="power", value = 0.2, min=-1.0, max=1.0, step=0.05, displayScale = 100, units = "%") double power,
			@Config(label="timeout", value = 30, min=5, max=120, step=5, units="sec") int timeout
	){
		DcMotor motor = pickMotor(motorStr);
		if(motor == null) return;
		super.motorRunToPosition(motor,targetPosition,power,timeout);
	}


	public void towerMotor(
		@Config(label="target pos", min=-5000, max=5000, step = 100, value = 100) int targetPosition,
		@Config(label="raise power", value = 0.2, min=0.0, max=1.0, step=0.05, displayScale = 100, units = "%") double upPower,
		@Config(label="lower power", value = 0.02, min=0.0, max=.20, step=0.005, displayScale = 100, units = "%") double downPower,
		@Config(label="timeout", value = 30, min=5, max=120, step=5, units="sec") int timeout
	) {
		DcMotor lMotor = hardware.leftTowerMotor;
		DcMotor rMotor = hardware.rightTowerMotor;

		// Go Up
		lMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		lMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		rMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		rMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		lMotor.setPower(upPower);
		rMotor.setPower(upPower);
		lMotor.setTargetPosition(targetPosition);
		rMotor.setTargetPosition(targetPosition);

		double startTime = opMode.time;
		double endTime = startTime + timeout;
		while (testModeIsActive()
				&& (endTime) > opMode.time
		) {
			int pos = lMotor.getCurrentPosition();
			opMode.telemetry.addData("raising position", pos);
			opMode.telemetry.addData("time remaining", "%.1f", endTime - opMode.time);
			opMode.telemetry.update();
		}

		// Go Down
		lMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		rMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		lMotor.setPower(downPower);
		rMotor.setPower(downPower);

		startTime = opMode.time;
		endTime = startTime + timeout;
		while (testModeIsActive()
				&& (endTime) > opMode.time
		) {
			int pos = lMotor.getCurrentPosition();
			opMode.telemetry.addData("lower position", pos);
			opMode.telemetry.addData("time remaining", "%.1f", endTime - opMode.time);
			opMode.telemetry.update();
		}
	}

	DcMotor pickMotor(String motorStr){
		switch(motorStr){
			case "FL": return hardware.frontLeftDrive;
			case "FR": return hardware.frontRightDrive;
			case "RL": return hardware.rearLeftDrive;
			case "RR": return hardware.rearRightDrive;
			case "L-Tower": return hardware.leftTowerMotor;
			case "R-Tower": return hardware.rightTowerMotor;
			//case "Bridge": return null;
		}
		return null;
	}

	// endregion


	// region private fields

	FoundationGrabber foundationGrabber;
	BlockGrabber blockGrabber;
	Hardware hardware;

	// endregion

}
