package bc.bcCommunicator.Views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import bc.bcCommunicator.WindowNames;
import bc.bcCommunicator.Model.BasicTypes.Username;

public class UsersTableView extends JPanel implements IUsersTableView{
	private final UsersTableModel tableModel = new UsersTableModel();
	private JTable usersTable = new JTable(tableModel);
	private TableColumn usernameColumn = new TableColumn();
	private List<UsersTableRow> rows = new ArrayList<>();
	
	public UsersTableView(){
		usersTable.setName(WindowNames.USERS_TABLE);
		add(usersTable);
		usernameColumn.setHeaderValue(WindowNames.USERS_TABLE_USERNAME_COLUMN);
		usersTable.addColumn(usernameColumn);
		setVisible(true);
	}
	
	@Override
	public void addLineToTable(Username username, UserConnectionState state) {
		rows.add( new UsersTableRow(username, state));
		tableModel.repaint();
	}
	
	@Override
	public void clearTable() {
		rows.clear();
		tableModel.repaint();
	}
	
	class UsersTableModel extends AbstractTableModel{

		@Override
		public int getRowCount() {
			return rows.size();
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			try {
				return rows.get(rowIndex).getNthElementInRow(columnIndex);
			} catch (Exception e) {
				e.printStackTrace();
				return "ERROR";
			}
		}
		
		public void repaint(){
			fireTableDataChanged();
		}
		
	}
	
	class UsersTableRow{
		public Username username;
		public UserConnectionState userConnectionState;
		
		public UsersTableRow(Username username, UserConnectionState userConnectionState) {
			this.username = username;
			this.userConnectionState = userConnectionState;
		}
		
		public String getNthElementInRow( int column ) throws Exception{
			if( column == 0){
				return username.getName();
			}
			if( column == 1){
				return userConnectionState.getStateDescription();
			}
			throw new Exception("Not expected, column num must be 0 or 1");
		}
	}
	
}
