package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.*;

// This class contains items that apply to the robot regardless of the opmode or operation.
public class Hardware {

	//==============================
	// Add Hardware Constants here:
	//==============================
	// static final double     WHEEL_SEPARATION = 15.25 ;
	// static final double     WHEEL_DIAMETER_INCHES   = 6.75 ;// For figuring circumference
	// static final double     COUNTS_PER_MOTOR_REV    = 288 ;
	// static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV) / (WHEEL_DIAMETER_INCHES * 3.1415);

	//==============================
	// Add Hardware Properties here:
	//==============================
	public DcMotor frontLeftDrive;
	public DcMotor frontRightDrive;
	public DcMotor rearLeftDrive;
	public DcMotor rearRightDrive;
	public DcMotor leftTowerMotor;
	public DcMotor rightTowerMotor;
//	public Servo marker_servo;
//	public Servo lifter_lock;

	public void init(HardwareMap hardwareMap) {
		frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeftWheel");
		frontLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
		frontRightDrive = hardwareMap.get(DcMotor.class, "frontRightWheel");
		frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
		rearLeftDrive = hardwareMap.get(DcMotor.class, "rearLeftWheel");
		rearLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
		rearRightDrive = hardwareMap.get(DcMotor.class, "rearRightWheel");
		rearRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
		leftTowerMotor = hardwareMap.get(DcMotor.class,"leftTowerMotor");
		leftTowerMotor.setDirection(DcMotorSimple.Direction.REVERSE);
		rightTowerMotor = hardwareMap.get(DcMotor.class,"rightTowerMotor");
		rightTowerMotor.setDirection(DcMotorSimple.Direction.FORWARD);
	}


}
