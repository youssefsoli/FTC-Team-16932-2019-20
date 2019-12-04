package org.firstinspires.ftc.teamcode;

import android.support.annotation.NonNull;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.MagneticFlux;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.robotcore.external.navigation.Temperature;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrainMotorPower;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.util.Locale;



@TeleOp(name="Basic: Iterative OpMode", group="Iterative Opmode")

public class Teleop extends OpMode
{
    // Declare OpMode members.
    private static ElapsedTime runtime = new ElapsedTime();
    private static DcMotor leftDriveMotor = null;
    private static DcMotor rightDriveMotor = null;
    private static DcMotor frontDriveMotor = null;
    private static DcMotor rearDriveMotor = null;
    DriveTrainMotorPower driveTrainMotorPower = new DriveTrainMotorPower();
    Drive drive = new Drive();

/*
    BNO055IMU imu;

    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();


    parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
    parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
    parameters.calibrationDataFile = "BNO055IMUCalibration.json";  // see the calibration sample opmode
    parameters.loggingEnabled      = true;
    parameters.loggingTag          = "IMU";
    parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

    // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
    // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
    // and named "imu".
    imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
*/

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        leftDriveMotor  = hardwareMap.get(DcMotor.class, "leftDriveMotor");
        rightDriveMotor = hardwareMap.get(DcMotor.class, "rightDriveMotor");
        frontDriveMotor = hardwareMap.get(DcMotor.class, "frontDriveMotor");
        rearDriveMotor  = hardwareMap.get(DcMotor.class, "rearDriveMotor");

        leftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDriveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontDriveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearDriveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDriveMotor.setDirection(DcMotor.Direction.REVERSE);
        rightDriveMotor.setDirection(DcMotor.Direction.REVERSE);
        frontDriveMotor.setDirection(DcMotor.Direction.REVERSE);
        rearDriveMotor.setDirection(DcMotor.Direction.REVERSE);
        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");

    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    boolean fastMode = false;
    boolean before = false;
    boolean after = false;
    final double robotOrientation = 45; // degrees, unit circle layout


    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {

        after = gamepad1.a;

        if(before && !after) {
            fastMode = !fastMode;
        }

        before = after;

        drive.calculateMotorPower(fastMode, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y);
        leftDriveMotor.setPower(driveTrainMotorPower.getLeft());
        rightDriveMotor.setPower(driveTrainMotorPower.getRight());
        frontDriveMotor.setPower(driveTrainMotorPower.getFront());
        rearDriveMotor.setPower(driveTrainMotorPower.getRear());


        telemetry.addData("Left Drive", leftDriveMotor.getCurrentPosition());
        telemetry.addData("Right Drive", rightDriveMotor.getCurrentPosition());
        telemetry.addData("Front Drive", frontDriveMotor.getCurrentPosition());
        telemetry.addData("Rear Drive", rearDriveMotor.getCurrentPosition());
        telemetry.addData("Robot Orientation", robotOrientation);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

    public void initNav(){
        BNO055IMU imu;
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit            = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit            = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile  = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled       = true;
        parameters.loggingTag           = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    public void getRobotOrientation(){

    }

}
