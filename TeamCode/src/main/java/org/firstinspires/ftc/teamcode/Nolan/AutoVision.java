package org.firstinspires.ftc.teamcode.Nolan;

import com.disnodeteam.dogecv.detectors.skystone.SkystoneDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class AutoVision {

    public AutoVision(Hardware hardware, LinearOpMode mode ){
        this.hardware = hardware;
        this.opMode = mode;
    }

    private Hardware hardware;
    private LinearOpMode opMode;

    private OpenCvCamera phoneCam;
    private SkystoneDetector skyStoneDetector;

    public void startUp(){
        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK);
        phoneCam.openCameraDevice();
        skyStoneDetector = new SkystoneDetector();
        phoneCam.setPipeline(skyStoneDetector);
    }

    public void activate(){
        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
    }

    public void shutDown(){
        phoneCam.stopStreaming();
    }







}
