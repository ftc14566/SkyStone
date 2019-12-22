package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.bravo.ConfigFile;
import org.firstinspires.ftc.teamcode.bravo.MethodExplorer;

@Autonomous(name="FTC-Bob Testing", group="dean")
public class BobMode extends LinearOpMode {

	@Override
	public void runOpMode() throws InterruptedException {

		MethodExplorer explorer = new MethodExplorer();
		explorer.setTarget( new BobBot(this ) );
		explorer.bindToFile( new ConfigFile("bob_steps.json", hardwareMap.appContext) );
		telemetry.addData("steps loaded from config.","");
		telemetry.update();

		waitForStart();

		while(this.opModeIsActive()){

			explorer.TrackGamePad(gamepad1);

			explorer.displayStatus(telemetry);

		}

	}

}
