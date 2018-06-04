package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controllers.GraphController;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RemoveNode extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private GraphController controller = GraphController.getInstance();
	private JLabel lblRemoveNode;
	private JComboBox comboBoxRemoveNode;
	private JPanel buttonPane;
	private JButton okButton;
	private JButton cancelButton;
	private JCheckBox chckbxRemoveMorNodes;

	/**
	 * Create the dialog.
	 */
	public RemoveNode(JFrame frame) {
		super(frame);
		setTitle("Remove node");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 365, 102);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 2, 0, 0));

		lblRemoveNode = new JLabel("Remove Node : ");
		lblRemoveNode.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblRemoveNode.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblRemoveNode);
		comboBoxRemoveNode = new JComboBox(controller.getNodeNames().toArray());
		contentPanel.add(comboBoxRemoveNode);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.removeNodeFromGraph(comboBoxRemoveNode.getSelectedItem().toString());
				controller.getGraph().getPath().clear();;
				((MapFrame) frame).getMapPanel().drawing();
				((MapFrame) frame).getMapPanel().changeSize();
				((MapFrame) frame).getScrollPaneMPanel().updateUI();

				if (!chckbxRemoveMorNodes.isSelected()) {
					RemoveNode.this.setVisible(false);
				}

			}
		});

		chckbxRemoveMorNodes = new JCheckBox("Remove mor nodes");
		chckbxRemoveMorNodes.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		chckbxRemoveMorNodes.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPane.add(chckbxRemoveMorNodes);
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RemoveNode.this.setVisible(false);
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

	}

}
