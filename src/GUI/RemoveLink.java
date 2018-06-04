package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controllers.GraphController;
import uni.Link;
import uni.Node;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class RemoveLink extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private GraphController controller = GraphController.getInstance();
	private JLabel lblFromNode;
	private JComboBox comboBoxFromNode;
	private JLabel lblToNode;
	private JComboBox comboBoxToNode;
	private JPanel buttonPane;
	private JCheckBox chckbxRemoveMoreLink;
	private JButton okButton;
	private JButton cancelButton;

	public RemoveLink(JFrame frame) {
		super(frame);
		setTitle("Remove Link");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 123);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2, 2, 0, 0));

		lblFromNode = new JLabel("From node :");
		lblFromNode.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblFromNode.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblFromNode);
		comboBoxFromNode = new JComboBox(controller.getNodeNames().toArray());
		fillComboNodeFrom();
		comboBoxFromNode.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				fillComboNodeTo();
			}
		});
		
		contentPanel.add(comboBoxFromNode);

		lblToNode = new JLabel("To node :");
		lblToNode.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblToNode.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblToNode);

		comboBoxToNode = new JComboBox();
		fillComboNodeTo();
		contentPanel.add(comboBoxToNode);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		chckbxRemoveMoreLink = new JCheckBox("Remove more link");
		chckbxRemoveMoreLink.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxRemoveMoreLink.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		buttonPane.add(chckbxRemoveMoreLink);

		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.removeLinkFromGraph(comboBoxFromNode.getSelectedItem().toString(),
							comboBoxToNode.getSelectedItem().toString());
					fillComboNodeTo();
				} catch (Exception ex) {
					MessageDialog mesageDialog = new MessageDialog(frame, "Error!\nIncorect input data.\n", false);
					mesageDialog.setVisible(true);
				}
				if (!chckbxRemoveMoreLink.isSelected()) {
					RemoveLink.this.setVisible(false);
				}
				((MapFrame) frame).getMapPanel().drawing();

			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RemoveLink.this.setVisible(false);
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	private void fillComboNodeTo() {
		Node temp = controller.getGraph().getMap().get(comboBoxFromNode.getSelectedItem().toString());
		
		comboBoxToNode.removeAllItems();
		for (Link l : temp.getLinks()) {
			comboBoxToNode.addItem(l.getToNode().getName());
		}
	}

	private void fillComboNodeFrom() {
		
    if(comboBoxFromNode.getItemCount()>0){
    	comboBoxFromNode.removeAllItems();
    }
		for (String name : controller.getNodeNames()) {
			comboBoxFromNode.addItem(name);
		}
	}

}
