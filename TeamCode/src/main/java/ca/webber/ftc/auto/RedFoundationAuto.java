package ca.webber.ftc.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import ca.webber.ftc.robot.Omnibot;
import ca.webber.ftc.robot.roadrunner.SampleMecanumDriveBase;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(name = "Red Foundation Auto", group = "Autonomous")
public class RedFoundationAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Omnibot robot = new Omnibot(hardwareMap);
        SampleMecanumDriveBase drive = robot.getDrive();

        // Set intial position
        drive.setPoseEstimate(new Pose2d(40, -62.5, Math.toRadians(-90)));


        waitForStart();

        if (isStopRequested()) return;

        // Go to foundation
        drive.followTrajectorySync(drive.trajectoryBuilder()
                .lineTo(new Vector2d(52, -30))
                .build());

//        // Go to test absolute position
//        drive.followTrajectorySync(drive.trajectoryBuilder()
//                .splineToConstantHeading(new Pose2d(-28, 15))
//                .build());

        // Attach to foundation
        robot.getFoundationMover().toggleFoundationLock();
        sleep(1000);

        // Rotate foundation
        drive.turnSync(Math.toRadians(180));

        // Detach from foundation
        robot.getFoundationMover().toggleFoundationLock();

        // Push foundation towards board
        drive.followTrajectorySync(drive.trajectoryBuilder()
                .back(20)
                .build()
        );

//        // Leave foundation
//        drive.followTrajectorySync(drive.trajectoryBuilder()
//                .forward(20)
//                .build()
//        );
//
//        // Turn towards park
//        drive.turnSync(Math.toRadians(90));
//
//        // Move into parking position
//        drive.followTrajectorySync(drive.trajectoryBuilder()
//                .forward(25)
//                .build()
//        );

        // Park under bridge constant
//        drive.followTrajectorySync(drive.trajectoryBuilder(/* new Pose2d(60.00, -45.00, Math.toRadians(160)), Math.toRadians(90) */)
//                .splineToConstantHeading(new Pose2d(10, -40.00, Math.toRadians(180)))
//                .build());

        // Drop arms
        robot.getIntake().toggleArms();

        // Park under bridge turn
        drive.followTrajectorySync(drive.trajectoryBuilder(/* new Pose2d(60.00, -50.00, Math.toRadians(180)), Math.toRadians(90) */)
                .splineToSplineHeading(new Pose2d(0, -35, Math.toRadians(180)), Math.toRadians(180))
                .build());
    }
}
