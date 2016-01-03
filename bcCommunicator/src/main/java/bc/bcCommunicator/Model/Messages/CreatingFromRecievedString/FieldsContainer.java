package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;

public class FieldsContainer implements IFieldsContainer {
	private List<IMessageFieldValue> list = new ArrayList<IMessageFieldValue>();
	
	@Override
	public <T extends IMessageFieldValue> T getFieldValue(Class<T> type) throws Exception {
		List<IMessageFieldValue> selectedObjects = list.stream().filter(u -> type.isInstance(u)).collect(Collectors.toList());
		if( selectedObjects.size() > 1){
			throw new Exception("There is more that one element of type "+type.getName());
		} else if ( selectedObjects.size() == 0){
			throw new Exception("There is no elements of type "+type.getName());
		}
		return (T) selectedObjects.get(0);
	}

	public void addField(IMessageFieldValue field) {
		list.add(field);
	}

	@Override
	public <T extends IMessageFieldValue> boolean containsField(Class<T> type) throws Exception {
		return list.stream().filter(u -> type.isInstance(u)).count() != 0;
	}

}
