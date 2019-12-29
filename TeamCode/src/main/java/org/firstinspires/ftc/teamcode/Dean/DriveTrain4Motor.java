package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class DriveTrain4Motor {

	public DriveTrain4Motor(DcMotor leftFront, DcMotor leftRear, DcMotor rightFront, DcMotor rightRear){
		this.leftFront = leftFront;
		this.leftRear = leftRear;
		this.rightFront = rightFront;
		this.rightRear = rightRear;

		this.leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
		this.leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
		this.rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
		this.rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
	}

	public void driveAndStrafe (double forward, double turnRight, double strafeRight){

		// combine drive,turn,strafe
		double fl = forward + turnRight + strafeRight;

		double fr = forward - turnRight - strafeRight;
		double rl = forward + turnRight - strafeRight;
		double rr = forward - turnRight + strafeRight;

		// limit each drom to 1.0 max
		double maxPower = Math.max(Math.abs(fl),Math.abs(fr));
		maxPower = Math.max(maxPower,Math.abs(rl));
		maxPower = Math.max(maxPower,Math.abs(rr));
		if(maxPower>1.0){
			fl/=maxPower;
			fr/=maxPower;
			rl/=maxPower;
			rr/=maxPower;
		}

		leftFront.setPower(fl);
		rightFront.setPower(fr);
		leftRear.setPower(rl);
		rightRear.setPower(rr);
	}

	// region Tank-like methods

	public void leftPower(double leftPower){
		leftFront.setPower(leftPower);
		leftRear.setPower(leftPower);
	}

	public void rightPower(double rightPower){
		rightFront.setPower(rightPower);
		rightRear.setPower(rightPower);
	}

	public void leftSetTargetPosition(int leftPosition){
		leftFront.setTargetPosition(leftPosition);
		leftRear.setTargetPosition(leftPosition);
	}

	public void rightSetTargetPosition(int rightPosition){
		rightFront.setTargetPosition(rightPosition);
		rightRear.setTargetPosition(rightPosition);
	}

	// endregion

	// region modes

	public void stopAndReset(){
		setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	}

	public void setMode(DcMotor.RunMode mode){
		leftFront.setMode(mode);
		leftRear.setMode(mode);
		rightFront.setMode(mode);
		rightRear.setMode(mode);
	}

	// endregion

	// region private fields
	DcMotor leftFront;
	DcMotor leftRear;
	DcMotor rightFront;
	DcMotor rightRear;
	// endregion

}
