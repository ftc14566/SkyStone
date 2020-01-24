package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Nathan.FoundationGrabber;
import org.firstinspires.ftc.teamcode.Nathan.Grabber;

public class TeleBot {
	
	protected Hardware hardware;
	
	public double towerPositionRight;//'' = hardware.rightTowerMotor.getCurrentPosition();
	public double towerPositionLeft;// = hardware.leftTowerMotor.getCurrentPosition();
	
	
	public TeleBot(Hardware hardware) {
		
		this.hardware = hardware;
		towerPositionRight = hardware.rightTowerMotor.getCurrentPosition();
		towerPositionLeft = hardware.leftTowerMotor.getCurrentPosition();
		grabber = new Grabber(hardware.grabberLeft, hardware.grabberRight);
		foundationGrabber = new FoundationGrabber(hardware.leftFoundationServo, hardware.rightFoundationServo);
	}
	
	Grabber grabber;
	FoundationGrabber foundationGrabber;
	
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
	
	public void foundationServos(boolean up, boolean down){
		if (up)
			foundationGrabber.up();
		else if (down)
			foundationGrabber.down();
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
	
	public void blockGrabberWithActivate(String action){
		
		switch(action){
			case "open": grabber.open(); break;
			case "grab": grabber.grab(); break;
		}
		
	}

	public void Lift(boolean up, boolean down){
		if(up)
			goUp();
		else if (down)
			goDownBetter();

		else
			liftIdle ();
	}
	private void goUp(){
		setTowerPower(0.5);
	}
	private void liftIdle(){
		setTowerPower(0.2);
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
	
	public String determineGrabberAction(boolean open, boolean close, boolean blockSenser, boolean activateSensor) {
		String action = "";
		if (open)
			action = "open";
		else if (close)
			action = "grab";
		else if (activateSensor && blockSenser)
			action = "grab";
		return action;
	}
	
	public void driveAndStrafe (double forward, double turnRight, double straifRight){
		
		double scale = 1;
		
		forward *= scale;
		turnRight *= scale;
		straifRight *= scale;
		
		//foward = ramp(foward, targetFoward, 0.15);
		//turnRight = ramp(turnRight, targetTurnRight, 0.15);
		//straifRight = ramp(straifRight, targetStraifRight, 0.15);

//    if(forward > .25) {
//       forward = scale2;
//       turnRight = scale2;
//       straifRight = scale2;
//    } else{
//       forward = scale;
//       turnRight = scale;
//       straifRight = scale;
//    }
		
		
		
		// combine drive,turn,straif
		double fl = forward + turnRight + straifRight;
		double rl = forward + turnRight - straifRight;
		double fr = forward - turnRight - straifRight;
		double rr = forward - turnRight + straifRight;
		
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
	
	public void SetLightColor (double time,boolean foundationDown, boolean grabberDown,boolean grabberOpen){
		
		RevBlinkinLedDriver.BlinkinPattern color;
		
		
		if (foundationDown)
			color = RevBlinkinLedDriver.BlinkinPattern.BLUE_VIOLET;
		else if (grabberDown)
			color = RevBlinkinLedDriver.BlinkinPattern.GREEN;
		else if (grabberOpen)
			color = RevBlinkinLedDriver.BlinkinPattern.GOLD;
		else if (time <90)
			color = RevBlinkinLedDriver.BlinkinPattern.DARK_BLUE;
		else if (time < 90.1)
			color = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
		else if (time < 90.2)
			color = RevBlinkinLedDriver.BlinkinPattern.BLACK;
		else if (time < 90.3)
			color = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
		else if (time < 90.4)
			color = RevBlinkinLedDriver.BlinkinPattern.BLACK;
		else if (time < 90.5)
			color = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
		else if (time < 90.6)
			color = RevBlinkinLedDriver.BlinkinPattern.BLACK;
		else if (time < 90.7)
			color = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
		else if (time < 90.8)
			color = RevBlinkinLedDriver.BlinkinPattern.BLACK;
		else if (time < 90.9)
			color = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
		else if (time <120)
			color = RevBlinkinLedDriver.BlinkinPattern.SINELON_LAVA_PALETTE;
		else
			color = RevBlinkinLedDriver.BlinkinPattern.BLACK;
		hardware.Lights.setPattern(color);
	}

	private void setTowerPower(double power){
		hardware.leftTowerMotor.setPower(power);
		hardware.rightTowerMotor.setPower(power);
	}

	private int liftCounter = 0;
	private void goDownBetter(){
		if (liftCounter<1)
			setTowerPower(0.0);
		else
			setTowerPower(0.01);
		liftCounter += 1;
		if (liftCounter == 2)
			liftCounter = 0;
	}

	public void RaiseElevator(){}
	public void LowerElevator(){}
	
}

