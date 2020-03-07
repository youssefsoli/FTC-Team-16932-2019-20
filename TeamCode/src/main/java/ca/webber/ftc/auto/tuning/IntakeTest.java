package ca.webber.ftc.auto.tuning;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import ca.webber.ftc.robot.Omnibot;
import ca.webber.ftc.robot.roadrunner.SampleMecanumDriveBase;
import ca.webber.ftc.subsystems.Intake;
import ca.webber.ftc.subsystems.Lift;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(name = "Lift Test", group = "Autonomous")
public class IntakeTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Omnibot robot = new Omnibot(hardwareMap);
        SampleMecanumDriveBase drive = robot.getDrive();

        Intake intake = robot.getIntake();
        Lift lift = robot.getLift();

        telemetry.addData("Encoders", intake.getEncoderValues());
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        intake.close();
        sleep(3000);
        telemetry.addData("Encoders", intake.getEncoderValues());
        telemetry.update();
        intake.open();
        sleep(3000);
        telemetry.addData("Encoders", intake.getEncoderValues());
        telemetry.update();
    }
}
