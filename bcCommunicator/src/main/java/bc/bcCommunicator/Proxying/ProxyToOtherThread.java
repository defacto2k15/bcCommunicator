package bc.bcCommunicator.Proxying;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class ProxyToOtherThread implements InvocationHandler {
	private BlockingQueue<MethodArgsPair> commandQueue = new ArrayBlockingQueue<>(30);
	private List<MethodsObjectPair> objectsToCallMap = new ArrayList<>();
	
	public ProxyToOtherThread(){
		Thread newThread = new Thread(()->{
			while(true){
				try {
					MethodArgsPair pair = commandQueue.take();
					Object target = objectsToCallMap.stream().filter( 
							p -> p.methods.contains(pair.method)).map( p -> p.concreteObject).findFirst().get();
					pair.method.invoke(target, pair.args);
				} catch (Exception e) {
					System.err.println("E751");
					e.printStackTrace();
				}
			}
		});
		newThread.setDaemon(true);
		newThread.start();	
	}

	public <T> void addObjectToProxy( Object object){
		MethodsObjectPair pair = new MethodsObjectPair(object);
		objectsToCallMap.add(pair);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if( method.getReturnType() != void.class){
			throw new IllegalArgumentException("Only void returning methods may be called by the proxy!");
		}
		commandQueue.put(new MethodArgsPair(method, args));
		return null;
	}
	

	
	
	class MethodsObjectPair{
		public List<Method> methods;
		Object concreteObject;
		
		public MethodsObjectPair(Object object ){
			methods = getAllMethods(object.getClass());
			concreteObject = object;
		}
		
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
