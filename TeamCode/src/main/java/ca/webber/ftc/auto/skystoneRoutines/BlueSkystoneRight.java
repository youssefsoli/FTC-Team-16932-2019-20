package ca.webber.ftc.auto.skystoneRoutines;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import ca.webber.ftc.robot.Omnibot;
import ca.webber.ftc.robot.roadrunner.SampleMecanumDriveBase;
import ca.webber.ftc.subsystems.FoundationMover;
import ca.webber.ftc.subsystems.Intake;
import ca.webber.ftc.subsystems.Lift;

public class BlueSkystoneRight extends SkystoneRoutine {

    public BlueSkystoneRight(Omnibot robot, SampleMecanumDriveBase drive, LinearOpMode opMode) {
        super(robot, drive, opMode);
    }

    public void run() {
        Intake intake = robot.getIntake();
        Lift lift = robot.getLift();
        FoundationMover foundationMover = robot.getFoundationMover();

        // Go to first block
        drive.followTrajectorySync(drive.trajectoryBuilder()
                .splineToConstantHeading(new Pose2d(-43, 25, Math.toRadians(-90)))
                .addDisplacementMarker(0, () -> intake.toggleArms())
                .build());

        // Grab brick
        intake.close();

        // Go to foundation
        drive.followTrajectorySync(drive.trajectoryBuilder(new Pose2d(drive.getPoseEstimate().getX(), drive.getPoseEstimate().getY(), Math.toRadians(50)), Math.toRadians(-90))
                .splineToSplineHeading(new Pose2d(15, 38, Math.toRadians(0)), Math.toRadians(-180))
                .splineToSplineHeading(new Pose2d(45, 37, Math.toRadians(-50)), Math.toRadians(-90))
                .addDisplacementMarker(0, () -> {
                    lift.toFoundation();
                })
                .build());

        // Drop brick
        intake.open();

        // Go to second block
        drive.followTrajectorySync(drive.trajectoryBuilder(new Pose2d(drive.getPoseEstimate().getX(), drive.getPoseEstimate().getY(), Math.toRadians(-180)), Math.toRadians(-90))
                .splineToSplineHeading(new Pose2d(20, 40, Math.toRadians(-180)), Math.toRadians(-180))
                .addDisplacementMarker(15, () -> {
                    lift.toBottom();
                    intake.close();
                })
                .splineToSplineHeading(new Pose2d(-47, 25, Math.toRadians(-140)), Math.toRadians(-180))
                .addDisplacementMarker(15, () -> {
                    intake.open();
                })
                //.splineToConstantHeading(new Pose2d(-55, -21, Math.toRadians(180)))
                .forward(8)
                .build());

        // Grab brick
        intake.close();

        // Go to foundation pt. 2
        drive.followTrajectorySync(drive.trajectoryBuilder(new Pose2d(drive.getPoseEstimate().getX(), drive.getPoseEstimate().getY(), Math.toRadians(50)), Math.toRadians(-180))
                .splineToSplineHeading(new Pose2d(20, 38, Math.toRadians(0)), Math.toRadians(-180))
                .splineToSplineHeading(new Pose2d(45, 35, Math.toRadians(-50)), Math.toRadians(-90))
                .addDisplacementMarker(0, () -> {
                    lift.toFoundation();
                })
                .build());

        // Drop brick
        intake.open();
        intake.toggleArms();
        lift.toBottom();

        // Move to pull foundation
        drive.followTrajectorySync(drive.trajectoryBuilder(new Pose2d(drive.getPoseEstimate().getX(), drive.getPoseEstimate().getY(), Math.toRadians(0)), Math.toRadians(-90))
                .splineToSplineHeading(new Pose2d(53.5, 25, Math.toRadians(-90)), Math.toRadians(90))
                .build());

        // Grab foundation
        foundationMover.toggleFoundationLock();

        // Rotate foundation
        drive.turnSync(Math.toRadians(-180));

        // Detach from foundation
        robot.getFoundationMover().toggleFoundationLock();

        // Push foundation towards board
        drive.followTrajectorySync(drive.trajectoryBuilder()
                .back(24)
                .build()
        );

        // Drop arms
        robot.getIntake().toggleArms();

        // Park under bridge turn
        drive.followTrajectorySync(drive.trajectoryBuilder(/* new Pose2d(60.00, -50.00, Math.toRadians(180)), Math.toRadians(90) */)
                .splineToSplineHeading(new Pose2d(0, 35, Math.toRadians(-180)), Math.toRadians(-180))
                .build());
    }
}