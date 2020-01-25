package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;

public final class Drive {
    private DcMotorEx frontRight = null;
    private DcMotorEx frontLeft = null;
    private DcMotorEx backRight = null;
    private DcMotorEx backLeft = null;
    private boolean fastMode = true;
    private int direction = 1;

    public Drive(DcMotorEx frontRight, DcMotorEx frontLeft, DcMotorEx backRight, DcMotorEx backLeft) {
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
        this.backRight = backRight;
        this.backLeft = backLeft;
    }

    public void toggleFastMode() {
        fastMode = !fastMode;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void drive(double x, double y, double turn) {
        double power = Math.hypot(x, y);
        double bearing = Math.atan2(y, x) + Math.toRadians(45);
        double driveY = (Math.sin(bearing)) * power * direction;
        double driveX = (Math.cos(bearing)) * power * direction;

        double frPower;
        double flPower;
        double brPower;
        double blPower;

        if (fastMode) {
            frPower = Range.clip(Math.pow(turn + driveY, 3), -1.0, 1.0);
            flPower = Range.clip(Math.pow(turn - driveX, 3), -1.0, 1.0);
            brPower = Range.clip(Math.pow(turn + driveX, 3), -1.0, 1.0);
            blPower = Range.clip(Math.pow(turn - driveY, 3), -1.0, 1.0);
        } else {
            frPower = Range.clip(Math.pow(turn + driveY, 3), -0.5, 0.5);
            flPower = Range.clip(Math.pow(turn - driveX, 3), -0.5, 0.5);
            brPower = Range.clip(Math.pow(turn + driveX, 3), -0.5, 0.5);
            blPower = Range.clip(Math.pow(turn - driveY, 3), -0.5, 0.5);
        }
        frontRight.setPower(frPower);
        frontLeft.setPower(flPower);
        backRight.setPower(brPower);
        backLeft.setPower(blPower);
    }
}
