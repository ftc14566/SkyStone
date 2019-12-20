package org.firstinspires.ftc.teamcode.bravo;

public class Repository {

	public MethodBinding[] deserialize(String src, Class<?> c){
		String[] lines = src.split("\\r?\\n");
		MethodBinding[] items = new MethodBinding[lines.length];
		for(int i=0;i<lines.length;++i)
			items[i] = deserializeLine(lines[i],c);
		return items;
	}

	private MethodBinding deserializeLine(String line, Class<?> c) {
		if(line.length() == 0) return null;
		String[] parts = line.split(":");
		MethodSignature sig = MethodManager.Singleton.find(parts[0],c);
		// !!! if sig == null, do something clever
		if(parts.length != sig.params.length+1) throw new IllegalStateException("wrong number of params");
		Object[] paramValues = new Object[sig.params.length];
		for(int i=0;i<paramValues.length;++i)
			paramValues[i] = sig.params[i].parseRawValueString(parts[i+1]);
		MethodBinding binding = new MethodBinding();
		binding.paramValues = paramValues;
		binding.method = sig.method;
		return binding;
	}

	public String serialize(MethodBinding[] bindings){
		return null;
	}

}
