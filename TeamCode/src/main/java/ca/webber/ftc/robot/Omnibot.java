package ca.webber.ftc.robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.List;

import ca.webber.ftc.subsystems.Drive;
import ca.webber.ftc.subsystems.FoundationMover;
import ca.webber.ftc.subsystems.Intake;
import ca.webber.ftc.subsystems.Lift;
import ca.webber.ftc.subsystems.RobotOrientation;

public class Omnibot {
    private List<DcMotorEx> motors;
    private Gamepad gamepad1, gamepad2;
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
    private LinearOpMode autoRuntime;
    private ElapsedTime runtime = new ElapsedTime();

    public Omnibot(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2, boolean startArmsUp) {
        DcMotorEx frontRight, frontLeft, backRight, backLeft, liftMotorL, liftMotorR, leftIntake, rightIntake;
        CRServo foundation1, foundation2, capStone, leftArm, rightArm;
        BNO055IMU imu;

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

        motors = Arrays.asList(frontRight, frontLeft, backRight, backLeft,
                liftMotorL, liftMotorR, leftIntake, rightIntake);
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.telemetry = telemetry;
        runtime.reset();

        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        lift = new Lift(liftMotorL, liftMotorR);
        intake = new Intake(leftIntake, rightIntake, leftArm, rightArm, startArmsUp);
        drive = new Drive(frontRight, frontLeft, backRight, backLeft);
        foundationMover = new FoundationMover(foundation1, foundation2, capStone);
        robotOrientation = new RobotOrientation(imu);
    }

    // Omnibot initialization for autonomous runtimes
    public Omnibot(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode autoRuntime) {
        this(hardwareMap, telemetry, null, null, true);
        this.autoRuntime = autoRuntime;
    }

    public void teleOpLoop() {
        telemetry.addData("Gyro", robotOrientation.getAbsoluteOrientation());

        for (DcMotorEx motor : motors) {
            telemetry.addData(motor.getDeviceName(), motor.getCurrentPosition());
        }

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

        if (!beforeCapstone && gamepad1.y) {
            foundationMover.toggleCapstoneController();
        }
        beforeCapstone = gamepad1.y;

        if (gamepad1.b)
            robotOrientation.resetOrientation();

        if (gamepad2.dpad_up || gamepad2.dpad_down)
            lift.move(gamepad2.dpad_up ? -1 : gamepad2.dpad_down ? 1 : 0);
        else
            lift.move(gamepad2.right_trigger - gamepad2.left_trigger);
    }

    public void gotoRedFoundation() {
        drive.drive(-1, 0, 0, .5);
        sleep(.8);

        drive.forward(0.3);
        sleep(2.2);
        drive.stop();

        foundationMover.toggleFoundationLock();
        sleep(0.8);

        turnRelative(-0.3, 170, 7);

        drive.forward(0.5);
        foundationMover.toggleFoundationLock();
        sleep(2);

        drive.drive(-1, 0, 0, .5);
        sleep(1.2);

        drive.forward(-0.4);
        sleep(0.4);

        intake.toggleArms();
        turnRelative(-0.3, 75);

        drive.forward(-0.4);
        sleep(0.7);
    }

    public void gotoBlueFoundation() {
        drive.drive(1, 0, 0, .35);
        sleep(1.3);

        drive.forward(0.3);
        sleep(2.3);
        drive.turn(-0.2);
        sleep(0.2);
        drive.stop();
        drive.forward(0.25);
        sleep(1);
        drive.stop();

        foundationMover.toggleFoundationLock();
        sleep(0.8);

        turnRelative(0.3, 175, 7);

        drive.forward(0.5);
        foundationMover.toggleFoundationLock();
        sleep(2);

        drive.drive(1, 0, 0, .5);
        sleep(1.2);

        drive.forward(-0.4);
        sleep(0.4);

        intake.toggleArms();
        turnRelative(0.3, 80);

        drive.forward(-0.4);
        sleep(1);
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
        while (runtime.seconds() < seconds && autoRuntime.opModeIsActive()) {
        }
    }

    private void turnRelative(double speed, double degrees) {
        drive.turn(speed);
        double startGyro = robotOrientation.getAbsoluteOrientation();
        if (speed < 0) {
            while (robotOrientation.getAbsoluteOrientation() < startGyro + degrees && autoRuntime.opModeIsActive()) {
            }
        } else if (speed > 0) {
            while (robotOrientation.getAbsoluteOrientation() > startGyro - degrees && autoRuntime.opModeIsActive()) {
            }
        }
        drive.stop();
    }

    private void turnRelative(double speed, double degrees, double timeout) {
        runtime.reset();
        drive.turn(speed);
        double startGyro = robotOrientation.getAbsoluteOrientation();
        if (speed < 0) {
            while (robotOrientation.getAbsoluteOrientation() < startGyro + degrees && autoRuntime.opModeIsActive() && runtime.seconds() < timeout) {
            }
        } else if (speed > 0) {
            while (robotOrientation.getAbsoluteOrientation() > startGyro - degrees && autoRuntime.opModeIsActive() && runtime.seconds() < timeout) {
            }
        }
        drive.stop();
    }

    private void straight(double speed, double distance) {
        drive.forward(speed);
        double startTicks = drive.averageTicks();
        while (drive.averageTicks() < startTicks + distance && autoRuntime.opModeIsActive()) {
        }
    }
}
