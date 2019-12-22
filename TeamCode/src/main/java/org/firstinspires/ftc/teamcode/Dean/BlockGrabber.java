package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.hardware.Servo;

public class BlockGrabber {

	public BlockServo leftGrabber;
	public BlockServo rightGrabber;

	public BlockGrabber(Servo leftServo, Servo rightServo){

		leftGrabber = new BlockServo(leftServo,
				0.14, 0.89, Servo.Direction.REVERSE
				,1.00,0.9,0.5,0.0
		);

		rightGrabber = new BlockServo(rightServo,
				0.14, 0.89, Servo.Direction.FORWARD,
				1.0,0.9,0.5,0.0
		);

	}

	public void move(String position){
		leftGrabber.move(position);
		rightGrabber.move(position);
	}

}
