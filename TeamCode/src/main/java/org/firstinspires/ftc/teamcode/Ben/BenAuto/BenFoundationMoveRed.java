package org.firstinspires.ftc.teamcode.Ben.BenAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware;

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

		waitForStart();

		telemetry.addData("Status", "Initialized");
		telemetry.update();

		// Run Code
		drive.moveForwardDistanceSensor(2,0.3,999);
		//bot.grabFoundation();
		//move to building zone
		//bot.releaseFoundation();
		//bot.waitUntilRunTime(25);
		//move to line
	}

}
