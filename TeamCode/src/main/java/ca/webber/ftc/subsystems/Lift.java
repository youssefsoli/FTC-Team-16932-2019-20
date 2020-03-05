package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Lift {
    private DcMotorEx liftMotorL;
    private DcMotorEx liftMotorR;

    public Lift(DcMotorEx liftMotorL, DcMotorEx liftMotorR) {
        this.liftMotorL = liftMotorL;
        this.liftMotorR = liftMotorR;
        liftMotorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotorL.setDirection(DcMotor.Direction.REVERSE);
    }

    public void move(double speed) {
        if (speed < 0)
            speed *= 1;

        if (speed < 0.05 && speed > -0.05)
            speed = 0;

        if (speed > 0)
            speed = 0.01;

        liftMotorL.setPower(speed);
        liftMotorR.setPower(speed);
    }

    public void toFoundation() {
        liftMotorL.setTargetPosition(50);
        liftMotorR.setTargetPosition(50);
        liftMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void toBottom() {
        liftMotorL.setTargetPosition(0);
        liftMotorR.setTargetPosition(0);
        liftMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
