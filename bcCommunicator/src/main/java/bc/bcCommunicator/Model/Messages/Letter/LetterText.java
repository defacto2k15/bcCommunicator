package bc.bcCommunicator.Model.Messages.Letter;

// TODO: Auto-generated Javadoc
/**
 * The Class LetterText.
 */
public class LetterText {
	
	/** The value. */
	String value;
	
	/**
	 * Instantiates a new letter text.
	 *
	 * @param value the value
	 */
	public LetterText( String value ){
		this.value = value;
	}
	
	/**
	 * Gets the text value.
	 *
	 * @return the text value
	 */
	public String getTextValue(){
		return value;
	}
	
   /* (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
    public boolean equals(Object obj) {
	   if(!( obj instanceof LetterText) ){
		   return false;
	   }
	   if( obj == this){
		   return true;
	   }
	   return ((LetterText)obj).value == this.value;
   }
}
