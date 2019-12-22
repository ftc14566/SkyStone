package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.hardware.Servo;

public class BlockServo {

	final private Servo servo;
	final private double grab;
	final private double down;
	final private double even;
	final private double up;


	public BlockServo(Servo servo,
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
