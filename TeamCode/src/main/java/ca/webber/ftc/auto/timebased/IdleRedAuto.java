
package ca.webber.ftc.auto.timebased;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import ca.webber.ftc.robot.Omnibot;

@Disabled
@Autonomous(name = "Idle Red Auto", group = "Old")
public class IdleRedAuto extends LinearOpMode {

    private Omnibot omnibot;

    @Override
    public void runOpMode() {
        omnibot = new Omnibot(hardwareMap, telemetry, this);

        waitForStart();

        if (opModeIsActive())
            omnibot.idleRed();
    }
}