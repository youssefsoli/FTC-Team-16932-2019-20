package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Lift {

    DcMotorEx liftMotor;

    //position values yet to be determined, below are filler values
    final int LEVEL_POS[] = {0, 1, 2, 4, 5};
    int currentLevel = 0;

    public Lift(DcMotorEx liftMotor) {
        this.liftMotor = liftMotor;
    }

    public void moveUp1Level() {
        currentLevel ++;
        liftMotor.setTargetPosition(LEVEL_POS[currentLevel]);
    }

    public void moveDown1Level() {
        currentLevel++;
        liftMotor.setTargetPosition(LEVEL_POS[currentLevel]);
    }

    public void move (double speed) {
        liftMotor.setPower(speed);
    }

}
