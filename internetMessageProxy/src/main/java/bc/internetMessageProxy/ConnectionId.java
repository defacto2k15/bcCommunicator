package bc.internetMessageProxy;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionId.
 */
public class ConnectionId {
	
	/** The id. */
	private int id;
	
	/**
	 * Instantiates a new connection id.
	 *
	 * @param id the id
	 */
	public ConnectionId( int id ){
		this.id = id;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId(){
		return id;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "ConnectionId with id:"+id;
	}
}