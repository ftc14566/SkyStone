package org.firstinspires.ftc.teamcode.Nathan;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.TeleBot;

@TeleOp(name="ZZZNathanDriverOp", group="Iterative Opmode")
public class NathanDriverOp extends OpMode {
    private NathanTeleBot bot;
    private Hardware hardware;

    @Override
    public void init() {

        hardware = new Hardware();
        hardware.init( hardwareMap );
        bot = new NathanTeleBot(hardware);
        
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */


    public double cube (double d){
        return d*d*d*d*d*d*d*d*d*d*d*d*d*d*d*d*d;
    }

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
gamepad1.setJoystickDeadzone(0.00f);

    }
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */

    @Override
    public void loop() {

        // Drive
        //bot.driveAndStraif(gamepad1.right_trigger, -gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x);
        bot.driveAndStrafe(cube(-gamepad1.left_stick_y),cube(gamepad1.left_stick_x),cube(gamepad1.right_stick_x));
        //bot.MoveLR(-gamepad1.left_stick_y,gamepad1.left_stick_x,-gamepad1.right_stick_y,gamepad1.right_stick_x);
        bot.extend(gamepad2.y,gamepad2.a);
        bot.foundationServos(gamepad1.x, gamepad1.b);
        bot.Lift(gamepad2.dpad_up, gamepad2.dpad_down);
        String grabberAction = bot.determineGrabberAction(gamepad2.x, gamepad2.b, bot.isBlockInFront(),gamepad2.left_bumper);

        bot.SetLightColor(this.time,gamepad1.b,grabberAction=="grab",gamepad2.x);

        telemetry.addData("GamePad",gamepad1.left_stick_y);

        //bot.grab(gamepad2.x,bot.isBlockInFront());
        bot.blockGrabberWithActivate(grabberAction);
        telemetry.addData("Bridge", hardware.bridgeMotor.getCurrentPosition());
        telemetry.update();
    }



    @Override
    public void stop() {
        telemetry.addData("HaHaHa I am stoped","");
        telemetry.addData("Tell Nathan What when you see this","");
    }

}
