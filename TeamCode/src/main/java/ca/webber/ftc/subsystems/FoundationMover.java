package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;

public class FoundationMover {

    private CRServo foundation1;
    private CRServo foundation2;

    public FoundationMover(CRServo foundation1, CRServo foundation2) {
        this.foundation1 = foundation1;
        this.foundation2 = foundation2;
    }

    public void lockDown(double power) {
        foundation1.setPower(power);
        foundation2.setPower(power);
    }
}
