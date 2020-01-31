package org.firstinspires.ftc.teamcode.Nolan;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware;

public class AutoLift {

    public AutoLift(Hardware hardware, LinearOpMode mode ){
        this.hardware = hardware;
        this.opMode = mode;
    }

    private Hardware hardware;
    private LinearOpMode opMode;

    public void lifterDown() {

    }

    public void grabBlock() {
        hardware.grabberLeft.setPosition(0.9);
        hardware.grabberRight.setPosition(0.9);
        opMode.sleep(1000);
    }

    public void releaseBlock() {
        hardware.grabberLeft.setPosition(0.1);
        hardware.grabberRight.setPosition(0.1);
    }

    public void linearSlideSet() {
        hardware.bridgeMotor.setPower(0.3);
        opMode.sleep(775);
        hardware.bridgeMotor.setPower(0);
        hardware.leftTowerMotor.setPower(1); hardware.rightTowerMotor.setPower(1);
        opMode.sleep(10);
        hardware.leftTowerMotor.setPower(0.2); hardware.rightTowerMotor.setPower(0.2);
    }

    public void upLifter() {
        hardware.leftTowerMotor.setPower(1); hardware.rightTowerMotor.setPower(1);
        opMode.sleep(2);
        hardware.leftTowerMotor.setPower(0.2); hardware.rightTowerMotor.setPower(0.2);
    }

    public void downLifter() {
        hardware.leftTowerMotor.setPower(0);
        hardware.rightTowerMotor.setPower(0);
    }

    public void linearSlideIn() {
        hardware.bridgeMotor.setPower(-0.3);
        opMode.sleep(775);
        hardware.bridgeMotor.setPower(0);
    }


}

