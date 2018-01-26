package org.usfirst.frc.team2856.robot.drivetrain;

import org.usfirst.frc.team2856.robot.Constants;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class DriveTrain{
	
	//just a git test, don't mind me
	
	public DifferentialDrive drive;
	//public RobotDrive drive;
	Encoder leftEnc, rightEnc;
//	PIDMotor leftPID, rightPID;
	Gyro gyro;
	PowerDistributionPanel power;

	MoveRefGen refGen;
	double leftMultiplier, rightMultiplier;
	double leftInitialPos, rightInitialPos;
	boolean moveActive;
	double smallNumber;
	PIDController leftPID, rightPID;
	
	private static double Kp = 2,
			Ki = 0.1,
			Kd = 1;

	private static double accelRate = 2.5;
	private static double maxSpeed = 2.5;


	public DriveTrain(){
		//drive = new RobotDrive(Constants.lMotor, Constants.rMotor);
		drive = new DifferentialDrive(Constants.lMotor, Constants.rMotor);
		leftPID = new PIDController(Kp, Ki, Kd, Constants.LEnc, Constants.lMotor, 0.01);
		rightPID = new PIDController(Kp, Ki, Kd, Constants.REnc, Constants.rMotor, 0.01);


		//Encoder Setup
		leftEnc = Constants.LEnc;
		rightEnc = Constants.REnc;

		leftEnc.reset();
		rightEnc.reset();

		leftEnc.setPIDSourceType(PIDSourceType.kDisplacement);
		rightEnc.setPIDSourceType(PIDSourceType.kDisplacement);

		leftEnc.setDistancePerPulse(Constants.distancePerPulse);
		rightEnc.setDistancePerPulse(Constants.distancePerPulse);

		leftEnc.setSamplesToAverage(Constants.DRIVE_ENC_SAMPLES_TO_AVERAGE);
		rightEnc.setSamplesToAverage(Constants.DRIVE_ENC_SAMPLES_TO_AVERAGE);

		//PIDController
//		leftPID.init(Constants.lMotor, true, Constants.LEnc);
//		rightPID.init(Constants.rMotor, false, Constants.REnc);
		leftPID.setOutputRange (-0.95, 0.95);
		rightPID.setOutputRange (-0.95, 0.95);

		

		gyro = Constants.gyro;
		gyro.reset();
		gyro.calibrate();
		
		moveActive = false;
		refGen = new MoveRefGen();

		power = new PowerDistributionPanel();
	}

	public void setSetpoint(){

		leftPID.setSetpoint(0000000000000000000);
		rightPID.setSetpoint(0000000000000000000);

	}

	//Built in arcade Drive
	public void arcadeDrive(GenericHID stick){
		drive.arcadeDrive(stick.getY(),stick.getX());
	}


	public void arcadeDrive(double move, double rot){
		drive.arcadeDrive(move, rot);
	}
	
	//tank drive 
	public void tankDrive(GenericHID leftStick, GenericHID rightStick){
		drive.tankDrive(leftStick, rightStick);
	}
	
	private void moveStart(double distance) {
		leftPID.reset(); 
		rightPID.reset();

		leftPID.setSetpoint( leftEnc.getDistance() );
		rightPID.setSetpoint( rightEnc.getDistance() );

		leftPID.enable();
		rightPID.enable();

		refGen.configure(accelRate, maxSpeed, Constants.DRIVE_PID_POS_SETTLE);
		refGen.start(distance);
		moveActive = true;
	}

	public void moveStraight(double distance) {
		leftMultiplier = 1;
		rightMultiplier = 1;
		moveStart(distance);
	}

	public void moveTurn(double angle, double radius) {
		double leftRadius, rightRadius;
		double fullRadius, distance;

		if(radius < 0.0)
		{
			leftRadius = -radius - Constants.DRIVE_BASE_WIDTH / 2.0;
			rightRadius = -radius + Constants.DRIVE_BASE_WIDTH / 2.0;
			fullRadius = rightRadius;
			leftMultiplier = leftRadius / fullRadius;
			rightMultiplier = 1.0;
		}
		else
		{
			leftRadius = radius + Constants.DRIVE_BASE_WIDTH / 2.0;
			rightRadius = radius - Constants.DRIVE_BASE_WIDTH / 2.0;
			fullRadius = leftRadius;
			leftMultiplier = 1.0;
			rightMultiplier = rightRadius/fullRadius;
		}
		distance = (angle / 360.0) * 2.0 * Math.PI * fullRadius;
		moveStart(distance);
	}

	public void moveStop() {
		// Disable PID controllers
		leftPID.disable();
		rightPID.disable();

		// Position move finished
		moveActive = false;
	}

	public void stop() {
		moveStop();
		drive.stopMotor();
	}

	public void update(boolean debug) {
		if (moveActive) {
			
			refGen.update();

			if (refGen.isActive()) {
				
				double refPos = refGen.getRefPosition();
				leftPID.setSetpoint(leftMultiplier * refPos + leftInitialPos);
				rightPID.setSetpoint(rightMultiplier * refPos + rightInitialPos);

			}else{
				moveStop();
			}
		}
	}
	
	
	public boolean moveGetActive() {
		return moveActive;
	}
	
	public void initAuto() {
		// Disable user watchdog (since RobotDrive will not be called)
		drive.setSafetyEnabled(false);

		// Set motor drive directions (both forward)
		Constants.lMotor.setInverted(Constants.DRIVE_MOTOR_LEFT_AUTO_DIR);
		Constants.rMotor.setInverted(Constants.DRIVE_MOTOR_RIGHT_AUTO_DIR);
	}

	public void initTele() {
		// Enable user watchdog (default when using RobotDrive)
		drive.setSafetyEnabled(true);

		// Set motor drive directions (left backward, right forward)
		Constants.lMotor.setInverted(Constants.DRIVE_MOTOR_LEFT_TELE_DIR);
		Constants.rMotor.setInverted(Constants.DRIVE_MOTOR_RIGHT_TELE_DIR);
	}
	
	
	
	
}