

package ca.webber.ftc.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.lang.Math;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Teleop", group="Iterative Opmode")

public class Teleop extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotorEx frontRight = null;
    private DcMotorEx frontLeft = null;
    private DcMotorEx backRight = null;
    private DcMotorEx backLeft = null;

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

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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

    boolean fastMode = false;
    boolean before = false;
    boolean after = false;

    final double robotOrientation = 45; // degrees, unit circle layout


    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {

        // Setup a variable for each drive wheel to save power level for telemetry
        double frPower;
        double flPower;
        double brPower;
        double blPower;

        after = gamepad1.a;

        if(before && !after) {
            fastMode = !fastMode;
        }

        before = after;


        // Left Stick for rotation, right for translation
        // displacement vector described by magnitude and bearing

        double turn = -gamepad1.left_stick_x;
        double power = Math.hypot(gamepad1.right_stick_x, gamepad1.right_stick_y);

        /* bearing is derived from gamepad x-y vector's angle
        (unit circle layout), plus known robot orientation,
        to produce absolute bearing angle, wrt to field */

        double rightStickY = gamepad1.right_stick_y;
        double rightStickX = gamepad1.right_stick_x;
        double bearing = Math.atan2(rightStickY, rightStickX) + Math.toRadians(robotOrientation);
        //unit circle layout, this var is in radians

        double driveY = (Math.sin(bearing)) * power;
        double driveX = (Math.cos(bearing)) * power;
        //breaks vector into x-y components

        telemetry.addData("Drive X", driveX);
        telemetry.addData("Drive Y", driveY);

        if(fastMode) {
            frPower   = Range.clip(Math.sqrt(turn + driveY), -1.0, 1.0) ;
            flPower  = Range.clip(Math.sqrt(turn - driveX), -1.0, 1.0) ;
            brPower  = Range.clip(Math.sqrt(turn + driveX), -1.0, 1.0) ;
            blPower   = Range.clip(Math.sqrt(turn - driveY), -1.0, 1.0) ;
        }
        else {
            frPower   = Range.clip(turn + driveY, -0.5, 0.5) ;
            flPower  = Range.clip(turn - driveX, -0.5, 0.5) ;
            brPower  = Range.clip(turn + driveX, -0.5, 0.5) ;
            blPower   = Range.clip(turn - driveY, -0.5, 0.5) ;
        }

        // Send calculated power to wheels
        frontRight.setPower(frPower);
        frontLeft.setPower(flPower);
        backRight.setPower(brPower);
        backLeft.setPower(blPower);

        telemetry.addData("frontRight Drive", frontRight.getCurrentPosition());
        telemetry.addData("frontLeft Drive", frontLeft.getCurrentPosition());
        telemetry.addData("backRight Drive", backRight.getCurrentPosition());
        telemetry.addData("backLeft Drive", backLeft.getCurrentPosition());

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
