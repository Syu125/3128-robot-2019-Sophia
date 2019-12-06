package org.team3128.common.autonomous;

import org.team3128.common.drive.SRXTankDrive;
import org.team3128.common.util.enums.Direction;
import org.team3128.common.util.units.Length;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class MazeAuto extends CommandGroup{
    public MazeAuto(){
    
        SRXTankDrive drive =  SRXTankDrive.getInstance();
        //addSequential(drive.new CmdDriveStraight(100 * Length.in, 0.5, 10000));
        //addSequential(drive.new CmdInPlaceTurn(90, Direction.LEFT, 0.5, 10000));


            addSequential(drive.new CmdDriveStraight(125*Length.in, 0.5, 3000));
            addSequential(drive.new CmdInPlaceTurn(90, Direction.LEFT, 0.5, 3000));
            addSequential(drive.new CmdDriveStraight(135*Length.in, 0.5, 3000));
            addSequential(drive.new CmdInPlaceTurn(90, Direction.RIGHT, 0.5, 3000));
            addSequential(drive.new CmdDriveStraight(136*Length.in, 0.5, 3000));
            addSequential(drive.new CmdInPlaceTurn(90, Direction.LEFT, 0.5, 3000));
            addSequential(drive.new CmdDriveStraight(130*Length.in, 0.5, 3000));
            addSequential(drive.new CmdInPlaceTurn(90, Direction.LEFT, 0.5, 3000));
            addSequential(drive.new CmdArcTurn(52*Length.in, 180, Direction.RIGHT, 0.5, 3000));
            addSequential(drive.new CmdInPlaceTurn(720, Direction.LEFT, 0.5, 1000));
    }
    //public static void main(String... args) {
        //CmdAutoTest cmd = new CmdAutoTest();
        //RobotBase.startRobot(CmdAutoTest::new);
    //}
}/*
1. 125 in
2. LEFT
3.135 in
4. right
5. 136 in
6. LEFT
7. 130 in
8. left
9 right 1/4 circ
10.spin
*/