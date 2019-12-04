package org.firstinspires.ftc.teamcode.subsystems;

public class DriveTrainMotorPower {


    double left;
    double right;
    double front;
    double rear;

    public void setPower(double left, double right, double front, double rear){
        this.left = left;
        this.right = right;
        this.rear = rear;
        this.front = front;
    }

    public double getLeft(){
        return left;
    }

    public double getRight(){return right; }

    public double getFront(){
        return front;
    }

    public double getRear(){
        return rear;
    }
}
