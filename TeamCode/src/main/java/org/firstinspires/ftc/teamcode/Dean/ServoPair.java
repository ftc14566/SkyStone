package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.hardware.Servo;

// sets 2 servos to the same position
public class ServoPair {
	public ServoPair(Servo s0,Servo s1){
		this.servo0 = servo0;
		this.servo1 = servo1;
	}

	public void setPosition(double position){
		servo0.setPosition(position);
		servo1.setPosition(position);
	}
	Servo servo0;
	Servo servo1;
}
