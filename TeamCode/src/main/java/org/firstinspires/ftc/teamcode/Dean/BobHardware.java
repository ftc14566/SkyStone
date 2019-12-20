package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.hardware.*;

// Hardware for Testing on FTC-Bob
public class BobHardware {

    public static final double     COUNTS_PER_MOTOR_REV    = 288 ;

    public DcMotor motor3;
    public Servo servo0;
    public ColorSensor colorSensor;
    public DistanceSensor distanceSensor;

    public BobHardware(HardwareMap hardwareMap){

        motor3 = hardwareMap.get(DcMotor.class, "motor3");
        servo0 = hardwareMap.get(Servo.class, "servo0");
        colorSensor = hardwareMap.get(ColorSensor.class, "color0");
  //      distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor0");
    }

}
