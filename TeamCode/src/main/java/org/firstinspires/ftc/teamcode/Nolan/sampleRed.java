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

        hardware.leftColorSensor.enableLed(true);
        hardware.rightColorSensor.enableLed(true);

		waitForStart();

		telemetry.addData("Status", "Initialized");
		telemetry.update();


		// Run Code
		lift.linearSlideSet();
		drive.moveForwardDistanceSensor(10,0.3,5);
		double addedDistance = drive.skystoneAlignRight();
        drive.driveForward(-5,-50,0.3);
        drive.strafeRight(30 + addedDistance, 0.4);
        lift.releaseBlock();
	}

}
