package org.firstinspires.ftc.teamcode.bravo;

public class ParamDouble extends Param {

	// region constructors

	public ParamDouble(Config cfg){
		super(cfg,double.class);

		// step
		step = cfg.step();
		if(step == 0.0) step = 0.1; else if(step<0.0) step = -step;

		// min,max
		min = cfg.min();
		max = cfg.max();
		if(max<min){ double temp = max; max=min;min=temp; }
		if(min==max) max = min + step * 100;

		// display scale and format
		displayScale = cfg.displayScale();
		if(displayScale == 0) displayScale=1.0;
		_format = determineFormat();

		initialValue = clip(cfg.value());

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
	public Object getInitialValue() {
		return initialValue;
	}

	@Override
	String getValueString(Object value){ return format((double)value); }

	@Override
	protected String getRangeString(){ return format(min)+" to "+format(max); }

	private String format(double value){ return String.format(_format, value*displayScale); }

	@Override
	public Object adjust(Object src, int steps){
		if(steps==0) return src;
		return clip( (double)src + steps*this.step );
	}

	double clip(double value){
		if(value<min) return min;
		if(value>max) return max;
		return value;
	}

	// region used by numerics
	private double min;
	private double max;
	private double step;
	private double displayScale;
	private double initialValue;
	// endregion

	// region private fields
	private String _format;
	// endregion


}
