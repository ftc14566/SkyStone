package org.firstinspires.ftc.teamcode.Dean;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Dean.BobHardware;
import org.firstinspires.ftc.teamcode.bravo.Config;

// Methods for testing on FTC-Bob controller.
public class BobBot {

    public BobBot(LinearOpMode opMode){
        _hardware = new BobHardware(opMode.hardwareMap);
        _opMode = opMode;
    }

    public void powerMotor(
            @Config(label="power", value = 0.2, min=-1.0, max=1.0, step=0.05, displayScale = 100, units = "%") double power,
            @Config(label="timeout", value = 30, min=5, max=120, step=5, units="sec") int timeout
    ){
        DcMotor motor = _hardware.getMotor3();
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor.setPower(power);

        double startTime = _opMode.time;
        double endTime = startTime + timeout;
        int pos=0;
        while(testModeIsActive()
                && (endTime)>_opMode.time
        ){
            pos = motor.getCurrentPosition();
            _opMode.telemetry.addData("motor position", pos);
            _opMode.telemetry.addData("revolutions", "%.2f",pos/ BobHardware.COUNTS_PER_MOTOR_REV);
            _opMode.telemetry.addData("time remaining", "%.1f", endTime-_opMode.time);
            _opMode.telemetry.addData("To stop, press:", "gamepad1.b");
            _opMode.telemetry.update();
        }

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // leave results on screen
        _opMode.telemetry.addData("--Mode Complete--", "");
        _opMode.telemetry.addData("motor position", pos);
        _opMode.telemetry.addData("revolutions", "%.2f",pos/ BobHardware.COUNTS_PER_MOTOR_REV);
        _opMode.telemetry.addData("elapsed time", "%.1f",_opMode.time - startTime );
        _opMode.telemetry.addData("To return, press:", "gamepad1.b");
        _opMode.telemetry.update();

        waitForBRelease();
        waitForBPress();

        waitForBRelease();

    }

    public void moveServo(
            @Config(label="position", min=0.0, max=1.0, step=.01, displayScale = 100, value = 0.5, units = "%") double position,
            @Config(label="direction", isTrue=true, trueString = "forward", falseString = "backward") boolean directionForward,
            @Config(label="range-min", min=0.0, max=1.0, step=.01, displayScale = 100, value = 0.0, units = "%") double rangeMin,
            @Config(label="range-max", min=0.0, max=1.0, step=.01, displayScale = 100, value = 1.0, units = "%") double rangeMax
            ){
        Servo s = _hardware.getServo0();

        // configure
        s.scaleRange(rangeMin,rangeMax);
        s.setDirection(directionForward ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);

        s.setPosition(position);

        _opMode.telemetry.addData("Servo Position:", "%.0f", position*100);
        _opMode.telemetry.addData("Servo Dir:", directionForward?"forward":"reverse");
        _opMode.telemetry.addData("Servo Range:", "%.0f to %.0f", rangeMin*100,rangeMax*100);
        _opMode.telemetry.addData("To return, press:", "gamepad1.b");
        _opMode.telemetry.update();
        double endTime = _opMode.time+2.0; // wait 2 seconds
        while(testModeIsActive() && _opMode.time < endTime);

    }

    public void moveGrabber(
            @Config(label="position", stringValue = "even",stringOptions = "up,even,down,grab") String position
    ){

        BlockGrabber grabber = getBlockGrabber();

        grabber.move(position);

        _opMode.telemetry.addData("Servo Grabber To:", position );
        _opMode.telemetry.addData("To return, press:", "gamepad1.b");
        _opMode.telemetry.update();
        double endTime = _opMode.time+2.0; // wait 2 seconds
        while(testModeIsActive() && _opMode.time < endTime);

    }

    public void trackColorSensor(
            @Config(label="enable LED") boolean enableLed
    ){
        ColorSensor colorSensor = _hardware.getColorSensor();
        DistanceSensor distanceSensor = _hardware.getDistance0();

        colorSensor.enableLed(enableLed);
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(enableLed);
        }

        while(testModeIsActive()){
            int red = colorSensor.red() >> 3; // convert from 5-bit to 8bit
            int green = colorSensor.green() >> 3;
            int blue = colorSensor.blue() >> 3;

            // convert the RGB values to HSV values.
            float hsvValues[] = {0F,0F,0F};
            Color.RGBToHSV( red, green, blue, hsvValues);

            _opMode.telemetry.addData("red green blue", red+" "+green+" "+blue);
            _opMode.telemetry.addData("H/S/V", "%.2f %.2f %.2f", hsvValues[0],hsvValues[1],hsvValues[2] );
            _opMode.telemetry.addData("Distance (cm)", "%.2f", distanceSensor.getDistance( DistanceUnit.CM ) );
            _opMode.telemetry.addData("To return, press:", "gamepad1.b");
            _opMode.telemetry.update();
        }

        colorSensor.enableLed(false);
        waitForBRelease();

    }

    public void trackDistanceSensor(
            @Config(label="dummy") boolean b
    ){
        DistanceSensor sensor = _hardware.getDistance2m();

        while(testModeIsActive()){
            _opMode.telemetry.addData("Distance (in)", sensor.getDistance(DistanceUnit.INCH) );
            _opMode.telemetry.update();
        }
        waitForBRelease();

    }


    // region private helper methods

    private boolean testModeIsActive(){ return _opMode.opModeIsActive() && !_opMode.gamepad1.b; }
    private boolean testModeIsPaused(){ return _opMode.opModeIsActive() && _opMode.gamepad1.b; }
    private void waitForBPress(){ while(testModeIsActive()); }
    private void waitForBRelease(){ while(testModeIsPaused()); }

    // endregion

    private BlockGrabber getBlockGrabber(){
        if(blockGrabber==null)
            blockGrabber = new BlockGrabber(_hardware.getServo0(), _hardware.getServo1());
        return blockGrabber;
    }
    private BlockGrabber blockGrabber;


    // region fields

    BobHardware _hardware;
    LinearOpMode _opMode;

    // endregion
}
