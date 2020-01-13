package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
public class FoundationMover {

    DcMotorEx foundationMotor;

    //numbers are just fillers, testing required
    final int ENGAGED_POS = 1;
    final int RETRACTED_POS = 0;

    public FoundationMover (DcMotorEx foundationMotor){
        this.foundationMotor = foundationMotor;
    }

    public void engageFoundationMover (){
        foundationMotor.setTargetPosition(ENGAGED_POS);
    }

    public void retractFoundationMover (){
        foundationMotor.setTargetPosition(RETRACTED_POS);
    }
}
