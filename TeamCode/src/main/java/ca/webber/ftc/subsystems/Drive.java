package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

public final class Drive {
    private DcMotorEx frontRight = null;
    private DcMotorEx frontLeft = null;
    private DcMotorEx backRight = null;
    private DcMotorEx backLeft = null;
    private boolean fastMode = true;

    public Drive(DcMotorEx frontRight, DcMotorEx frontLeft, DcMotorEx backRight, DcMotorEx backLeft) {
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
        this.backRight = backRight;
        this.backLeft = backLeft;
    }

    public void toggleFastMode() {
        fastMode = !fastMode;
    }

    public boolean isFastMode() {
        return fastMode;
    }

    public void drive(Gamepad gamepad) {
        double x = gamepad.left_stick_x;
        double y = gamepad.left_stick_y;

        if (gamepad.dpad_up)
            y = 1;
        else if (gamepad.dpad_right)
            x = 1;
        else if (gamepad.dpad_down)
            y = -1;
        else if (gamepad.dpad_left)
            x = -1;

        double turn = -gamepad.right_stick_x;

        double power = Math.pow(Math.hypot(x, y), 2);
        double bearing = Math.atan2(y, x) + Math.toRadians(45);
        double driveY = (Math.sin(bearing)) * power;
        double driveX = (Math.cos(bearing)) * power;

        double frPower;
        double flPower;
        double brPower;
        double blPower;

        turn *= 0.5;

        if (fastMode) {
            frPower = Range.scale(turn + driveY, -1.0, 1.0, -1.0, 1.0);
            flPower = Range.scale(turn - driveX, -1.0, 1.0, -1.0, 1.0);
            brPower = Range.scale(turn + driveX, -1.0, 1.0, -1.0, 1.0);
            blPower = Range.scale(turn - driveY, -1.0, 1.0, -1.0, 1.0);
        } else {
            frPower = Range.scale(turn + driveY, -1.0, 1.0, -0.5, 0.5);
            flPower = Range.scale(turn - driveX, -1.0, 1.0, -0.5, 0.5);
            brPower = Range.scale(turn + driveX, -1.0, 1.0, -0.5, 0.5);
            blPower = Range.scale(turn - driveY, -1.0, 1.0, -0.5, 0.5);
        }

        frontRight.setPower(frPower);
        frontLeft.setPower(flPower);
        backRight.setPower(brPower);
        backLeft.setPower(blPower);
    }
}
