package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.hardware.Servo;

public class FoundationGrabber {

	public PositionedServo leftGrabber;
	public PositionedServo rightGrabber;

	public FoundationGrabber(Servo leftServo, Servo rightServo){

		leftGrabber = new PositionedServo(leftServo,
				0.0, 1.0, Servo.Direction.REVERSE
				,0.9,0.53
		);

		rightGrabber = new PositionedServo(rightServo,
				0, 1.0, Servo.Direction.FORWARD,
				0.42,0.08
		);

	}

	public void move(String position){
		leftGrabber.move(position);
		rightGrabber.move(position);
	}

	class PositionedServo {

		final private Servo servo;
		final private double grab;
		final private double up;


		public PositionedServo(Servo servo,
							   double rangeMin, double rangeMax, Servo.Direction direction,
							   double grab, double up
		){
			this.servo = servo;
			servo.setDirection(direction);
			servo.scaleRange(rangeMin,rangeMax);

			this.grab = grab;
			this.up = up;
		}

		public void move(String position){
			switch(position) {
				case "up":    servo.setPosition(up); break;
				case "grab":  servo.setPosition(grab); break;
			}
		}

	}


}
