package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Hashtable;

public class InteractiveMethodList extends InteractiveList {

	public InteractiveMethodList(CallbackListener listener){
		this.listener = listener;
	}

	public void DisplayStatus(Telemetry telemetry){
		telemetry.addData("Mode","Select Method  A:select B:cancel ");

		int end = Math.min(_topOfPageIndex+LinesPerPage, _items.size()); // exclude this
		for(int index = _topOfPageIndex; index<end; ++index){
			String text = _items.get(index);
			if(index==_curIndex) text = "["+text+"]";
			telemetry.addData(""+index, text);
		}
		telemetry.update();
	}

	public void accessClass(Class c){
		methodLookup = new Hashtable<String,Method>();
		_items.clear();
		final Method[] methods = c.getDeclaredMethods();
		for(int i=0;i<methods.length;++i){
			Method method = methods[i];
			if(Modifier.isPublic(method.getModifiers())){
				String key = MethodSignature.formatMethodSignature(method);
				_items.add( key );
				methodLookup.put(key,method);
			}
		}
	}

	public String getSelectedKey(){ return _items.get(_curIndex); }

	public Method getCurrent(){ return find( getSelectedKey() ); }

	public Method find(String key){ return methodLookup.get( key ); }

	// region button presses

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
			if(listener !=null)
				listener.selectMethod();
	}

	@Override
	public void B_Pressed(){
		if(listener !=null)
			listener.cancelMethodSelection();
	}

	// endregion

	CallbackListener listener;

	public interface CallbackListener {
		public void selectMethod();
		public void cancelMethodSelection();
	}

	// region fields

	Hashtable<String,Method> methodLookup;
	public ArrayList<String> _items = new ArrayList<String>();
	int _curIndex = 0;
	int _topOfPageIndex = 0;
	final static int LinesPerPage = 4;

	// endregion

}
