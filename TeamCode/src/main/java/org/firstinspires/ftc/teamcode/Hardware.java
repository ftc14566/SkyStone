package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.*;


// This class contains items that apply to the robot regardless of the opmode or operation.
public class Hardware {

	public Hardware(){}
	public Hardware(HardwareMap hardwareMap){ init(hardwareMap); }

	//==============================
	// Add Hardware Constants here:
	//==============================

	 public static final double     WHEEL_DIAMETER_INCHES   = 1.25 ;// For figuring circumference
	 public static final double     COUNTS_PER_MOTOR_REV    = 288 ;
	 public static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV) / (WHEEL_DIAMETER_INCHES * 3.1415);

	//==============================
	// Add Hardware Properties here:
	//==============================
	public DcMotor frontLeftDrive;
	public DcMotor frontRightDrive;
	public DcMotor rearLeftDrive;
	public DcMotor rearRightDrive;
	public DcMotor leftTowerMotor;
	public DcMotor rightTowerMotor;
	public DcMotor bridgeMotor;
	public ColorSensor leftColorSensor;
	public ColorSensor rightColorSensor;
	public DistanceSensor distanceSensor;
	public Servo grabberLeft;
	public Servo grabberRight;
	public Servo leftFoundationServo;
	public Servo rightFoundationServo;

	public void init(HardwareMap hardwareMap) {
		frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeftWheel");
		frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

		frontRightDrive = hardwareMap.get(DcMotor.class, "frontRightWheel");
		frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);

		rearLeftDrive = hardwareMap.get(DcMotor.class, "rearLeftWheel");
		rearLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

		rearRightDrive = hardwareMap.get(DcMotor.class, "rearRightWheel");
		rearRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

		leftTowerMotor = hardwareMap.get(DcMotor.class, "leftTowerMotor");
		leftTowerMotor.setDirection(DcMotorSimple.Direction.REVERSE);
		rightTowerMotor = hardwareMap.get(DcMotor.class, "rightTowerMotor");
		rightTowerMotor.setDirection(DcMotorSimple.Direction.FORWARD);

		bridgeMotor = hardwareMap.get(DcMotor.class, "bridge");
		bridgeMotor.setDirection(DcMotorSimple.Direction.REVERSE);


		grabberLeft = hardwareMap.get(Servo.class, "grabberLeft");
		grabberRight = hardwareMap.get(Servo.class, "grabberRight");
		grabberLeft.setDirection(Servo.Direction.REVERSE);

		leftFoundationServo = hardwareMap.get(Servo.class,"leftFoundationServo");
		rightFoundationServo = hardwareMap.get(Servo.class, "rightFoundationServo");


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

		distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor");



		Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor)distanceSensor;

	}

}
