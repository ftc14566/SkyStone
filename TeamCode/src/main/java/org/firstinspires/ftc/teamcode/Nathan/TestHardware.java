package org.firstinspires.ftc.teamcode.Nathan;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.hardware.rev.RevSPARKMini;
import com.qualcomm.robotcore.hardware.*;


// This class contains items that apply to the robot regardless of the opmode or operation.
public class TestHardware {

    public DcMotor frontLeftDrive;
    public DcMotor frontRightDrive;
    public DcMotor rearLeftDrive;
    public DcMotor rearRightDrive;
    public DcMotor leftTowerMotor;
    public DcMotor rightTowerMotor;
    public DcMotor bridgeMotor;
    public ColorSensor leftColorSensor;
    public ColorSensor rightColorSensor;
    public DistanceSensor distanceSensor;
    public Servo graberLeft;
    public Servo graberRight;

    public RevBlinkinLedDriver Lights;

    public void init(HardwareMap hardwareMap) {
        /*frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeftWheel");
        frontLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRightWheel");
        frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        rearLeftDrive = hardwareMap.get(DcMotor.class, "rearLeftWheel");
        rearLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        rearRightDrive = hardwareMap.get(DcMotor.class, "rearRightWheel");
        rearRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        leftTowerMotor = hardwareMap.get(DcMotor.class, "leftTowerMotor");
        leftTowerMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightTowerMotor = hardwareMap.get(DcMotor.class, "rightTowerMotor");
        rightTowerMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        bridgeMotor = hardwareMap.get(DcMotor.class, "bridge");
        bridgeMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        graberLeft = hardwareMap.get(Servo.class, "graberLeft");
        graberRight = hardwareMap.get(Servo.class, "graberRight");
        graberLeft.setDirection(Servo.Direction.REVERSE);

        leftColorSensor = hardwareMap.get(ColorSensor.class, "colorSensorLeft");
        leftColorSensor.red();
        leftColorSensor.green();
        leftColorSensor.blue();
        leftColorSensor.alpha();
        leftColorSensor.argb();
        rightColorSensor = hardwareMap.get(ColorSensor.class, "colorSensorRight");
        rightColorSensor.red();
        rightColorSensor.green();
        rightColorSensor.blue();
        rightColorSensor.alpha();
        rightColorSensor.argb();


        distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor");

        Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor)distanceSensor;

        rearRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);*/
        Lights = hardwareMap.get(RevBlinkinLedDriver .class, "Lights");

    }

}
