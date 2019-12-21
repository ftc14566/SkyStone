package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class InteractiveStepList extends InteractiveList {

	// region constructor

	public InteractiveStepList(CallbackListener listener){
		this.listener = listener;
		steps = new ArrayList<MethodBinding>();
	}

	// endregion

	public void setBindings(MethodBinding[] bindings){
		steps.clear();
		for(int index=0;index<bindings.length;++index)
			steps.add(index,bindings[index]);
		this.curStep = 0;
	}

	public MethodBinding[] getBindings(){
		return steps.toArray(new MethodBinding[0]);
	}

	public interface CallbackListener {
		void stepSelected(MethodBinding binding);
	}

	@Override
	public void DisplayStatus(Telemetry telemetry){
		telemetry.addData("Mode","Select Step  A:sel X:del Y:ins L-BMP:clear R-BPM:toggle drag");
		int end = Math.min(topOfPageStep +4, steps.size());
		for(int index = topOfPageStep; index<=end; ++index){
			String text = getStepDescription(index);
			text = decorateStepDescription(text,index);
			telemetry.addData(""+index, text);
		}
		telemetry.update();
	}

	String decorateStepDescription(String description, int index){
		if(index!=curStep) return description;
		if(dragItem) return "[[["+description+"]]]";
		return "["+description+"]";
	}

	String getStepDescription(int index){
		if(index>= steps.size()) return "- end -";
		MethodBinding item = steps.get(index);
		if(item==null) return "-empty-";
		return item.getDisplay();
	}

	public boolean bindingSelected(){
		return curStep < steps.size()
				&& steps.get(curStep) != null;
	}

	public void setCurrentSignature(MethodBinding binding){
		steps.set(curStep,binding);
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
		if(listener != null && curStep < steps.size()){
			MethodBinding binding = steps.get(curStep);
			listener.stepSelected(binding);
		}
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

	// Steps, current stepSize
	final int DisplayCount = 4;
	ArrayList<MethodBinding> steps;
	int topOfPageStep = 0;
	int curStep = 0;
	boolean dragItem;
	CallbackListener listener;

	// endregion

}
