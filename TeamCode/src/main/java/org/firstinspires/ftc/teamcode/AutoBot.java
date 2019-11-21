package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutoBot", group="Linear Opmode")
public class AutoBot {

	private Hardware hardware;
	private LinearOpMode opMode;

	public double defaultDriveSpeed = 0.2;
	public double defaultSpinSpeed = 0.2;
	public double defaultStrafeSpeed = 0.2;
	static final double     COUNTS_PER_MOTOR_REV    = 288 ;
	static final double     DRIVE_GEAR_REDUCTION    = 2 ;     // This is < 1.0 if geared UP
	static final double     WHEEL_DIAMETER_INCHES   = 6.75 ;// For figuring circumference
	static final double     WHEEL_SEPARATION = 15.25 ;
	static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV) /
			(WHEEL_DIAMETER_INCHES * 3.1415);
	static final double     TURN_SPEED              = 0.3;
	static final double     TIME_PER_INCH           = 0.5;

	public void resetEncoder(){
		hardware.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		hardware.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		hardware.rearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		hardware.rearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	}
	public void encoderRun() {
		hardware.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		hardware.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		hardware.rearLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		hardware.rearRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
	}


	public AutoBot(Hardware hardware, LinearOpMode opMode){
		this.hardware = hardware;
		resetEncoder();

	}

	public void DriveForward(double inches, double speed ){

		while(opMode.opModeIsActive()){
			resetEncoder();
		}
	}

	public void SpinRight(double degrees, double speed){

		while(opMode.opModeIsActive()){
			resetEncoder();
		}
	}

	public void StrafeRight(double inches,double speed){

		while(opMode.opModeIsActive()) {
			resetEncoder();
		}
	}


	//============================================
	// TODO: put other high level autonomous operations here.
	//============================================
	//public void GrabBlock(){}
	//public void ReleaseBlock(){}
	//public void RaiseElevator(double inches){}
	//public void ExtendArm(){}
	//public void RetractArm(){}


}
