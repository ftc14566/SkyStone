package org.firstinspires.ftc.teamcode.Nolan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware;

@Autonomous(name="1B: Foundation Move", group="Linear Opmode")
public class foundationMoveBlue extends LinearOpMode {

	public boolean allianceRed = true;

	@Override
	public void runOpMode() {

		// Init
		Hardware hardware = new Hardware();
		hardware.init(hardwareMap);
		AutoDrive drive = new AutoDrive(hardware,this);
		AutoBot bot = new AutoBot(hardware,this);

        bot.releaseFoundation();

		waitForStart();

		telemetry.addData("Status", "Initialized");
		telemetry.update();


		// Run Code
		drive.moveForwardDistanceSensor(4,0.3,3);
		bot.grabFoundation();
		drive.spinRight(-90,0.4);
		drive.driveStrait(10,0.3);
		drive.strafeRight(-27, 0.3);
		bot.releaseFoundation();
		bot.waitUntilRunTime(25);
		drive.driveStrait(-48,0.3);
	}

}
