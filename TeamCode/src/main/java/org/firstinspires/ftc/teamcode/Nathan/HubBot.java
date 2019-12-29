package org.firstinspires.ftc.teamcode.Nathan;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.Light;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.R;

public class HubBot{
    public TestHardware hardware;

    public HubBot(TestHardware hardware){
        this.hardware = hardware;
    }

    public void SetLightColor (boolean beBlue, boolean beRed){

        RevBlinkinLedDriver.BlinkinPattern color = RevBlinkinLedDriver.BlinkinPattern.GREEN;
        if (beBlue)
            color = RevBlinkinLedDriver.BlinkinPattern.BLUE;
        else if (beRed)
            color = RevBlinkinLedDriver.BlinkinPattern.RED;
        hardware.Lights.setPattern(color);
    }

}