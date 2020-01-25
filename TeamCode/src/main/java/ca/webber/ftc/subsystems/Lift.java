package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Lift {
    private DcMotorEx liftMotorL;
    private DcMotorEx liftMotorR;

    public Lift(DcMotorEx liftMotorL, DcMotorEx liftMotorR) {
        this.liftMotorL = liftMotorL;
        this.liftMotorR = liftMotorR;
    }

    public void moveUp1Level() {
        /* Use limit switches at each level */
    }

    public void moveDown1Level() {
        /* Use limit switches at each level */
    }

    public void move(double speed) {
        liftMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotorL.setPower(speed);
        liftMotorR.setPower(speed);
    }

}
