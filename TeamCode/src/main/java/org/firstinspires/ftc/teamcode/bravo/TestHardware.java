package org.firstinspires.ftc.teamcode.bravo;

import com.qualcomm.robotcore.hardware.*;

public class TestHardware {

    public DcMotor motor3;
    public Servo servo0;
    public ColorSensor colorSensor;
    public DistanceSensor distanceSensor;

    public TestHardware(HardwareMap hardwareMap){

        motor3 = hardwareMap.get(DcMotor.class, "motor3");
        servo0 = hardwareMap.get(Servo.class, "servo0");
        colorSensor = hardwareMap.get(ColorSensor.class, "color0");
  //      distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor0");
    }

}
