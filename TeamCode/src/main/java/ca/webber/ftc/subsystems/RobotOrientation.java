package ca.webber.ftc.subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

public class RobotOrientation {

    private BNO055IMU imu;
    private double orientationOffset = 0;
    private double previousAngle = 0;
    private double globalAngle = 0;

    public RobotOrientation(BNO055IMU imu) {
        this.imu = imu;
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imu.initialize(parameters);
    }

    public double getRobotOrientation() {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle - orientationOffset;
    }

    public double getAbsoluteOrientation() {
        double currentAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        double netAngle = currentAngle - previousAngle;

        if (netAngle < -180)
            netAngle += 360;
        else if (netAngle > 180)
            netAngle -= 360;

        globalAngle += netAngle;

        previousAngle = currentAngle;

        return globalAngle;
    }

    public void resetOrientation() {
        orientationOffset += getRobotOrientation();
    }
}