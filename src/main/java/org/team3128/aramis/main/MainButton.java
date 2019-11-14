//AUTHORSES:
//MASON HOLST
//jude
//caylin
//daniel
//tyler
//teo

//not adham :/

package org.team3128.aramis.main;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.team3128.athos.autonomous.*;
import org.team3128.athos.util.PrebotDeepSpaceConstants;

import org.team3128.common.NarwhalRobot;
import org.team3128.common.drive.DriveCommandRunning;
import org.team3128.common.drive.SRXTankDrive;
import org.team3128.common.drive.TankDrive;
import org.team3128.common.drive.SRXTankDrive.Wheelbase;
import org.team3128.common.drive.calibrationutility.DriveCalibrationUtility;
import org.team3128.common.hardware.limelight.Limelight;
import org.team3128.common.hardware.navigation.Gyro;
import org.team3128.common.hardware.navigation.NavX;
import org.team3128.common.util.Constants;
import org.team3128.common.util.units.Angle;
import org.team3128.common.util.units.Length;
import org.team3128.common.vision.CmdHorizontalOffsetFeedbackDrive;
import org.team3128.gromit.util.DeepSpaceConstants;
import org.team3128.common.util.Log;
import org.team3128.common.util.RobotMath;
import org.team3128.common.util.datatypes.PIDConstants;
import org.team3128.common.narwhaldashboard.NarwhalDashboard;
import org.team3128.common.listener.ListenerManager;
import org.team3128.common.listener.controllers.ControllerExtreme3D;
import org.team3128.common.listener.controltypes.Button;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//start typing the stuff to make this a robot that isn't non-functional and bad and blank and boring and stuff thanks lol
    // - Mason Holst, "Helpful Reminders", published November 2019

public class MainButton extends NarwhalRobot {
    public TalonSRX t;
    public ListenerManager lm;
    public Joystick j;
    private DriveCommandRunning dcm;
    private TankDrive td;

    public SRXTankDrive tankDrive;
    public TalonSRX rightDriveLeader;
    public VictorSPX rightDriveFollower;
    public TalonSRX leftDriveLeader;
    public VictorSPX leftDriveFollower;

	@Override
	protected void constructHardware()
	{
        j = new Joystick(2);
        lm = new ListenerManager(j);
        addListenerManager(lm);
       // t = new TalonSRX(11);

        rightDriveLeader = new TalonSRX(15);
        rightDriveFollower = new VictorSPX(6);

        leftDriveLeader = new TalonSRX(13);
        leftDriveFollower = new VictorSPX(5);

        rightDriveLeader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.CAN_TIMEOUT);
        rightDriveFollower.set(ControlMode.Follower, rightDriveLeader.getDeviceID());

        leftDriveLeader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.CAN_TIMEOUT);
        leftDriveFollower.set(ControlMode.Follower, leftDriveLeader.getDeviceID());

        double wheelCirc = 13.21 * Length.in;
        //double wheelBase = 31.5 * Length.in; 5 feet
        //double wheelBase = 32.25 * Length.in; 4 feet
        double wheelBase = 32.3 * Length.in;
        int robotFreeSpeed = 3700;

        SRXTankDrive.initialize(leftDriveLeader, rightDriveLeader, wheelCirc, wheelBase, robotFreeSpeed);

        leftDriveLeader.setInverted(false);
        leftDriveFollower.setInverted(false);

        rightDriveLeader.setInverted(true);
        rightDriveFollower.setInverted(true);

        leftDriveLeader.setSensorPhase(true);
        rightDriveLeader.setSensorPhase(true);

        tankDrive = SRXTankDrive.getInstance();
        tankDrive.setLeftSpeedScalar(1.0);
        tankDrive.setRightSpeedScalar(.983);
        
        
    }
    
    @Override
    protected void constructAutoPrograms() {
        
    }

	@Override
    protected void setupListeners() {
        
        lm.nameControl(ControllerExtreme3D.TWIST, "MoveTurn");
		lm.nameControl(ControllerExtreme3D.JOYY, "MoveForwards");
		lm.nameControl(ControllerExtreme3D.THROTTLE, "Throttle");		

        lm.addMultiListener(()-> {
            td.arcadeDrive(
                -0.7 * RobotMath.thresh(lm.getAxis("MoveTurn"), 0.1),
                -1.0 * RobotMath.thresh(lm.getAxis("MoveForwards"), 0.1),
                -1.0 * lm.getAxis("Throttle"),
                 true
            );
        		
          //  td.arcadeDrive(joyX, joyY, throttle, fullSpeed);
        
    }, "MoveTurn", "MoveForward", "Throttle");
    
        lm.nameControl(new Button(11), "moveFoward");
        lm.addButtonDownListener("moveForward", ()->
        {
            t.set(ControlMode.PercentOutput, 100);
        });
        lm.addButtonUpListener("moveForward", ()->
        {
            t.set(ControlMode.PercentOutput, 0);
        });
        lm.nameControl(new Button(12), "moveBackward");
        lm.addButtonDownListener("moveBackward", ()->
        {
            t.set(ControlMode.PercentOutput, -100);
        });
        lm.addButtonUpListener("moveBackward", ()->
        {
            t.set(ControlMode.PercentOutput,0);
        });
    }


    @Override
    protected void teleopPeriodic() {
    }

    @Override
    protected void updateDashboard() {

    }


    public static void main(String... args) {
        RobotBase.startRobot(MainButton::new);
    }
}