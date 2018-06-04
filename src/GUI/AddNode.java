package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controllers.GraphController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

public class AddNode extends JDialog {
	private GraphController controller = GraphController.getInstance();
	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JTextField textField_Name;
	private JTextField textField_X_Coordinate;
	private JTextField textField_Y_Coordinate;
	private JTextField textField_Weight;
	private JLabel lbl_Name;
	private JLabel lbl_X_Coordinate;
	private JLabel lbl_Y_Coordinate;
	private JLabel lbl_Weight;
	private JButton okButton;
	private JButton cancelButton;
	private JCheckBox chckbxInsertMorNodes;

	public AddNode(JFrame frame) {
		super(frame);
		setTitle("Add node");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 360, 175);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(4, 2, 0, 0));

		lbl_Name = new JLabel("Name :");
		lbl_Name.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Name.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		contentPanel.add(lbl_Name);

		textField_Name = new JTextField();
		textField_Name.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(textField_Name);

		lbl_X_Coordinate = new JLabel("X coordinate :");
		lbl_X_Coordinate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_X_Coordinate.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		contentPanel.add(lbl_X_Coordinate);

		textField_X_Coordinate = new JTextField();
		textField_X_Coordinate.setHorizontalAlignment(SwingConstants.CENTER);
		textField_X_Coordinate.setText("0");
		contentPanel.add(textField_X_Coordinate);

		lbl_Y_Coordinate = new JLabel("Y coordinate :");
		lbl_Y_Coordinate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Y_Coordinate.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		contentPanel.add(lbl_Y_Coordinate);

		textField_Y_Coordinate = new JTextField();
		textField_Y_Coordinate.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Y_Coordinate.setText("0");
		contentPanel.add(textField_Y_Coordinate);

		lbl_Weight = new JLabel("Weigth :");
		lbl_Weight.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Weight.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		contentPanel.add(lbl_Weight);

		textField_Weight = new JTextField();
		textField_Weight.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Weight.setText("0");
		contentPanel.add(textField_Weight);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		chckbxInsertMorNodes = new JCheckBox("Insert more nodes");
		chckbxInsertMorNodes.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		chckbxInsertMorNodes.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPane.add(chckbxInsertMorNodes);

		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					controller.addNodeInGraph(textField_Name.getText(),
							Integer.parseInt(textField_X_Coordinate.getText()),
							Integer.parseInt(textField_Y_Coordinate.getText()),
							Double.parseDouble(textField_Weight.getText()));
				} catch (NumberFormatException exception) {
					MessageDialog errorDialog = new MessageDialog(frame,
							"Error!\nIncorect input data.\n" + exception.getMessage(), false);
					errorDialog.setVisible(true);
				}
				;
				if (!chckbxInsertMorNodes.isSelected()) {
					AddNode.this.setVisible(false);
				}
				((MapFrame) frame).getMapPanel().drawing();
				((MapFrame) frame).getMapPanel().changeSize();
				((MapFrame) frame).getScrollPaneMPanel().updateUI();

			}

		});

		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddNode.this.setVisible(false);
			}
		});

		buttonPane.add(cancelButton);

	}

}
