package bc.bcCommunicator.Model.BasicTypes;

// TODO: Auto-generated Javadoc
/**
 * The Class Username, encapsulates the username string with equality methods.
 */
public class Username implements Comparable<Username>{
	
	/** The name. */
	private String name;
	
	/**
	 * Instantiates a new username.
	 *
	 * @param name the username as String
	 */
	public Username(String name){
		this.name = name;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name as string
	 */
	public String getName(){
		return name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override public boolean equals(Object other) {
	    boolean result = false;
	    if (other instanceof Username) {
	        Username that = (Username) other;
	        
	        result = (this.getName().equals(that.getName()));
	    }
	    return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Username o) {
		return o.name.compareTo(this.name);
	}
	

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    	return name.hashCode();
    }

}
