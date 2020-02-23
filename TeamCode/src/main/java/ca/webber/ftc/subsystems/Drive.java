package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
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

        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void toggleFastMode() {
        fastMode = !fastMode;
    }

    public void drive(double x, double y, double turn, double speedFactor, double orientation) {
        double power = Math.pow(Math.hypot(x, y), 2);
        double bearing = Math.atan2(y, x) + Math.toRadians(45 + orientation);
        double driveY = (Math.sin(bearing)) * power;
        double driveX = (Math.cos(bearing)) * power;

        double frontRightPower = turn + driveY;
        double frontLeftPower = turn - driveX;
        double backRightPower = turn + driveX;
        double backLeftPower = turn - driveY;

        // Normalize values
        double maxValue = Math.max(Math.abs(backLeftPower),
                Math.max(Math.abs(backRightPower),
                        Math.max(Math.abs(frontRightPower),
                                Math.abs(frontLeftPower))));

        frontRightPower /= maxValue;
        frontLeftPower /= maxValue;
        backRightPower /= maxValue;
        backLeftPower /= maxValue;

        frontRight.setPower(Range.scale(frontRightPower, -1.0, 1.0, -speedFactor, speedFactor));
        frontLeft.setPower(Range.scale(frontLeftPower, -1.0, 1.0, -speedFactor, speedFactor));
        backRight.setPower(Range.scale(backRightPower, -1.0, 1.0, -speedFactor, speedFactor));
        backLeft.setPower(Range.scale(backLeftPower, -1.0, 1.0, -speedFactor, speedFactor));
    }

    public void drive(double x, double y, double turn, double speedFactor) {
        drive(x, y, turn, speedFactor, 0);
    }

    public void forward(double speed) {
        drive(0, 1, 0, speed);
    }

    public void turn(double speed) {
        drive(0, 0, 1, speed);
    }

    public void turn(double speed, double degrees) {
    }

    public double averageTicks() {
        return Math.abs(Math.abs(frontRight.getCurrentPosition()) + Math.abs(frontLeft.getCurrentPosition()) +
                Math.abs(backRight.getCurrentPosition()) + Math.abs(backLeft.getCurrentPosition())) / 4.0;
    }

    public void stop() {
        drive(0, 0, 0, 0);
    }

    public void driveController(Gamepad gamepad, double orientation) {
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

        turn *= 0.5;

        if (fastMode)
            speedFactor = 0.5;

        if (gamepad.left_stick_button)
            speedFactor = 1;

        drive(x, y, turn, speedFactor, orientation);
    }
}
