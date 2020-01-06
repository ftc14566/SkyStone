package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.hardware.DcMotor;

// for motors that must run in unison
public class DcMotorPair {

	private DcMotor motor0;
	private DcMotor motor1;

	public DcMotorPair(DcMotor motor0,DcMotor motor1){
		this.motor0 = motor0;
		this.motor1 = motor1;
	}

	public void setMode(DcMotor.RunMode mode){
		motor0.setMode(mode);
		motor1.setMode(mode);
	}

	public void setTargetPosition(int targetPosition){
		motor0.setTargetPosition(targetPosition);
		motor1.setTargetPosition(targetPosition);
	}

	public void setPower(double power){
		motor0.setPower(power);
		motor1.setPower(power);
	}

	public int getCurrentPosition(){
		return (motor0.getCurrentPosition()+motor1.getCurrentPosition())/2;
	}
}
