package org.firstinspires.ftc.teamcode;

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

	public void Move(double forwardSpeed, double rightSpeed){

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


	public void RaiseElevator(){}
	public void LowerElevator(){}

}
