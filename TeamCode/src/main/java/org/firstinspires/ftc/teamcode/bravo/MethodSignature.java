package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.teamcode.AutoBot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodSignature {

	public MethodSignature(String methodName, ConfigParam[] params){
		MethodName = methodName;
		_params = params;
	}

	public void Execute(AutoBot bot){
		try{
			Method method = GetMethod(AutoBot.class);

			Object[] values = new Object[_params.length+1];

			values[0]=bot;
			for(int i=0;i<_params.length;++i)
				values[i + 1] = _params[i].getValue();

			method.invoke(values);

		} catch(NoSuchMethodException ex){

		} catch(IllegalAccessException ex){

		} catch(InvocationTargetException ex){

		}

	}


	Method GetMethod(Class<?> hostClass) throws NoSuchMethodException{

		// 	public void Test(int i, double d, String s,boolean b){
		Class<?>[] paramTypes = new Class<?>[4];

		for(int i=0;i<_params.length;++i) {
			paramTypes[i] = _params[i].getValueClass();
		}

		return AutoBot.class.getMethod(MethodName, paramTypes);
	}


	public MethodSignature Clone(){
		ConfigParam[] params = new ConfigParam[_params.length];
		for(int i=0;i<_params.length;++i)
			params[i] = _params[i].Clone();
		return new MethodSignature(MethodName,params);
	}

	public String MethodName;
	ConfigParam[] _params;

}
