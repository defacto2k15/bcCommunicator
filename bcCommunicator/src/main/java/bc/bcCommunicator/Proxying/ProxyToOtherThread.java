package bc.bcCommunicator.Proxying;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


// TODO: Auto-generated Javadoc
/**
 * The Class ProxyToOtherThread.
 */
public abstract class ProxyToOtherThread implements InvocationHandler {
	
	/** The command queue. */
	protected BlockingQueue<MethodArgsPair> commandQueue = new ArrayBlockingQueue<>(30);
	
	/** The objects to call map. */
	protected List<MethodsObjectPair> objectsToCallMap = new ArrayList<>();
	
	/**
	 * Instantiates a new proxy to other thread.
	 */
	public ProxyToOtherThread(){
		threadingMethod();
	}
	
	/**
	 * Threading method.
	 */
	abstract void threadingMethod();

	/**
	 * Adds the object to proxy.
	 *
	 * @param <T> the generic type
	 * @param object the object
	 */
	public <T> void addObjectToProxy( Object object){
		MethodsObjectPair pair = new MethodsObjectPair(object);
		objectsToCallMap.add(pair);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if( method.getReturnType() != void.class){
			throw new IllegalArgumentException("Only void returning methods may be called by the proxy!");
		}
		commandQueue.put(new MethodArgsPair(method, args));
		return null;
	}
	

	
	
	/**
	 * The Class MethodsObjectPair.
	 */
	class MethodsObjectPair{
		
		/** The methods. */
		public List<Method> methods;
		
		/** The concrete object. */
		Object concreteObject;
		
		/**
		 * Instantiates a new methods object pair.
		 *
		 * @param object the object
		 */
		public MethodsObjectPair(Object object ){
			methods = getAllMethods(object.getClass());
			concreteObject = object;
		}
		
		/**
		 * Gets the all methods.
		 *
		 * @param cls the cls
		 * @return the all methods
		 */
		private List<Method> getAllMethods(Class<?> cls){
			List<Method> methods = new ArrayList<>();
			methods.addAll( Arrays.asList(cls.getMethods()));
			for( Class<?> otherClass : cls.getDeclaredClasses()){
				methods.addAll( getAllMethods(otherClass));
			}
			for( Class<?> otherClass : cls.getInterfaces()){
				methods.addAll( getAllMethods(otherClass));
			}
			return methods;
		}
	}
}
