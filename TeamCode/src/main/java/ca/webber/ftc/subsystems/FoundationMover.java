package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;

public class FoundationMover {

    private CRServo foundation1;
    private CRServo foundation2;
    private CRServo capstoneController;
    private boolean foundationLock = false;
    private boolean capstoneLock = false;

    public FoundationMover(CRServo foundation1, CRServo foundation2, CRServo capstoneController) {
        this.foundation1 = foundation1;
        this.foundation2 = foundation2;
        this.capstoneController = capstoneController;

        foundation1.setPower(0);
        foundation2.setPower(0);
        capstoneController.setPower(0);
    }

    public void toggleFoundationLock() {
        foundationLock = !foundationLock;
        if (foundationLock) {
            foundation1.setPower(1);
            foundation2.setPower(-1);
        } else {
            foundation1.setPower(0);
            foundation2.setPower(0);
        }
    }

    public void toggleCapstoneController() {
        capstoneLock = !capstoneLock;
        if (capstoneLock)
            capstoneController.setPower(-0.5);
        else
            capstoneController.setPower(0);
    }
}
