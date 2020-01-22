package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Lift {
    DcMotorEx liftMotorL;
    DcMotorEx liftMotorR;
    //position values yet to be determined, below are filler values
    final int LEVEL_POS[] = {0, 1000, 2000, 4000, 5000};
    int currentLevel = 0;

    public Lift(DcMotorEx liftMotorL, DcMotorEx liftMotorR) {
        this.liftMotorL = liftMotorL;
        this.liftMotorR = liftMotorR;
        liftMotorL.setTargetPosition(0);
        liftMotorR.setTargetPosition(0);
    }

    public void moveUp1Level() {
        if (currentLevel < LEVEL_POS.length - 1){
            currentLevel ++;
            liftMotorL.setTargetPosition(LEVEL_POS[currentLevel]);
            liftMotorR.setTargetPosition(LEVEL_POS[currentLevel]);
            liftMotorL.setPower(1);
            liftMotorR.setPower(1);
        }

    }

    public void moveDown1Level() {
        if (currentLevel > 0){
            currentLevel--;
            liftMotorL.setTargetPosition(LEVEL_POS[currentLevel]);
            liftMotorR.setTargetPosition(LEVEL_POS[currentLevel]);
           liftMotorL.setPower(1);
            liftMotorR.setPower(1);
        }

    }

    public void move (double speed) {
        liftMotorL.setPower(speed);
        liftMotorR.setPower(speed);
    }

}
