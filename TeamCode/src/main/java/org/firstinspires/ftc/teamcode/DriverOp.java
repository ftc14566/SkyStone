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


    }
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */

    @Override
    public void loop() {

        bot.checkSpeed(gamepad1.left_trigger, -gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
        bot.extend(gamepad2.y,gamepad2.a);
        bot.foundationServos(gamepad1.x, gamepad1.b);
        bot.Lift(gamepad2.dpad_up, gamepad2.dpad_down);
        /*
        if (gamepad2.b)
            bot.ExtendWithEncoders(NathanBot.BridgePosition.Grabbing);

        else if (gamepad2.a)
            bot.ExtendWithEncoders(NathanBot.BridgePosition.In);
        else if (gamepad2.y)
            bot.ExtendWithEncoders(NathanBot.BridgePosition.Out);
        */
        String grabberAction = bot.determineGrabberAction(gamepad2.x, gamepad2.b, bot.isBlockInFront(),gamepad2.left_bumper);

        bot.SetLightColor(this.time,gamepad1.b,grabberAction=="grab",gamepad2.x);

        //bot.grab(gamepad2.x,bot.isBlockInFront());
        bot.blockGrabberWithActivate(grabberAction);
        telemetry.addData("Bridge", hardware.bridgeMotor.getCurrentPosition());
        telemetry.update();
    }

/*
		telemetry.addData("Right Tower Position", hardware.rightTowerMotor.getCurrentPosition());
		telemetry.addData("Left Tower Position", hardware.leftTowerMotor.getCurrentPosition());

		telemetry.addData("Stick X",gamepad1.left_stick_x);
		telemetry.addData("Stick Y",gamepad1.left_stick_y);
		telemetry.addData("Spin",gamepad1.right_stick_x);
		telemetry.addLine("Left Color Sensor:");
		telemetry.addData("		Red",hardware.leftColorSensor.red());
		telemetry.addData("		Green",hardware.leftColorSensor.green());
		telemetry.addData("		Blue",hardware.leftColorSensor.blue());
		telemetry.addData("		Alpha (Light)", hardware.leftColorSensor.alpha());
		telemetry.addLine("Right Color Sensor:");
		telemetry.addData("		Red",hardware.rightColorSensor.red());
		telemetry.addData("		Green",hardware.rightColorSensor.green());
		telemetry.addData("		Blue",hardware.rightColorSensor.blue());
		telemetry.addData("		Alpha (Light)", hardware.rightColorSensor.alpha());

		telemetry.addData("lStick X",gamepad1.left_stick_x);
		telemetry.addData("lStick Y",gamepad1.left_stick_y);
		telemetry.addData("rStick X",gamepad1.right_stick_x);
		telemetry.addData("rStick Y",gamepad1.right_stick_x);

		telemetry.addData("up",gamepad1.dpad_up);
		telemetry.update();
*/

    @Override
    public void stop() {
        telemetry.addData("HaHaHa I am stoped","");
        telemetry.addData("Tell Nathan What when you see this","");
    }

}
