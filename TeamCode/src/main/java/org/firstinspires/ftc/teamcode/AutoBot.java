
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class AutoBot {

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
}
