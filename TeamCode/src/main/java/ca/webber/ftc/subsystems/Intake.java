package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

public class Intake {

    private DcMotorEx leftArm;
    private DcMotorEx rightArm;

    public Intake (DcMotorEx leftArm, DcMotorEx rightArm){
        this.leftArm = leftArm;
        this.rightArm = rightArm;
    }

    public void moveLeft(double speed) {
        leftArm.setPower(-speed);
    }

    public void moveRight(double speed) {
        rightArm.setPower(-speed);
    }

    public void move(Gamepad gamepad) {
        double leftPower = gamepad.left_stick_x;
        double rightPower = gamepad.right_stick_x;

        if (gamepad.right_bumper || gamepad.dpad_right) {
            leftPower = 1;
            rightPower = 1;
        } else if (gamepad.left_bumper || gamepad.dpad_left) {
            leftPower = -1;
            rightPower = -1;
        }

        moveLeft(leftPower);
        moveRight(rightPower);
    }

}
