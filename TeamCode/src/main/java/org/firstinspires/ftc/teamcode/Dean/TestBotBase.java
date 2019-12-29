package org.firstinspires.ftc.teamcode.Dean;

import android.graphics.Color;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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

	public TestBotBase(LinearOpMode opMode){
		this.opMode = opMode;
		clearTimeout();
	}

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
	protected void trackTime(){ startTime = opMode.time; }
	protected double elapsed(){ return opMode.time - startTime; }
	protected void waitFor(double durationSeconds){ setTimeout(durationSeconds);while(testModeIsActive());clearTimeout(); }

	protected LinearOpMode opMode;
	private double timeoutTime;
	private double startTime;

}
