package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

public final class Drive {
    private DcMotorEx frontRight;
    private DcMotorEx frontLeft;
    private DcMotorEx backRight;
    private DcMotorEx backLeft;
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

    public void drive(Gamepad gamepad) {
        double x = gamepad.left_stick_x;
        double y = gamepad.left_stick_y;

        if (gamepad.dpad_up)
            y = -1;
        else if (gamepad.dpad_right)
            x = 1;
        else if (gamepad.dpad_down)
            y = 1;
        else if (gamepad.dpad_left)
            x = -1;

        double turn = gamepad.right_stick_x;
        double speedFactor = 0.3;

        double power = Math.pow(Math.hypot(x, y), 2);
        double bearing = Math.atan2(y, x) + Math.toRadians(45);
        double driveY = (Math.sin(bearing)) * power;
        double driveX = (Math.cos(bearing)) * power;

        double frPower;
        double flPower;
        double brPower;
        double blPower;

        turn *= 0.5;

        if (fastMode)
            speedFactor = 0.5;

        if (gamepad.left_stick_button)
            speedFactor = 1;

        frPower = Range.scale(turn + driveY, -1.0, 1.0, -speedFactor, speedFactor);
        flPower = Range.scale(turn - driveX, -1.0, 1.0, -speedFactor, speedFactor);
        brPower = Range.scale(turn + driveX, -1.0, 1.0, -speedFactor, speedFactor);
        blPower = Range.scale(turn - driveY, -1.0, 1.0, -speedFactor, speedFactor);

        frontRight.setPower(frPower);
        frontLeft.setPower(flPower);
        backRight.setPower(brPower);
        backLeft.setPower(blPower);
    }
}
