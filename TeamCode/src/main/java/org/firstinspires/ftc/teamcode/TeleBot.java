package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class TeleBot {

	protected Hardware hardware;

	public double towerPositionRight;//'' = hardware.rightTowerMotor.getCurrentPosition();
	public double towerPositionLeft;// = hardware.leftTowerMotor.getCurrentPosition();


	public TeleBot(Hardware hardware) {

		this.hardware = hardware;
		towerPositionRight = hardware.rightTowerMotor.getCurrentPosition();
		towerPositionLeft = hardware.leftTowerMotor.getCurrentPosition();
	}

	public void colorSensorsYellow() {
		while (hardware.leftColorSensor.red() <= 235 && hardware.leftColorSensor.red() >= 213 && hardware.rightColorSensor.red() <= 235 && hardware.rightColorSensor.red() >= 213) {
			while (hardware.leftColorSensor.green() <= 192 && hardware.leftColorSensor.green() >= 235 && hardware.leftColorSensor.green() <= 192 && hardware.leftColorSensor.green() >= 235) {
				while (hardware.leftColorSensor.blue() <= 52 && hardware.leftColorSensor.blue() >= 52 && hardware.rightColorSensor.blue() <= 52 && hardware.rightColorSensor.blue() >= 52) {
					//TODO Block Collector Code
					this.Move(0.0, 0.0); //STOP
				}
			}
		}
	}


	public void towerDown(float towerDownBind) {
		//towerPositionRight = hardware.rightTowerMotor.getCurrentPosition();
		//towerPositionLeft = hardware.leftTowerMotor.getCurrentPosition();
		if (towerDownBind > 0.25) {
            hardware.leftTowerMotor.setPower(0.9);
            hardware.rightTowerMotor.setPower(0.9);
            hardware.leftTowerMotor.setTargetPosition(0);
            hardware.rightTowerMotor.setTargetPosition(0);
            hardware.leftTowerMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hardware.rightTowerMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
		else{
			hardware.leftTowerMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); //Stop and Reset
			hardware.rightTowerMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		}
	}

	public void encodersWithGrabber(float autoGrabBind, boolean bridgePosition){ //TRUE = OUT :: FALSE = IN
		double power=0;
		if(bridgePosition = false)power=0.3;
		if(bridgePosition = true)power=-0.3;
		if(autoGrabBind > .25){
			hardware.bridgeMotor.setPower(power);
			double toBlock = hardware.distanceSensor.getDistance(DistanceUnit.INCH);
			int perInch = (int)hardware.COUNTS_PER_INCH;
			while(toBlock > .01){
				hardware.bridgeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
			}
			hardware.bridgeMotor.setTargetPosition(perInch);
			hardware.bridgeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		}
		else{
			hardware.bridgeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		}
	}

	public void moveFoundation(float foundationBind){//, double foundationGrabbed, double foundationBack){
	    if(foundationBind > .25){
	        hardware.leftFoundationServo.setPosition(0);
	        hardware.rightFoundationServo.setPosition(0);
        }
	    else {
            hardware.leftFoundationServo.setPosition(50);
            hardware.rightFoundationServo.setPosition(50);
	    }
    }



	public void raiseElevators(double elevatorBind, double rate){

		if(elevatorBind >= .25)
			hardware.leftTowerMotor.setPower(rate);
			hardware.rightTowerMotor.setPower(rate);
		if(elevatorBind <= -.25) {
			hardware.leftTowerMotor.setPower(-rate);
			hardware.rightTowerMotor.setPower(-rate);
		}

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

	public void SpinRight(double speed){;
		hardware.frontLeftDrive.setPower(speed);
		hardware.frontRightDrive.setPower(-speed);
		hardware.rearLeftDrive.setPower(speed);
		hardware.rearRightDrive.setPower(-speed);
	}


	public void Move(double forward, double rightSpeed){

		double sumLeft=Math.abs(forward)+Math.abs(rightSpeed);
		if(sumLeft > 1.0){
			forward/=sumLeft;
			rightSpeed/=sumLeft;
		}


		hardware.frontLeftDrive.setPower(forward+rightSpeed);//
		hardware.rearLeftDrive.setPower(forward-rightSpeed);//

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

	double strafe = 0.0;
	double forward = 0.0;
	double turn = 0.0;

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

	public void driveAndStrafe (double targetFoward, double targetTurnRight, double targetStrafeRight){
		forward = targetFoward/2;
		turn = targetTurnRight/2;
		strafe = targetStrafeRight/2;
		// combine drive,turn,strafe
		double fl = forward + turn + strafe;

		double fr = forward - turn - strafe;
		double rl = forward + turn - strafe;
		double rr = forward - turn + strafe;

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

        if(open)
			setGrabberPos(0.5);

		else if(close)
            setGrabberPos(0.9);
	}

	private void setGrabberPos(double pos){
        hardware.grabberLeft.setPosition(pos);
        hardware.grabberRight.setPosition(pos);

    }

	public boolean isBlockInFront(){

		return hardware.distanceSensor.getDistance(DistanceUnit.CM)<3.5;
	}

	public void extend (boolean out, boolean in){
		double power = 0;
		if (out) power = 0.3;
		if (in) power = -0.3;

		hardware.bridgeMotor.setPower(power);
		hardware.bridgeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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

		hardware.frontLeftDrive.setPower(forwardSpeedLeft+rightSpeedLeft);//
		hardware.rearLeftDrive.setPower(forwardSpeedLeft-rightSpeedLeft);//
		hardware.frontRightDrive.setPower(forwardSpeedRight-rightSpeedRight);//
		hardware.rearRightDrive.setPower(forwardSpeedRight+rightSpeedRight);//

	}

	public void RaiseElevator(){}
	public void LowerElevator(){}

}
