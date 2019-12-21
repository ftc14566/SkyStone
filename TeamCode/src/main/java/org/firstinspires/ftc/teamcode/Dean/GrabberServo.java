package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.hardware.Servo;

public class GrabberServo {

	final private Servo servo;
	final private double grab;
	final private double down;
	final private double even;
	final private double up;


	public GrabberServo(Servo servo, double grab, double down, double event, double up){
		this.servo = servo;
		servo.setDirection(Servo.Direction.FORWARD);
		servo.scaleRange(0.0,1.0);

		this.grab = grab;
		this.down = down;
		this.even = event;
		this.up = up;
	}

	public void move(String position){
		switch(position) {
			case "up":    up(); break;
			case "even":  even(); break;
			case "down":  down(); break;
			case "grab":  grab(); break;
		}
	}

	public void grab(){ servo.setPosition(grab); }
	public void down(){ servo.setPosition(down); }
	public void even(){ servo.setPosition(even); }
	public void up(){ servo.setPosition(up); }

}
