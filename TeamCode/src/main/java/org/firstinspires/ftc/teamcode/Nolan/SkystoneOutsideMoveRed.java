package org.firstinspires.ftc.teamcode.Nolan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware;

@Autonomous(name="2R: Outside Skystone Move", group="Linear Opmode")
public class SkystoneOutsideMoveRed extends LinearOpMode {

	public boolean allianceRed = true;

	@Override
	public void runOpMode() {

		// Init
		Hardware hardware = new Hardware();
		hardware.init(hardwareMap);

		AutoDrive drive = new AutoDrive(hardware,this);
		AutoBot bot = new AutoBot(hardware,this);
		AutoVision vision = new AutoVision(hardware, this);

		vision.startUp();

		waitForStart();

		telemetry.addData("Status", "Initialized");
		telemetry.update();

		// Run Code
		vision.activate();
	}

}
