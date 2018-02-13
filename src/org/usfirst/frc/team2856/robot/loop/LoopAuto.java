package org.usfirst.frc.team2856.robot.loop;

import org.usfirst.frc.team2856.robot.Constants;
import org.usfirst.frc.team2856.robot.Robot;
import org.usfirst.frc.team2856.robot.drivetrain.DriveTrain;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LoopAuto extends Loop{	

	static String[] modes = {"l", "m", "r", "s"};
	
	//IterativeRobot robot;
	private String autoSelected;
	private Integer state;
	private DriveTrain drive;
	// private double startPos;
	private SendableChooser<String> chooser;
	private double startTime;
	
	//Names of our options for Autonomous
	private final String 
		s_defaultAuto = "Default Auto",
		s_customAuto = "My Auto",
		s_sideSwitchCommands = "sideSwitchCommands",
		s_adjust = "adjust",
		s_directSwitchCommands = "directSwitchCommands",
		s_scaleCommands = "scaleCommands",
		s_depositAtSwitch = "depositAtSwitch";
	
	public LoopAuto(Robot rob){
		//First instantiating through the parent class
		super(rob);

		// Then adding options for Autonomous mode
		chooser = new SendableChooser<String>();
		chooser.addDefault(s_defaultAuto, s_defaultAuto);
		chooser.addObject(s_customAuto, s_customAuto);
		chooser.addObject(s_sideSwitchCommands, s_sideSwitchCommands);
		chooser.addObject(s_adjust, s_adjust);
		chooser.addObject(s_directSwitchCommands, s_directSwitchCommands);
		chooser.addObject(s_scaleCommands, s_scaleCommands);
		chooser.addObject(s_directSwitchCommands, s_directSwitchCommands);
		chooser.addObject(s_depositAtSwitch, s_depositAtSwitch);
		SmartDashboard.putData("Auto modes", chooser);

		/*
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(0) == 'L')
		{
			//Put left auto code here
			System.out.println("Left");
		} else {
			//Put right auto code here
			System.out.println("Right");
		}
		*/
	}
	public void init() {
		autoSelected = SmartDashboard.getString("Auto Selector", "None");
		System.out.println("Auto selected: " + autoSelected);
		state = 0;

		// startPos = Double.parseDouble(SmartDashboard.getString("Starting
		// Position", "0"));
		
		drive = robot.driveTrain;
		drive.initAuto();

		// Gyro for tracking direction of the robot
//		robot.gyro.reset();
//		robot.gyro.calibrate();
	}

	public void loop() {
		this.switchAuto("Test");
		drive.update(false);
	}

	public static void addModes() {
		for (int i = 0; i < modes.length; i++) {
			SmartDashboard.putString("Auto Selector", modes[i]);
		}
	}
	
	//Controls the switching of the functions in Auto
	//E.g. putting power boxes on the switch
	public void switchAuto(String mode) {
		switch (mode) {
			case "Test":
				this.testingAuto(0, false);
				break;
			case "Switch":
				this.depositAtSwitch(0, false);
				break;
			case "Scale":
				this.depositAtScale(0, false);
				break;
			case "Forward":
				// this.
				break;
			default:
				break;
		}
	}

	// in memoriam: the stateMachine(). 2017-2018. lay here until the day of
	// it's death.
	// a fickle and confusing creature, but nonetheless a good friend until the
	// very end

	public void adjust() {
		// Adjust the robot back on track
	}

	public void testingAuto(double start, boolean side){
		if (state == 0) {
			if (!robot.driveTrain.moveGetActive()) {
				System.out.println(state);
				System.out.println("driving forward");
				//previous parameter value: 5
				robot.driveTrain.moveStraight(2);
				state++;
			}
			return;
		}
		if (state == 1) {
			if (!robot.driveTrain.moveGetActive()) {
				System.out.println(state);
				System.out.println(state);
				//previous first parameter value: 90*1.25
				robot.driveTrain.moveTurn(90, 0);
				state++;
			}
			return;
		}
	}

	public void depositAtSwitch(double start, boolean side) { // left = true,
																// right = false
		if (state == 0) {
			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveStraight(5); // clear any obstacles
				state++;
			}
			return;
		}
		// align bot with switch
		if (side) { 
			// do we have the left switch . . .
			if (start > -4.5) { 
				// if we start to the right of the switch

				if (state == 1) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(-90, 0);
						state++;
					}
					return;
				}
				if (state == 2) {
					if (!robot.driveTrain.moveGetActive()) {

						robot.driveTrain.moveStraight(start + 9.5);
						state++;
					}
					return;
				}
				if (state == 3) {
					if (!robot.driveTrain.moveGetActive()) {

						robot.driveTrain.moveTurn(90, 0);
						state++;
					}
					return;
				}

			} else if (start < -9.5) { // if we start to the left of the switch
				if (state == 1) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(90, 0);
						state++;
					}
					return;
				}
				if (state == 2) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveStraight(-start - 9.5);
						state++;
					}
					return;
				}
				if (state == 3) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(-90, 0);
						state++;
					}
					return;
				}
			} // do nothing if we start directly adjacent to the switch

		} else { // . . . or the right switch
			if (start > 4.5) { // if we start to the right of the switch
				if (state == 1) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(-90, 0);
						state++;
					}
					return;
				}
				if (state == 2) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveStraight(start - 9.5);
						state++;
					}
					return;
				}
				if (state == 3) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(90, 0);
						state++;
					}
					return;
				}
				// if we start to the left of the switch
			} else if (start < 9.5) {
				if (state == 1) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(90, 1);
						state++;
					}
					return;
				}
				if (state == 2) {
					if (!robot.driveTrain.moveGetActive()) {
						System.out.println("moved to ");
						robot.driveTrain.moveStraight(-start + 9.5);
						state++;
					}
					return;
				}
				if (state == 3) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(-90, 0);
						state++;
					}
					return;
				}

			}
			// do nothing if we start directly adjacent to the switch

		}

		if (state == 4) {
			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveStraight(6.66 - Constants.DRIVE_BASE_LENGTH);
				state++;
			}
			return;
		}

		// deposit the cube
		if (state == 5) {
			startTime = System.currentTimeMillis();
			// update on time required
			robot.lift.liftUp(1);
			state++;
			return;
		}
		if (state == 6) {
			if (System.currentTimeMillis() - startTime > 3000) {
				robot.lift.liftStop();
				state++;
			}
			return;
		}
		if (state == 7) {
			robot.manipulator.pullOut(1);
			state++;
			return;
		}

	}

	public void depositAtScale(double start, boolean side) { // left = true,
																// right = false
		robot.driveTrain.moveStraight(5); // clear any obstacles
		// Align robot with the scale
		if (side) { // do we have the left scale . . .
			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveTurn(-90, 0);
				state++;
			}

			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveStraight(start + 11);
				state++;
			}

			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveTurn(90, 0);
				state++;
			}

		} else { // . . . or the right scale
			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveTurn(90, 0);
				state++;
			}
			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveStraight(-start - 11);
				state++;
			}
			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveTurn(-90, 0);
				state++;
			}

		}
		
		// Move to the center of the arena
		if (!robot.driveTrain.moveGetActive()) {
			robot.driveTrain.moveStraight(22 - Constants.DRIVE_BASE_LENGTH);
			state++;
		}

		// turn to face the scale
		if (side) {

			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveTurn(90, 0);
				state++;
			}
		} else {

			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveTurn(-90, 0);
				state++;
			}
		}

		if (!robot.driveTrain.moveGetActive()) {
			robot.driveTrain.moveStraight(5 - Constants.DRIVE_BASE_LENGTH);
			state++;
		}
		/*
		 * /deposit the cube /long startTime = System.currentTimeMillis();
		 * while(System.currentTimeMillis()-startTime< 3000){ //update on time
		 * required robot.lift.liftUp(1); } robot.lift.liftStop();
		 * robot.manipulator.pullOut(1);
		 */
	}

	public void Crossline(double start) {
		if (start > 9.5 || start < -9.5) {
			if (state == 0) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(13);
					state++;
				}
				return;
			}
		}
		if (start >= 0 && start <= 4.5) { //----------
			if (state == 0) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(1);
					state++;
				}
				return;
			}
			if (state == 1) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveTurn(-90, 0);
					state++;
				}
				return;
			}
			if (state == 2) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(5);
					state++;
				}
				return;
			}
			if (state == 3) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveTurn(90, 0);
					state++;
				}
				return;
			}
			if (state == 4) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(12);
					state++;
				}
				return;
			}
			if (start < 0 && start >= -4.5) { //-------
				if (state == 0) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveStraight(1);
						state++;
					}
					return;
				}
				if (state == 1) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(90, 0);
						state++;
					}
					return;
				}
				if (state == 2) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveStraight(5);
						state++;
					}
					return;
				}
				if (state == 3) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(-90, 0);
						state++;
					}
					return;
				}
				if (state == 4) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveStraight(12);
						state++;
					}
					return;
				}
			}
		}

	}

}