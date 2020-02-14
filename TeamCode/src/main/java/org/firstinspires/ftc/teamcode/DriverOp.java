package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.ColorSensor;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DigitalChannel;

        import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name="DriverOp", group="Iterative Opmode")
public class DriverOp extends OpMode {
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

    public double cube (double d){
        return d*d*d;
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
        this.resetStartTime();
    }

    @Override
    public void loop() {
        bot.autoGrab(gamepad2,time);
        bot.checkSpeed(gamepad1.left_trigger, -gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
        bot.foundationServos(gamepad1.x, gamepad1.b);
        bot.SetLightColor(bot.time,
                bot.foundationIsGrabbed,
                bot.grabberAction == TeleBot.GrabberAction.grab,
                bot.grabberAction == TeleBot.GrabberAction.open,
                bot.doAutoGrab);
      }

    @Override
    public void stop() {
        telemetry.addData("HaHaHa I am stoped","");
        telemetry.addData("Tell Nathan What when you see this","");
    }

}
