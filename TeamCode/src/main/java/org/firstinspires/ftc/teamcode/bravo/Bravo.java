package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.teamcode.*;
import com.qualcomm.robotcore.eventloop.opmode.*;

@TeleOp(name="Bravo", group="Testing")
public class Bravo  extends
		LinearOpMode implements
		SelectStep.StepSequenceListener,
		SelectMethod.MethodListListener,
		SelectParamValue.EventListener
{

	SelectStep _selectStep;
	SelectMethod _selectMethod;
	SelectParamValue _selectParam;
	InteractiveList _mode;

	AutoBot _bot;

	@Override
	public void runOpMode() throws InterruptedException {

		// Init Bot
		Hardware hardware = new Hardware();
		hardware.init(hardwareMap);
		_bot = new AutoBot(hardware,this);

		// init modes
		_selectStep = new SelectStep(this);
		_selectMethod = new SelectMethod(this);
		_selectParam = new SelectParamValue(this);
		_mode = _selectStep;


		// provide steps
		_selectMethod._items.add(new MethodSignature("examineServo", new ConfigParam[]{
			new ConfigDouble("position",0.5,0,1,.5)
		}));

		_selectMethod._items.add(new MethodSignature("driveForward", new ConfigParam[]{
				new ConfigDouble("distance",0.5,0,1,.5) // units!
		}));


		// start
		waitForStart();

		// loop
		while(this.opModeIsActive()){
			_mode.TrackGamePad(gamepad1);
		}

	}

	@Override
	public void stepSelected() {// step-index ->down-> select method
		MethodSignature sig = _selectStep.getCurrentSignature();
		if(sig != null) {
			_selectParam.SetMethod(sig.Clone());
			_mode = _selectParam;
		} else {
			_mode = _selectMethod;
		}
	}
	@Override
	public void cancelMethodSelection() { _mode = _selectStep; } // select method ->up-> step-index


	@Override
	public void selectMethod() { _selectParam._methodData= _selectMethod.getCurrent(); _mode = _selectParam;  } // select method ->down-> config-params

	@Override
	public void cancelMethodConfig() { // config-params ->up-> select method
		MethodSignature sig = _selectStep.getCurrentSignature();
		if(sig==null){
			_mode = _selectMethod;
		} else {
			_mode = _selectStep;
		}
	}

	@Override
	public void executeMethod() {
		_selectParam._methodData.Execute(this._bot);
	}

	@Override
	public void saveMethodConfig() {
		_selectStep.setCurrentSignature( _selectParam._methodData.Clone() );
		_mode = _selectStep;
	}

}
