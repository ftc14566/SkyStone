package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="AutoBot", group="Linear Opmode")
@Disabled
public class AutoBot {

	private Hardware hardware;
	private LinearOpMode opMode;

	public void waitUntilRunTime(int time){
		while(opMode.opModeIsActive() && time < opMode.getRuntime()){
			opMode.idle();
		}
	}

}
