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
        leftServo.setPosition(1);
        rightServo.setPosition(0.52);
    }
    public void up(){
        leftServo.setPosition(0.5);
        rightServo.setPosition(0.05);
    }
}
