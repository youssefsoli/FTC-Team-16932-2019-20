package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Intake {

    DcMotorEx leftArm;
    DcMotorEx rightArm;

    //position numbers yet to be determined, filler values used below
    final int LEFT_MAX_POS = 1;
    final int RIGHT_MAX_POS = -1;

    public Intake (DcMotorEx leftArm, DcMotorEx rightArm){
        this.leftArm = leftArm;
        this.rightArm = rightArm;
    }

    public void shift (double speed){
        if (leftArm.getCurrentPosition() < LEFT_MAX_POS && rightArm.getCurrentPosition() < RIGHT_MAX_POS){
            leftArm.setPower(speed);
            rightArm.setPower(-speed);
        }
    }

    public void open (){
        leftArm.setTargetPosition(LEFT_MAX_POS);
        rightArm.setTargetPosition(RIGHT_MAX_POS);
    }

    public void close (){
        leftArm.setTargetPosition(0);
        rightArm.setTargetPosition(0);
    }


}
