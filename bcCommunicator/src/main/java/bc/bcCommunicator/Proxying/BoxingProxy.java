package bc.bcCommunicator.Proxying;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// TODO: Auto-generated Javadoc
/**
 * The Class BoxingProxy.
 *
 * @param <T> the generic type
 */
public class BoxingProxy<T> implements InvocationHandler{
	
	/** The target. */
	T target;
	
	/**
	 * Sets the target.
	 *
	 * @param target the new target
	 */
	public void setTarget( T target ){
		this.target = target;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(target, args);
	}
}
