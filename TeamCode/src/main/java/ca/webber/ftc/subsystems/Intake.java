package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Intake {

    DcMotorEx leftArm;
    DcMotorEx rightArm;

    //position numbers yet to be determined, filler values used below
    final int LEFT_MAX_POS = 260;
    final int RIGHT_MAX_POS = -260;

    public Intake (DcMotorEx leftArm, DcMotorEx rightArm){
        this.leftArm = leftArm;
        this.rightArm = rightArm;
        //leftArm.setTargetPosition(0);
        //rightArm.setTargetPosition(0);
    }

    public void shift (double speed){
        leftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftArm.setPower(speed);
        rightArm.setPower(speed);
    }

    public void shiftLeft(double speed) {
        leftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftArm.setPower(speed);
    }

    public void shiftRight(double speed) {
        rightArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightArm.setPower(speed);
    }

    public void open (){
        leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setPower(1);
        leftArm.setPower(1);
        leftArm.setTargetPosition(LEFT_MAX_POS);
        rightArm.setTargetPosition(RIGHT_MAX_POS);
    }

    public void close (){
        leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setPower(1);
        leftArm.setPower(1);
        leftArm.setTargetPosition(0);
        rightArm.setTargetPosition(0);
    }


}
