package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class FoundationMover {

    DcMotorEx foundationMotor;

    //numbers are just fillers, testing required
    final int ENGAGED_POS = 1000;
    final int RETRACTED_POS = 0;

    public FoundationMover (){
     //   DcMotorEx foundationMotor = ;
       // foundationMotor.setTargetPosition(0);
    }

    public void engageFoundationMover (){
        foundationMotor.setTargetPosition(ENGAGED_POS);
    }

    public void retractFoundationMover (){
        foundationMotor.setTargetPosition(RETRACTED_POS);
    }
}
