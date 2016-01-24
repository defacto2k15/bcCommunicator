package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import java.net.URL;

import bc.bcCommunicator.Model.Messages.MessageField;

// TODO: Auto-generated Javadoc
/**
 * The Class UrlMessageFieldValue.
 */
public class UrlMessageFieldValue implements IMessageFieldValue{
	
	/** The url. */
	private URL url;
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#getCorrespondingField()
	 */
	@Override
	public MessageField getCorrespondingField() {
		return MessageField.CLIENT_URL_FIELD;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#createFromString(java.lang.String)
	 */
	@Override
	public IMessageFieldValue createFromString(String text) throws Exception {
		UrlMessageFieldValue fieldValue = new UrlMessageFieldValue();
		fieldValue.url = new URL(text);
		return fieldValue;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

}
