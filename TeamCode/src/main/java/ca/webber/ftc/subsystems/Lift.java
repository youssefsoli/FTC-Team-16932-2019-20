package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Lift {
    private DcMotorEx liftMotorL;
    private DcMotorEx liftMotorR;

    public Lift(DcMotorEx liftMotorL, DcMotorEx liftMotorR) {
        this.liftMotorL = liftMotorL;
        this.liftMotorR = liftMotorR;
        liftMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotorR.setDirection(DcMotor.Direction.REVERSE);
    }

    public void move(double speed) {
        if (liftMotorL.getCurrentPosition() < 1 && liftMotorR.getCurrentPosition() < 1 && speed < 0)
            speed = 0;

        if (speed < 0)
            speed *= 0.5;

        liftMotorL.setPower(speed);
        liftMotorR.setPower(speed);
    }

    public String getEncoderValues() {
        return "" + liftMotorL.getCurrentPosition() + " " + liftMotorR.getCurrentPosition();
    }

    public void toFoundation() {
        liftMotorL.setTargetPosition(400);
        liftMotorR.setTargetPosition(400);
        liftMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorL.setPower(1);
        liftMotorR.setPower(1);
    }

    public void toBottom() {
        liftMotorL.setTargetPosition(0);
        liftMotorR.setTargetPosition(0);
        liftMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorL.setPower(-0.75);
        liftMotorR.setPower(-0.75);
    }
}
