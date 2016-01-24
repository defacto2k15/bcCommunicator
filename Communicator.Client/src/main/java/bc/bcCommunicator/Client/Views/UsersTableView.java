package bc.bcCommunicator.Client.Views;

import java.awt.Dimension;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import bc.bcCommunicator.Client.WindowNames;
import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersTableView.
 */
public class UsersTableView extends JPanel implements IUsersTableView{
	
	/** The table model. */
	private final UsersTableModel tableModel = new UsersTableModel();
	
	/** The users table. */
	private JTable usersTable = new JTable(tableModel);
	
	/** The rows. */
	private List<UsersTableRow> rows = new ArrayList<>();
	
	/** The controller. */
	private ICommunicatorController controller;
	
	/**
	 * Instantiates a new users table view.
	 *
	 * @param controller the controller
	 */
	public UsersTableView(ICommunicatorController controller){
		this.controller = controller;
		
		usersTable.setName(WindowNames.USERS_TABLE);
		add(usersTable);
		ComponentHelp.setAllThreeSizes(this, new Dimension(300, 500));
		ComponentHelp.setAllThreeSizes(usersTable, new Dimension(300, 500));
		
		
		usersTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = usersTable.rowAtPoint(evt.getPoint());
		        int col = usersTable.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		        	try {
						controller.rowInUserTableWasClicked( rows.get(row).username);
					} catch (ParseException e) {
						e.printStackTrace();
					}
		        }
		    }
		});
		setVisible(true);
		
		usersTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		usersTable.getColumnModel().getColumn(1).setPreferredWidth(140);
		usersTable.getColumnModel().getColumn(2).setPreferredWidth(60);
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.IUsersTableView#addLineToTable(bc.bcCommunicator.Model.BasicTypes.Username, bc.bcCommunicator.Client.Views.UserConnectionState, bc.bcCommunicator.Client.Views.TalkState)
	 */
	@Override
	public void addLineToTable(Username username, UserConnectionState state, TalkState talkState) {
		rows.add( new UsersTableRow(username, state, talkState));
		tableModel.repaint();
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.IUsersTableView#clearTable()
	 */
	@Override
	public void clearTable() {
		rows.clear();
		tableModel.repaint();
	}
	

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.IUsersTableView#changeStateOfUser(bc.bcCommunicator.Model.BasicTypes.Username, bc.bcCommunicator.Client.Views.UserConnectionState)
	 */
	@Override
	public void changeStateOfUser(Username username, UserConnectionState state) {
		getRowOfUser(username).userConnectionState = state;
		tableModel.repaint();
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.IUsersTableView#changeStateOfUser(bc.bcCommunicator.Model.BasicTypes.Username, bc.bcCommunicator.Client.Views.TalkState)
	 */
	@Override
	public void changeStateOfUser(Username username, TalkState state) {
		getRowOfUser(username).state = state;
		tableModel.repaint();
	}
	
	/**
	 * Gets the row of user.
	 *
	 * @param username the username
	 * @return the row of user
	 */
	private UsersTableRow getRowOfUser(Username username){
		List<UsersTableRow> onlyRow = rows.stream().filter( (row)->{return row.username.getName().equals( username.getName()); }).collect( Collectors.toList());
		if( onlyRow.size() != 1){
			throw new IllegalStateException("There should be one line with username "+username.getName()+" but is "+onlyRow.size());
		}
		return onlyRow.get(0);
	}
	
	/**
	 * The Class UsersTableModel.
	 */
	class UsersTableModel extends AbstractTableModel{

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return rows.size();
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 3;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			try {
				return rows.get(rowIndex).getNthElementInRow(columnIndex);
			} catch (Exception e) {
				e.printStackTrace();
				return "ERROR";
			}
		}
		
		/** The column names. */
		String[] columnNames={ "Username", "Connection", "Talk" };
		
		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int index) {
			System.out.println("M765 ");
		    return columnNames[index];
		}
		
		/**
		 * Repaint.
		 */
		public void repaint(){
			fireTableDataChanged();
		}
		
	}
	
	/**
	 * The Class UsersTableRow.
	 */
	class UsersTableRow{
		
		/** The username. */
		public Username username;
		
		/** The user connection state. */
		public UserConnectionState userConnectionState;
		
		/** The state. */
		public TalkState state;
		
		/**
		 * Instantiates a new users table row.
		 *
		 * @param username the username
		 * @param userConnectionState the user connection state
		 * @param state the state
		 */
		public UsersTableRow(Username username, UserConnectionState userConnectionState, TalkState state) {
			this.username = username;
			this.userConnectionState = userConnectionState;
			this.state = state;
		}
		
		/**
		 * Gets the nth element in row.
		 *
		 * @param column the column
		 * @return the nth element in row
		 * @throws Exception the exception
		 */
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
