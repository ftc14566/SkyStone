package org.firstinspires.ftc.teamcode.bravo;

import java.util.ArrayList;

public class SelectMethod extends InteractiveList {

	public SelectMethod(MethodListListener listener){
		_listener = listener;
	}

	public ArrayList<MethodSignature> _items = new ArrayList<MethodSignature>();
	int _curIndex = 0;

	public MethodSignature getCurrent(){ return _items.get(_curIndex);}

	@Override
	protected void DpadUp_Pressed(){
		if(_curIndex>0) _curIndex--;
	}

	@Override
	protected void DpadDown_Pressed(){
		if(_curIndex<_items.size()-1) _curIndex++;
	}

	@Override
	protected void A_Pressed(){
		if(_curIndex < _items.size() )
			if(_listener!=null)
				_listener.selectMethod();
	}

	@Override
	protected void B_Pressed(){
		if(_listener!=null) _listener.cancelMethodSelection();
	}

	MethodListListener _listener;

	public interface MethodListListener {
		public void selectMethod();
		public void cancelMethodSelection();
	}

}
