package org.firstinspires.ftc.teamcode;

public class TeleBot {

	private Hardware hardware;

	public TeleBot( Hardware hardware ){
		this.hardware = hardware;
	}

	public void DriveForward(double speed){
		this.Move(speed,0.0);
	}

	public void Stop(){
		this.Move(0.0,0.0);
	}

	public void StrafeRight(double speed){
		this.Move(0,speed);
	}

	/*public void SpinRight(double speed){;
		hardware.frontLeftDrive.setPower(-speed);
		hardware.frontRightDrive.setPower(-speed);
		hardware.rearLeftDrive.setPower(-speed);
		hardware.rearRightDrive.setPower(-speed);
	}
*/

	public void Move(double forward, double rightSpeed){

		double sumLeft=Math.abs(forward)+Math.abs(rightSpeed);
		if(sumLeft > 1.0){
			forward/=sumLeft;
			rightSpeed/=sumLeft;
		}

		hardware.frontLeftDrive.setPower(-forward-rightSpeed);//
		hardware.rearLeftDrive.setPower(-forward+rightSpeed);//
		hardware.frontRightDrive.setPower(forward-rightSpeed);//
		hardware.rearRightDrive.setPower(forward+rightSpeed);//

	}

	public void Lift(boolean up, boolean down){

		double power=0.2;
		if(up&&!down)power=.6;
		if(down&&!up)power=-.1;

		hardware.leftTowerMotor.setPower(power);
		hardware.rightTowerMotor.setPower(power);
	}


	private double AdjustInputs(double x){
		return x*x*x;
	}

	public void MoveLR(double forwardSpeedLeft, double rightSpeedLeft,double forwardSpeedRight, double rightSpeedRight){

		forwardSpeedLeft = AdjustInputs(forwardSpeedLeft);
		forwardSpeedRight = AdjustInputs(forwardSpeedRight);
		rightSpeedLeft = AdjustInputs(rightSpeedLeft);
		rightSpeedRight = AdjustInputs(rightSpeedRight);

		double sumLeft=Math.abs(forwardSpeedLeft)+Math.abs(rightSpeedLeft);
		if(sumLeft > 1.0){
			forwardSpeedLeft/=sumLeft;
			rightSpeedLeft/=sumLeft;
		}

		double sumRight=Math.abs(forwardSpeedRight)+Math.abs(rightSpeedRight);
		if(sumRight > 1.0){
			forwardSpeedRight/=sumRight;
			rightSpeedRight/=sumRight;
		}

		hardware.frontLeftDrive.setPower(-forwardSpeedLeft-rightSpeedLeft);//
		hardware.rearLeftDrive.setPower(-forwardSpeedLeft+rightSpeedLeft);//
		hardware.frontRightDrive.setPower(forwardSpeedRight-rightSpeedRight);//
		hardware.rearRightDrive.setPower(forwardSpeedRight+rightSpeedRight);//

	}
	public void RaiseElevator(){}
	public void LowerElevator(){}

}
