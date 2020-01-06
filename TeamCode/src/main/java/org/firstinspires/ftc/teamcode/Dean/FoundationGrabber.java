package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.hardware.Servo;

public class FoundationGrabber {

	private ServoPair servos;
	private final double GRAB = 1.0;
	private final double UP = 0.0;

	public FoundationGrabber(Servo leftServo, Servo rightServo){

		// configure left to useful range
		leftServo.scaleRange(0.1,0.47);
		leftServo.setDirection(Servo.Direction.REVERSE); // make 1.0 grab and 0.0 up

		// configue right to useful range
		rightServo.scaleRange(0.08, 0.42);
		rightServo.setDirection(Servo.Direction.FORWARD);

		servos = new ServoPair(leftServo,rightServo);
	}

	public void move(String position){
		switch(position) {
			case "up":    servos.setPosition(UP); break;
			case "grab":  servos.setPosition(GRAB); break;
		}
	}

}
