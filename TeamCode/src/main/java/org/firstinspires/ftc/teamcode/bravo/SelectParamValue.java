package org.firstinspires.ftc.teamcode.bravo;

public class SelectParamValue extends InteractiveList {

	public SelectParamValue(EventListener listener){
		_listener = listener;
	}

	int _curIndex = 0;
	public MethodSignature _methodData;

	public void SetMethod(MethodSignature methodData){
		_methodData = methodData;
	}

	@Override
	protected void DpadUp_Pressed(){
		if(_curIndex<_methodData._params.length-1) _curIndex++;
	}

	@Override
	protected void DpadDown_Pressed(){
		if(_curIndex<_methodData._params.length-1) _curIndex++;
	}

	@Override
	protected void DpadLeft_Pressed(){
		_methodData._params[_curIndex].dec();
	}

	@Override
	protected void DpadRight_Pressed(){
		_methodData._params[_curIndex].inc();
	}

	// execute?
	// back
	// save

	public EventListener _listener;

	public interface EventListener{
		void executeMethod();
		void saveMethodConfig();
		void cancelMethodConfig();
	}

}
