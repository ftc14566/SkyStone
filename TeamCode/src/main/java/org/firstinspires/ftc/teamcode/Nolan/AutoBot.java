
package org.firstinspires.ftc.teamcode.Nolan;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware;

public class AutoBot {

	public AutoBot(Hardware hardware, LinearOpMode mode) {
		this.hardware = hardware;
		this.opMode = mode;
	}

	private Hardware hardware;
	private LinearOpMode opMode;

	public void waitUntilRunTime(int time) {
		while (opMode.opModeIsActive() && time < opMode.getRuntime()) {
			opMode.sleep(50);
		}
	}

	public void grabFoundation() {
		//hardware.foundationServoLeft.setPosition(1);
		//hardware.foundationServoRight.setPosition(1);
	}


	public void releaseFoundation() {
		//hardware.foundationServoLeft.setPosition(0);
		//hardware.foundationServoRight.setPosition(0);
	}
}
