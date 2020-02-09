package org.firstinspires.ftc.teamcode.Nathan;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp(name="NathanOp", group="Iterative Opmode")
public class NathanOp extends OpMode {
    private NathanBot bot;
    private Hardware hardware;



    @Override
    public void init() {

        hardware = new Hardware();
        hardware.init( hardwareMap );
        bot = new NathanBot(hardware);
        telemetry.addLine("Why are you looking at me?");
        telemetry.addLine("I mean nothing");
        telemetry.addLine("LOOK AWAY!");
        telemetry.addLine("and tell Nathan you found me");
        telemetry.update();

        hardware.leftTowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rightTowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.leftTowerMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hardware.rightTowerMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hardware.bridgeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    private void motorTelemetry(String caption, DcMotor motor, int zero){
        telemetry.addData(caption, "%d Counts : %2.0f Power", motor.getCurrentPosition()-zero, motor.getPower()*100);

    }

    private void showWheelTelemetry(){
        motorTelemetry("Front Left", hardware.frontLeftDrive, 0);
        motorTelemetry("Front Right", hardware.frontRightDrive, 0);
        motorTelemetry("Rear Left", hardware.rearLeftDrive, 0);
        motorTelemetry("Rear Right", hardware.rearRightDrive, 0);
        telemetry.update();
    }

    private void showTowerTelemetry(){
        motorTelemetry("Left Tower", hardware.leftTowerMotor, bot.leftzero);
        motorTelemetry("RIght Tower",hardware.rightTowerMotor, bot.rightzero);
        telemetry.addData("Bridge","%2.1f", hardware.bridgeDistance.getDistance(DistanceUnit.CM));
        telemetry.addData("Switch", hardware.touchSensor.isPressed()?"PRESSED":"--");
        telemetry.addData("BlockDistance", hardware.distanceSensor.getDistance(DistanceUnit.CM));
        telemetry.addData("Bridge Mode", bot.bridgeAction.name());
        telemetry.addData("Auto", bot.doAutoGrab);
        telemetry.addData("Auto Extend", bot.autoExtend);
        telemetry.addData(":)",null);
        telemetry.update();
    }

    boolean previousLeftBummperState = false;
    int telemetryIndex = 0;

    private void showTelemetry (){



        if (!previousLeftBummperState && gamepad1.left_bumper ){
            telemetryIndex = (telemetryIndex +1) %2;
        }


        previousLeftBummperState = gamepad1.left_bumper;



        if (telemetryIndex == 0)
            showTowerTelemetry();
        else if (telemetryIndex == 1)
            showWheelTelemetry();



    }


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
        bot.autoGrab(gamepad2,time);
        bot.checkSpeed(gamepad1.left_trigger, -gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
        bot.foundationServos(gamepad1.x, gamepad1.b);
        showTelemetry();



    }


    @Override
    public void stop() {
        telemetry.addData("HaHaHa I am stoped","");
        telemetry.addData("Tell Nathan What when you see this","");
        telemetry.update();
    }

}

