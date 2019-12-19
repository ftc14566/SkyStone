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




    public void resetEncoder(){
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

    public void stopDrive(){
        hardware.frontLeftDrive.setPower(0);
        hardware.frontRightDrive.setPower(0);
        hardware.rearLeftDrive.setPower(0);
        hardware.rearRightDrive.setPower(0);
    }




    public void DriveForward(double inches, double speed ){
        while(opMode.opModeIsActive()){
            resetEncoder();
        }
    }

    public void SpinRight(double degrees, double speed){

        while(opMode.opModeIsActive()){
            resetEncoder();
        }
    }

    public void StrafeRight(double inches,double speed){

        while(opMode.opModeIsActive()) {
            resetEncoder();
        }
    }

    public void moveForwardDistanceSensor(double inches, double speed, int timeout){
        noEncoderRun();
        hardware.frontLeftDrive.setPower(speed);
        hardware.frontRightDrive.setPower(speed);
        hardware.rearLeftDrive.setPower(speed);
        hardware.rearRightDrive.setPower(speed);

        //double startTime = opMode.getRuntime();
        while((inches <= hardware.distanceSensor.getDistance(DistanceUnit.INCH))){
            opMode.telemetry.addData("distance inch", hardware.distanceSensor.getDistance(DistanceUnit.INCH));
            opMode.telemetry.update();

            opMode.sleep(50);
        }
        stopDrive();
    }

}

