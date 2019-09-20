package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;

public class AutoBot {

	private Hardware hardware;
	private LinearOpMode opMode;

	public double defaultDriveSpeed = 0.2;
	public double defaultSpinSpeed = 0.2;
	public double defaultStrafeSpeed = 0.2;

	public AutoBot(Hardware hardware, LinearOpMode opMode){
		this.hardware = hardware;

		// TODO: Add any autonomous-specific initialization here:
		//hardware.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		//hardware.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		//hardware.rearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		//hardware.rearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	}

	public void DriveForward(double inches){
		DriveForward(inches,defaultDriveSpeed);
	}

	public void DriveForward(double inches, double speed){
		// TODO: implement drive forward x-inches
		while(opMode.opModeIsActive()){
		}
	}

	public void SpinRight(double degrees){
		SpinRight(degrees,defaultSpinSpeed);
	}

	public void SpinRight(double degrees, double speed){
		// TODO: implement spin clockwise n-degrees
		while(opMode.opModeIsActive()){
		}
	}

	public void StrafeRight(double inches){
		this.StrafeRight(inches,defaultStrafeSpeed);
	}

	public void StrafeRight(double inches,double speed){
		// TODO: put code here that makes robot move to the right
		while(opMode.opModeIsActive()) {
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
