package ca.webber.ftc.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import ca.webber.ftc.auto.skystoneRoutines.RedSkystoneLeft;
import ca.webber.ftc.auto.skystoneRoutines.RedSkystoneMiddle;
import ca.webber.ftc.auto.skystoneRoutines.RedSkystoneRight;
import ca.webber.ftc.auto.skystoneRoutines.SkystoneRoutine;
import ca.webber.ftc.robot.Omnibot;
import ca.webber.ftc.robot.roadrunner.SampleMecanumDriveBase;
import ca.webber.ftc.subsystems.Vision;

@Autonomous(name = "Red Skystone Auto", group = "Autonomous")
public class RedSkystoneAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Omnibot robot = new Omnibot(hardwareMap);
        SampleMecanumDriveBase drive = robot.getDrive();
        Vision vision = new Vision(hardwareMap);

        // Set initial starting point
        drive.setPoseEstimate(new Pose2d(-32.5, -62.5, Math.toRadians(90)));

        // Open up intake to max
        robot.getIntake().gotoMax();

        waitForStart();
        if (isStopRequested()) return;

        int skystonePosition = vision.getSkystonePosition();
        vision.stop();
        telemetry.addData("Position", skystonePosition);
        telemetry.update();

        SkystoneRoutine routine = null;

        switch (skystonePosition) {
            case 0:
                routine = new RedSkystoneLeft(robot, drive, this);
                break;
            case 1:
                routine = new RedSkystoneMiddle(robot, drive, this);
                break;
            case 2:
                routine = new RedSkystoneRight(robot, drive, this);
                break;
            default:
                telemetry.addData("Failed", true);
                telemetry.update();
        }

        routine.run();
    }
}
