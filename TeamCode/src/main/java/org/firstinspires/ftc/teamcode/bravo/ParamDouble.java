package org.firstinspires.ftc.teamcode.bravo;

public class ParamDouble extends Param {

	// region constructors

	public ParamDouble(Config cfg){
		super(cfg);

		// fix / assign default
		if(label==null || label.isEmpty()) label = "double";
		if(units==null) units="";
		if(step == 0.0) step = 0.1; else if(step<0.0) step = -step;
		if(max<min){ double temp = max; max=min;min=temp; }
		if(min==max) max = min + step * 100;
		if(value < min) value = min;
		if(value > max) value = max;
		if(displayScale == 0) displayScale=1.0;

		_format = determineFormat();
	}

	ParamDouble(ParamDouble src) {
		super(src);
		_format = src._format;
	}

	String determineFormat(){
		if(step<=0.0) throw new IllegalStateException("step should be positive");
		int places = 0;
		double step = this.step*displayScale;
		while(step<1.0){
			step *= 10;
			places++;
		}
		return  "%."+places+"f";
	}

	// endregion

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public String getValueString() {
		return String.format(_format, value * displayScale) + units;
	}

	@Override
	public String getRangeString(){
		String low = String.format(_format, min*displayScale);
		String high = String.format(_format, max*displayScale);
		return low+" to "+high;
	}

	@Override
	public void inc() {
		value = Math.min(value +step,max);
	}

	@Override
	public void dec() {
		value = Math.max(value -step,min);
	}

	@Override
	public Param Clone() { return new ParamDouble(this); }

	// region private fields
	String _format;
	// endregion


}
