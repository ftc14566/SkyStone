package org.firstinspires.ftc.teamcode.bravo;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.Gamepad;
import java.lang.reflect.Method;

@Autonomous(name="BravoMode", group="Testing")
public class BravoMode extends LinearOpMode {

	@Override
	public void runOpMode() throws InterruptedException {

		TestHardware hardware = new TestHardware(hardwareMap);
		_bot = new TestBot(hardware,this);

		Bravo bravo = new Bravo(_bot);

		waitForStart();

		while(this.opModeIsActive()){

			bravo.TrackGamePad(gamepad1);

			bravo.displayStatus(telemetry);

		}

	}


    TestBot _bot;

}
