package org.firstinspires.ftc.teamcode.bravo;

import java.util.ArrayList;

public class SelectStep extends InteractiveList {

	public SelectStep(StepSequenceListener listener){
		_listener = listener;s
	}

	public interface StepSequenceListener {
		void stepSelected();
	}

	public MethodSignature getCurrentSignature(){
		if(_curStep >= _steps.size() )
			return null; // this should never happen, nothing should ask for current step when we are on the end.
		return _steps.get(_curStep);
	}

	public void setCurrentSignature(MethodSignature sig){
		_steps.set(_curStep,sig);
	}

	@Override
	protected void DpadUp_Pressed(){
		if(_curStep>0) _curStep--;
		if(_topOfPageStep>_curStep) _topOfPageStep=_curStep;
	}

	@Override
	protected void DpadDown_Pressed() {
		if (_curStep < _steps.size()) _curStep++;
		if (_topOfPageStep < _curStep - DisplayCount) _topOfPageStep = _curStep - DisplayCount;
	}

	@Override
	protected void Y_Pressed() { // insert slot
		if(_curStep<_steps.size())
			_steps.add(_curStep,null);
	}

	protected void X_Pressed() { // delete slot
		if(_curStep<_steps.size())
			_steps.remove(_curStep);
	}

	protected void A_Pressed() { // insert slot
		if(_listener != null) _listener.stepSelected();
	}

	// ===============================
	// =====  Script  Steps  =========
	// ===============================
	void DisplayStepList(){
//		int end = Math.min(_topOfPageStep +4, _steps.size());
//		for(int i = _topOfPageStep; i<=end; ++i){
//			String text = i<_steps.size()
//					? "step"
//					: "end";
//			if(i==this._curStep)
//				text = "[" + text + "]";
//			telemetry.addData(text, i);
//			telemetry.update();
//		}
	}


	// Steps, current step
	final int DisplayCount = 4;
	ArrayList<MethodSignature> _steps = new ArrayList<MethodSignature>();
	int _topOfPageStep = 0;
	int _curStep = 0;
	StepSequenceListener _listener;

}
