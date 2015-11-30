package bc.bcCommunicator.Model.BasicTypes;

public class Username {
	private String name;
	
	public Username(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	@Override public boolean equals(Object other) {
	    boolean result = false;
	    if (other instanceof Username) {
	        Username that = (Username) other;
	        result = (this.getName() == that.getName());
	    }
	    return result;
	}
}
