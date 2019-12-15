package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class SelectStep extends InteractiveList {

	public SelectStep(StepSequenceListener listener){
		_listener = listener;
	}

	public interface StepSequenceListener {
		void stepSelected();
	}

	@Override
	public void DisplayStatus(Telemetry telemetry){
		telemetry.addData("Mode","Select Step  A:sel X:del Y:ins L-BMP:clear R-BPM:toggle drag");
		int end = Math.min(_topOfPageStep +4, _steps.size());
		for(int i = _topOfPageStep; i<=end; ++i){
			String text = getStepDescription(i);
			if(i==_curStep){
				if(_dragItem)
					text = "[[["+text+"]]]";
				else
					text = "["+text+"]";
			}
			telemetry.addData(""+i, text);
		}
		telemetry.update();
	}

	String getStepDescription(int index){
		if(index>=_steps.size()) return "end";
		MethodSignature sig = _steps.get(index);
		if(sig==null) return "-empty-";
		return sig.getVerbose();

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
	public void DpadUp_Pressed(){

		if(_curStep==0) return;
		if(_dragItem&&_curStep<_steps.size())
			_steps.add(_curStep-1,_steps.remove(_curStep));
		_curStep--;
		if(_topOfPageStep>_curStep) _topOfPageStep=_curStep;
	}

	@Override
	public void DpadDown_Pressed() {
		if (_curStep == _steps.size()) return;
		if( _dragItem && _curStep == _steps.size()-1) return;
		_curStep++;
		if(_dragItem && _curStep < _steps.size())
			_steps.add(_curStep,_steps.remove(_curStep-1));
		if (_topOfPageStep < _curStep - DisplayCount) _topOfPageStep = _curStep - DisplayCount;
	}

	@Override
	public void Y_Pressed() { // insert slot
		if(_curStep<=_steps.size())
			_steps.add(_curStep,null);
	}

	@Override
	public void X_Pressed() { // delete slot
		if(_curStep<_steps.size())
			_steps.remove(_curStep);
	}

	@Override
	public void A_Pressed() { // select
		if(_listener != null && _curStep<_steps.size()) _listener.stepSelected();
	}

	@Override
	public void LeftBumper_Pressed(){
		if(_curStep<_steps.size())
			_steps.set(_curStep,null);
	}

	@Override
	public void RightBumper_Pressed(){
		// prevent from them getting into drag mode when on end.
		if(_curStep<_steps.size())
			_dragItem = !_dragItem;
	}


	// Steps, current step
	final int DisplayCount = 8;
	ArrayList<MethodSignature> _steps = new ArrayList<MethodSignature>();
	int _topOfPageStep = 0;
	int _curStep = 0;
	boolean _dragItem;
	StepSequenceListener _listener;

}
