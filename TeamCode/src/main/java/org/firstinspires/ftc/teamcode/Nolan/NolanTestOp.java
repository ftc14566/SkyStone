/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Nolan;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.TeleBot;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Basic: Iterative OpMode", group="Iterative Opmode")
//@Disabled
public class NolanTestOp extends OpMode
{
    // Declare OpMode members.
    private Hardware hardware;
    private ElapsedTime runtime = new ElapsedTime();

    private int rightHSV;

    public void lifterUp() {
        while(gamepad1.dpad_up) {
            hardware.leftTowerMotor.setPower(1);
            hardware.leftTowerMotor.setPower(1);
        }
        hardware.leftTowerMotor.setPower(0.2);
        hardware.leftTowerMotor.setPower(0.2);
    }

    public void lifterDown() {
        while(gamepad1.dpad_down) {
            hardware.leftTowerMotor.setPower(0.02);
            hardware.rightTowerMotor.setPower(0.02);
        }
        hardware.leftTowerMotor.setPower(0.2);
        hardware.leftTowerMotor.setPower(0.2);
    }

    public void bridgeOut() {
        while(gamepad1.dpad_right) {
            hardware.bridgeMotor.setPower(0.2);
        }
        hardware.bridgeMotor.setPower(0);
    }

    public void bridgeIn() {
        while(gamepad1.dpad_left) {
            hardware.bridgeMotor.setPower(-0.2);
        }
        hardware.bridgeMotor.setPower(0);
    }

    public void grabBlock() {
       if(gamepad1.a) {
           hardware.grabberLeft.setPosition(0.9);
           hardware.grabberRight.setPosition(0.9);
       }
    }

    public void releaseBlock() {
        if(gamepad1.b) {
            hardware.grabberLeft.setPosition(0.1);
            hardware.grabberRight.setPosition(0.1);
        }
    }

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        hardware = new Hardware();
        hardware.init( hardwareMap );

        hardware.leftTowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.leftTowerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //bot = new TeleBot(hardware);
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        //leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        //rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        //leftDrive.setDirection(DcMotor.Direction.FORWARD);
        //rightDrive.setDirection(DcMotor.Direction.REVERSE);

        // Tell the driver that initialization is complete.
       // telemetry.addData("Status", "Initialized");
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
        runtime.reset();
        hardware.leftColorSensor.enableLed(true);
        hardware.rightColorSensor.enableLed(true);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    float hsvValues[] = {0F, 0F, 0F};
    final float values[] = hsvValues;
    final double SCALE_FACTOR = 255;

    @Override
    public void loop() {
        lifterUp();
        lifterDown();
        bridgeOut();
        bridgeIn();
        grabBlock();
        releaseBlock();

         Color.RGBToHSV((int) (hardware.rightColorSensor.red() * SCALE_FACTOR),
                (int) (hardware.rightColorSensor.green() * SCALE_FACTOR),
                (int) (hardware.rightColorSensor.blue() * SCALE_FACTOR),
                hsvValues);

        telemetry.addData("right color sensor",hardware.rightColorSensor.alpha());
        telemetry.addData("left color sensor",hardware.leftColorSensor.alpha());
        telemetry.addData("distance(CM)", hardware.distanceSensor.getDistance(DistanceUnit.CM));
        telemetry.addData("right tower motor", hardware.rightTowerMotor.getCurrentPosition());
        telemetry.addData("left tower motor", hardware.leftTowerMotor.getCurrentPosition());
        telemetry.addData("bridge motor", hardware.bridgeMotor.getCurrentPosition());
        telemetry.update();

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        hardware.leftTowerMotor.setPower(0);
        hardware.rightTowerMotor.setPower(0);
        hardware.bridgeMotor.setPower(0);

        hardware.leftColorSensor.enableLed(false);
        hardware.rightColorSensor.enableLed(false);
    }

}