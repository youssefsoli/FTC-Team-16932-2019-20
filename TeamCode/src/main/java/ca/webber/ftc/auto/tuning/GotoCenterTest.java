package ca.webber.ftc.auto.tuning;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import ca.webber.ftc.robot.Omnibot;
import ca.webber.ftc.robot.roadrunner.SampleMecanumDriveBase;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Config
@Autonomous(group = "drive")
public class GotoCenterTest extends LinearOpMode {
    public static double startX = -32.5;
    public static double startY = -62.5;
    public static double startHeading = 90;

    @Override
    public void runOpMode() throws InterruptedException {
        Omnibot robot = new Omnibot(hardwareMap);
        SampleMecanumDriveBase drive = robot.getDrive();

        // Set intial position
        drive.setPoseEstimate(new Pose2d(startX, startY, Math.toRadians(startHeading)));


        waitForStart();

        if (isStopRequested()) return;

        // Go to center
        drive.followTrajectorySync(drive.trajectoryBuilder()
                .lineTo(new Vector2d(0, 0))
                .build());
    }
}
