package org.firstinspires.ftc.teamcode.Dean;

import android.graphics.Color;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.bravo.ButtonTracker;
import org.firstinspires.ftc.teamcode.bravo.Config;

// Methods for testing on FTC-Bob controller.
public class SimpleHardwareTestMethods extends TestBotBase {

    public SimpleHardwareTestMethods(LinearOpMode opMode){
        super(opMode);
    }

    // region motor
    public static final double  COUNTS_PER_MOTOR_REV    = 288 ;

    public void motorPosition(
            DcMotor motor,
            @Config(label = "target", min = 0, max=5000, step=100, value=300, units = " counts")int targetPosition,
            @Config(label = "power", min = 0, max=1.0, step=0.01, value=0.2, displayScale = 100, units = "%") double power,
            @Config(label = "target delta", min = 0, max=1000, step=10, value=100, units = " counts")int positionStepSize,
            @Config(label = "direction", trueString = "forward", falseString = "reverse", isTrue=true )boolean forward
    ){
        motor.setDirection(forward?DcMotor.Direction.FORWARD:DcMotor.Direction.REVERSE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setPower(power);
        motor.setTargetPosition(targetPosition); // must come before .setMode(RUN_TO_POSITION)

        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        ButtonTracker tracker = new ButtonTracker(opMode.gamepad1);

        while(testModeIsActive()) {
            if(tracker.dpadRightPressed()) {targetPosition += positionStepSize; motor.setTargetPosition(targetPosition);}
            if(tracker.dpadLeftPressed()) {targetPosition -= positionStepSize; motor.setTargetPosition(targetPosition);}

            int pos = motor.getCurrentPosition();
            double rev = pos/COUNTS_PER_MOTOR_REV;
            opMode.telemetry.addData("Motor Position - Interactive", "");
            opMode.telemetry.addData("Target/Power:", "%d %.0f", targetPosition, power*100 );
            opMode.telemetry.addData("curPos (#,rev)", "%d %.2f", pos, rev);
            opMode.telemetry.addData("Change position using:", "dpad-left/dpad-right");
            tellUserHowToExit();
            opMode.telemetry.update();
        }

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForBRelease();
    }

    public void motorSpeed(
            DcMotor motor,
            @Config(label = "speed", min = -1.0, max=1.0, value = 0.2, step=0.01, displayScale = 100, units = "%") double speed,
            @Config(label = "speed delta", min = 0, max=0.2, step=.005, value=0.1, displayScale = 100, units = "%")double delta,
            @Config(label = "direction", trueString = "forward", falseString = "reverse", isTrue=true )boolean forward
    ){
        if(motor == null) throw new IllegalStateException("motor is null");
        motor.setDirection(forward?DcMotor.Direction.FORWARD:DcMotor.Direction.REVERSE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setPower(speed);

        ButtonTracker tracker = new ButtonTracker(opMode.gamepad1);

        while(testModeIsActive()) {
            if(tracker.dpadRightPressed()) {speed += delta; motor.setPower(speed);}
            if(tracker.dpadLeftPressed()) {speed -= delta; motor.setPower(speed);}

            int pos = motor.getCurrentPosition();
            double rev = COUNTS_PER_MOTOR_REV;
            opMode.telemetry.addData("Motor Speed - Interactive", "");
            opMode.telemetry.addData("Speed:", "%.0f", speed*100 );
            opMode.telemetry.addData("curPos (#,rev)", "%d %.2f", pos, rev);
            opMode.telemetry.addData("Change speed using:", "dpad-left/dpad-right");
            tellUserHowToExit();
            opMode.telemetry.update();
        }

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForBRelease();

    }

    public void motorPower(
            DcMotor motor,
            @Config(label = "power", min = -1.0, max=1.0, value = 0.2, step=0.01, displayScale = 100, units = "%") double power,
            @Config(label = "power delta", min = 0, max=0.2, step=.005, value=0.1, displayScale = 100, units = "%")double powerStepSize,
            @Config(label = "direction", trueString = "forward", falseString = "reverse", isTrue=true )boolean forward
    ){
        motor.setDirection(forward?DcMotor.Direction.FORWARD:DcMotor.Direction.REVERSE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setPower(power);

        ButtonTracker tracker = new ButtonTracker(opMode.gamepad1);

        while(testModeIsActive()) {
            if(tracker.dpadRightPressed()) {power += powerStepSize; motor.setPower(power);}
            if(tracker.dpadLeftPressed()) {power -= powerStepSize; motor.setPower(power);}

            int pos = motor.getCurrentPosition();
            double rev = pos/COUNTS_PER_MOTOR_REV;
            opMode.telemetry.addData("Motor Power - Interactive", "");
            opMode.telemetry.addData("Power:", "%.0f", power*100 );
            opMode.telemetry.addData("curPos (#,rev)", "%d %.2f", pos, rev);
            opMode.telemetry.addData("Change power using:", "dpad-left/dpad-right");
            tellUserHowToExit();
            opMode.telemetry.update();
        }

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForBRelease();
    }

    // endregion

    // region motor - timed

    public void motorPowerTimed(DcMotor motor, double power, int timeout){
        if(motor == null) throw new IllegalStateException("motor is null");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setPower(power);
        runMotorTimed(motor,timeout);
    }

    public void motorSpeedTimed(DcMotor motor, double power, int timeout){
        if(motor == null) throw new IllegalStateException("motor is null");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setPower(power);
        runMotorTimed(motor, timeout);
    }

    public void motorPositionTimed(DcMotor motor, int targetPosition, double power, int timeout){
        if(motor == null) throw new IllegalStateException("motor is null");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setPower(power);
        motor.setTargetPosition(targetPosition); // must set before calling .setMode(RUN_TO_POSITION)
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        runMotorTimed(motor, timeout);
    }

    private void runMotorTimed(DcMotor motor, int timeout) {
        setTimeout(timeout);
        trackTime();
        int pos=0;
        double rev=0.0;
        while(testModeIsActive()){
            pos = motor.getCurrentPosition();
            rev = pos/ SimpleHardwareTestMethods.COUNTS_PER_MOTOR_REV;
            opMode.telemetry.addData("pos (#,rev)", "%d %.2f", pos, rev);
            tellUserTimeRemaining();
            tellUserHowToExit();
            opMode.telemetry.update();
        }
        clearTimeout();
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double elapsed = elapsed();
        int posRate = (int)Math.round(pos/elapsed);
        double revRate = rev/elapsed;

        // leave results on screen
        opMode.telemetry.addData("--Mode Complete--", "");
        opMode.telemetry.addData("pos (#,rev)", "%d %.2f", pos, rev);
        opMode.telemetry.addData("rate (#/s,rev/s)", "%d %.2f", posRate, revRate);
        tellUserHowToExit();
        opMode.telemetry.update();

        waitForBRelease();
        waitForBPress();
        waitForBRelease();
    }

    // endregion

    // region servo
    public void moveServo(
            Servo servo,
            @Config(label="position", min=0.0, max=1.0, step=.01, displayScale = 100, value = 0.5, units = "%") double position,
            @Config(label="direction", isTrue=true, trueString = "forward", falseString = "backward") boolean directionForward,
            @Config(label="range-min", min=0.0, max=1.0, step=.01, displayScale = 100, value = 0.0, units = "%") double rangeMin,
            @Config(label="range-max", min=0.0, max=1.0, step=.01, displayScale = 100, value = 1.0, units = "%") double rangeMax
            ){
        // configure
        servo.scaleRange(rangeMin,rangeMax);
        servo.setDirection(directionForward ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);

        ButtonTracker tracker = new ButtonTracker(opMode.gamepad1);

        while(testModeIsActive()) {
            if(tracker.dpadRightPressed()) position += 0.01;
            if(tracker.dpadLeftPressed()) position -= 0.01;
            servo.setPosition(position);
            opMode.telemetry.addData("Servo Position:", "%.0f", position * 100);
            opMode.telemetry.addData("Change using:", "dpad-left/dpad-right");
            opMode.telemetry.addData("Servo Dir:", directionForward ? "forward" : "reverse");
            opMode.telemetry.addData("Servo Range:", "%.0f to %.0f", rangeMin * 100, rangeMax * 100);
            tellUserHowToExit();
            opMode.telemetry.update();
        }

    }

    // endregion

    public void colorSensorV3(RevColorSensorV3 sensor){
        clearTimeout();
        while(testModeIsActive()){
            int red = sensor.red() >> 3; // convert from 5-bit to 8bit
            int green = sensor.green() >> 3;
            int blue = sensor.blue() >> 3;

            // convert the RGB values to HSV values.
            float hsvValues[] = {0F,0F,0F};
            Color.RGBToHSV( red, green, blue, hsvValues);

            opMode.telemetry.addData("red green blue", red+" "+green+" "+blue);
            opMode.telemetry.addData("H/S/V", "%.2f %.2f %.2f", hsvValues[0],hsvValues[1],hsvValues[2] );
            opMode.telemetry.addData("Distance (cm)", "%.2f", sensor.getDistance( DistanceUnit.CM ) );
            tellUserHowToExit();
            opMode.telemetry.update();
        }

        waitForBRelease();
    }

    public void distanceSensor2m(Rev2mDistanceSensor sensor){
        DistanceUnit unit = DistanceUnit.INCH;
        while(testModeIsActive()){
            opMode.telemetry.addData("Distance ("+unit.toString()+")", sensor.getDistance(unit) );
            tellUserHowToExit();
            opMode.telemetry.update();
        }
        waitForBRelease();
    }

    public void blinkinLeds(RevBlinkinLedDriver driver){
        RevBlinkinLedDriver.BlinkinPattern color = RevBlinkinLedDriver.BlinkinPattern.GREEN;

        RevBlinkinLedDriver.BlinkinPattern[] colors = new RevBlinkinLedDriver.BlinkinPattern[]{
                RevBlinkinLedDriver.BlinkinPattern.BLACK,
                RevBlinkinLedDriver.BlinkinPattern.RED,
                RevBlinkinLedDriver.BlinkinPattern.ORANGE,
                RevBlinkinLedDriver.BlinkinPattern.YELLOW,
                RevBlinkinLedDriver.BlinkinPattern.GREEN,
                RevBlinkinLedDriver.BlinkinPattern.BLUE,
                RevBlinkinLedDriver.BlinkinPattern.BLUE_VIOLET,
                RevBlinkinLedDriver.BlinkinPattern.VIOLET,
                RevBlinkinLedDriver.BlinkinPattern.RAINBOW_PARTY_PALETTE,
                RevBlinkinLedDriver.BlinkinPattern.RAINBOW_OCEAN_PALETTE,
                RevBlinkinLedDriver.BlinkinPattern.RAINBOW_LAVA_PALETTE,
                RevBlinkinLedDriver.BlinkinPattern.RAINBOW_FOREST_PALETTE,
                RevBlinkinLedDriver.BlinkinPattern.RAINBOW_WITH_GLITTER,
                RevBlinkinLedDriver.BlinkinPattern.CONFETTI,
                RevBlinkinLedDriver.BlinkinPattern.SHOT_RED,
                RevBlinkinLedDriver.BlinkinPattern.SHOT_BLUE,
                RevBlinkinLedDriver.BlinkinPattern.SHOT_WHITE,
                RevBlinkinLedDriver.BlinkinPattern.SINELON_RAINBOW_PALETTE,
                RevBlinkinLedDriver.BlinkinPattern.SINELON_PARTY_PALETTE,
                RevBlinkinLedDriver.BlinkinPattern.SINELON_OCEAN_PALETTE,
                RevBlinkinLedDriver.BlinkinPattern.SINELON_LAVA_PALETTE,
                RevBlinkinLedDriver.BlinkinPattern.SINELON_FOREST_PALETTE,
                RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_RAINBOW_PALETTE,
                RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE,
                RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_OCEAN_PALETTE,
                RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_LAVA_PALETTE,
                RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_FOREST_PALETTE,
        };

        int index = 0;
        driver.setPattern(colors[index]);

        ButtonTracker tracker = new ButtonTracker(opMode.gamepad1);

        while(testModeIsActive()) {
            if(tracker.dpadRightPressed() && index<colors.length-1) driver.setPattern(colors[++index]);
            if(tracker.dpadLeftPressed() && index>0 ) driver.setPattern(colors[--index]);
            opMode.telemetry.addData("BLINKING Lights", "");
            opMode.telemetry.addData("Pattern:", colors[index].toString() );
            opMode.telemetry.addData("Change Colors","left/right dpad");
            tellUserHowToExit();
            opMode.telemetry.update();
        }

        waitForBRelease();
    }

}
