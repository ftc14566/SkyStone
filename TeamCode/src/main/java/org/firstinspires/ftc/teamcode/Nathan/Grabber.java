package org.firstinspires.ftc.teamcode.Nathan;

import com.qualcomm.robotcore.hardware.Servo;
public class Grabber {

    Servo leftServo;
    Servo rightServo;

    public Grabber (Servo leftServo, Servo rightServo){
        this.leftServo = leftServo;
        this.rightServo = rightServo;
        this.leftServo.setDirection(Servo.Direction.REVERSE);
    }

    public void grab (){
        leftServo.setPosition(0.9);
        rightServo.setPosition(0.9);
    }
    public void open (){
        leftServo.setPosition(0.5);
        rightServo.setPosition(0.5);
    }


}
