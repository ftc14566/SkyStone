package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class InteractiveMethodList extends InteractiveList {

	public InteractiveMethodList(CallbackListener listener){
		this.listener = listener;
	}

	public void DisplayStatus(Telemetry telemetry){
		telemetry.addData("Mode","Select Method  A:select B:cancel ");

		int end = Math.min(topOfPageIndex +LinesPerPage, items.length); // exclude this
		for(int index = topOfPageIndex; index<end; ++index){
			String text = items[index].methodString;
			if(index== curIndex) text = "["+text+"]";
			telemetry.addData(""+index, text);
		}
		telemetry.update();
	}

	public void accessClass(Class<?> c){
		this.items = MethodManager.Singleton.getAllSignatures(c);
		curIndex = 0;
	}

	// region button presses

	@Override
	public void DpadUp_Pressed(){
		if(curIndex >0) curIndex--;
	//	while(curIndex<topOfPageIndex) --topOfPageIndex;
	}

	@Override
	public void DpadDown_Pressed(){
		if(curIndex < items.length-1) curIndex++;
	//	while(topOfPageIndex<=curIndex) ++topOfPageIndex;
	}

	@Override
	public void A_Pressed(){
		if(curIndex < items.length && listener !=null){
			MethodBinding binding = new MethodBinding();
			MethodSignature sig = items[curIndex];
			binding.method = sig.method;
			binding.paramValues = sig.getInitialParamValues();
			listener.selectMethod( binding );
		}
	}

	@Override
	public void B_Pressed(){
		if(listener !=null)
			listener.cancelMethodSelection();
	}

	// endregion

	public interface CallbackListener {
		public void selectMethod(MethodBinding binding);
		public void cancelMethodSelection();
	}

	// region fields

	private MethodSignature[] items;
	private int curIndex = 0;
	private int topOfPageIndex = 0;
	private CallbackListener listener;

	final static int LinesPerPage = 4;


	// endregion

}
