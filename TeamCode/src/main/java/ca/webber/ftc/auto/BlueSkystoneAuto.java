package ca.webber.ftc.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import ca.webber.ftc.auto.skystoneRoutines.BlueSkystoneLeft;
import ca.webber.ftc.auto.skystoneRoutines.BlueSkystoneMiddle;
import ca.webber.ftc.auto.skystoneRoutines.BlueSkystoneRight;
import ca.webber.ftc.auto.skystoneRoutines.SkystoneRoutine;
import ca.webber.ftc.robot.Omnibot;
import ca.webber.ftc.robot.roadrunner.SampleMecanumDriveBase;
import ca.webber.ftc.subsystems.Vision;

@Autonomous(name = "Blue Skystone Auto", group = "Autonomous")
public class BlueSkystoneAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Omnibot robot = new Omnibot(hardwareMap);
        SampleMecanumDriveBase drive = robot.getDrive();
        Vision vision = new Vision(hardwareMap);

        // Set initial starting point
        drive.setPoseEstimate(new Pose2d(-32.5, 62.5, Math.toRadians(-90)));

        telemetry.addData("Vision ready!", vision.getSkystonePosition());
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        int skystonePosition = vision.getSkystonePosition();
        vision.stop();
        telemetry.addData("Position", skystonePosition);
        telemetry.update();

        SkystoneRoutine routine = null;

        switch (skystonePosition) {
            case 0:
                routine = new BlueSkystoneRight(robot, drive, this);
                break;
            case 1:
                routine = new BlueSkystoneMiddle(robot, drive, this);
                break;
            case 2:
                routine = new BlueSkystoneLeft(robot, drive, this);
                break;
            default:
                telemetry.addData("Failed", true);
                telemetry.update();
        }

        if (routine != null)
            routine.run();
    }
}
