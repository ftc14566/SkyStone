package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.*;

// Hardware for Testing on FTC-Bob
public class BobHardware {

    private HardwareMap hardwareMap;

    public BobHardware(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
    }

    // region motors

    public DcMotor getMotor2(){
        if(motor2==null)
            motor2 = hardwareMap.get(DcMotor.class, "motor2");
        return motor2;
    }
    private DcMotor motor2 = null;

    public DcMotor getMotor3(){
        if(motor3==null)
            motor3 = hardwareMap.get(DcMotor.class, "motor3");
        return motor3;
    }
    private DcMotor motor3 = null;

    public DcMotorPair getMotor23Pair(){
        if(motor23Pair==null) {
            DcMotor left = getMotor2();
            DcMotor right = getMotor3();
            left.setDirection(DcMotorSimple.Direction.REVERSE);
            right.setDirection(DcMotorSimple.Direction.FORWARD);
            motor23Pair = new DcMotorPair(left, right);
        }
        return motor23Pair;
    }
    private DcMotorPair motor23Pair;


    // endregion

    // region servos

    public Servo getServo0(){
        if(servo0==null)
            servo0 = hardwareMap.get(Servo.class, "servo0");
        return servo0;
    }
    private Servo servo0 = null;

    public Servo getServo1(){
        if(servo1==null)
            servo1 = hardwareMap.get(Servo.class, "servo1");
        return servo1;
    }
    private Servo servo1;

    // endregion

    public DistanceSensor getDistance2m(){
        if(distance2m==null)
            distance2m = hardwareMap.get(DistanceSensor.class, "distance0");
        return distance2m;
    }
    private DistanceSensor distance2m = null;

    private BlockGrabber getBlockGrabber(){
        if(blockGrabber==null)
            blockGrabber = new BlockGrabber(getServo0(), getServo1());
        return blockGrabber;
    }
    private BlockGrabber blockGrabber;


}
