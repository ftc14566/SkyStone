package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.bravo.ButtonTracker;
import org.firstinspires.ftc.teamcode.bravo.Config;

public class TestBot extends TestBotBase {

	public TestBot(LinearOpMode opMode){
		super(opMode);
		this.opMode = opMode;
		Hardware hardware = new Hardware(opMode.hardwareMap);
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

	// endregion

	// region motor pairs

	public void mPairPosition(
			DcMotorPair motor,
			@Config(label = "target", min = 0, max=5000, step=100, value=300, units = " counts")int targetPosition,
			@Config(label = "power", min = 0, max=1.0, step=0.01, value=0.2, displayScale = 100, units = "%") double power,
			@Config(label = "target delta", min = 0, max=1000, step=10, value=100, units = " counts")int positionStepSize
	){
		motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		motor.setPower(power);
		motor.setTargetPosition(targetPosition); // must come before .setMode(RUN_TO_POSITION)

		motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		ButtonTracker tracker = new ButtonTracker(opMode.gamepad1);

		while(testModeIsActive()) {
			if(tracker.dpadRightPressed()) {targetPosition += positionStepSize; motor.setTargetPosition(targetPosition);}
			if(tracker.dpadLeftPressed()) {targetPosition -= positionStepSize; motor.setTargetPosition(targetPosition);}

			int pos = motor.getCurrentPosition();
			double rev = pos/ SimpleHardwareTestMethods.COUNTS_PER_MOTOR_REV;
			opMode.telemetry.addData("Motor Position - Interactive", "");
			opMode.telemetry.addData("Target/Power:", "%d %.0f", targetPosition, power*100 );
			opMode.telemetry.addData("curPos (#,rev)", "%d %.2f", pos, rev);
			opMode.telemetry.addData("Change position using:", "dpad-left/dpad-right");
			tellUserHowToExit();
			opMode.telemetry.update();
		}

		motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		waitForBRelease();
	}

	public void mPairSpeed(
			DcMotorPair motor,
			@Config(label = "speed", min = -1.0, max=1.0, value = 0.2, step=0.01, displayScale = 100, units = "%") double speed,
			@Config(label = "speed delta", min = 0, max=0.2, step=.005, value=0.1, displayScale = 100, units = "%")double speedStepSize
	){
		motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		motor.setPower(speed);

		ButtonTracker tracker = new ButtonTracker(opMode.gamepad1);

		while(testModeIsActive()) {
			if(tracker.dpadRightPressed()) {speed += speedStepSize; motor.setPower(speed);}
			if(tracker.dpadLeftPressed()) {speed -= speedStepSize; motor.setPower(speed);}

			int pos = motor.getCurrentPosition();
			double rev = pos/ SimpleHardwareTestMethods.COUNTS_PER_MOTOR_REV;
			opMode.telemetry.addData("Motor Speed - Interactive", "");
			opMode.telemetry.addData("Speed:", "%.0f", speed*100 );
			opMode.telemetry.addData("curPos (#,rev)", "%d %.2f", pos, rev);
			opMode.telemetry.addData("Change speed using:", "dpad-left/dpad-right");
			tellUserHowToExit();
			opMode.telemetry.update();
		}

		motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		waitForBRelease();
	}

	public void mPairPower(
			DcMotorPair motor,
			@Config(label = "power", min = -1.0, max=1.0, value = 0.2, step=0.01, displayScale = 100, units = "%") double power,
			@Config(label = "power delta", min = 0, max=0.2, step=.005, value=0.1, displayScale = 100, units = "%")double powerStepSize
	){
		motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		motor.setPower(power);

		ButtonTracker tracker = new ButtonTracker(opMode.gamepad1);

		while(testModeIsActive()) {
			if(tracker.dpadRightPressed()) {power += powerStepSize; motor.setPower(power);}
			if(tracker.dpadLeftPressed()) {power -= powerStepSize; motor.setPower(power);}

			int pos = motor.getCurrentPosition();
			double rev = pos/ SimpleHardwareTestMethods.COUNTS_PER_MOTOR_REV;
			opMode.telemetry.addData("Motor Power - Interactive", "");
			opMode.telemetry.addData("Power:", "%.0f", power*100 );
			opMode.telemetry.addData("curPos (#,rev)", "%d %.2f", pos, rev);
			opMode.telemetry.addData("Change power using:", "dpad-left/dpad-right");
			tellUserHowToExit();
			opMode.telemetry.update();
		}

		motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		waitForBRelease();
	}


	// endregion

	public void moveGrabber(
			BlockGrabber grabber,
			@Config(label="position", stringValue = "even",stringOptions = "grab,down,even,up") String position
	){
		grabber.move(position);

		opMode.telemetry.addData("Servo Grabber To:", position );
		opMode.telemetry.addData("To return, press:", "gamepad1.b");
		opMode.telemetry.update();
		double endTime = opMode.time+2.0; // wait 2 seconds
		while(testModeIsActive() && opMode.time < endTime);

	}

	// region private fields

	FoundationGrabber foundationGrabber;
	BlockGrabber blockGrabber;


	// endregion

}
