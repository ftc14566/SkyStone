package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BlockGrabber {

	public GrabberServo leftGrabber;
	public GrabberServo rightGrabber;

	public BlockGrabber(Servo leftServo, Servo rightServo){
		leftGrabber = new GrabberServo(leftServo,0.14,0.17,0.53,0.89);
		rightGrabber = new GrabberServo(rightServo,0.89,0.85,0.5,0.14);
	}

	public void move(String position){
		leftGrabber.move(position);
		rightGrabber.move(position);
	}

}
