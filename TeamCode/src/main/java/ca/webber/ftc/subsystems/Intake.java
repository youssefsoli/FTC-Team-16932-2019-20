package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Intake {

    private DcMotorEx leftArm;
    private DcMotorEx rightArm;

    public Intake (DcMotorEx leftArm, DcMotorEx rightArm){
        this.leftArm = leftArm;
        this.rightArm = rightArm;
    }

    public void shiftRight() {
        leftArm.setPower(-1);
        rightArm.setPower(-0.97);
    }

    public void shiftLeft() {
        rightArm.setPower(1);
        leftArm.setPower(0.97);
    }

    public void moveLeft(double speed) {
        leftArm.setPower(-speed);
    }

    public void moveRight(double speed) {
        rightArm.setPower(-speed);
    }


}
