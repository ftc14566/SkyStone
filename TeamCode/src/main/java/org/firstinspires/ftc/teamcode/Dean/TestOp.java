package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.bravo.MethodExplorer;

@Autonomous(name="Meterics Testing", group="dean")
public class TestOp extends LinearOpMode {

	@Override
	public void runOpMode() throws InterruptedException {

		MethodExplorer explorer = new MethodExplorer(gamepad1);

		explorer.setTarget(new TestBot(this));
//		explorer.bindToFile(new ConfigFile("metrics_steps.json", hardwareMap.appContext));
		telemetry.addData("steps loaded from config.", "");
		telemetry.update();

		waitForStart();

		while (this.opModeIsActive()) {

			explorer.trackGamePad();

			explorer.displayStatus(telemetry);

		}
	}


}
