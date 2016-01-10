package bc.bcCommunicator.Views;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import Controller.ICommunicatorController;
import bc.bcCommunicator.WindowNames;
import bc.bcCommunicator.Model.BasicTypes.Username;

public class UsersTableView extends JPanel implements IUsersTableView{
	private final UsersTableModel tableModel = new UsersTableModel();
	private JTable usersTable = new JTable(tableModel);
	private TableColumn usernameColumn = new TableColumn();
	private List<UsersTableRow> rows = new ArrayList<>();
	private ICommunicatorController controller;
	
	public UsersTableView(ICommunicatorController controller){
		this.controller = controller;
		
		usersTable.setName(WindowNames.USERS_TABLE);
		add(usersTable);
		usernameColumn.setHeaderValue(WindowNames.USERS_TABLE_USERNAME_COLUMN);
		usersTable.addColumn(usernameColumn);
		usersTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = usersTable.rowAtPoint(evt.getPoint());
		        int col = usersTable.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		        	controller.rowInUserTableWasClicked( rows.get(row).username);
		        }
		    }
		});
		setVisible(true);
	}
	
	@Override
	public void addLineToTable(Username username, UserConnectionState state, TalkState talkState) {
		rows.add( new UsersTableRow(username, state, talkState));
		tableModel.repaint();
	}
	
	@Override
	public void clearTable() {
		rows.clear();
		tableModel.repaint();
	}
	

	@Override
	public void changeStateOfUser(Username username, UserConnectionState state) {
		getRowOfUser(username).userConnectionState = state;
		tableModel.repaint();
	}

	@Override
	public void changeStateOfUser(Username username, TalkState state) {
		getRowOfUser(username).state = state;
		tableModel.repaint();
	}
	
	private UsersTableRow getRowOfUser(Username username){
		List<UsersTableRow> onlyRow = rows.stream().filter( (row)->{return row.username.getName().equals( username.getName()); }).collect( Collectors.toList());
		if( onlyRow.size() != 1){
			throw new IllegalStateException("There should be one line with username "+username.getName()+" but is "+onlyRow.size());
		}
		return onlyRow.get(0);
	}
	
	class UsersTableModel extends AbstractTableModel{

		@Override
		public int getRowCount() {
			return rows.size();
		}

		@Override
		public int getColumnCount() {
			return 3;
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
		public TalkState state;
		
		public UsersTableRow(Username username, UserConnectionState userConnectionState, TalkState state) {
			this.username = username;
			this.userConnectionState = userConnectionState;
			this.state = state;
		}
		
		public String getNthElementInRow( int column ) throws Exception{
			if( column == 0){
				return username.getName();
			}
			if( column == 1){
				return userConnectionState.getStateDescription();
			} 
			if(column == 2){
				return state.getText();
			}
			throw new Exception("Not expected, column num must be 0 or 1");
		}
	}


	
}
