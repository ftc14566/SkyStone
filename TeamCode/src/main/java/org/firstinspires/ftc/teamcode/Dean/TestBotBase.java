package org.firstinspires.ftc.teamcode.Dean;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.bravo.ButtonTracker;
import org.firstinspires.ftc.teamcode.bravo.Config;

// Contains hardware testing methods that are not bound to any hardware
// and therefore can be used with bot configuration.
public class TestBotBase {

	// region constructor

	public TestBotBase(LinearOpMode opMode){
		this.opMode = opMode;
		clearTimeout();
	}

	// endregion

	// region DcMotor

	public void motorRunWithoutEncoder(DcMotor motor, double power, int timeout){
		motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		motor.setPower(power);
		runMotor(motor,timeout);
	}


	public void motorRunUsingEncoder(DcMotor motor, double power, int timeout){
		motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		motor.setPower(power);
		runMotor(motor, timeout);
	}

	public void motorRunToPosition(DcMotor motor, int targetPosition, double power, int timeout){
		motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		motor.setPower(power);
		motor.setTargetPosition(targetPosition);
		runMotor(motor, timeout);
	}

	private void runMotor(DcMotor motor, int timeout) {
		setTimeout(timeout);
		int pos=0;
		double rev=0.0;
		double starTime = opMode.time;
		while(testModeIsActive()){
			pos = motor.getCurrentPosition();
			rev = pos/BobHardware.COUNTS_PER_MOTOR_REV;
			opMode.telemetry.addData("pos (#,rev)", "%i %.2f", pos, rev);
			tellUserTimeRemaining();
			tellUserHowToExit();
			opMode.telemetry.update();
		}
		clearTimeout();
		motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		double elapsed = opMode.time-starTime;
		int posRate = (int)Math.round(pos/elapsed);
		double revRate = rev/elapsed;

		// leave results on screen
		opMode.telemetry.addData("--Mode Complete--", "");
		opMode.telemetry.addData("pos (#,rev)", "%i %.2f", pos, rev);
		opMode.telemetry.addData("rate (#/s,rev/s)", "%i %.2f", posRate, revRate);
		tellUserHowToExit();
		opMode.telemetry.update();

		waitForBRelease();
		waitForBPress();
		waitForBRelease();
	}


	// endregion

	// region Servo

	public void moveServo( Servo s, double position, boolean directionForward, double rangeMin, double rangeMax){

		// configure
		s.scaleRange(rangeMin,rangeMax);
		s.setDirection(directionForward ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);

		ButtonTracker tracker = new ButtonTracker(opMode.gamepad1);

		while(testModeIsActive()) {
			if(tracker.dpadRightPressed()) position += 0.01;
			if(tracker.dpadLeftPressed()) position -= 0.01;
			s.setPosition(position);
			opMode.telemetry.addData("Servo Position:", "%.0f", position * 100);
			opMode.telemetry.addData("Change using:", "dpad-left/dpad-right");
			opMode.telemetry.addData("Servo Dir:", directionForward ? "forward" : "reverse");
			opMode.telemetry.addData("Servo Range:", "%.0f to %.0f", rangeMin * 100, rangeMax * 100);
			tellUserHowToExit();
			opMode.telemetry.update();
		}

	}

	// endregion

	// region Sensors

	public void trackV3ColorSensor(ColorSensor colorSensor, DistanceSensor distanceSensor ){

		clearTimeout();
		while(testModeIsActive()){
			int red = colorSensor.red() >> 3; // convert from 5-bit to 8bit
			int green = colorSensor.green() >> 3;
			int blue = colorSensor.blue() >> 3;

			// convert the RGB values to HSV values.
			float hsvValues[] = {0F,0F,0F};
			Color.RGBToHSV( red, green, blue, hsvValues);

			opMode.telemetry.addData("red green blue", red+" "+green+" "+blue);
			opMode.telemetry.addData("H/S/V", "%.2f %.2f %.2f", hsvValues[0],hsvValues[1],hsvValues[2] );
			opMode.telemetry.addData("Distance (cm)", "%.2f", distanceSensor.getDistance( DistanceUnit.CM ) );
			tellUserHowToExit();
			opMode.telemetry.update();
		}

		waitForBRelease();

	}

	public void trackDistanceSensor( DistanceSensor sensor, DistanceUnit unit ){

		while(testModeIsActive()){
			opMode.telemetry.addData("Distance ("+unit.toString()+")", sensor.getDistance(unit) );
			tellUserHowToExit();
			opMode.telemetry.update();
		}
		waitForBRelease();

	}

	// endregion

	// region private helper methods

	protected boolean testModeIsActive(){
		return opMode.opModeIsActive() && !opMode.gamepad1.b && opMode.time < this.timeoutTime;
	}

	protected boolean testModeIsPaused(){ return opMode.opModeIsActive() && opMode.gamepad1.b; }
	protected void waitForBPress(){ while(testModeIsActive()); }
	protected void waitForBRelease(){ while(testModeIsPaused()); }
	protected void tellUserHowToExit(){ opMode.telemetry.addData("To exit press:", "GamePad1.B" ); }
	protected void tellUserTimeRemaining(){ opMode.telemetry.addData("time remaining", "%.1f", timeoutTime- opMode.time); }
	protected void clearTimeout(){ setTimeout(60*60*24); } // 1 day
	protected void setTimeout(double durationSeconds){ this.timeoutTime = opMode.time + durationSeconds; }
	protected void waitFor(double durationSeconds){ setTimeout(durationSeconds);while(testModeIsActive());clearTimeout(); }

	// endregion

	// region private fields

	protected LinearOpMode opMode;

	private double timeoutTime;

	// endregion

}
