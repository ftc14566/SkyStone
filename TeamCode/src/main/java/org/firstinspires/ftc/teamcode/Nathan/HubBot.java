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

    public void SetLightColor (double time,boolean foundationDown, boolean grabberDown,boolean grabberOpen){

        RevBlinkinLedDriver.BlinkinPattern color;


        if (foundationDown)
            color = RevBlinkinLedDriver.BlinkinPattern.BLUE_VIOLET;
        else if (grabberDown)
            color = RevBlinkinLedDriver.BlinkinPattern.GREEN;
        else if (grabberOpen)
            color = RevBlinkinLedDriver.BlinkinPattern.GOLD;
        else if (time <90)
            color = RevBlinkinLedDriver.BlinkinPattern.DARK_BLUE;
        else if (time < 90.1)
            color = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
        else if (time < 90.2)
            color = RevBlinkinLedDriver.BlinkinPattern.BLACK;
        else if (time < 90.3)
            color = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
        else if (time < 90.4)
            color = RevBlinkinLedDriver.BlinkinPattern.BLACK;
        else if (time < 90.5)
            color = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
        else if (time < 90.6)
            color = RevBlinkinLedDriver.BlinkinPattern.BLACK;
        else if (time < 90.7)
            color = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
        else if (time < 90.8)
            color = RevBlinkinLedDriver.BlinkinPattern.BLACK;
        else if (time < 90.9)
            color = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;

        else
            color = RevBlinkinLedDriver.BlinkinPattern.SINELON_LAVA_PALETTE;
        hardware.Lights.setPattern(color);
    }

}