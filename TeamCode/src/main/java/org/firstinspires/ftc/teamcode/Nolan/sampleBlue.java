package org.firstinspires.ftc.teamcode.Nolan;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware;

@Autonomous(name="2B: Sample", group="Linear Opmode")
public class sampleBlue extends LinearOpMode {
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
		hardware.Lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GOLD);

        hardware.leftColorSensor.enableLed(true);
        hardware.rightColorSensor.enableLed(true);

		waitForStart();

		telemetry.addData("Status", "Initialized");
		telemetry.update();


		// Run Code
		lift.linearSlideSet();
		drive.moveForwardDistanceSensor(5,0.3,5);
		lift.grabBlock();
		//double addedDistance = drive.skystoneAlignRight();
        drive.driveForward(-15,-15,0.3);
        drive.strafeRight(-80, 0.4);
        lift.releaseBlock();
		drive.strafeRight(25, 0.4);
		lift.linearSlideIn();
		drive.driveForward(8,8,0.3);
	}

}
