package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.hardware.*;

// Hardware for Testing on FTC-Bob
public class BobHardware {

    private HardwareMap hardwareMap;

    public static final double  COUNTS_PER_MOTOR_REV    = 288 ;

    private DcMotor motor3 = null;
    private Servo servo0 = null;
    private ColorSensor colorSensor = null;
    private DistanceSensor distance0 = null;
    private DistanceSensor distance2m = null;

    public BobHardware(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
    }

    public DcMotor getMotor3(){
        if(motor3==null)
            motor3 = hardwareMap.get(DcMotor.class, "motor3");
        return motor3;
    }

    public BlockGrabber getBlockGrabber(){
        if(blockGrabber==null)
            blockGrabber = new BlockGrabber(getServo0(),getServo1());// !!! push these init back into hardware.
        return blockGrabber;
    }
    BlockGrabber blockGrabber;

    public Servo getServo0(){
        if(servo0==null)
            servo0 = hardwareMap.get(Servo.class, "servo0");
        return servo0;
    }

    public Servo getServo1(){
        if(servo1==null)
            servo1 = hardwareMap.get(Servo.class, "servo1");
        return servo1;
    }
    private Servo servo1;

    public DistanceSensor getDistance2m(){
        if(distance2m==null)
            distance2m = hardwareMap.get(DistanceSensor.class, "bus2");
        return distance2m;
    }

    public DistanceSensor getDistance0(){
        if(distance0==null)
            distance0 = hardwareMap.get(DistanceSensor.class, "color0");
        return distance0;
    }

    public ColorSensor getColorSensor(){
        if(colorSensor==null)
            colorSensor = hardwareMap.get(ColorSensor.class, "color0");
        return colorSensor;
    }
}
