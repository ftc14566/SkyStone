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
		this.curIndex = 0;
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
		int end = Math.min(topOfPageIndex +4, steps.size());
		for(int index = topOfPageIndex; index<=end; ++index){
			String text = getStepDescription(index);
			text = decorateStepDescription(text,index);
			telemetry.addData(""+index, text);
		}
		telemetry.update();
	}

	String decorateStepDescription(String description, int index){
		if(index!= curIndex) return description;
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
		return curIndex < steps.size()
				&& steps.get(curIndex) != null;
	}

	public void setCurrentSignature(MethodBinding binding){
		steps.set(curIndex,binding);
	}

	// region button handlers

	@Override
	protected void indexChanged(int oldIndex){
		if(!dragItem) return;
		if(curIndex<oldIndex) swap(curIndex,oldIndex);
		else swap(oldIndex,curIndex);
	}
	void swap(int lower,int higher){ steps.add(lower, steps.remove(higher)); }

	@Override
	protected int getLastIndex(){
		return steps.size() - (dragItem ? 1 : 0);
	}


	@Override
	public void Y_Pressed() { // insert slot
		if(curIndex <= steps.size())
			steps.add(curIndex,null);
	}

	@Override
	public void X_Pressed() { // delete slot
		if(curIndex < steps.size())
			steps.remove(curIndex);
	}

	@Override
	public void A_Pressed() { // select
		if(listener != null && curIndex < steps.size()){
			MethodBinding binding = steps.get(curIndex);
			listener.stepSelected(binding);
		}
	}

	@Override
	public void LeftBumper_Pressed(){
		if(curIndex < steps.size())
			steps.set(curIndex,null);
	}

	@Override
	public void RightBumper_Pressed(){
		// prevent from them getting into drag mode when on end.
		if(curIndex < steps.size())
			dragItem = !dragItem;
	}

	// endregion

	// region private fields

	// Steps, current stepSize
	ArrayList<MethodBinding> steps;
	boolean dragItem;
	CallbackListener listener;

	// endregion

}
