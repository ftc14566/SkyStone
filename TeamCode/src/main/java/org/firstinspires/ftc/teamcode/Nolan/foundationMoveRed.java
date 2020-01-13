package org.firstinspires.ftc.teamcode.Nolan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.Nolan.AutoBot;

@Autonomous(name="1R: Foundation Move", group="Linear Opmode")
public class foundationMoveRed extends LinearOpMode {

	public boolean allianceRed = true;

	@Override
	public void runOpMode() {

		// Init
		Hardware hardware = new Hardware();
		hardware.init(hardwareMap);
		AutoDrive drive = new AutoDrive(hardware,this);
		AutoBot bot = new AutoBot(hardware,this);

        //bot.releaseFoundation();

		waitForStart();

		telemetry.addData("Status", "Initialized");
		telemetry.update();


		// Run Code
		drive.driveForward(5,5,0.4);
		drive.strafeRight(20,0.4);
		drive.moveForwardDistanceSensor(4,0.4,3);
		bot.grabFoundation();
		//drive.strafeRight(-7,0.4);
		drive.driveForward(-35,-35,0.5);
		drive.spinRight(600,0.7);
		//drive.spinRight(20,0.4);
		//drive.driveStrait(15,0.3);
		//drive.strafeRight(50, 0.3);
		bot.releaseFoundation();
		drive.strafeRight(25,0.4);
		//bot.waitUntilRunTime(25);
		//drive.strafeRight(17,0.5);
		//drive.spinRight(-25,0.4);
		drive.driveStrait(-42,0.7);
		//drive.strafeRight(10,0.3);
	}

}
