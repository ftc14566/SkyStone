package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SelectParamValue extends InteractiveList {

	// region consturctor

	public SelectParamValue(EventListener listener){
		_listener = listener;
	}

	// endregion

	@Override
	public void DisplayStatus(Telemetry telemetry){
		telemetry.addData("Mode","Enter Param Values for:"+ _signature._method.getName());

		int end = Math.min(_topOfPageIndex+LinesPerPage, _signature._params.length); // exclude end-index
		for(int i = _topOfPageIndex; i<end; ++i)
			AddParamToTelemetry(telemetry,_signature._params[i],i==_curIndex);
		telemetry.update();
	}

	void AddParamToTelemetry(Telemetry telemetry, Param p,boolean selected){
		// !! this could be moved into Parameter.
		String valueText = selected
				? "["+p.getValueString() + "] (" + p.getRangeString() + ")"
				: p.getValueString();
		telemetry.addData(p.getLabel(),valueText);
	}

	public void SetMethod(MethodSignature methodData){
		_curIndex = 0;
		_signature = methodData;
	}

	//region button presses

	@Override
	public void DpadUp_Pressed(){
		if(_curIndex>0) _curIndex--;
		if(_topOfPageIndex<_curIndex+1-LinesPerPage) _topOfPageIndex=_curIndex+1-LinesPerPage;
	}

	@Override
	public void DpadDown_Pressed(){
		if(_curIndex< _signature._params.length-1) _curIndex++;
		if(_topOfPageIndex>_curIndex) _topOfPageIndex=_curIndex;
	}

	@Override
	public void DpadLeft_Pressed(){ _signature._params[_curIndex].dec(); }

	@Override
	public void DpadRight_Pressed(){
		_signature._params[_curIndex].inc();
	}

	@Override
	public void B_Pressed() { _listener.cancelMethodConfig(); }

	@Override
	public void A_Pressed() { _listener.executeMethod(); }

	@Override
	public void LeftBumper_Pressed() { _listener.saveMethodConfig(); }

	//endregion

	//region interface

	public interface EventListener{
		void executeMethod();
		void saveMethodConfig();
		void cancelMethodConfig();
	}

	//endregion

	//region private fields

	int _topOfPageIndex = 0;
	int _curIndex = 0;
	static final int LinesPerPage = 4;
	MethodSignature _signature;

	EventListener _listener;

	//endregion

}
