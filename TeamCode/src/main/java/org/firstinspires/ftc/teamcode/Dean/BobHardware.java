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

    public GrabberServo getLeftGrabber(){
        final double  GRABBER_UP     = 0.89;
        final double  GRABBER_EVEN   = 0.53;
        final double  GRABBER_GRAB   = 0.14;
        final double  GRABBER_DOWN   = 0.17;
        if(leftGrabberServo == null)
            leftGrabberServo = new GrabberServo(getServo0(),GRABBER_GRAB,GRABBER_DOWN,GRABBER_EVEN,GRABBER_UP);
        return leftGrabberServo;
    }
    GrabberServo leftGrabberServo;

    public GrabberServo getRightGrabber(){
        final double  GRABBER_UP = 0.14;
        final double  GRABBER_EVEN = 0.5;
        final double  GRABBER_GRAB = 0.89;
        final double  GRABBER_DOWN = 0.85;
        if(rightGrabberServo == null)
            rightGrabberServo = new GrabberServo(getServo0(),GRABBER_GRAB,GRABBER_DOWN,GRABBER_EVEN,GRABBER_UP);
        return rightGrabberServo;
    }
    GrabberServo rightGrabberServo;


    public Servo getServo0(){
        if(servo0==null)
            servo0 = hardwareMap.get(Servo.class, "servo0");
        return servo0;
    }

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
