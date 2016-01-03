package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import java.net.MalformedURLException;
import java.net.URL;

import bc.bcCommunicator.Model.Messages.MessageField;

public class UrlMessageFieldValue implements IMessageFieldValue{
	private URL url;
	
	@Override
	public MessageField getCorrespondingField() {
		return MessageField.CLIENT_URL_FIELD;
	}

	@Override
	public IMessageFieldValue createFromString(String text) throws Exception {
		UrlMessageFieldValue fieldValue = new UrlMessageFieldValue();
		fieldValue.url = new URL(text);
		return fieldValue;
	}

	public URL getUrl() {
		return url;
	}

}
