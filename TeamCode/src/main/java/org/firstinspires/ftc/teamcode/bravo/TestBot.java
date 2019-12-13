package org.firstinspires.ftc.teamcode.bravo;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware;

public class TestBot {

    public TestBot(TestHardware hardware, LinearOpMode opMode){
        _hardware = hardware;
        _opMode = opMode;
    }

    public void moveServo(
            @BooleanAnnotation(label="dir=forward", initial=true) boolean directionForward,
            @DoubleAnnotation(label="position", min=-1.0,max=1.0,step=.05) double position
    ){
        Servo s = _hardware.servo0;

        s.setDirection(directionForward ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);
        s.setPosition(position);

        // s.scaleRange(0.0,1.0);
    }

    public void trackColorSensor(
            @BooleanAnnotation(label="enable LED") boolean enableLed,
            @IntAnnotation(label="timeout", initial = 30, min=5, max=120, step=5) int timeout
    ){
        ColorSensor sensor = _hardware.colorSensor;
        sensor.enableLed(enableLed);

        double endTime = _opMode.time + timeout;
        while(_opMode.opModeIsActive() && _opMode.time<endTime){

            _opMode.telemetry.addData("red", sensor.red());
            _opMode.telemetry.addData("green", sensor.green());
            _opMode.telemetry.addData("blue", sensor.blue());
            _opMode.telemetry.addData("Time remaining", (endTime-_opMode.time));
            _opMode.telemetry.update();
        }

        sensor.enableLed(false);
    }

    public void trackDistanceSensor(
            @IntAnnotation(label="timeout", initial = 30, min=5, max=120, step=5) int timeout
    ){
        DistanceSensor sensor = _hardware.distanceSensor;

        double endTime = _opMode.time + timeout;
        while(_opMode.opModeIsActive() && _opMode.time<endTime){
            _opMode.telemetry.addData("Distance (in)", sensor.getDistance(DistanceUnit.INCH) );
            _opMode.telemetry.addData("Time remaining", (endTime-_opMode.time));
            _opMode.telemetry.update();
        }

    }



    TestHardware _hardware;
    LinearOpMode _opMode;
}
