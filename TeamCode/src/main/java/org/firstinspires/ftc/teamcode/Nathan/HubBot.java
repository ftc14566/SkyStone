package org.firstinspires.ftc.teamcode.Nathan;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.Light;

import org.firstinspires.ftc.teamcode.Hardware;

public class HubBot{
    public TestHardware hardware;

    public HubBot(TestHardware hardware){
        this.hardware = hardware;
    }

    public void SetLightPattern (){

        hardware.Lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE_GREEN);

    }

}