package ca.webber.ftc.auto.tuning;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import ca.webber.ftc.robot.Omnibot;
import ca.webber.ftc.robot.roadrunner.SampleMecanumDriveBase;
import ca.webber.ftc.subsystems.Lift;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(name = "Intake Test", group = "Autonomous")
public class LiftTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Omnibot robot = new Omnibot(hardwareMap);
        SampleMecanumDriveBase drive = robot.getDrive();

        Lift lift = robot.getLift();

        telemetry.addData("Encoders", lift.getEncoderValues());
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            lift.toFoundation();
            sleep(3000);
            telemetry.addData("Encoders", lift.getEncoderValues());
            telemetry.update();
            lift.toBottom();
            sleep(3000);
            telemetry.addData("Encoders", lift.getEncoderValues());
            telemetry.update();
        }
    }
}
