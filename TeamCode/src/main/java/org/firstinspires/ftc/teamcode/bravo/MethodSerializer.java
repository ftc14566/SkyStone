package org.firstinspires.ftc.teamcode.bravo;

public class MethodSerializer {

	public MethodBinding[] deserialize(String src, MethodManager mgr){
		String[] lines = src.split("---");

		System.out.println(lines.length);

		MethodBinding[] items = new MethodBinding[lines.length];
		for(int i=0;i<lines.length;++i)
			items[i] = deserializeLine(lines[i],mgr);
		return items;
	}

	public String serialize(MethodBinding[] bindings){
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<bindings.length;++i){
			if(i!=0) builder.append("---");
			builder.append(serialize(bindings[i]));
		}
		return builder.toString();
	}


	private MethodBinding deserializeLine(String line, MethodManager mgr) {
		if(line.length() == 0) return null;
		String[] parts = line.split(":");
		MethodSignature sig = mgr.find(parts[0]);
		// !!! if sig == null, do something clever
		if(sig==null || sig.params.length != parts.length-1) {
			return null;
			//throw new IllegalStateException("[" + line + "] had " + (parts.length - 1) + " params instead of " + sig.params.length);
		}
		Object[] paramValues = new Object[sig.params.length];
		for(int i=0;i<paramValues.length;++i) {
			try {
				paramValues[i] = sig.params[i].parseRawValueString(parts[i + 1]);
			} catch(Exception ex){
				paramValues[i] = sig.params[i].getInitialValue();
			}
		}
		return new MethodBinding(sig,paramValues);
	}

	private String serialize(MethodBinding binding){
		if(binding==null) return "";
		MethodSignature sig = binding.getSignature();
		Object[] paramValues = binding.getParamValues();
		String text = sig.methodString;
		for(int i=0;i<sig.params.length;++i)
			text += ':' + sig.params[i].getRawValueString(paramValues[i]);
		return text;
	}


}
