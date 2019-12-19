package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class InteractiveStepList extends InteractiveList {

	public InteractiveStepList(CallbackListener listener){
		this.listener = listener;
		steps = new ArrayList<SavedMethodCall>();
		steps.add(0,null); // start with 1 empty slot
	}

	public interface CallbackListener {
		void stepSelected();
	}

	@Override
	public void DisplayStatus(Telemetry telemetry){
		telemetry.addData("Mode","Select Step  A:sel X:del Y:ins L-BMP:clear R-BPM:toggle drag");
		int end = Math.min(topOfPageStep +4, steps.size());
		for(int i = topOfPageStep; i<=end; ++i){
			String text = getStepDescription(i);
			if(i== curStep){
				if(dragItem)
					text = "[[["+text+"]]]";
				else
					text = "["+text+"]";
			}
			telemetry.addData(""+i, text);
		}
		telemetry.update();
	}

	String getStepDescription(int index){
		if(index>= steps.size()) return "- end -";
		SavedMethodCall sig = steps.get(index);
		if(sig==null) return "-empty-";
		return sig.display;

	}

	public SavedMethodCall getCurrentSignature(){
		if(curStep >= steps.size() )
			return null; // this should never happen, nothing should ask for current step when we are on the end.
		return steps.get(curStep);
	}


	public void setCurrentSignature(SavedMethodCall sig){
		steps.set(curStep,sig);
	}

	// region button handlers

	@Override
	public void DpadUp_Pressed(){

		if(curStep ==0) return;
		if(dragItem && curStep < steps.size())
			steps.add(curStep -1, steps.remove(curStep));
		curStep--;
		if(topOfPageStep > curStep) topOfPageStep = curStep;
	}

	@Override
	public void DpadDown_Pressed() {
		if (curStep == steps.size()) return;
		if( dragItem && curStep == steps.size()-1) return;
		curStep++;
		if(dragItem && curStep < steps.size())
			steps.add(curStep, steps.remove(curStep -1));
		if (topOfPageStep < curStep - DisplayCount) topOfPageStep = curStep - DisplayCount;
	}

	@Override
	public void Y_Pressed() { // insert slot
		if(curStep <= steps.size())
			steps.add(curStep,null);
	}

	@Override
	public void X_Pressed() { // delete slot
		if(curStep < steps.size())
			steps.remove(curStep);
	}

	@Override
	public void A_Pressed() { // select
		if(listener != null && curStep < steps.size()) listener.stepSelected();
	}

	@Override
	public void LeftBumper_Pressed(){
		if(curStep < steps.size())
			steps.set(curStep,null);
	}

	@Override
	public void RightBumper_Pressed(){
		// prevent from them getting into drag mode when on end.
		if(curStep < steps.size())
			dragItem = !dragItem;
	}

	// endregion

	// region private fields

	// Steps, current step
	final int DisplayCount = 4;
	ArrayList<SavedMethodCall> steps;
	int topOfPageStep = 0;
	int curStep = 0;
	boolean dragItem;
	CallbackListener listener;

	// endregion


}
