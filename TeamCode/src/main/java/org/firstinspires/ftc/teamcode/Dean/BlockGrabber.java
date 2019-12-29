package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.hardware.Servo;

public class BlockGrabber {

	private final double DOWN = 0.9;
	private final double EVEN = 0.5;
	private final double UP = 0.0;
	private final double GRAB = 0.0;

	private ServoPair servos;

	public BlockGrabber(Servo leftServo, Servo rightServo){

		// one of these scaled ranges is not correct!!!
		leftServo.setDirection(Servo.Direction.REVERSE);
		leftServo.scaleRange(0.14, 0.89);

		rightServo.setDirection(Servo.Direction.FORWARD);
		rightServo.scaleRange(0.14,0.89);

		servos = new ServoPair( leftServo, rightServo );
	}

	public void move(String position){

		switch(position) {
			case "up":    servos.setPosition(UP); break;
			case "even":  servos.setPosition(EVEN); break;
			case "down":  servos.setPosition(DOWN); break;
			case "grab":  servos.setPosition(GRAB); break;
		}
	}

}
