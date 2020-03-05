package ca.webber.ftc.auto.skystoneRoutines;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import ca.webber.ftc.robot.Omnibot;
import ca.webber.ftc.robot.roadrunner.SampleMecanumDriveBase;
import ca.webber.ftc.subsystems.Intake;
import ca.webber.ftc.subsystems.Lift;

public class RedSkystoneLeft extends SkystoneRoutine {

    public RedSkystoneLeft(Omnibot robot, SampleMecanumDriveBase drive, LinearOpMode opMode) {
        super(robot, drive, opMode);
    }

    public void run() {
        Intake intake = robot.getIntake();
        Lift lift = robot.getLift();

        intake.toggleArms();

        // Go to first block
//        drive.followTrajectorySync(drive.trajectoryBuilder()
//                .lineTo(new Vector2d(-43, -30))
//                .build());

        // Go to first block
        drive.followTrajectorySync(drive.trajectoryBuilder()
                .splineToConstantHeading(new Pose2d(-43, -25, Math.toRadians(90)))
                .build());

        //intake.close();

        // Go to foundation
        drive.followTrajectorySync(drive.trajectoryBuilder(new Pose2d(drive.getPoseEstimate().getX(), drive.getPoseEstimate().getY(), Math.toRadians(-20)), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(20, -38, Math.toRadians(0)), Math.toRadians(180))
                .splineToSplineHeading(new Pose2d(45, -37, Math.toRadians(20)), Math.toRadians(90))
                .addDisplacementMarker(5, () -> {
                    //lift.toFoundation();
                })
                .build());

        // Drop brick
        //intake.open();
    }
}
