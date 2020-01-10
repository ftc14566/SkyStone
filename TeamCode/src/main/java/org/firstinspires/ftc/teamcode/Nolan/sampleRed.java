package org.firstinspires.ftc.teamcode.Nolan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware;

@Autonomous(name="2R: Sample", group="Linear Opmode")
public class sampleRed extends LinearOpMode {
	public boolean allianceRed = true;
	@Override
	public void runOpMode() {

		// Init
		Hardware hardware = new Hardware();
		hardware.init(hardwareMap);
		AutoDrive drive = new AutoDrive(hardware,this);
		AutoBot bot = new AutoBot(hardware,this);
		AutoLift lift = new AutoLift(hardware,this);
        //bot.releaseFoundation();
        lift.releaseBlock();

		waitForStart();

		telemetry.addData("Status", "Initialized");
		telemetry.update();


		// Run Code
		lift.linearSlideSet();
		drive.moveForwardDistanceSensor(10,0.3,5);
		lift.grabBlock();
		lift.upLifter();
		drive.driveForward(-7,-7,0.3);
		drive.strafeRight(80,0.4);
		lift.downLifter();
		lift.releaseBlock();
		drive.strafeRight(-30,0.4);
		drive.driveForward(7,7,0.3);
	}

}
