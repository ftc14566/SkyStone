package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class TeleBot {

	private Hardware hardware;

	public TeleBot( Hardware hardware ){
		this.hardware = hardware;
	}

	public void colorSensorsYellow(){
		while(hardware.leftColorSensor.red() <= 235 && hardware.leftColorSensor.red() >= 213 && hardware.rightColorSensor.red() <= 235 && hardware.rightColorSensor.red() >= 213){
			while(hardware.leftColorSensor.green() <= 192 && hardware.leftColorSensor.green() >= 235 && hardware.leftColorSensor.green() <= 192 && hardware.leftColorSensor.green() >= 235){
				while(hardware.leftColorSensor.blue() <= 52 && hardware.leftColorSensor.blue() >= 52 && hardware.rightColorSensor.blue() <= 52 && hardware.rightColorSensor.blue() >= 52){
					//TODO Block Collector Code
					this.Move(0.0, 0.0); //STOP
				}
			}
		}
	}

/*	public void raiseElevators(double elevatorBind, double rate){

	public void towerEncoders(double towerPosition){
		towerPosition = hardware.rightTowerMotor.getCurrentPosition();

	}

	public void towerDown(float towerDownBind){
		double towerPositionRight = hardware.rightTowerMotor.getCurrentPosition();
		double towerPositionLeft = hardware.leftTowerMotor.getCurrentPosition();
		if(towerDownBind > 0.25){
			while(towerPositionRight > 0) {
				hardware.leftTowerMotor.setTargetPosition(0);
				hardware.rightTowerMotor.setTargetPosition(0);
				hardware.leftTowerMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
				hardware.rightTowerMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
				hardware.leftTowerMotor.setPower(.10);
				hardware.rightTowerMotor.setPower(.10);
				while(hardware.leftTowerMotor.isBusy() || hardware.rightTowerMotor.isBusy()) {
					hardware.leftColorSensor.enableLed(true);
					hardware.rightColorSensor.enableLed(true);
					hardware.leftColorSensor.enableLed(false);
					hardware.rightColorSensor.enableLed(false);
				}
				hardware.leftTowerMotor.setPower(0);
				hardware.rightTowerMotor.setPower(0);
			}
		}
	}

	public void raiseElevators(double elevatorBind, double rate){
>>>>>>> 85c45fb77021d3248a58c487724831c3c45f7d09
		if(elevatorBind >= .25)
			hardware.leftTowerMotor.setPower(rate);
			hardware.rightTowerMotor.setPower(rate);
		if(elevatorBind <= -.25) {
			hardware.leftTowerMotor.setPower(-rate);
			hardware.rightTowerMotor.setPower(-rate);
		}

	}
*/


	public void DriveForward(double speed){
		this.Move(speed,0.0);
	}

	public void Stop(){
		this.Move(0.0,0.0);
	}

	public void StrafeRight(double speed){
		this.Move(0,speed);
	}

	public void SpinRight(double speed){;
		hardware.frontLeftDrive.setPower(-speed);
		hardware.frontRightDrive.setPower(-speed);
		hardware.rearLeftDrive.setPower(-speed);
		hardware.rearRightDrive.setPower(-speed);
	}


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

		double power = 0.2;
		if(up)power = 1.0;
		if(down)power = 0.02;


		hardware.leftTowerMotor.setPower(power);
		hardware.rightTowerMotor.setPower(power);
	}

	double leftGrabberPosition;
	double rightGrabberPosition;

	private double ramp (double current, double target, double stepUpSize){
		if(current<target){
			// going up
			if(current<0) return 0;
			return Math.min(target,current + stepUpSize);
		} else{
			// going down
			if(current > 0) return 0;
			return Math.max(target, current - stepUpSize);
		}
	}

	public void driveAndStraif (double forward, double turnRight, double straifRight){

		double scale = 1;

		forward *= scale;
		turnRight *= scale;
		straifRight *= scale;

		//foward = ramp(foward, targetFoward, 0.15);
		//turnRight = ramp(turnRight, targetTurnRight, 0.15);
		//straifRight = ramp(straifRight, targetStraifRight, 0.15);

//		if(forward > .25) {
//			forward = scale2;
//			turnRight = scale2;
//			straifRight = scale2;
//		} else{
//			forward = scale;
//			turnRight = scale;
//			straifRight = scale;
//		}



		// combine drive,turn,straif
		double fl = -forward - turnRight - straifRight;
		double fr = forward - turnRight  - straifRight;
		double rl = -forward - turnRight + straifRight;
		double rr = forward - turnRight  + straifRight;

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
		hardware.frontLeftDrive.setPower(fl);
		hardware.frontRightDrive.setPower(fr);
		hardware.rearLeftDrive.setPower(rl);
		hardware.rearRightDrive.setPower(rr);

	}

	public void grab(boolean open,boolean close){

		if(open) {
			leftGrabberPosition = 0.5;
			rightGrabberPosition = 0.5;
		}
		else if(close){
			leftGrabberPosition =0.9;
			rightGrabberPosition = 0.9;
		}

		hardware.graberLeft.setPosition(leftGrabberPosition);
		hardware.graberRight.setPosition(rightGrabberPosition);
	}

	public boolean isBlockInFront(){

		return hardware.distanceSensor.getDistance(DistanceUnit.CM)<3.5;
	}

	public void Extend(boolean extend, boolean in){
		double power=0;
		if(extend&&!in)power=0.3;
		if(in&&!extend)power=-0.3;

		hardware.bridgeMotor.setPower(power);
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
