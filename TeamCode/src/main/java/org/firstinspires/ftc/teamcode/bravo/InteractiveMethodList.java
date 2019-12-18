package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class InteractiveMethodList extends InteractiveList {

	public InteractiveMethodList(CallbackListener listener){
		_listener = listener;
	}

	public void DisplayStatus(Telemetry telemetry){
		telemetry.addData("Mode","Select Method  A:select B:cancel ");

		int end = Math.min(_topOfPageIndex+LinesPerPage, _items.size()); // exclude this
		for(int i = _topOfPageIndex; i<end; ++i){
			String text = getMethodDisplayText(i);
			if(i==_curIndex) text = "["+text+"]";
			telemetry.addData(""+i, text);
		}
		telemetry.update();
	}

	public void accessClass(Class c){
		_items.clear();
		final Method[] methods = c.getDeclaredMethods();
		for(int i=0;i<methods.length;++i){
			Method method = methods[i];
			if(Modifier.isPublic(method.getModifiers())){
				MethodSignature sig = new MethodSignature(method);
				_items.add( sig );
			}
		}
	}


	String getMethodDisplayText(int index){
		return _items.get(index).getName();
	}


	public ArrayList<MethodSignature> _items = new ArrayList<MethodSignature>();
	int _curIndex = 0;
	int _topOfPageIndex = 0;
	final static int LinesPerPage = 4;

	public MethodSignature getCurrent(){ return _items.get(_curIndex);}

	@Override
	public void DpadUp_Pressed(){
		if(_curIndex>0) _curIndex--;
	//	while(_curIndex<_topOfPageIndex) --_topOfPageIndex;
	}

	@Override
	public void DpadDown_Pressed(){
		if(_curIndex<_items.size()-1) _curIndex++;
	//	while(_topOfPageIndex<=_curIndex) ++_topOfPageIndex;
	}

	@Override
	public void A_Pressed(){
		if(_curIndex < _items.size() )
			if(_listener!=null)
				_listener.selectMethod();
	}

	@Override
	public void B_Pressed(){
		if(_listener!=null)
			_listener.cancelMethodSelection();
	}

	CallbackListener _listener;

	public interface CallbackListener {
		public void selectMethod();
		public void cancelMethodSelection();
	}

}
