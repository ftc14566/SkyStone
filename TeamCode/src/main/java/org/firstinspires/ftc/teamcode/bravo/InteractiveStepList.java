package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class InteractiveStepList extends InteractiveList {

	public InteractiveStepList(CallbackListener listener){
		this.listener = listener;
		steps = new ArrayList<SigAndBinding>();
		steps.add(0,null); // start with 1 empty slot
	}

	public interface CallbackListener {
		void stepSelected(MethodBinding binding);
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
		SigAndBinding item = steps.get(index);
		if(item==null) return "-empty-";
		return item.display;

	}

	public boolean bindingSelected(){
		return curStep < steps.size()
				&& steps.get(curStep) != null;
	}

	// LOAD!!! / SAVE!!!

	public void setCurrentSignature(MethodBinding binding){

		MethodSignature sig = MethodManager.Singleton.find(binding.method);

		SigAndBinding item = new SigAndBinding();
		item.binding = binding;
		item.signature = sig; // do we need to save this for anything?
		item.display = sig.getParamValueSummary(binding.paramValues);
		item.serializeString = sig.methodString;
		for(int i=0;i<binding.paramValues.length;++i){
			item.serializeString += "\t" + sig.params[i].getRawValueString(binding.paramValues[i]);
		}


		steps.set(curStep,item);
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
			SigAndBinding item = steps.get(curStep);
			MethodBinding binding = null;
			if(item != null)
				binding = item.binding;
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

	// Steps, current step
	final int DisplayCount = 4;
	ArrayList<SigAndBinding> steps;
	int topOfPageStep = 0;
	int curStep = 0;
	boolean dragItem;
	CallbackListener listener;

	// endregion

	class SigAndBinding{
		public MethodBinding binding;
		public MethodSignature signature;
		public String display;
		public String serializeString;
	}


}
