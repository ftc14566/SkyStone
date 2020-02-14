package org.firstinspires.ftc.teamcode.Nolan;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
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

        hardware.Lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);

        hardware.leftColorSensor.enableLed(true);
        hardware.rightColorSensor.enableLed(true);

		waitForStart();

		telemetry.addData("Status", "Initialized");
		telemetry.update();


		// Run Code
		lift.linearSlideSet();
		drive.moveForwardDistanceSensor(14,0.3,99);
		//drive.moveForwardDistanceSensor(4,0.1,99);
		//drive.skystoneAlignRed();
		lift.grabBlock();
		lift.upLifter();
		//double addedDistance = drive.skystoneAlignRight();
        drive.driveForward(-15,-15,0.3);
        drive.strafeRight(80, 0.4);
        lift.releaseBlock();
		drive.strafeRight(-25, 0.4);
		lift.linearSlideIn();
		drive.driveForward(6,6,0.3);
	}

}
