package org.firstinspires.ftc.teamcode.Nolan;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware;

public class AutoDrive {

    public AutoDrive(Hardware hardware, LinearOpMode mode ){
        this.hardware = hardware;
        this.opMode = mode;
    }

    private Hardware hardware;
    private LinearOpMode opMode;

    public static final double     WHEEL_DIAMETER_INCHES   = 6 ;// For figuring circumference
    public static final double     COUNTS_PER_MOTOR_REV    = 1120 ;
    public static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV) / (WHEEL_DIAMETER_INCHES * 3.1415);
    public static final double     WHEEL_SEPARATION        = 9.5;

    public void resetEncoders(){
        hardware.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void encoderRun() {
        hardware.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hardware.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hardware.rearLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hardware.rearRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void noEncoderRun(){
        hardware.frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hardware.frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hardware.rearLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hardware.rearRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setPowerStrafe(double speed) {
        hardware.frontLeftDrive.setPower(speed);
        hardware.frontRightDrive.setPower(speed);
        hardware.rearLeftDrive.setPower(speed);
        hardware.rearRightDrive.setPower(speed);
    }

    public void stopDrive(){
        hardware.frontLeftDrive.setPower(0);
        hardware.frontRightDrive.setPower(0);
        hardware.rearLeftDrive.setPower(0);
        hardware.rearRightDrive.setPower(0);
    }


    public void driveStrait(double inches, double speed) {
        driveForward(inches, inches, speed);
    }

    public void driveForward(double leftInches, double rightInches, double speed ){
        resetEncoders();
        encoderRun();

        int frontLeftTarget = hardware.frontLeftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
        int frontRightTarget = hardware.frontRightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
        int rearLeftTarget = hardware.rearLeftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
        int rearRightTarget = hardware.rearRightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);

        hardware.frontLeftDrive.setTargetPosition(frontLeftTarget);
        hardware.frontRightDrive.setTargetPosition(frontRightTarget);
        hardware.rearLeftDrive.setTargetPosition(rearLeftTarget);
        hardware.rearRightDrive.setTargetPosition(rearRightTarget);

        hardware.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.rearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.rearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        hardware.frontLeftDrive.setPower(Math.abs(speed));
        hardware.frontRightDrive.setPower(Math.abs(speed));
        hardware.rearLeftDrive.setPower(Math.abs(speed));
        hardware.rearRightDrive.setPower(Math.abs(speed));

        while(opMode.opModeIsActive() &&
                ((hardware.frontLeftDrive.isBusy() && hardware.frontRightDrive.isBusy())
                        && (hardware.rearLeftDrive.isBusy() && hardware.frontRightDrive.isBusy()))){
                opMode.sleep(100);
        }
        stopDrive();
    }


    public void spinRight(double degrees, double speed){
        double inches = (degrees * WHEEL_SEPARATION/2 * 3.1415926 / 180)/2;
        driveForward(inches*2, -inches*2,.3);
    }

    public void strafeRight(double inches,double speed){
        resetEncoders();
        encoderRun();

        int frontLeftTarget = hardware.frontLeftDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH) ;
        int frontRightTarget = (hardware.frontRightDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH))*-1;
        int rearLeftTarget = (hardware.rearLeftDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH))*-1;
        int rearRightTarget = hardware.rearRightDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);

        hardware.frontLeftDrive.setTargetPosition(frontLeftTarget);
        hardware.frontRightDrive.setTargetPosition(frontRightTarget);
        hardware.rearLeftDrive.setTargetPosition(rearLeftTarget);
        hardware.rearRightDrive.setTargetPosition(rearRightTarget);

        hardware.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.rearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.rearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        hardware.frontLeftDrive.setPower(Math.abs(speed));
        hardware.frontRightDrive.setPower(Math.abs(speed));
        hardware.rearLeftDrive.setPower(Math.abs(speed));
        hardware.rearRightDrive.setPower(Math.abs(speed));

        while(opMode.opModeIsActive() &&
                ((hardware.frontLeftDrive.isBusy() && hardware.frontRightDrive.isBusy())
                        && (hardware.rearLeftDrive.isBusy() && hardware.rearRightDrive.isBusy()))){
            opMode.sleep(100);
        }
        stopDrive();
    }

    public void moveForwardDistanceSensor(double CM, double speed, int timeout){
        noEncoderRun();
        hardware.frontLeftDrive.setPower(speed);
        hardware.frontRightDrive.setPower(speed);
        hardware.rearLeftDrive.setPower(speed);
        hardware.rearRightDrive.setPower(speed);

        int endCount = timeout*10000000;
        int count = 0;
        while(opMode.opModeIsActive() && ((count < endCount) && (CM <= hardware.distanceSensor.getDistance(DistanceUnit.CM)))){
            //opMode.telemetry.addData("distance inch", hardware.distanceSensor.getDistance(DistanceUnit.INCH));
            //opMode.telemetry.update();

            opMode.sleep(100);
            count = count + 1;
        }
        stopDrive();
    }

    public void moveForwardDistanceBreaking(double CM, double startSpeed, double startDecrease ,double decreaseFactor) {
        hardware.frontLeftDrive.setPower(startSpeed);
        hardware.rearLeftDrive.setPower(startSpeed);
        hardware.frontRightDrive.setPower(startSpeed);
        hardware.rearRightDrive.setPower(startSpeed);

        double newSpeed = startSpeed;

        while(opMode.opModeIsActive() && (CM <= hardware.distanceSensor.getDistance(DistanceUnit.CM))) {
            if(startDecrease <= hardware.distanceSensor.getDistance(DistanceUnit.CM))
                newSpeed -= decreaseFactor;
            hardware.frontLeftDrive.setPower(newSpeed);
            hardware.rearLeftDrive.setPower(newSpeed);
            hardware.frontRightDrive.setPower(newSpeed);
            hardware.rearRightDrive.setPower(newSpeed);
        }
        stopDrive();
    }

    public void skystoneAlignRed() {
        double leftGreen = hardware.leftColorSensor.green();
        double leftRed = hardware.leftColorSensor.red();
        double rightGreen = hardware.rightColorSensor.green();
        double rightRed = hardware.rightColorSensor.red();

        if(leftGreen >= (leftRed + (.1*leftGreen))+50 && leftGreen <= (leftRed + (.1*leftGreen))-50){
            skystoneAlignLeft();
        } else if(rightGreen >= (rightRed + (.1*rightGreen))+50 && rightGreen <= (rightRed + (.1*rightGreen))-50){
            skystoneAlignRight();
        } else{
            skystoneAlignRight();
        }
    }

    public void skystoneAlignRight(){
        double leftGreen = hardware.leftColorSensor.green();
        double leftRed = hardware.leftColorSensor.red();
        hardware.frontLeftDrive.setPower(-0.2);
        hardware.frontRightDrive.setPower(-0.2);
        hardware.rearLeftDrive.setPower(0.2);
        hardware.rearRightDrive.setPower(0.2);
        while(!(leftGreen >= (leftRed + (.1*leftGreen))+15 && leftGreen <= (leftRed + (.1*leftGreen))-15)){
            leftGreen = hardware.leftColorSensor.green();
            leftRed = hardware.leftColorSensor.red();
            opMode.sleep(10);
        }
        stopDrive();
    }

    public void skystoneAlignLeft(){
        double rightGreen = hardware.leftColorSensor.green();
        double rightRed = hardware.leftColorSensor.red();
        hardware.frontLeftDrive.setPower(0.2);
        hardware.frontRightDrive.setPower(0.2);
        hardware.rearLeftDrive.setPower(-0.2);
        hardware.rearRightDrive.setPower(-0.2);
        while(!(rightGreen >= (rightRed + (.1*rightGreen))+50 && rightGreen <= (rightRed + (.1*rightGreen))-50)){
            rightGreen = hardware.leftColorSensor.green();
            rightRed = hardware.leftColorSensor.red();
            opMode.sleep(10);
        }
        stopDrive();
    }
}

/*
TODO POSSIBLE EQUATIONS
 *R = B
 * G = R + .1G
 */
