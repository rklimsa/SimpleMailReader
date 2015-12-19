/**
 *
 */
package de.klimsa.rico.maven.test;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 * @author Rico Klimsa
 *
 */
public class MailTableModel extends AbstractTableModel {

	private final List< List <String>> mails = new ArrayList<>();

	public void setMails(final List< List <String>> mails) {
		this.mails.clear();
		this.mails.addAll(mails);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return this.mails.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 3;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(final int columnIndex) {
		switch(columnIndex) {
		case 0: return "Datum";
		case 1: return "Absender";
		case 2: return "Betreff";
		}
		return "";
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		return String.class;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		if(this.mails.size() > rowIndex && this.mails.get(rowIndex).size() > columnIndex) {
			return this.mails.get(rowIndex).get(columnIndex);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		if(this.mails.size()> rowIndex && columnIndex < 3) {
			this.mails.get(rowIndex).add(columnIndex, aValue.toString());
		}
		if(this.mails.size()< rowIndex && columnIndex < 3) {
			final List<String> list = new ArrayList<>();
			list.add(aValue.toString());
			this.mails.add(list);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#addTableModelListener(javax.swing.event.TableModelListener)
	 */
	@Override
	public void addTableModelListener(final TableModelListener l) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#removeTableModelListener(javax.swing.event.TableModelListener)
	 */
	@Override
	public void removeTableModelListener(final TableModelListener l) {
		// TODO Auto-generated method stub

	}

}
