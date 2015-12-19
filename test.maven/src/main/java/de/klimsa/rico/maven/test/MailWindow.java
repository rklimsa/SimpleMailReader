/**
 *
 */
package de.klimsa.rico.maven.test;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MenuBar;
import java.awt.TextArea;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Properties;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * @author Rico Klimsa
 *
 */
public class MailWindow implements IMailWindow {

	private final static String TITLE_PREFIX = "SimpleMailReader - ";

	private final JFrame mainFrame = new JFrame();

	private final JPanel optionPanel = new JPanel(true);
	private final JCheckBox selectAllBox = new JCheckBox();

	private final JPanel communicationPanel = new JPanel(true);
	private final TextArea communicationArea = new TextArea();

	private final JPanel tablePanel = new JPanel(true);
	private final MailTableModel mailTableModel = new MailTableModel();
	private final JTable jTable = new JTable(this.mailTableModel);

	private final MenuBar menu = new MenuBar();

	private final JTree jTree = new JTree();

	private final JSplitPane jSplitPane = new JSplitPane();

	private final JPanel contentPane = new JPanel(true);

	private final Callback callback;

	private String actualProvider;

	private String lastSelected = "INBOX";

	public MailWindow(final Callback callback, final String actualProvider) {
		this.actualProvider = actualProvider;
		this.updateTitle();
		((DefaultTreeModel)this.jTree.getModel()).setRoot(new DefaultMutableTreeNode(actualProvider, true));
		this.callback = callback;
	}

	@Override
	public void setActualProvider(final String actualProvider) {
		this.actualProvider = actualProvider;
		this.updateTitle();
	}

	private void updateTitle() {
		this.mainFrame.setTitle(TITLE_PREFIX + this.actualProvider);
	}

	@Override
	public void updateMails(final List< List <String>> mails) {
		this.mailTableModel.setMails(mails);
		this.mailTableModel.fireTableDataChanged();
	}

	@Override
	public void init(final Properties properties) {
		// TODO set column names

		this.mainFrame.setPreferredSize(new Dimension(800, 600));
		this.mainFrame.setResizable(false);

		this.communicationPanel.add(this.communicationArea);
		this.jTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(final MouseEvent e) {
				MailWindow.this.callback.openMail(MailWindow.this.jTable.getSelectedRow(), MailWindow.this.getSelectedFolder());

			}

			@Override
			public void mousePressed(final MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(final MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(final MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(final MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		this.tablePanel.add(new JScrollPane(this.jTable));

		this.contentPane.setLayout(new GridBagLayout());

		this.jTree.setCellRenderer(new MailTreeCellRenderer());
		this.jTree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(final TreeSelectionEvent e) {
				MailWindow.this.callback.getMailsFromFolder(MailWindow.this.getSelectedFolder());
			}
		});
		this.jSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		this.jSplitPane.setTopComponent(this.jTree);
		this.jSplitPane.setBottomComponent(this.contentPane);


		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;

		this.contentPane.add(this.optionPanel,gridBagConstraints);

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;

		this.contentPane.add(this.tablePanel,gridBagConstraints);

		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setMenuBar(this.menu);
		this.mainFrame.setContentPane(this.jSplitPane);
		this.mainFrame.pack();
		this.mainFrame.setVisible(true);

	}

	@Override
	public void upateFolders(final List<String> folders) {
		for(final String string : folders) {
			final DefaultMutableTreeNode node = new DefaultMutableTreeNode();
			node.setUserObject(string);
			node.setAllowsChildren(true);
			this.addNode(node);
		}
	}

	private void addNode(final DefaultMutableTreeNode defaultMutableTreeNode) {
		final DefaultMutableTreeNode root = (DefaultMutableTreeNode) ((DefaultTreeModel) this.jTree.getModel()).getRoot();
		if(root!=null) {
			this.addNode(defaultMutableTreeNode, root, root.getChildCount());
		} else {
			((DefaultTreeModel)this.jTree.getModel()).setRoot(defaultMutableTreeNode);
		}
	}

	private void addNode(final DefaultMutableTreeNode defaultMutableTreeNode, final DefaultMutableTreeNode parent) {
		if(defaultMutableTreeNode == null) {
			throw new IllegalArgumentException("Node to insert is null.");
		} else if(parent == null) {
			throw new IllegalArgumentException("Parent node is null.");
		}
		this.addNode(defaultMutableTreeNode, parent, parent.getChildCount());
	}

	private void addNode(final DefaultMutableTreeNode defaultMutableTreeNode, final DefaultMutableTreeNode parent, final int index) {
		if(index>parent.getChildCount()) {
			throw new IllegalArgumentException("index exceeds parent childcount");
		}
		((DefaultTreeModel) this.jTree.getModel()).insertNodeInto(defaultMutableTreeNode, parent, index);
	}

	@Override
	public void displayMailContent(final String content) {
		JOptionPane.showMessageDialog(null, content);
	}

	private String getSelectedFolder() {
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.jTree.getLastSelectedPathComponent();
		if(node == null) {
			return this.lastSelected;
		}
		this.lastSelected = (String) node.getUserObject();
		return this.lastSelected;
	}

}
