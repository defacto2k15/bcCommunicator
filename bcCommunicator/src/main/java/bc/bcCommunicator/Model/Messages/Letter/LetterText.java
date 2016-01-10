package bc.bcCommunicator.Model.Messages.Letter;

public class LetterText {
	String value;
	public LetterText( String value ){
		this.value = value;
	}
	
	public String getTextValue(){
		return value;
	}
	
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
