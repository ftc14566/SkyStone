package org.firstinspires.ftc.teamcode.Ben.BenAuto;
import com.qualcomm.robotcore.hardware.Servo;

public class BenFoundationGrabber {

    Servo leftServo;
    Servo rightServo;

    public BenFoundationGrabber(Servo leftServo, Servo rightServo){
        this.leftServo = leftServo;
        this.rightServo = rightServo;
        this.leftServo.setDirection(Servo.Direction.REVERSE);
    }
    public void down(){
        leftServo.setPosition(1);
        rightServo.setPosition(0.52);
    }
    public void up(){
        leftServo.setPosition(0.5);
        rightServo.setPosition(0.05);
    }
}
