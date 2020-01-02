
package org.firstinspires.ftc.teamcode.Ben.BenAuto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware;

public class BenAutoBot {

	public BenAutoBot(Hardware hardware, LinearOpMode mode) {
		this.hardware = hardware;
		this.opMode = mode;
		foundationGrabber = new BenFoundationGrabber(hardware.leftFoundationServo, hardware.rightFoundationServo);
	}

	private Hardware hardware;
	private LinearOpMode opMode;
	BenFoundationGrabber foundationGrabber;

	public void waitUntilRunTime(int time) {
		while (opMode.opModeIsActive() && time < opMode.getRuntime()) {
			opMode.sleep(50);
		}
	}
	
	public void driveAndStrafe (double targetFoward, double targetTurnRight, double targetStrafeRight){
		double forward = 0.0;
		double strafe = 0.0;
		double turn = 0.0;
		
		forward = targetFoward/2;
		turn = targetTurnRight/2;
		strafe = targetStrafeRight/2;
		
		// combine drive,turn,strafe
		double fl = forward + turn + strafe;
		double fr = forward - turn - strafe;
		double rl = forward + turn - strafe;
		double rr = forward - turn + strafe;
		
		// limit each drom to 1.0 max
		double maxPower = Math.max(Math.abs(fl),Math.abs(fr));
		maxPower = Math.max(maxPower,Math.abs(rl));
		maxPower = Math.max(maxPower,Math.abs(rr));
		if(maxPower>1.0){
			fl/=maxPower;
			fr/=maxPower;
			rl/=maxPower;
			rr/=maxPower;
		}
		
		hardware.frontLeftDrive.setPower(fl);
		hardware.frontRightDrive.setPower(fr);
		hardware.rearLeftDrive.setPower(rl);
		hardware.rearRightDrive.setPower(rr);
		
	}
	
	
	public void moveToFoundationFromBuildingZone(){
	
	}

	
	


	public void releaseFoundation() {
		//hardware.foundationServoLeft.setPosition(0);
		//hardware.foundationServoRight.setPosition(0);
	}
}
