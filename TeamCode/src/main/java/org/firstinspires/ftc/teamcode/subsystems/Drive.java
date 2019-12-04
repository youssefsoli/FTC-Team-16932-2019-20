package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.lang.Math;
import org.firstinspires.ftc.teamcode.Teleop;

public class Drive
{

    DriveTrainMotorPower driveTrainMotorPower = new DriveTrainMotorPower();
    double turn, power, bearing, driveX, driveY;
    double robotOrientation = 45;

    public void calculateMotorPower(boolean fastMode, double leftStickX, double rightStickX, double rightStickY) {

        turn = -leftStickX;
        power = Math.hypot(rightStickX, rightStickY);
        bearing = Math.toDegrees( Math.atan2(rightStickY, rightStickX) )+ robotOrientation;
        //unit circle layout, this var is in radians

        driveY = (Math.sin(bearing)) * power;
        driveX = (Math.cos(bearing)) * power;
        //breaks vector into x-y components

        if (fastMode) {
            driveTrainMotorPower.setPower( Range.clip(turn + driveX, -1.0, 1.0),
                    Range.clip(turn - driveX, -1.0, 1.0),
                    Range.clip(turn + driveY, -1.0, 1.0),
                    Range.clip(turn - driveY, -1.0, 1.0));
        } else {
            driveTrainMotorPower.setPower( Range.clip(turn + driveX, -1.0, 1.0),
                    Range.clip(turn - driveX, -0.3, 0.3),
                    Range.clip(turn + driveY, -0.3, 0.3),
                    Range.clip(turn - driveY, -0.3, 0.3));
        }
    }
}
