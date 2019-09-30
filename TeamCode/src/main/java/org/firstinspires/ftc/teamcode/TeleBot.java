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

	public void SpinRight(double speed){
		// TODO: put code here that spins clockwise
		// negative speeds should spin counter clockwise
	}

	public void Move(double forwardSpeed, double rightSpeed){
		// TODO: put code here that makes robot move to the right

		double sum=Math.abs(forwardSpeed)+Math.abs(rightSpeed);
		if(sum > 1.0){
			forwardSpeed/=sum;
			rightSpeed/=sum;
		}

		hardware.frontLeftDrive.setPower(-forwardSpeed-rightSpeed);//
		hardware.rearLeftDrive.setPower(-forwardSpeed+rightSpeed);//
		hardware.frontRightDrive.setPower(forwardSpeed-rightSpeed);//
		hardware.rearRightDrive.setPower(forwardSpeed+rightSpeed);//


	}


	//============================================
	// TODO: put other high level operations here.
	//============================================
	public void RaiseElevator(){}
	public void LowerElevator(){}

}
