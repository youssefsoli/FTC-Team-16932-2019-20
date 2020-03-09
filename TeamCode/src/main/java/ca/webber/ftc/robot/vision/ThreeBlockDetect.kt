package ca.webber.ftc.robot.vision

import org.openftc.easyopencv.OpenCvPipeline

abstract class ThreeBlockDetect : OpenCvPipeline() {
    var detectedSkystonePosition = -1
    val width = 640
    val height = 480

    var offset: Double = 0.0
}
