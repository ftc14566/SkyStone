package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="AutoOpModeExample", group="Linear Opmode")
public class ExampleAutoOpMode extends LinearOpMode {

	@Override
	public void runOpMode() {

		// Init
		Hardware hardware = new Hardware();
		hardware.init(hardwareMap);
		AutoBot bot = new AutoBot(hardware,this);

		waitForStart();

		telemetry.addData("Status", "Initialized");
		telemetry.update();

		// Run Code
		bot.DriveForward(10, 0.5);
		bot.SpinRight(90, 0.5);
		bot.DriveForward(-200, 0.5);

	}

}
