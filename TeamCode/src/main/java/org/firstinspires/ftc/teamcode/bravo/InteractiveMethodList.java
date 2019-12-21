package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class InteractiveMethodList extends InteractiveList {

	public InteractiveMethodList(CallbackListener listener){
		this.listener = listener;
	}

	public void DisplayStatus(Telemetry telemetry){
		telemetry.addData("Mode","Select Method  A:select B:cancel ");

		int end = Math.min(topOfPageIndex +LinesPerPage, signatures.length); // exclude this
		for(int index = topOfPageIndex; index<end; ++index){
			String text = signatures[index].methodString;
			if(index== curIndex) text = "["+text+"]";
			telemetry.addData(""+index, text);
		}
		telemetry.update();
	}

	public void initMethods(MethodSignature[] methods){
		this.signatures = methods;
		curIndex = 0;
	}

	// region button presses

	@Override
	public void DpadUp_Pressed(){
		if(curIndex==0) return;
		curIndex--;
		if(topOfPageIndex > curIndex) topOfPageIndex = curIndex;
	}

	@Override
	public void DpadDown_Pressed(){
		int last = signatures.length-1;
		if(curIndex == last) return;
		curIndex++;
		if (topOfPageIndex < curIndex - LinesPerPage) topOfPageIndex = curIndex - LinesPerPage;
	}

	@Override
	public void A_Pressed(){
		if(curIndex < signatures.length && listener !=null){
			MethodSignature sig = signatures[curIndex];
			listener.selectMethod( sig.createInitialBinding() );
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

	private MethodSignature[] signatures;
	private int curIndex = 0;
	private int topOfPageIndex = 0;
	private CallbackListener listener;

	final static int LinesPerPage = 4;


	// endregion

}
