package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.hardware.Servo;

public class BlockGrabber {

	public PositionedServo leftGrabber;
	public PositionedServo rightGrabber;

	public BlockGrabber(Servo leftServo, Servo rightServo){

		leftGrabber = new PositionedServo(leftServo,
				0.14, 0.89, Servo.Direction.REVERSE
				,1.00,0.9,0.5,0.0
		);

		rightGrabber = new PositionedServo(rightServo,
				0.14, 0.89, Servo.Direction.FORWARD,
				1.0,0.9,0.5,0.0
		);

	}

	public void move(String position){
		leftGrabber.move(position);
		rightGrabber.move(position);
	}

	class PositionedServo {

		final private Servo servo;
		final private double grab;
		final private double down;
		final private double even;
		final private double up;


		public PositionedServo(Servo servo,
							   double rangeMin, double rangeMax, Servo.Direction direction,
							   double grab, double down, double even, double up
		){
			this.servo = servo;
			servo.setDirection(direction);
			servo.scaleRange(rangeMin,rangeMax);

			this.grab = grab;
			this.down = down;
			this.even = even;
			this.up = up;
		}

		public void move(String position){
			switch(position) {
				case "up":    servo.setPosition(up); break;
				case "even":  servo.setPosition(even); break;
				case "down":  servo.setPosition(down); break;
				case "grab":  servo.setPosition(grab); break;
			}
		}

	}


}
