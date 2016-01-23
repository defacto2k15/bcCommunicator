package bc.bcCommunicator.Proxying;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BoxingProxy<T> implements InvocationHandler{
	T target;
	
	public void setTarget( T target ){
		this.target = target;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(target, args);
	}
}
