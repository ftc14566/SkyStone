package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Tower {

	private DcMotorPair motors;

	public Tower(DcMotor leftMotor, DcMotor rightMotor){
		motors = new DcMotorPair(leftMotor,rightMotor);
	}



}
