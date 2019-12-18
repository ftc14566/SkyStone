package org.firstinspires.ftc.teamcode.testing;

import android.content.Context;
import com.qualcomm.robotcore.eventloop.opmode.*;
import org.firstinspires.ftc.teamcode.AutoBot;
import org.firstinspires.ftc.teamcode.Hardware;

import java.io.*;

@TeleOp(name="Metrics Tester", group="Bot")
public class MetricsMode extends LinearOpMode {

	AutoBot robot;

	@Override
	public void runOpMode() {

		telemetry.addData("Status", "Initializing Autobot"); telemetry.update();
		Hardware hardware = new Hardware();
		hardware.init(hardwareMap);
		robot = new AutoBot(hardware,this);

		// create the controller
		SettingsSelector menu = new SettingsSelector();
		// wire it up to game pad 1
		PressController pad = new PressController( gamepad1 );
		pad.Listener = menu;

		waitForStart();

		while(opModeIsActive()){
			pad.exec();
			menu.showStatus(telemetry);
		}

	}


}
