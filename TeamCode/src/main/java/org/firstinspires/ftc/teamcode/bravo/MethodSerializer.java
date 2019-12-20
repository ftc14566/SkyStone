package org.firstinspires.ftc.teamcode.bravo;

public class MethodSerializer {

	public MethodBinding[] deserialize(String src, MethodManager mgr){
		String[] lines = src.split("\\r?\\n");
		MethodBinding[] items = new MethodBinding[lines.length];
		for(int i=0;i<lines.length;++i)
			items[i] = deserializeLine(lines[i],mgr);
		return items;
	}

	private MethodBinding deserializeLine(String line, MethodManager mgr) {
		if(line.length() == 0) return null;
		String[] parts = line.split(":");
		MethodSignature sig = mgr.find(parts[0]);
		// !!! if sig == null, do something clever
		if(parts.length != sig.params.length+1) throw new IllegalStateException("wrong number of params");
		Object[] paramValues = new Object[sig.params.length];
		for(int i=0;i<paramValues.length;++i)
			paramValues[i] = sig.params[i].parseRawValueString(parts[i+1]);

		return new MethodBinding(sig,paramValues);
	}

	public String serialize(MethodBinding[] bindings){
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<bindings.length;++i){
			if(i!=0) builder.append("\r\n");
			builder.append(serialize(bindings[i]));
		}
		return builder.toString();
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
