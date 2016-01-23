package bc.bcCommunicator.Proxying;

import java.lang.reflect.Method;

public class MethodArgsPair{
	public Method method;
	public Object[] args;
	
	public MethodArgsPair(Method method, Object[] args) {
		this.method = method;
		this.args = args;
	}		
}