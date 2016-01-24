package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;

// TODO: Auto-generated Javadoc
/**
 * The Class FieldsContainer.
 */
public class FieldsContainer implements IFieldsContainer {
	
	/** The list. */
	private List<IMessageFieldValue> list = new ArrayList<IMessageFieldValue>();
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer#getFieldValue(java.lang.Class)
	 */
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

	/**
	 * Adds the field.
	 *
	 * @param field the field
	 */
	public void addField(IMessageFieldValue field) {
		list.add(field);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer#containsField(java.lang.Class)
	 */
	@Override
	public <T extends IMessageFieldValue> boolean containsField(Class<T> type) throws Exception {
		return list.stream().filter(u -> type.isInstance(u)).count() != 0;
	}

}
