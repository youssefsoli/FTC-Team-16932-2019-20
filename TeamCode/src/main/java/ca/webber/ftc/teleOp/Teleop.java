package ca.webber.ftc.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import ca.webber.ftc.subsystems.Drive;
import ca.webber.ftc.subsystems.FoundationMover;
import ca.webber.ftc.subsystems.Intake;
import ca.webber.ftc.subsystems.Lift;

@TeleOp(name="Teleop", group="Iterative Opmode")
public class Teleop extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotorEx frontRight = null;
    private DcMotorEx frontLeft = null;
    private DcMotorEx backRight = null;
    private DcMotorEx backLeft = null;
    private DcMotorEx liftMotorL = null;
    private DcMotorEx liftMotorR = null;
    private DcMotorEx leftIntake = null;
    private DcMotorEx rightIntake = null;
    private CRServo foundation1 = null;
    private CRServo foundation2 = null;
    //private DcMotorEx foundationMotor = null;
    //FoundationMover foundationMover;
    private Intake intake;
    private Lift lift;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
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

        lift = new Lift(liftMotorL, liftMotorR);
        intake = new Intake(leftIntake, rightIntake);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");

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

    boolean before = false;
    boolean after = false;

    boolean dPadUpPressed = false;
    boolean dPadDownPressed = false;


    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {

        Drive drive = new Drive(frontRight, frontLeft, backRight, backLeft);
        FoundationMover foundationMover = new FoundationMover(foundation1, foundation2);

        after = gamepad1.a;

        if(before && !after) {
            drive.toggleFastMode();
        }

        before = after;

        if (gamepad1.x){
            drive.setDirection(-1);
        }

        if (gamepad1.y){
            drive.setDirection(1);
        }

        drive.drive(gamepad1.left_stick_x, gamepad1.left_stick_y, -gamepad1.right_stick_x);

        //intake.shift(gamepad2.right_stick_x);

        intake.shiftRight(gamepad2.left_stick_x);
        intake.shiftLeft(gamepad2.right_stick_x);

        foundationMover.lockDown(gamepad2.right_trigger);

        if (gamepad2.x){
            intake.close();
        }

        if (gamepad2.y){
            intake.open();
        }

        if (gamepad2.dpad_up && !dPadUpPressed){
            lift.moveUp1Level();
            dPadUpPressed = false;
        }

        if (gamepad2.dpad_down && !dPadDownPressed){
            lift.moveDown1Level();
            dPadDownPressed = false;
        }

        telemetry.addData("leftInt", leftIntake.getCurrentPosition());
        telemetry.addData("rightInt", rightIntake.getCurrentPosition());

        telemetry.addData("leftLift", liftMotorL.getCurrentPosition());
        telemetry.addData("rightLift", liftMotorL.getCurrentPosition());

        lift.move(gamepad2.left_stick_y);

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
