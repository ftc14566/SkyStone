
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class AutoBot {

	public AutoBot( Hardware hardware, LinearOpMode mode ) {
		this.hardware = hardware;
		this.opMode = mode;
	}

	private Hardware hardware;
	private LinearOpMode opMode;

	public void waitUntilRunTime(int time){
			while(opMode.opModeIsActive() && time < opMode.getRuntime()){
			opMode.sleep(50);
			}
	}

	public void grabFoundation(){
		hardware.foundationServoLeft.setPosition(1);
		hardware.foundationServoRight.setPosition(1);
	}


	public  void releaseFoundation(){
		hardware.foundationServoLeft.setPosition(0);
		hardware.foundationServoRight.setPosition(0);
	}

	public void SpinRight(double degrees, double speed){

		while(opMode.opModeIsActive()){
//			resetEncoder();
		}
	}

	public void StrafeRight(double inches,double speed){

		while(opMode.opModeIsActive()) {
			//resetEncoder();
		}
	}

	public void Test(int i, double d, String s,boolean b){

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
