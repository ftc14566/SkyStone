package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Z teleOp", group="Iterative Opmode")
public class TelemetryTestOpMode extends OpMode {
    private TeleBot bot;

    @Override
    public void init() {

        Hardware hardware = new Hardware();
        hardware.init( hardwareMap );
        bot = new TeleBot(hardware);

    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {


        bot.MoveLR(-gamepad1.left_stick_y,gamepad1.left_stick_x,-gamepad1.right_stick_y,gamepad1.right_stick_x);
        //bot.SpinRight(gamepad1.right_stick_x);
        telemetry.addData("Stick L X",gamepad1.left_stick_x);
        telemetry.addData("Stick L Y",gamepad1.left_stick_y);
        telemetry.addData("Stick R X",gamepad1.right_stick_x);
        telemetry.addData("Stick R Y",gamepad1.right_stick_y);
       // telemetry.addData("Spin",gamepad1.right_stick_x);
        telemetry.update();
    }


    @Override
    public void stop() {
    }

}
