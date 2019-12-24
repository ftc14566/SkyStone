package org.firstinspires.ftc.teamcode.Dean;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.bravo.Config;

// Methods for testing on FTC-Bob controller.
public class BobBot extends TestBotBase {

    public BobBot(LinearOpMode opMode){
        super(opMode);
        hardware = new BobHardware(opMode.hardwareMap);
    }

    public void powerMotor(
            @Config(label="power", value = 0.2, min=-1.0, max=1.0, step=0.05, displayScale = 100, units = "%") double power,
            @Config(label="timeout", value = 30, min=5, max=120, step=5, units="sec") int timeout
    ){
        DcMotor motor = hardware.getMotor3();
        super.motorRunUsingEncoder(motor,power,timeout);
    }

    public void moveServo(
            @Config(label="position", min=0.0, max=1.0, step=.01, displayScale = 100, value = 0.5, units = "%") double position,
            @Config(label="direction", isTrue=true, trueString = "forward", falseString = "backward") boolean directionForward,
            @Config(label="range-min", min=0.0, max=1.0, step=.01, displayScale = 100, value = 0.0, units = "%") double rangeMin,
            @Config(label="range-max", min=0.0, max=1.0, step=.01, displayScale = 100, value = 1.0, units = "%") double rangeMax
            ){
        Servo s = hardware.getServo0();
        super.moveServo(s,position,directionForward,rangeMin,rangeMax);
    }

    public void moveGrabber(
            @Config(label="position", stringValue = "even",stringOptions = "grab,down,even,up") String position
    ){

        BlockGrabber grabber = getBlockGrabber();

        grabber.move(position);

        opMode.telemetry.addData("Servo Grabber To:", position );
        opMode.telemetry.addData("To return, press:", "gamepad1.b");
        opMode.telemetry.update();
        double endTime = opMode.time+2.0; // wait 2 seconds
        while(testModeIsActive() && opMode.time < endTime);

    }

    public void trackColorSensor(){
        super.trackV3ColorSensor(hardware.getColorSensor(),hardware.getDistance0());
    }

    public void trackDistanceSensor(){
        super.trackDistanceSensor(hardware.getDistance2m(),DistanceUnit.INCH);
    }

    private BlockGrabber getBlockGrabber(){
        if(blockGrabber==null)
            blockGrabber = new BlockGrabber(hardware.getServo0(), hardware.getServo1());
        return blockGrabber;
    }
    private BlockGrabber blockGrabber;


    // region fields

    BobHardware hardware;

    // endregion
}
