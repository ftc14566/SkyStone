package org.firstinspires.ftc.teamcode.Ben.BenAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware;

@Autonomous(name="1R: Foundation Move", group="Linear Opmode")
@Disabled
public class BenFoundationMoveRed extends LinearOpMode {

	public boolean allianceRed = true;

	@Override
	public void runOpMode() {

		// Init
		Hardware hardware = new Hardware();
		hardware.init(hardwareMap);
		BenAutoDrive drive = new BenAutoDrive(hardware,this);
		BenAutoBot bot = new BenAutoBot(hardware,this);

		waitForStart();

		telemetry.addData("Status", "Initialized");
		telemetry.update();

		// Run Code
		//drive.moveForwardDistanceSensor(2,0.3,999);
		drive.encoderRun();
		bot.driveAndStrafe(0,0,10); //TODO ENCODER VALUES
		
		//bot.grabFoundation();
		//move to building zone
		//bot.releaseFoundation();
		//bot.waitUntilRunTime(25);
		//move to line
	}

}
