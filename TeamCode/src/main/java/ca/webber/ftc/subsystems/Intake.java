package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

public class Intake {

    private DcMotorEx leftArm;
    private DcMotorEx rightArm;

    private CRServo leftArmHinge;
    private CRServo rightArmHinge;
    private boolean lock = false;

    public Intake(DcMotorEx leftArm, DcMotorEx rightArm, CRServo leftArmHinge, CRServo rightArmHinge, boolean startUp) {
        this.leftArm = leftArm;
        this.rightArm = rightArm;
        leftArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.leftArmHinge = leftArmHinge;
        this.rightArmHinge = rightArmHinge;

        if (startUp) {
            leftArmHinge.setPower(0.8);
            rightArmHinge.setPower(-0.8);
        } else {
            leftArmHinge.setPower(-1);
            rightArmHinge.setPower(1);
        }
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

    public void toggleArms() {
        lock = !lock;
        if (lock) {
            leftArmHinge.setPower(-1);
            rightArmHinge.setPower(1);
        } else {
            leftArmHinge.setPower(0.8);
            rightArmHinge.setPower(-0.8);
        }
    }

    public void open(double speed) {
        leftArm.setPower(-speed);
        rightArm.setPower(speed);
    }

    public void stop() {
        leftArm.setPower(0);
        rightArm.setPower(0);
    }
}
