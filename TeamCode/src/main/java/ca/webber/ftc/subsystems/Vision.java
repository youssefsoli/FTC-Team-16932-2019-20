package ca.webber.ftc.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.NaivePointSampleSkystoneDetectionPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import ca.webber.ftc.robot.vision.ThreeBlockDetect;

public class Vision {
    private OpenCvCamera camera;
    private ThreeBlockDetect pipeline;

    public Vision(HardwareMap hardwareMap) {
        pipeline = new NaivePointSampleSkystoneDetectionPipeline();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);
        camera.openCameraDevice();
        camera.setPipeline(pipeline);
        camera.startStreaming(pipeline.getWidth(), pipeline.getHeight(), OpenCvCameraRotation.UPRIGHT);
    }

    public int getSkystonePosition() {
        return pipeline.getDetectedSkystonePosition();
    }

    public void stop() {
        camera.stopStreaming();
        camera.closeCameraDevice();
    }
}
