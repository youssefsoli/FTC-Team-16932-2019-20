package ca.webber.ftc.subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class RobotOrientation {

    private BNO055IMU imu;
    private Orientation orientation;
    private double orientationOffset = 0;

    public RobotOrientation(BNO055IMU imu) {
        this.imu = imu;
    }

    public double getRobotOrientation() {
        orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return orientation.firstAngle - orientationOffset;
    }

    public void resetOrientation() {
        orientationOffset += getRobotOrientation();
    }
}