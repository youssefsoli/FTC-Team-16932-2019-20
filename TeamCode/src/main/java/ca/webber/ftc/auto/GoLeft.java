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
@Autonomous(name = "Go Left", group = "Autonomous")
public class GoLeft extends LinearOpMode {
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
                .lineTo(new Vector2d(0, -62.5))
                .build());
    }
}
