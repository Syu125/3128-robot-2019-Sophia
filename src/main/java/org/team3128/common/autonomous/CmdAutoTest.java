package org.team3128.common.autonomous;

import org.team3128.common.drive.SRXTankDrive;
import org.team3128.common.util.enums.Direction;
import org.team3128.common.util.units.Length;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CmdAutoTest extends CommandGroup{
    public CmdAutoTest(){
        SRXTankDrive drive =  SRXTankDrive.getInstance();
        //addSequential(drive.new CmdDriveStraight(100 * Length.in, 0.5, 10000));
        //addSequential(drive.new CmdInPlaceTurn(90, Direction.LEFT, 0.5, 10000));

        for (int i = 0; i < 4; i++) {
            addSequential(drive.new CmdDriveStraight(50*Length.in, 0.5, 2000));
            addSequential(drive.new CmdInPlaceTurn(90, Direction.LEFT, 0.5, 1000));
        }
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