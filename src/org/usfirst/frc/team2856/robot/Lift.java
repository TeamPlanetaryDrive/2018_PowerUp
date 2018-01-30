
package org.usfirst.frc.team2856.robot;

import edu.wpi.first.wpilibj.*;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Lift {
	
	public Lift(){

		if(Constants.button4_right.get()) {				//should make it so that when button 4 (right) is pressed, lift goes up -S
			 LiftDown(.5);
		}
		else {
			if(!Constants.button4_right.get() && !Constants.button5_right.get()) {				//should make it so if neither buttons are pressed, lift stays still -S
				LiftStop();
			}
			if(Constants.button5_right.get()) {					//should make it so that when button 5 (right) is pressed, lift goes down -S
				LiftUp(-.5);
			}
		}
		
	}		
	
	public void LiftUp (double speed) {
		 Constants.lift.setSpeed(speed);
		} 
		
	public void LiftDown (double speed) {
		Constants.lift.setSpeed(speed);
	}
	public void LiftStop () {
		Constants.lift.stopMotor();
	}
	
	public void updateTele() {
		
	}
/* VV Old Code VV
	//PMVs
	private SpeedController motor;
	private double climbSpeed = 0.3424243244;	//climbSpeed & intakeSpeed are random values
	
	//booleans for button pressing
	boolean running;
	
	
	//constructor
	public Climber(){
		motor = Constants.climber; //not actual channel
		running = true;
	}
	
	//move
	public void updateTele(){
		if( Constants.leftJoystick.getRawButton(6)){
			motor.set(1);
		}
	}
	
	//accessor
	public double getClimbSpeed(){return climbSpeed;}
	
	//mutator
	public void setClimbSpeed(double num){climbSpeed=num;}
*/
}