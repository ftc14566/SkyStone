package org.firstinspires.ftc.teamcode.bravo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="FTC-Bob Testing", group="dean")
public class BobMode extends LinearOpMode {

	@Override
	public void runOpMode() throws InterruptedException {

		MethodExplorer explorer = new MethodExplorer(hardwareMap);

		explorer.setTarget( new BobBot(this ) );
//		explorer.setTarget( new AutoBot( new Hardware(this.hardwareMap),this) );

		waitForStart();

		while(this.opModeIsActive()){

			explorer.TrackGamePad(gamepad1);

			explorer.displayStatus(telemetry);

		}

	}

}
