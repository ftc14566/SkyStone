package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.firstinspires.ftc.teamcode.bravo.ConfigFile;
import org.firstinspires.ftc.teamcode.bravo.Linq;
import org.firstinspires.ftc.teamcode.bravo.MethodExplorer;
import org.firstinspires.ftc.teamcode.bravo.ParamValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

@Autonomous(name="Simple Hardware Tests", group="dean")
public class SimpleHardwareTestMode extends LinearOpMode {

	@Override
	public void runOpMode() throws InterruptedException {

		Iterator<HardwareDevice> itt = hardwareMap.iterator();
		while(itt.hasNext()){
			HardwareDevice device = itt.next();
			Set<String> names = hardwareMap.getNamesOf(device);
			for(String name : names )
				ParamValue.registerValue(name, device);
		}

		MethodExplorer explorer = new MethodExplorer(gamepad1);
		explorer.setTarget( new SimpleHardwareTestMethods(this ));
//		explorer.bindToFile( new ConfigFile("bob_steps.json", hardwareMap.appContext) );
//		telemetry.addData("steps loaded from config.","");
		telemetry.addData("ready to begin testing.","");
		telemetry.update();

		waitForStart();

		while(this.opModeIsActive()){

			explorer.trackGamePad();

			explorer.displayStatus(telemetry);

		}

	}

	private void reportHardwareDevices(){
		ArrayList<String> lines = new ArrayList<String>();

		Iterator<HardwareDevice> itt = hardwareMap.iterator();
		while(itt.hasNext()){
			HardwareDevice device = itt.next();
			String typeName = Linq.getShortName(device.getClass());
			if(!typeName.startsWith("Lynx"))
				lines.add('['+typeName+':'+Linq.join(",", hardwareMap.getNamesOf(device))+']');
		}
		if(lines.size()>0) throw new IllegalStateException(Linq.join("",lines));

	}


}
