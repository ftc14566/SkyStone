package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.teamcode.*;
import com.qualcomm.robotcore.eventloop.opmode.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@TeleOp(name="Bravo", group="Testing")
public class Bravo  extends
		LinearOpMode implements
		SelectStep.StepSequenceListener,
		SelectMethod.MethodListListener,
		SelectParamValue.EventListener
{

	@Override
	public void runOpMode() throws InterruptedException {

        initBravoMode();

		waitForStart();

		while(this.opModeIsActive()){
			_mode.TrackGamePad(gamepad1);
		}

	}

	void initBravoMode(){
        // Init Bot
        TestHardware hardware = new TestHardware(hardwareMap);
        _bot = new TestBot(hardware,this);

        // init modes
        _selectStep = new SelectStep(this);
        _selectMethod = new SelectMethod(this);
        _selectParam = new SelectParamValue(this);
        _mode = _selectStep;

        // provide steps
        final Class c = _bot.getClass();
        final Method[] methods = c.getMethods();
        for(int i=0;i<methods.length;++i){
            MethodSignature sig = new MethodSignature((methods[i]));
            _selectMethod._items.add( sig );
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

    SelectStep _selectStep;
    SelectMethod _selectMethod;
    SelectParamValue _selectParam;
    InteractiveList _mode;

    TestBot _bot;


}
