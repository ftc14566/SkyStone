package org.firstinspires.ftc.teamcode.bravo;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class TestBot {

    public TestBot(TestHardware hardware, LinearOpMode opMode){
        _hardware = hardware;
        _opMode = opMode;
    }

    public void powerMotor(
            @Config(label="power", initial = 0.2, min=-1.0, max=1.0, step=0.05, displayScale = 100, units = "%") double power,
            @Config(label="timeout", initial = 30, min=5, max=120, step=5, units="sec") int timeout
    ){
        DcMotor motor = _hardware.motor3;
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
            _opMode.telemetry.addData("revolutions", "%.2f",pos/TestHardware.COUNTS_PER_MOTOR_REV);
            _opMode.telemetry.addData("time remaining", "%.1f", endTime-_opMode.time);
            _opMode.telemetry.addData("To stop, press:", "gamepad1.b");
            _opMode.telemetry.update();
        }

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // leave results on screen
        _opMode.telemetry.addData("--Mode Complete--", "");
        _opMode.telemetry.addData("motor position", pos);
        _opMode.telemetry.addData("revolutions", "%.2f",pos/TestHardware.COUNTS_PER_MOTOR_REV);
        _opMode.telemetry.addData("elapsed time", "%.1f",_opMode.time - startTime );
        _opMode.telemetry.addData("To return, press:", "gamepad1.b");
        _opMode.telemetry.update();

        waitForBRelease();
        waitForBPress();

        waitForBRelease();

    }

    public void moveServo(
            @Config(label="dir", isTrue=true, trueString = "forward", falseString = "backward") boolean directionForward,
            @Config(label="position", min=0.0,max=1.0,step=.01,displayScale = 100, units = "%") double position
    ){
        Servo s = _hardware.servo0;

        s.setDirection(directionForward ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);
        s.setPosition(position);

        // s.scaleRange(0.0,1.0);
    }

    public void trackColorSensor(
            @Config(label="enable LED") boolean enableLed
    ){
        ColorSensor sensor = _hardware.colorSensor;
        sensor.enableLed(enableLed);

        while(testModeIsActive()){
            int red = sensor.red() >> 3; // convert from 5-bit to 8bit
            int green = sensor.green() >> 3;
            int blue = sensor.blue() >> 3;

            // convert the RGB values to HSV values.
            float hsvValues[] = {0F,0F,0F};
            Color.RGBToHSV( red, green, blue, hsvValues);

            _opMode.telemetry.addData("red green blue", red+" "+green+" "+blue);
            _opMode.telemetry.addData("H/S/V", "%.2f %.2f %.2f", hsvValues[0],hsvValues[1],hsvValues[2] );

            _opMode.telemetry.addData("To return, press:", "gamepad1.b");
            _opMode.telemetry.update();
        }

        sensor.enableLed(false);
        waitForBRelease();

    }

    public void trackDistanceSensor(
            @Config(label="timeout", initial = 30, min=5, max=120, step=5) int timeout
    ){
        DistanceSensor sensor = _hardware.distanceSensor;

        double endTime = _opMode.time + timeout;
        while(_opMode.opModeIsActive() && _opMode.time<endTime){
            _opMode.telemetry.addData("Distance (in)", sensor.getDistance(DistanceUnit.INCH) );
            _opMode.telemetry.addData("Time remaining", (endTime-_opMode.time));
            _opMode.telemetry.update();
        }

    }

    private boolean testModeIsActive(){ return _opMode.opModeIsActive() && !_opMode.gamepad1.b; }
    private boolean testModeIsPaused(){ return _opMode.opModeIsActive() && _opMode.gamepad1.b; }
    private void waitForBPress(){ while(testModeIsActive()); }
    private void waitForBRelease(){ while(testModeIsPaused()); }


    TestHardware _hardware;
    LinearOpMode _opMode;
}
