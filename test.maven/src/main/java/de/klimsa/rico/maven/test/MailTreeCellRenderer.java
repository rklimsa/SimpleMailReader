package de.klimsa.rico.maven.test;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;

public class MailTreeCellRenderer extends DefaultTreeCellRenderer{

	@Override
	public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean selected, final boolean expanded,
			final boolean leaf, final int row, final boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		this.setIcon(UIManager.getIcon("FileChooser.homeFolderIcon"));

		return this;
	}

}
