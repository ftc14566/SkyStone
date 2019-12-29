package org.firstinspires.ftc.teamcode.Dean;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.TeleBot;

public class DeanBot extends TeleBot {

	public DeanBot(Hardware hardware){
		super(hardware);
	}

	public void deanLift(boolean up, boolean down){
		double power = 0.2;
		if(up)power = 1.0;
		if(down)power = 0.01;

		hardware.leftTowerMotor.setPower(power);
		hardware.rightTowerMotor.setPower(power);
	}

}
