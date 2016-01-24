package bc.bcCommunicator.Proxying;

import java.lang.reflect.Method;

// TODO: Auto-generated Javadoc
/**
 * The Class MethodArgsPair.
 */
public class MethodArgsPair{
	
	/** The method. */
	public Method method;
	
	/** The args. */
	public Object[] args;
	
	/**
	 * Instantiates a new method args pair.
	 *
	 * @param method the method
	 * @param args the args
	 */
	public MethodArgsPair(Method method, Object[] args) {
		this.method = method;
		this.args = args;
	}		
}