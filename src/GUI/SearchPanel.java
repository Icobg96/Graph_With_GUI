package GUI;

import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Controllers.GraphController;
import Main.MainClass;
import SearchAlgorithm.EscapeByTypeAndWeight;
import uni.Node;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;

public class SearchPanel extends JPanel {
	private JTextField textField_From;
	private JTextField textField_To;
	private GraphController controller;
	private JLabel lblNewLabel;
	private JLabel lblFrom;
	private JLabel lblTo;
	private JLabel lblMethodForSearch;
	private JLabel lbl_Intermediate_Nodes;
	private JComboBox<String> comboBox_algorithm;
	private JComboBox<String> comboBoxNodes;
	private JTextArea textAreaContent;
	private JTextArea textAreaIntermediateNodes;
	private JButton btnNewButton;
	private JButton btn_Add_Intermediate_Nodes;
	private JButton btn_Remove_Intermediate_Node;
	private JScrollPane textAreaContentScrollPane;
	private JScrollPane IntermediateNodesScrollPane;
	private JLabel lbl_Weight;
	private JLabel lbl_Type;
	private JTextField textField_Type;
	private JTextField textField_Weight;

	/**
	 * Create the panel.
	 */
	private JFrame frame;

	public SearchPanel() {
		controller = GraphController.getInstance();
		setLayout(null);
		setPreferredSize(new Dimension(665, 664));
		lblNewLabel = new JLabel("Search by algorithm");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblNewLabel.setBounds(10, 11, 645, 34);
		add(lblNewLabel);

		lblFrom = new JLabel("From");
		lblFrom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFrom.setForeground(Color.BLACK);
		lblFrom.setHorizontalAlignment(SwingConstants.CENTER);
		lblFrom.setBounds(20, 60, 170, 20);
		add(lblFrom);

		textField_From = new JTextField();
		textField_From.setBounds(200, 60, 170, 20);
		add(textField_From);
		textField_From.setColumns(10);

		lblTo = new JLabel("To");
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTo.setForeground(Color.BLACK);
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTo.setBounds(20, 90, 170, 20);
		add(lblTo);

		textField_To = new JTextField();
		textField_To.setColumns(10);
		textField_To.setBounds(200, 90, 170, 20);
		add(textField_To);

		lblMethodForSearch = new JLabel("Method for search");
		lblMethodForSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblMethodForSearch.setForeground(Color.BLACK);
		lblMethodForSearch.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMethodForSearch.setBounds(20, 120, 170, 20);
		add(lblMethodForSearch);

		try {
			comboBox_algorithm = new JComboBox(
					controller.getAlgorithmName(MainClass.class.getClassLoader(), "SearchAlgorithm").toArray());

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		comboBox_algorithm.setBounds(200, 120, 170, 20);
		comboBox_algorithm.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				switch (comboBox_algorithm.getSelectedItem().toString()) {
				case "IntermediateNodesSearch":
					lbl_Intermediate_Nodes.setVisible(true);
					btn_Add_Intermediate_Nodes.setVisible(true);
					btn_Remove_Intermediate_Node.setVisible(true);
					textAreaIntermediateNodes.setVisible(true);
					comboBoxNodes.setVisible(true);
					IntermediateNodesScrollPane.setVisible(true);

					lbl_Weight.setVisible(false);
					lbl_Type.setVisible(false);
					textField_Type.setVisible(false);
					textField_Weight.setVisible(false);
					break;
				case "EscapeByTypeAndWeight":
					lbl_Weight.setVisible(true);
					lbl_Type.setVisible(true);
					textField_Type.setVisible(true);
					textField_Weight.setVisible(true);

					lbl_Intermediate_Nodes.setVisible(false);
					btn_Add_Intermediate_Nodes.setVisible(false);
					btn_Remove_Intermediate_Node.setVisible(false);
					textAreaIntermediateNodes.setVisible(false);
					comboBoxNodes.setVisible(false);
					IntermediateNodesScrollPane.setVisible(false);
					break;

				default:
					lbl_Intermediate_Nodes.setVisible(false);
					btn_Add_Intermediate_Nodes.setVisible(false);
					btn_Remove_Intermediate_Node.setVisible(false);
					textAreaIntermediateNodes.setVisible(false);
					comboBoxNodes.setVisible(false);
					IntermediateNodesScrollPane.setVisible(false);

					lbl_Weight.setVisible(false);
					lbl_Type.setVisible(false);
					textField_Type.setVisible(false);
					textField_Weight.setVisible(false);
					break;
				}
			}
		});
		add(comboBox_algorithm);

		textAreaContent = new JTextArea();
		textAreaContent.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		textAreaContent.setEditable(false);
		textAreaContent.setBounds(20, 211, 635, 215);
		textAreaContentScrollPane = new JScrollPane(textAreaContent);
		textAreaContentScrollPane.setSize(635, 215);
		textAreaContentScrollPane.setLocation(20, 211);
		add(textAreaContentScrollPane);
		btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean path = false;
				try {
					if (comboBox_algorithm.getSelectedItem().equals(EscapeByTypeAndWeight.class.getSimpleName())) {
						path = controller.search((String) textField_From.getText(), (String) textField_To.getText(),
								comboBox_algorithm.getSelectedItem().toString(),
								Double.parseDouble(textField_Weight.getText()), textField_Type.getText());

					} else {
						path = controller.search((String) textField_From.getText(), (String) textField_To.getText(),
								comboBox_algorithm.getSelectedItem().toString());
					}
				} catch (NumberFormatException ex) {
					MessageDialog messageDialog = new MessageDialog(frame, "Incorect data input", false);
					messageDialog.setVisible(true);
				}
				if (path) {
					textAreaContent.setText("");
					textAreaContent.append("Have a path : ");
					textAreaContent.append(controller.getGraph().getPathInforamtion() + "\n");
					textAreaContent.append("Path is : ");
					for (int i = controller.getPathNodes().size() - 1; i >= 0; i--) {
						textAreaContent.append(controller.getPathNodes().get(i).getName() + ",");
					}
					textAreaContent.append("\n");
					textAreaContent.append(controller.getGraph().getFullInformation());

				} else {
					textAreaContent.setText("Don't have a path");

				}
				((MapFrame) frame).getMapPanel().drawing();
			}
		});
		btnNewButton.setBounds(20, 150, 80, 23);
		add(btnNewButton);

		lbl_Intermediate_Nodes = new JLabel("Intermediate nodes");
		lbl_Intermediate_Nodes.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lbl_Intermediate_Nodes.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Intermediate_Nodes.setBounds(380, 60, 137, 20);
		add(lbl_Intermediate_Nodes);

		btn_Add_Intermediate_Nodes = new JButton("Add");
		btn_Add_Intermediate_Nodes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaIntermediateNodes.setText("Selected nodes: ");
				controller.addWithOutDuplicateIntermediateNodes(comboBoxNodes.getSelectedItem().toString());
				for (String name : controller.getIntermediateNodesNames()) {
					textAreaIntermediateNodes.append(name + ",");
				}
			}
		});
		btn_Add_Intermediate_Nodes.setBounds(520, 90, 60, 23);
		add(btn_Add_Intermediate_Nodes);

		textAreaIntermediateNodes = new JTextArea();
		textAreaIntermediateNodes.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		textAreaIntermediateNodes.setEditable(false);
		textAreaIntermediateNodes.setBounds(350, 120, 305, 53);
		textAreaIntermediateNodes.append("Selected nodes: ");

		btn_Remove_Intermediate_Node = new JButton("Delete");
		btn_Remove_Intermediate_Node.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.removeIntermediateNodes(comboBoxNodes.getSelectedItem().toString());
				textAreaIntermediateNodes.setText("Selected nodes: ");
				for (String name : controller.getIntermediateNodesNames()) {
					textAreaIntermediateNodes.append(name + ",");
				}
			}
		});
		btn_Remove_Intermediate_Node.setBounds(585, 90, 70, 23);
		add(btn_Remove_Intermediate_Node);
		IntermediateNodesScrollPane = new JScrollPane(textAreaIntermediateNodes);
		IntermediateNodesScrollPane.setBounds(380, 120, 275, 53);
		add(IntermediateNodesScrollPane);

		comboBoxNodes = new JComboBox(controller.getNodeNames().toArray());
		comboBoxNodes.setBounds(380, 90, 135, 23);
		add(comboBoxNodes);

		lbl_Type = new JLabel("Type :");
		lbl_Type.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Type.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Type.setBounds(380, 60, 64, 20);
		add(lbl_Type);

		lbl_Weight = new JLabel("Weight :");
		lbl_Weight.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Weight.setBounds(380, 90, 80, 21);
		add(lbl_Weight);

		textField_Type = new JTextField();
		textField_Type.setBounds(470, 60, 155, 20);
		add(textField_Type);
		textField_Type.setColumns(10);

		textField_Weight = new JTextField();
		textField_Weight.setBounds(470, 90, 155, 20);
		add(textField_Weight);
		textField_Weight.setColumns(10);
		if (!comboBox_algorithm.getSelectedItem().equals("IntermediateNodesSearch")) {
			lbl_Intermediate_Nodes.setVisible(false);
			btn_Add_Intermediate_Nodes.setVisible(false);
			btn_Remove_Intermediate_Node.setVisible(false);
			textAreaIntermediateNodes.setEditable(false);
			textAreaIntermediateNodes.setVisible(false);
			comboBoxNodes.setVisible(false);
			IntermediateNodesScrollPane.setVisible(false);
		}
		if (!comboBox_algorithm.getSelectedItem().equals("EscapeByTypeAndWeight")) {
			lbl_Weight.setVisible(false);
			lbl_Type.setVisible(false);
			textField_Type.setVisible(false);
			textField_Weight.setVisible(false);
		}

	}

	public SearchPanel(JFrame frame) {
		this();
		this.frame = frame;
	}

	public void filComboBoxIntermediateNodes() {
		comboBoxNodes.removeAllItems();
		for (String name : controller.getNodeNames()) {
			comboBoxNodes.addItem(name);
		}
	}

	public void resetTextAreaContent() {
		textAreaContent.setText("");
	}
	public void resetTextAreaIntermediateNodes(){
		textAreaIntermediateNodes.setText("Selected nodes: ");
	}
}
