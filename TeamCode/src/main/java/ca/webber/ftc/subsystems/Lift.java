package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Lift {
    private DcMotorEx liftMotorL;
    private DcMotorEx liftMotorR;

    public Lift(DcMotorEx liftMotorL, DcMotorEx liftMotorR) {
        this.liftMotorL = liftMotorL;
        this.liftMotorR = liftMotorR;
    }

    public void move(double speed) {
        liftMotorL.setPower(speed);
        liftMotorR.setPower(speed);
    }

}
