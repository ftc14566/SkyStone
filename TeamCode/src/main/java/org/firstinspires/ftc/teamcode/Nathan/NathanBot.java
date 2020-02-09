package org.firstinspires.ftc.teamcode.Nathan;


import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.TeleBot;


public class NathanBot extends TeleBot {

    public NathanBot(Hardware hardware){
        super(hardware);
        grabber = new Grabber(hardware.grabberLeft, hardware.grabberRight);
        foundationGrabber = new FoundationGrabber(hardware.leftFoundationServo, hardware.rightFoundationServo);
    }

    Grabber grabber;
    FoundationGrabber foundationGrabber;



    public void DriveForward(double speed){
        this.Move(speed,0.0);
    }

    public void Stop(){
        this.Move(0.0,0.0);
    }

    public void StrafeRight(double speed){
        this.Move(0,speed);
    }

    public void SpinRight(double speed){;
        hardware.frontLeftDrive.setPower(speed);
        hardware.frontRightDrive.setPower(-speed);
        hardware.rearLeftDrive.setPower(speed);
        hardware.rearRightDrive.setPower(-speed);
    }


    public void Move(double forward, double rightSpeed){

        double sumLeft=Math.abs(forward)+Math.abs(rightSpeed);
        if(sumLeft > 1.0){
            forward/=sumLeft;
            rightSpeed/=sumLeft;
        }

        hardware.frontLeftDrive.setPower(forward+rightSpeed);//
        hardware.rearLeftDrive.setPower(forward-rightSpeed);//
        hardware.frontRightDrive.setPower(forward-rightSpeed);//
        hardware.rearRightDrive.setPower(forward+rightSpeed);//

    }

    public enum LiftAction{
        idle,up,down,slowUp,slowDown
    }

    public void Lift(LiftAction action){
        if(action == LiftAction.up)
            setTowerPower(0.5);
        else if (action == LiftAction.slowUp)
            setTowerPower(0.35);
        else if(hardware.touchSensor.isPressed()|| hardware.leftTowerMotor.getCurrentPosition()<80 )
            liftIdle();
        else if (action == LiftAction.down)
            goDownBetter();
        else if (action == LiftAction.slowDown)
            setTowerPower(0.02);
        else
            liftIdle ();
    }


    public boolean doAutoGrab = false;
    public boolean autoExtend = false;

    public int leftzero = 0;
    public int rightzero = 0;

    double time;
    double downTime = 0;
    boolean wasDown = false;
    public BridgeAction bridgeAction;

    public void autoGrab (Gamepad gamepad, double time){
        this.time = time;

        if (gamepad.a) {
            doAutoGrab = true;
            autoExtend = true;
        }else if (gamepad.dpad_up || gamepad.dpad_right || gamepad.dpad_down || gamepad.dpad_left){
            doAutoGrab = false;
            autoExtend = false;
        }


        boolean isDown = hardware.touchSensor.isPressed();
        if (isDown) {
            leftzero = hardware.leftTowerMotor.getCurrentPosition();
            rightzero = hardware.rightTowerMotor.getCurrentPosition();
        }
        if(!wasDown && isDown) downTime = time;
        wasDown = isDown; //record for next time

        if (doAutoGrab){
            boolean isBlock = hardware.distanceSensor.getDistance(DistanceUnit.CM)<3;

            int liftPosition = (
                    hardware.leftTowerMotor.getCurrentPosition()-leftzero
                    +hardware.rightTowerMotor.getCurrentPosition()-rightzero
            )/2;
            if (isBlock) {
                Lift(LiftAction.down);
            } else {
                int grabHight = 130;
                if (liftPosition < grabHight)
                    Lift(LiftAction.slowUp);
                else if (liftPosition > grabHight + 60)
                    Lift(LiftAction.slowDown);
                else
                    Lift(LiftAction.idle);
            }

/////////EXTEND/////////////////////////////////////////////////////////////////////////////////////

            if (liftPosition<150 && hardware.distanceSensor.getDistance(DistanceUnit.CM)>4 && autoExtend) {
                if (hardware.bridgeDistance.getDistance(DistanceUnit.CM) < 20)
                    bridgeAction=BridgeAction.out;
                else if (hardware.bridgeDistance.getDistance(DistanceUnit.CM) > 22)
                    bridgeAction=BridgeAction.inSlow;
                else {
                    bridgeAction=BridgeAction.idle;
                    autoExtend = false;
                }
            }
            else
                bridgeAction=BridgeAction.idle;
            extend(bridgeAction);
////////////////GRABER//////////////////////////////////////////////////////////////////////////////

            if (isBlock)
                doGraberAction("grab");
            else
                doGraberAction("open");

        }
        else {
            //// MANUAL OVERIDE ////////////////////////////////////////////////////////////////////
            LiftAction liftAction = determinLiftActionFromButtons(gamepad.dpad_up, gamepad.dpad_down);
            String grabberAction = determineGrabberAction(gamepad.x, gamepad.b, isBlockInFront(),gamepad.left_bumper);
            bridgeAction = determinBridgeAction (gamepad.dpad_left, gamepad.dpad_right);

            Lift(liftAction);
            extend(gamepad.dpad_right,gamepad.dpad_left);
            doGraberAction(grabberAction);

        }
    }

    private BridgeAction determinBridgeAction(boolean dpad_left, boolean dpad_right) {
        if (dpad_left)
            return BridgeAction.in;
        if (dpad_right)
            return BridgeAction.out;
        return BridgeAction.idle;
    }

    private NathanBot.LiftAction determinLiftActionFromButtons(boolean up, boolean down) {
        NathanBot.LiftAction action = NathanBot.LiftAction.idle;
        if (up)
            action = NathanBot.LiftAction.up;
        else if (down)
            action = NathanBot.LiftAction.down;
        return action;
    }

    private void goDown(){
        setTowerPower(0.01);
    }

    private int liftCounter = 0;
    private void goDownBetter(){
        if (liftCounter<1)
            setTowerPower(0.0);
        else
            setTowerPower(-0.005);

        liftCounter += 1;
        if (liftCounter == 2)
            liftCounter = 0;
    }

    private void setTowerPower(double power){
        hardware.leftTowerMotor.setPower(power);
        hardware.rightTowerMotor.setPower(power);
    }



    private void liftIdle(){
        if (hardware.touchSensor.isPressed() && downTime+1 < time   )
            setTowerPower(0);
        else
            setTowerPower(0.2);
    }
    private double ramp (double current, double target, double stepUpSize){
        if(current<target){
            // going up
            if(current<0) return 0;
            return Math.min(target,current + stepUpSize);
        } else{
            // going down
            if(current > 0) return 0;
            return Math.max(target, current - stepUpSize);
        }
    }

    public void driveAndStraif (double forward, double turnRight, double straifRight){

        double scale = 1;

        forward *= scale;
        turnRight *= scale;
        straifRight *= scale;

        // combine drive,turn,straif
        double fl = forward + turnRight + straifRight;
        double rl = forward + turnRight - straifRight;
        double fr = forward - turnRight - straifRight;
        double rr = forward - turnRight + straifRight;

        // limit each drom to 1.0 max
        double maxPower = Math.max(Math.abs(fl),Math.abs(fr));
        maxPower = Math.max(maxPower,Math.abs(rl));
        maxPower = Math.max(maxPower,Math.abs(rr));
        if(maxPower>1.0){
            fl/=maxPower;
            fr/=maxPower;
            rl/=maxPower;
            rr/=maxPower;
        }
        hardware.frontLeftDrive.setPower(fl);
        hardware.frontRightDrive.setPower(fr);
        hardware.rearLeftDrive.setPower(rl);
        hardware.rearRightDrive.setPower(rr);

    }

    public void grab(boolean open,boolean blockInFront, boolean closeButton){


        if(open)
            grabber.open();
        else if(blockInFront)
           grabber.grab();


    }

    public void doGraberAction(String action){

        switch(action){
            case "open": grabber.open(); break;
            case "grab": grabber.grab(); break;
        }

    }

    public String determineGrabberAction(boolean open, boolean close, boolean blockSenser, boolean activateSensor) {
        String action = "";
        if (open)
            action = "open";
        else if (close)
            action = "grab";
        else if (activateSensor && blockSenser)
            action = "grab";
        return action;
    }

    public boolean isBlockInFront(){

        return hardware.distanceSensor.getDistance(DistanceUnit.CM)<3.5;
    }

    public enum BridgePosition{In,Out,Grabbing}
    public void ExtendWithEncoders(BridgePosition position){

        switch(position){
            case In:
                hardware.bridgeMotor.setTargetPosition(0);
                hardware.bridgeMotor.setPower(0.3);
                break;
            case Out:
                hardware.bridgeMotor.setTargetPosition(500);
                hardware.bridgeMotor.setPower(0.3);
                break;
            case Grabbing:
                hardware.bridgeMotor.setTargetPosition(168);
                hardware.bridgeMotor.setPower(0.3);
                break;

        }

    }

    private double AdjustInputs(double x){
        return x*x*x;
    }

    public void MoveLR(double forwardSpeedLeft, double rightSpeedLeft,double forwardSpeedRight, double rightSpeedRight){

        forwardSpeedLeft = AdjustInputs(forwardSpeedLeft);
        forwardSpeedRight = AdjustInputs(forwardSpeedRight);
        rightSpeedLeft = AdjustInputs(rightSpeedLeft);
        rightSpeedRight = AdjustInputs(rightSpeedRight);

        double sumLeft=Math.abs(forwardSpeedLeft)+Math.abs(rightSpeedLeft);
        if(sumLeft > 1.0){
            forwardSpeedLeft/=sumLeft;
            rightSpeedLeft/=sumLeft;
        }

        double sumRight=Math.abs(forwardSpeedRight)+Math.abs(rightSpeedRight);
        if(sumRight > 1.0){
            forwardSpeedRight/=sumRight;
            rightSpeedRight/=sumRight;
        }

        hardware.frontLeftDrive.setPower(forwardSpeedLeft+rightSpeedLeft);//
        hardware.rearLeftDrive.setPower(forwardSpeedLeft-rightSpeedLeft);//
        hardware.frontRightDrive.setPower(forwardSpeedRight-rightSpeedRight);//
        hardware.rearRightDrive.setPower(forwardSpeedRight+rightSpeedRight);//

    }
    public void foundationServos(boolean up, boolean down){
        if (up)
            foundationGrabber.up();
        else if (down)
            foundationGrabber.down();
    }

    public void SetLightColor (double time,boolean foundationDown, boolean grabberDown,boolean grabberOpen){

        RevBlinkinLedDriver.BlinkinPattern color;


        if (foundationDown)
            color = RevBlinkinLedDriver.BlinkinPattern.BLUE_VIOLET;
        else if (grabberDown)
            color = RevBlinkinLedDriver.BlinkinPattern.GREEN;
        else if (grabberOpen)
            color = RevBlinkinLedDriver.BlinkinPattern.GOLD;
        else if (time <90)
            color = RevBlinkinLedDriver.BlinkinPattern.DARK_BLUE;
        else if (time < 90.1)
            color = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
        else if (time < 90.2)
            color = RevBlinkinLedDriver.BlinkinPattern.BLACK;
        else if (time < 90.3)
            color = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
        else if (time < 90.4)
            color = RevBlinkinLedDriver.BlinkinPattern.BLACK;
        else if (time < 90.5)
            color = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
        else if (time < 90.6)
            color = RevBlinkinLedDriver.BlinkinPattern.BLACK;
        else if (time < 90.7)
            color = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
        else if (time < 90.8)
            color = RevBlinkinLedDriver.BlinkinPattern.BLACK;
        else if (time < 90.9)
            color = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;
        else if (time <120)
            color = RevBlinkinLedDriver.BlinkinPattern.SINELON_LAVA_PALETTE;
        else
            color = RevBlinkinLedDriver.BlinkinPattern.BLACK;
        hardware.Lights.setPattern(color);
    }

    enum BridgeAction {
        idle,in,out,inSlow,outSlow
    }

    public void extend (BridgeAction action){
        double power = 0;
        if (action == BridgeAction.out) power = 0.3;
        if (action == BridgeAction.in) power = -0.3;
        if (action == BridgeAction.inSlow) power = -0.2;
        if (action == BridgeAction.outSlow) power = 0.1;

        hardware.bridgeMotor.setPower(power);
        hardware.bridgeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void checkSpeed(float checkButton, double forward, double turnRight, double straifRight) {
        if (checkButton > .25) {
            slowDriveAndStrafe(forward, turnRight, straifRight);
        }
        else {
            driveAndStrafe(forward, turnRight, straifRight);
        }
    }


}
