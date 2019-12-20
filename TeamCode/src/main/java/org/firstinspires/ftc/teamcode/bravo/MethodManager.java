package org.firstinspires.ftc.teamcode.bravo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Hashtable;

public class MethodManager {

	// region constructor

	public MethodManager(Class<?> c){
		table = new Hashtable<>();

		final Method[] methods = c.getDeclaredMethods();
		ArrayList<MethodSignature> newItems = new ArrayList<MethodSignature>();
		for(int i=0;i<methods.length;++i){
			Method method = methods[i];
			if(Modifier.isPublic(method.getModifiers())){
				MethodSignature sig = new MethodSignature(method);
				newItems.add(sig);
				table.put(sig.methodString,sig);
			}
		}

		items = newItems.toArray(new MethodSignature[0]);
	}

	// endregion

	public MethodSignature find(String stringRepresentative){
		return table.get(stringRepresentative);
	}

	public MethodSignature[] getAllSignatures(){
		return items;
	}

	private MethodSignature[] items;
	private Hashtable<String,MethodSignature> table;

}
