package ca.webber.ftc.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import ca.webber.ftc.robot.Omnibot;
import ca.webber.ftc.robot.roadrunner.SampleMecanumDriveBase;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(name = "Red Auto", group = "Autonomous")
public class TestAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Omnibot robot = new Omnibot(hardwareMap);
        SampleMecanumDriveBase drive = robot.getDrive();

        waitForStart();

        if (isStopRequested()) return;

        // Go to foundation
        drive.followTrajectorySync(drive.trajectoryBuilder(true)
                .splineToConstantHeading(new Pose2d(25, 10, 0))
                .build());

        // Attach to foundation
        robot.getFoundationMover().toggleFoundationLock();
        sleep(1000);

        // Rotate foundation
        drive.turnSync(Math.toRadians(180));

        // Detach from foundation
        robot.getFoundationMover().toggleFoundationLock();

        // Push foundation towards board
        drive.followTrajectorySync(drive.trajectoryBuilder()
                .forward(10)
                .build()
        );

        // Leave foundation
        drive.followTrajectorySync(drive.trajectoryBuilder()
                .back(10)
                .build()
        );

        // Turn towards park
        drive.turnSync(Math.toRadians(90));

        // Move into parking position
        drive.followTrajectorySync(drive.trajectoryBuilder()
                .forward(10)
                .build()
        );
    }
}
