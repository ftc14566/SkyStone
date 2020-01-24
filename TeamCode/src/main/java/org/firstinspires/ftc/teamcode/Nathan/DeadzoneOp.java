package org.firstinspires.ftc.teamcode.Nathan;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.TeleBot;

@TeleOp(name="DeadzoneOp", group="Iterative Opmode")
public class DeadzoneOp extends OpMode {
    private TeleBot bot;
    private Hardware hardware;

    @Override
    public void init() {

        hardware = new Hardware();
        hardware.init( hardwareMap );
        bot = new TeleBot(hardware);
        
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */



    @Override
    public void init_loop() {
        //hardware.leftTowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //hardware.rightTowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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

    private boolean lastDpadUp;
    private float deadZone = 0.0f;

    @Override
    public void loop() {

        if (gamepad1.dpad_up && ! lastDpadUp){
            deadZone += 0.01;
            gamepad1.setJoystickDeadzone(deadZone);
        }
        lastDpadUp = gamepad1.dpad_up;

        gamepad1.setJoystickDeadzone(0.0f);
        bot.driveAndStrafe(-gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x);

        telemetry.addData("DeadZone","%0.0f",deadZone*100);
        telemetry.update();
    }


    @Override
    public void stop() {

    }

}
