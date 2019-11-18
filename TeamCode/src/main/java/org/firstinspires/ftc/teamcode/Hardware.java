package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

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
	public ColorSensor leftColorSensor;
	public ColorSensor rightColorSensor;


	public void init(HardwareMap hardwareMap) {
		frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeftWheel");
		frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
		frontRightDrive = hardwareMap.get(DcMotor.class, "frontRightWheel");
		frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
		rearLeftDrive = hardwareMap.get(DcMotor.class, "rearLeftWheel");
		rearLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
		rearRightDrive = hardwareMap.get(DcMotor.class, "rearRightWheel");
		rearRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
		leftTowerMotor = hardwareMap.get(DcMotor.class, "leftTower");
		leftTowerMotor.setDirection(DcMotorSimple.Direction.REVERSE);
		rightTowerMotor = hardwareMap.get(DcMotor.class, "rightTower");
		rightTowerMotor.setDirection(DcMotorSimple.Direction.REVERSE);
		leftColorSensor = hardwareMap.get(ColorSensor.class, "colorSensorLeft");
		leftColorSensor.red();
		leftColorSensor.green();
		leftColorSensor.blue();
		leftColorSensor.alpha();
		leftColorSensor.argb();
		rightColorSensor = hardwareMap.get(ColorSensor.class, "colorSensorRight");
		rightColorSensor.red();
		rightColorSensor.green();
		rightColorSensor.blue();
		rightColorSensor.alpha();
		rightColorSensor.argb();

	}


}
