package bc.internetMessageProxy;

public class ConnectionId {
	private int id;
	public ConnectionId( int id ){
		this.id = id;
	}
	public int getId(){
		return id;
	}
	
	public String toString(){
		return "ConnectionId with id:"+id;
	}
}