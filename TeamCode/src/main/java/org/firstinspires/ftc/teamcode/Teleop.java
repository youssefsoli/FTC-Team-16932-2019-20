

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
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
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor frontDrive = null;
    private DcMotor rearDrive = null;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDrive  = hardwareMap.get(DcMotor.class, "leftDriveMotor");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDriveMotor");
        frontDrive = hardwareMap.get(DcMotor.class, "frontDriveMotor");
        rearDrive  = hardwareMap.get(DcMotor.class, "rearDriveMotor");

        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        frontDrive.setDirection(DcMotor.Direction.REVERSE);
        rearDrive.setDirection(DcMotor.Direction.REVERSE);


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
        double leftPower;
        double rightPower;
        double frontPower;
        double rearPower;

        after = gamepad1.a;

        if(before && !after) {
            fastMode = !fastMode;
        }

        before = after;


        // Left Stick for rotation, right for translation
        // displacement vector described by magnitude and bearing

        double turn = -gamepad1.left_stick_x;
        double power = Math.hypot(gamepad1.right_stick_x, gamepad1.right_stick_y);

        /*bearing is derived from gamepad x-y vector's angle
        (unit circle layout), plus known robot orientation,
        to produce absolute bearing angle, wrt to field*/

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
            leftPower   = Range.clip(turn + driveX, -1.0, 1.0) ;
            rightPower  = Range.clip(turn - driveX, -1.0, 1.0) ;
            frontPower  = Range.clip(turn + driveY, -1.0, 1.0) ;
            rearPower   = Range.clip(turn - driveY, -1.0, 1.0) ;
        }
        else {
            leftPower   = Range.clip(turn + driveX, -0.5, 0.5) ;
            rightPower  = Range.clip(turn - driveX, -0.5, 0.5) ;
            frontPower  = Range.clip(turn + driveY, -0.5, 0.5) ;
            rearPower   = Range.clip(turn - driveY, -0.5, 0.5) ;
        }

        // Send calculated power to wheels
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        frontDrive.setPower(frontPower);
        rearDrive.setPower(rearPower);

        telemetry.addData("Left Drive", leftDrive.getCurrentPosition());
        telemetry.addData("Right Drive", rightDrive.getCurrentPosition());
        telemetry.addData("Front Drive", frontDrive.getCurrentPosition());
        telemetry.addData("Rear Drive", rearDrive.getCurrentPosition());

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
