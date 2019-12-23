package org.firstinspires.ftc.teamcode.Nathan;
import com.qualcomm.robotcore.hardware.Servo;
public class FoundationGrabber {

    Servo leftServo;
    Servo rightServo;

    public FoundationGrabber (Servo leftServo,Servo rightServo){
        this.leftServo = leftServo;
        this.rightServo = rightServo;
        this.leftServo.setDirection(Servo.Direction.REVERSE);
    }
    public void down(){
        leftServo.setPosition(0.9);
        rightServo.setPosition(0.42);
    }
    public void up(){
        leftServo.setPosition(0.53);
        rightServo.setPosition(0.08);
    }
}
