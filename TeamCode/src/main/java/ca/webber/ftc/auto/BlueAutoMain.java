
package ca.webber.ftc.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import ca.webber.ftc.robot.Omnibot;


@Autonomous(name = "Blue Auto", group = "OmniWheel")
public class BlueAutoMain extends LinearOpMode {

    private Omnibot omnibot;

    @Override
    public void runOpMode() {
        omnibot = new Omnibot(hardwareMap, telemetry);

        waitForStart();
        omnibot.gotoBlueFoundation();
        while (opModeIsActive()) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", 2.f);
            telemetry.update();
        }
    }
}
