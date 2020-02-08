package ca.webber.ftc.robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import ca.webber.ftc.subsystems.Drive;
import ca.webber.ftc.subsystems.FoundationMover;
import ca.webber.ftc.subsystems.Intake;
import ca.webber.ftc.subsystems.Lift;
import ca.webber.ftc.subsystems.RobotOrientation;

public class Omnibot {

    private DcMotorEx frontRight;
    private DcMotorEx frontLeft;
    private DcMotorEx backRight;
    private DcMotorEx backLeft;
    private DcMotorEx liftMotorL;
    private DcMotorEx liftMotorR;
    private DcMotorEx leftIntake;
    private DcMotorEx rightIntake;
    private Gamepad gamepad1;
    private Gamepad gamepad2;
    private CRServo foundation1;
    private CRServo foundation2;
    private CRServo capStone;
    private CRServo leftArm;
    private CRServo rightArm;
    private BNO055IMU imu;
    private Intake intake;
    private Lift lift;
    private Drive drive;
    private FoundationMover foundationMover;
    private RobotOrientation robotOrientation;
    private Telemetry telemetry;
    private boolean beforeFast = false;
    private boolean beforeLock = false;
    private boolean beforeArm = false;
    private boolean beforeCapstone = false;
    private ElapsedTime runtime = new ElapsedTime();

    public Omnibot(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2) {
        frontRight = (DcMotorEx) hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = (DcMotorEx) hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = (DcMotorEx) hardwareMap.get(DcMotor.class, "backRight");
        backLeft = (DcMotorEx) hardwareMap.get(DcMotor.class, "backLeft");

        liftMotorL = (DcMotorEx) hardwareMap.get(DcMotor.class, "liftMotorL");
        liftMotorR = (DcMotorEx) hardwareMap.get(DcMotor.class, "liftMotorR");
        leftIntake = (DcMotorEx) hardwareMap.get(DcMotor.class, "leftIntake");
        rightIntake = (DcMotorEx) hardwareMap.get(DcMotor.class, "rightIntake");

        foundation1 = hardwareMap.get(CRServo.class, "foundation1");
        foundation2 = hardwareMap.get(CRServo.class, "foundation2");
        capStone = hardwareMap.get(CRServo.class, "capStone");
        leftArm = hardwareMap.get(CRServo.class, "leftArm");
        rightArm = hardwareMap.get(CRServo.class, "rightArm");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.telemetry = telemetry;
        runtime.reset();

        lift = new Lift(liftMotorL, liftMotorR);
        intake = new Intake(leftIntake, rightIntake, leftArm, rightArm);
        drive = new Drive(frontRight, frontLeft, backRight, backLeft);
        foundationMover = new FoundationMover(foundation1, foundation2, capStone);
        robotOrientation = new RobotOrientation(imu);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftMotorL.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));
        liftMotorR.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));

        leftIntake.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));
        rightIntake.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));

        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftIntake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightIntake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftIntake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightIntake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public Omnibot(HardwareMap hardwareMap, Telemetry telemetry) {
        this(hardwareMap, telemetry, null, null);
    }

    public void teleOpLoop() {
        if (!beforeFast && gamepad1.a) {
            drive.toggleFastMode();
        }
        beforeFast = gamepad1.a;

        drive.driveController(gamepad1, robotOrientation.getRobotOrientation());

        intake.move(gamepad2);

        if (!beforeLock && gamepad1.x) {
            foundationMover.toggleFoundationLock();
        }
        beforeLock = gamepad1.x;

        if (!beforeArm && gamepad2.a) {
            intake.toggleArms();
        }
        beforeArm = gamepad2.a;

        if (!beforeArm && gamepad1.y) {
            foundationMover.toggleCapstoneController();
        }
        beforeCapstone = gamepad1.y;

        if (gamepad1.b)
            robotOrientation.resetOrientation();

        if (gamepad2.dpad_up || gamepad2.dpad_down)
            lift.move(gamepad2.dpad_up ? -1 : gamepad2.dpad_down ? 1 : 0);
        else
            lift.move(gamepad2.right_trigger - gamepad2.left_trigger);

        telemetry.addData("Orientation", robotOrientation.getRobotOrientation());
    }

    public void gotoRedFoundation() {
        drive.drive(-1, 0, 0, .5);
        sleep(.8);

        drive.turn(0.2);
        sleep(0.08); // 0.04
        drive.forward(0.45);
        sleep(1.75);

        foundationMover.toggleFoundationLock();
        sleep(0.8);

        drive.turn(-0.3);
        sleep(5); // 2.3 on rough

        drive.forward(0.5);
        foundationMover.toggleFoundationLock();
        sleep(2.3);

        drive.drive(-1, 0, 0, .5);
        sleep(1.2);

        drive.forward(-0.4);
        sleep(0.4);

        drive.turn(-0.3);
        intake.toggleArms();
        sleep(0.45);

        drive.forward(-0.4);
        sleep(1.8);

    }

    public void gotoBlueFoundation() {
        drive.drive(1, 0, 0, .5);
        sleep(.8);

        drive.turn(-0.2);
        sleep(0.08);
        drive.forward(0.45);
        sleep(2);

        foundationMover.toggleFoundationLock();
        sleep(0.8);

        drive.turn(0.3);
        sleep(5); // 2.2 on rough

        drive.forward(0.5);
        foundationMover.toggleFoundationLock();
        sleep(2);

        drive.drive(1, 0, 0, .5);
        sleep(1.2);

        drive.forward(-0.4);
        sleep(0.4);

        drive.turn(0.3);
        intake.toggleArms();
        sleep(0.6);

        drive.forward(-0.4);
        sleep(2.3);
    }

    public void gotoBlueFoundationAndGrab() {
        gotoBlueFoundation();
        intake.open(0.5);
        sleep(1.5);

        drive.drive(-1, 0, 0, 1);
        sleep(0.5);
        intake.stop();

        drive.forward(-0.5);
        sleep(1);
        intake.open(-.5);
        sleep(1);

        drive.drive(-1, 0, 0, 1);
        sleep(1);

        drive.forward(0.5);
        sleep(1.5);

        drive.turn(0.5);
        sleep(1.2);
    }

    public void idleBlue() {
        drive.drive(-1, 0, 0, .5);
        sleep(1.4);
        drive.forward(.5);
        sleep(1.8);
        drive.stop();
    }

    public void idleRed() {
        drive.drive(1, 0, 0, .5);
        intake.toggleArms();
        sleep(2);
        drive.forward(.5);
        sleep(1.8);
        drive.stop();
    }

    private void sleep(double seconds) {
        runtime.reset();
        while (runtime.seconds() < seconds) {
        }
    }
}
