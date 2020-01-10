
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


	public void waitUntilRunTime(double time) {
		while (opMode.opModeIsActive() && time <= opMode.getRuntime()) {
			opMode.sleep(50);
		}
	}

	public void grabFoundation() {
		hardware.leftFoundationServo.setPosition(1);
		hardware.rightFoundationServo.setPosition(0.52);
		opMode.sleep(1000);
	}

	public void releaseFoundation() {
		hardware.leftFoundationServo.setPosition(0.53);
		hardware.rightFoundationServo.setPosition(0.08);
		opMode.sleep(50);
	}
}
