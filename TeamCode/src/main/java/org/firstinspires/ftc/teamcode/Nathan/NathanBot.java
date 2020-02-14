package org.firstinspires.ftc.teamcode.Nathan;


import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.TeleBot;


public class NathanBot {

    public NathanBot(Hardware hardware){

        this.hardware = hardware;
        towerPositionRight = hardware.rightTowerMotor.getCurrentPosition();
        towerPositionLeft = hardware.leftTowerMotor.getCurrentPosition();
        grabber = new Grabber(hardware.grabberLeft, hardware.grabberRight);
        foundationGrabber = new FoundationGrabber(hardware.leftFoundationServo, hardware.rightFoundationServo);

        grabber = new Grabber(hardware.grabberLeft, hardware.grabberRight);
        foundationGrabber = new FoundationGrabber(hardware.leftFoundationServo, hardware.rightFoundationServo);
    }

    public enum LiftAction{
        idle,up,down,slowUp,slowDown

    }
    public enum BridgeAction {
        idle,in,out,inSlow,outSlow
    }
    public enum BridgePosition{In,Out,Grabbing;}

    // region fields

    protected Hardware hardware;

    public double towerPositionRight;//'' = hardware.rightTowerMotor.getCurrentPosition();
    public double towerPositionLeft;// = hardware.leftTowerMotor.getCurrentPosition();

    public boolean doAutoGrab = false;
    public boolean autoExtend = false;
    public boolean goToBasement = false;
    public int leftzero = 0;
    public int rightzero = 0;
    public BridgeAction bridgeAction;
    public LiftAction liftAction;
    String grabberAction;


    private int towerDownPulser = 0;

    boolean wasDown = false;

    double time;

    double downTime = 0;

    public boolean noBlock;
    public double bridgeDistanceCm;
    public int liftPosition;

    // endregion

    // region WHEEL STUFF
    public void DriveForward(double speed){
        this.Move(speed,0.0);
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

    private double AdjustInputs(double x){
        return x*x*x;
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


    public void checkSpeed(float checkButton, double forward, double turnRight, double straifRight) {
        if (checkButton > .25) {
            slowDriveAndStrafe(forward, turnRight, straifRight);
        }
        else {
            driveAndStrafe(forward, turnRight, straifRight);
        }
    }

    public void driveAndStrafe(double forward, double turnRight, double straifRight) {

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
        double maxPower = Math.max(Math.abs(fl), Math.abs(fr));
        maxPower = Math.max(maxPower, Math.abs(rl));
        maxPower = Math.max(maxPower, Math.abs(rr));
        if (maxPower > 1.0) {
            fl /= maxPower;
            fr /= maxPower;
            rl /= maxPower;
            rr /= maxPower;
        }
        hardware.frontLeftDrive.setPower(fl);
        hardware.frontRightDrive.setPower(fr);
        hardware.rearLeftDrive.setPower(rl);
        hardware.rearRightDrive.setPower(rr);

    }


    public void slowDriveAndStrafe(double forward, double turnRight, double straifRight) {

        double scale = .25;

        forward *= scale;
        turnRight *= scale;
        straifRight *= scale;

        // combine drive,turn,straif
        double fl = forward + turnRight + straifRight;
        double rl = forward + turnRight - straifRight;
        double fr = forward - turnRight - straifRight;
        double rr = forward - turnRight + straifRight;

        // limit each drom to 1.0 max
        double maxPower = Math.max(Math.abs(fl), Math.abs(fr));
        maxPower = Math.max(maxPower, Math.abs(rl));
        maxPower = Math.max(maxPower, Math.abs(rr));
        if (maxPower > 1.0) {
            fl /= maxPower;
            fr /= maxPower;
            rl /= maxPower;
            rr /= maxPower;
        }
        hardware.frontLeftDrive.setPower(fl);
        hardware.frontRightDrive.setPower(fr);
        hardware.rearLeftDrive.setPower(rl);
        hardware.rearRightDrive.setPower(rr);

    }



    // endregion

    // region LIFT
    private void liftIdle(){
        if (hardware.touchSensor.isPressed() && downTime+1 < time   )
            setTowerPower(0);
        else
            setTowerPower(0.2);
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
    private NathanBot.LiftAction determinLiftActionFromButtons(Gamepad gamepad) {
        NathanBot.LiftAction action = NathanBot.LiftAction.idle;
        if (gamepad.dpad_up)
            return NathanBot.LiftAction.up;
        if (gamepad.dpad_down)//goes down when grabing block
            return NathanBot.LiftAction.down;
        if (gamepad.x)
            return LiftAction.slowUp;
        if (gamepad.b)//goes down when grabing block
            return liftAction.slowDown;
        return LiftAction.idle;
    }

    private void goDown(){
        setTowerPower(0.01);
    }

    private void goDownBetter(){
        if (towerDownPulser <1)
            setTowerPower(0.0);
        else
            setTowerPower(-0.005);

        towerDownPulser += 1;
        if (towerDownPulser == 2)
            towerDownPulser = 0;
    }

    private void setTowerPower(double power){
        hardware.leftTowerMotor.setPower(power);
        hardware.rightTowerMotor.setPower(power);
    }

    // endregion

    //region Bridge

    private BridgeAction determinBridgeAction(boolean dpad_left, boolean dpad_right) {
        if (dpad_left)
            return BridgeAction.in;
        if (dpad_right)
            return BridgeAction.out;
        return BridgeAction.idle;
    }

    public void extend (BridgeAction action){
        double power = 0;
        if (action == BridgeAction.out) power = 0.3;
        if (action == BridgeAction.in) power = -0.3;
        if (action == BridgeAction.inSlow) power = -0.2;
        if (action == BridgeAction.outSlow) power = 0.2;

        hardware.bridgeMotor.setPower(power);
        hardware.bridgeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    // endregion

    // region grabber

    Grabber grabber;

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

    public void doGraberAction(String action){

        switch(action){
            case "open": grabber.open(); break;
            case "grab": grabber.grab(); break;
        }

    }

    // endregion

    // region Foundation

    FoundationGrabber foundationGrabber;

    public void foundationServos(boolean up, boolean down){
        if (up)
            foundationGrabber.up();
        else if (down)
            foundationGrabber.down();
    }


    // endregion

    public void autoGrab (Gamepad gamepad, double time){
        this.time = time;

        if (gamepad.a) {
            doAutoGrab = true;
            autoExtend = true;
            goToBasement = true;
        }else if (gamepad.dpad_up || gamepad.dpad_right || gamepad.dpad_down || gamepad.dpad_left){
            doAutoGrab = false;
            autoExtend = false;
            goToBasement = false;
        }

        boolean towerIsDown = hardware.touchSensor.isPressed();
        if (towerIsDown) {
            leftzero = hardware.leftTowerMotor.getCurrentPosition();
            rightzero = hardware.rightTowerMotor.getCurrentPosition();
            goToBasement = false;
        }
        if(!wasDown && towerIsDown) downTime = time;
        wasDown = towerIsDown; //record for next time

        if (doAutoGrab){
            boolean isBlock = hardware.distanceSensor.getDistance(DistanceUnit.CM)<6;

            liftPosition = (
                    hardware.leftTowerMotor.getCurrentPosition()-leftzero
                            +hardware.rightTowerMotor.getCurrentPosition()-rightzero
            )/2;
            if (isBlock) {
                liftAction = LiftAction.down;
            } else {
                int grabHight = 130;
                if (goToBasement)
                    liftAction = LiftAction.slowDown;
                else if (liftPosition < grabHight)
                    liftAction = LiftAction.slowUp;
                else if (liftPosition > grabHight + 60)
                    liftAction = LiftAction.slowDown;
                else
                    liftAction = LiftAction.idle;
            }
            Lift(liftAction);

///////// BRIDGE EXTEND/////////////////////////////////////////////////////////////////////////////

            noBlock = hardware.distanceSensor.getDistance(DistanceUnit.CM)>4;
            bridgeDistanceCm = hardware.bridgeDistance.getDistance(DistanceUnit.CM);


            if (liftPosition<170 && noBlock && autoExtend) {
                if ( bridgeDistanceCm < 20)
                    bridgeAction = BridgeAction.outSlow;
                else if (bridgeDistanceCm > 22)
                    bridgeAction = BridgeAction.inSlow;
                else {
                    bridgeAction=BridgeAction.idle;
                    autoExtend = false;
                }
            }
            else
                bridgeAction=BridgeAction.idle;
            extend(bridgeAction);

////////////////GRABER//////////////////////////////////////////////////////////////////////////////

            if (isBlock) {
                grabberAction = "grab";
                doAutoGrab = false;
            }
            else
                grabberAction = "open";


        }
        else {
            //// MANUAL OVERIDE ////////////////////////////////////////////////////////////////////

            liftAction = determinLiftActionFromButtons(gamepad);

            grabberAction = determineGrabberAction(gamepad.x, gamepad.b, isBlockInFront(),gamepad.left_bumper);
            bridgeAction = determinBridgeAction (gamepad.dpad_left, gamepad.dpad_right);
        }

        Lift(liftAction);
        extend(bridgeAction);
        doGraberAction(grabberAction);


    }

    public boolean isBlockInFront(){

        return hardware.distanceSensor.getDistance(DistanceUnit.CM)<3.5;
    }

    // region LED lights

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

    public void SetLightColor(double time, boolean foundationDown, boolean grabberDown, boolean grabberOpen, boolean autoGrab) {

        RevBlinkinLedDriver.BlinkinPattern color;


        if (autoGrab)
            color = RevBlinkinLedDriver.BlinkinPattern.YELLOW;
        else if (foundationDown)
            color = RevBlinkinLedDriver.BlinkinPattern.BLUE_VIOLET;
        else if (grabberDown)
            color = RevBlinkinLedDriver.BlinkinPattern.GREEN;
        else if (grabberOpen)
            color = RevBlinkinLedDriver.BlinkinPattern.GOLD;
        else if (time < 90)
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
        else if (time < 120)
            color = RevBlinkinLedDriver.BlinkinPattern.SINELON_LAVA_PALETTE;
        else
            color = RevBlinkinLedDriver.BlinkinPattern.BLACK;
        hardware.Lights.setPattern(color);
    }

    // endregion
}
