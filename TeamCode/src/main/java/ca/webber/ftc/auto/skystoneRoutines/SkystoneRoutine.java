package ca.webber.ftc.auto.skystoneRoutines;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import ca.webber.ftc.robot.Omnibot;
import ca.webber.ftc.robot.roadrunner.SampleMecanumDriveBase;

public abstract class SkystoneRoutine {
    protected Omnibot robot;
    protected SampleMecanumDriveBase drive;
    protected LinearOpMode opMode;

    public SkystoneRoutine(Omnibot robot, SampleMecanumDriveBase drive, LinearOpMode opMode) {
        this.robot = robot;
        this.drive = drive;
        this.opMode = opMode;
    }

    public abstract void run();
}
