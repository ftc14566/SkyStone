package org.firstinspires.ftc.teamcode.bravo;

public class ParamDouble extends Param {

	// region constructors

	public ParamDouble(Config cfg){
		super(cfg,double.class);

		// stepSize
		stepSize = cfg.step();
		if(stepSize == 0.0) stepSize = 0.1; else if(stepSize <0.0) stepSize = -stepSize;

		// min,max
		min = cfg.min();
		max = cfg.max();
		if(max<min){ double temp = max; max=min;min=temp; }
		if(min==max) max = min + stepSize * 100;

		// display scale and format
		displayScale = cfg.displayScale();
		if(displayScale == 0) displayScale=1.0;
		scaledFormat = determineFormat(stepSize *displayScale);
		rawFormat = determineFormat(stepSize);

		initialValue = clip(cfg.value());

	}

	String determineFormat(double stepSize){
		if(stepSize<=0.0) throw new IllegalStateException("stepSize should be positive");
		int places = 0;
		while(stepSize<1.0){
			stepSize *= 10;
			places++;
		}
		return  "%."+places+"f";
	}

	// endregion

	@Override
	public Object getInitialValue() {
		return initialValue;
	}

	@Override
	String getScaledValueString(Object value){ return format((double)value); }

	@Override
	String getRawValueString(Object value){ return String.format(rawFormat, (double)value); }
	@Override
	Object parseRawValueString(String s){ return Double.parseDouble(s);	}


	@Override
	protected String getRangeString(){ return format(min)+" to "+format(max) + " ("+format(stepSize)+")"; }

	private String format(double value){ return String.format(scaledFormat, value*displayScale); }

	@Override
	public Object adjust(Object src, int steps){
		if(steps==0) return src;
		return clip( (double)src + steps*this.stepSize);
	}

	double clip(double value){
		if(value<min) return min;
		if(value>max) return max;
		return value;
	}

	// region used by numerics
	private double min;
	private double max;
	private double stepSize;
	private double displayScale;
	private double initialValue;
	// endregion

	// region private fields
	private String scaledFormat;
	private String rawFormat;
	// endregion


}
