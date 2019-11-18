package org.firstinspires.ftc.teamcode;

//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name="AutoBot", group="Linear Opmode")
//@Disabled
public class AutoBot extends LinearOpMode {

	// Declare OpMode members.

	private ElapsedTime runtime = new ElapsedTime();

	private int counts_per_rev = 2240;
	private double wheel_diameter = 4;
	private double counts_per_inch = (counts_per_rev) / (wheel_diameter * 3.1415);
	protected Hardware bot;

	public void stopAndResetEncoders(){
		bot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		bot.rearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		bot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		bot.rearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	}

	public void runWithoutEncoders(){
		bot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		bot.rearLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		bot.frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		bot.rearRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
	}

	public void runUsingEncoder(){
		bot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		bot.rearLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		bot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		bot.rearRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
	}

	public void determineDistance(double distance){
		int new_front_left_position = bot.frontLeftDrive.getCurrentPosition() + (int)(distance * counts_per_inch);
		int new_rear_left_position = bot.rearLeftDrive.getCurrentPosition() + (int)(distance * counts_per_inch);
		int new_front_right_position = bot.frontRightDrive.getCurrentPosition() + (int)(distance * counts_per_inch);
		int new_rear_right_position = bot.rearRightDrive.getCurrentPosition() + (int)(distance * counts_per_inch);
		setTargets(new_front_left_position, new_rear_left_position, new_front_right_position, new_rear_right_position);
	}

	public void setTargets(int frontLeft, int rearLeft, int frontRight, int rearRight){
		bot.frontLeftDrive.setTargetPosition(frontLeft);
		bot.rearLeftDrive.setTargetPosition(rearLeft);
		bot.frontRightDrive.setTargetPosition(frontRight);
		bot.rearRightDrive.setTargetPosition(rearRight);
	}

	public void runPosition(){
		bot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		bot.rearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		bot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		bot.rearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	}

	public void runMotors(double howFast){
		bot.frontLeftDrive.setPower(howFast);
		bot.rearLeftDrive.setPower(howFast);
		bot.frontRightDrive.setPower(howFast);
		bot.rearRightDrive.setPower(howFast);
	}

	public void stopMotors(){
		bot.frontLeftDrive.setPower(0);
		bot.rearLeftDrive.setPower(0);
		bot.frontRightDrive.setPower(0);
		bot.rearRightDrive.setPower(0);
	}

	public void driveForwards(double inches, double speed) {
		stopAndResetEncoders();
		runUsingEncoder();
		determineDistance(inches);
		runPosition();
		runMotors(speed);

		while (opModeIsActive() && ((bot.frontLeftDrive.isBusy() || bot.rearLeftDrive.isBusy()) || (bot.frontRightDrive.isBusy() || bot.rearRightDrive.isBusy()))) {
	}
		idle();

		stopMotors();
		runWithoutEncoders();
	}

	@Override
	public void runOpMode() {
		//hardware.frontLeftDrive.setPower(.5);
		bot = new Hardware();
		bot.init(hardwareMap);
		// Wait for the game to start (driver presses PLAY)
		waitForStart();
		runtime.reset();

		driveForwards(10, .3);
	}
}


