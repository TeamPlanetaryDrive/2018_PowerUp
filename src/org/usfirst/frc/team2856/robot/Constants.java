//what up
package org.usfirst.frc.team2856.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Constants {

	//CONSTANTS as in  numbers
	public static final double PERIODIC_UPDATE_PERIOD = 0.020; //Periodic update period (s)
	
	//Power Channels
	public static final int 
		DRIVE_POWER_LEFT_FRONT = 0,
		DRIVE_POWER_RIGHT_FRONT = 1,
		DRIVE_POWER_LEFT_REAR = 2,
		DRIVE_POWER_RIGHT_REAR = 3,
		CAMERA_POWER = 4,
		VRM_POWER = 5;

	//Digital (0-9, 10-25)
	public static int 
		LEFT_MOTOR_CHANNEL = 0,
		RIGHT_MOTOR_CHANNEL = 1;

	public static int 
		MANIPULATOR_CHANNEL = 2,
		LIFT_CHANNEL = 4,
		CLIMB_CHANNEL = 3;
	
	public static int LIMIT_SWITCH_CHANNEL = 6;

	//public static Port GYRO_PORT;

	//Analog Channels (0-3, 4-7)

	public static int 
		LEFT_ENC_CHANNEL_A = 0,
		LEFT_ENC_CHANNEL_B = 1,
		RIGHT_ENC_CHANNEL_A = 2,
		RIGHT_ENC_CHANNEL_B = 3;


	public static int 
		LEFT_JOYSTICK_PORT = 0,
		RIGHT_JOYSTICK_PORT = 1;

	//DriveTrain Variables
	public static double distancePerPulse = 0.003522;

	public static final double AUTO_DIST = 0.5;		// (feet) 15.0

	public static final boolean
		DRIVE_MOTOR_LEFT_AUTO_DIR = false,
		DRIVE_MOTOR_LEFT_TELE_DIR = true,
		DRIVE_MOTOR_RIGHT_AUTO_DIR = true,
		DRIVE_MOTOR_RIGHT_TELE_DIR = true;

	public static final double 
		DRIVE_ACCEL_RATE = 2.5,				// (ft/s^2) 5.0
		DRIVE_ENCODER_RESOLUTION = 0.003522,// (feet/count) Real: 0.003568, Practice: 0.003522
		DRIVE_GYRO_SENSITIVITY = 0.007,		// (volts/(degree/second)) 0.007
		DRIVE_PID_EFFORT_MAX = 0.50,			// (0-1) 1.0
		DRIVE_PID_PERIOD = 0.010,			// (s) 0.010
		DRIVE_PID_POS_SETTLE = 0.25,		// (s) 0.25
		DRIVE_SPEED_MAX = 2.5,				// (ft/s) 5.0
		DRIVE_BASE_WIDTH = (23.5 / 12.0),	// ft (in/12) 
		DRIVE_BASE_LENGTH = (32.0 /12.0);	// ft (in/12)
	
	public static final double
		MOVE_RIGHT_TURN_ANGLE = 90*(1.25);
	
	public static final int DRIVE_ENC_SAMPLES_TO_AVERAGE = 4;
	
	//Camera Resolution Dimensions
	public static final int CAMERA_RESOLUTION_WIDTH = 320, CAMERA_RESOLUTION_HEIGHT = 240;
	
	//HARDWARE
	//Left: Channel 0, Right: Channel 1
	public static SpeedController 
		lMotor, 
		rMotor;
	
	//Manipulator: Channel 2, Lift: Channel 4
	public static SpeedController 
		manipulator, 
		lift,
		climb;

	public static DigitalInput gearIn; //Channel 6

	public static Encoder 
		LEnc, //Channel 0 & 1
		REnc; //Channel 2 & 3

	//Left: Port 0, Right: Port 1
	public static Joystick 
		leftJoystick, 
		rightJoystick;
	
	public static JoystickButton 
		button2_left,
		button3_left,
		button4_left,
		button5_left,
		button2_right,
		button3_right, 
		button4_right, 
		button5_right;
	
	public static Gyro gyro;

	public static DigitalInput gear;

	public static void init(){
		//GYRO_PORT.value = 0;
/*
		lMotor = new Talon(LEFT_MOTOR_CHANNEL);
		rMotor = new Talon(RIGHT_MOTOR_CHANNEL);*/
		lMotor = new Jaguar(LEFT_MOTOR_CHANNEL);
		rMotor = new Jaguar(RIGHT_MOTOR_CHANNEL);

		lift = new Spark(LIFT_CHANNEL);
		
		climb = new Victor(CLIMB_CHANNEL);
		
		gearIn = new DigitalInput(LIMIT_SWITCH_CHANNEL);

		LEnc = new Encoder(LEFT_ENC_CHANNEL_A, LEFT_ENC_CHANNEL_B, true, EncodingType.k4X);
		REnc = new Encoder(RIGHT_ENC_CHANNEL_A, RIGHT_ENC_CHANNEL_B, false, EncodingType.k4X);

		leftJoystick = new Joystick(LEFT_JOYSTICK_PORT);
		button2_left = new JoystickButton(leftJoystick, 2);
		button3_left = new JoystickButton(leftJoystick, 3);
		button4_left = new JoystickButton(leftJoystick, 4);
		button5_left = new JoystickButton(leftJoystick, 5);
		
		rightJoystick = new Joystick(RIGHT_JOYSTICK_PORT);
		button2_right = new JoystickButton(rightJoystick, 2);
		button3_right = new JoystickButton(rightJoystick, 3);
		button4_right = new JoystickButton(rightJoystick, 4);
		button5_right = new JoystickButton(rightJoystick, 5);
		
		manipulator = new Spark(MANIPULATOR_CHANNEL);
		
		

		//gyro = new ADXRS450_Gyro();
		//gyro.calibrate();
	}
	
}
