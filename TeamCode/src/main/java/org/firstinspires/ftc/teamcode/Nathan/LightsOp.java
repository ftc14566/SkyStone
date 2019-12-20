package org.firstinspires.ftc.teamcode.Nathan;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.Nathan.NathanBot;

@TeleOp(name="LightsOp", group="Iterative Opmode")
public class LightsOp extends OpMode {
    private HubBot bot;
    private TestHardware hardware;

    @Override
    public void init() {

        hardware = new TestHardware();
        hardware.init( hardwareMap );
        bot = new HubBot(hardware);

    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */



    @Override
    public void init_loop() {
         }

    @Override
    public void start() {


    }
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */

    @Override
    public void loop() {
    bot.SetLightPattern();


       }

}



