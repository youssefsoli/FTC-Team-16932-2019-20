package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;

public class FoundationMover {

    private CRServo foundation1;
    private CRServo foundation2;
    private boolean lock = false;

    public FoundationMover(CRServo foundation1, CRServo foundation2) {
        this.foundation1 = foundation1;
        this.foundation2 = foundation2;
    }

    public void loop() {
        if (lock) {
            foundation1.setPower(1);
            foundation2.setPower(-1);
        }
    }

    public void toggleLock() {
        lock = !lock;
    }

    public boolean getLock() {
        return lock;
    }
}
