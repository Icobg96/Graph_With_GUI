package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controllers.GraphController;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddLink extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private GraphController controller = GraphController.getInstance();
	private JPanel buttonPane;
	private JButton okButton;
	private JButton cancelButton;
	private JLabel lbl_From_Node;
	private JLabel lbl_To_Node;
	private JLabel lbl_Route_Lenght;
	private JLabel lbl_Route_Type;
	private JTextField textField_Route_Lenght;
	private JCheckBox chckbox_Is_To_Way;
	private JTextField textField_Route_Type;
	private JComboBox comboBox_From_Node;
	private JComboBox comboBox_To_Node;
	private JCheckBox chckbxMakingMenyRoute;

	public AddLink(JFrame frame) {
		super(frame);
		setTitle("Add link");
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(5, 2, 0, 0));

		lbl_From_Node = new JLabel("From node :");
		lbl_From_Node.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lbl_From_Node.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lbl_From_Node);

		comboBox_From_Node = new JComboBox(controller.getNodeNames().toArray());
		contentPanel.add(comboBox_From_Node);

		lbl_To_Node = new JLabel("To node :");
		lbl_To_Node.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lbl_To_Node.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lbl_To_Node);

		comboBox_To_Node = new JComboBox(controller.getNodeNames().toArray());

		contentPanel.add(comboBox_To_Node);

		lbl_Route_Lenght = new JLabel("Route length :");
		lbl_Route_Lenght.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lbl_Route_Lenght.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lbl_Route_Lenght);

		textField_Route_Lenght = new JTextField();
		textField_Route_Lenght.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Route_Lenght.setText("0");
		contentPanel.add(textField_Route_Lenght);
		textField_Route_Lenght.setColumns(10);

		lbl_Route_Type = new JLabel("Route type :");
		lbl_Route_Type.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lbl_Route_Type.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lbl_Route_Type);

		textField_Route_Type = new JTextField();
		textField_Route_Type.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Route_Type.setText("NORMAL");
		contentPanel.add(textField_Route_Type);
		textField_Route_Type.setColumns(10);

		chckbox_Is_To_Way = new JCheckBox("Two way route");
		chckbox_Is_To_Way.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		chckbox_Is_To_Way.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(chckbox_Is_To_Way);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					controller.addRouteInGraph(comboBox_From_Node.getSelectedItem().toString(),
							comboBox_To_Node.getSelectedItem().toString(),
							Double.parseDouble(textField_Route_Lenght.getText()), textField_Route_Type.getText(),
							chckbox_Is_To_Way.isSelected());
				} catch (NumberFormatException exception) {
					MessageDialog errorDialog = new MessageDialog(frame,
							"Error!\nIncorect input data.\n" + exception.getMessage(), false);
					errorDialog.setVisible(true);
				}
				if (!chckbxMakingMenyRoute.isSelected()) {
					AddLink.this.setVisible(false);
				}
				((MapFrame) frame).getMapPanel().drawing();
			}
		});

		chckbxMakingMenyRoute = new JCheckBox("Making many routes");
		chckbxMakingMenyRoute.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		buttonPane.add(chckbxMakingMenyRoute);
		buttonPane.add(okButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddLink.this.setVisible(false);
			}
		});
		buttonPane.add(cancelButton);

	}

}
