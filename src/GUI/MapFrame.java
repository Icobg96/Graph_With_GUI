package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controllers.GraphController;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.Window.Type;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.awt.event.ActionEvent;

public class MapFrame extends JFrame {

	private JPanel contentPane;
	private MapPanel mPanel;
	private SearchPanel sPanel;
	private GraphController controller = GraphController.getInstance();
	private JMenuBar menuBar;
	private JScrollPane scrollPane1;

	public MapFrame() {
		setTitle("Map");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 900, 600);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				if (fileChooser.showOpenDialog(MapFrame.this) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					controller.readingFile(file);
					mPanel.repaint();
					mPanel.changeSize();
					scrollPane1.updateUI();
					sPanel.filComboBoxIntermediateNodes();
					sPanel.resetTextAreaContent();
					sPanel.resetTextAreaIntermediateNodes();
					controller.getGraph().resetGraph();
					controller.getIntermediateNodesNames().clear();
				}
			}
		});
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				if (fileChooser.showSaveDialog(MapFrame.this) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					controller.saveInFile(file);
				}
			}
		});
		mnFile.add(mntmSave);

		JMenu mnAdd = new JMenu("Add");
		menuBar.add(mnAdd);

		JMenuItem mntmAddNode = new JMenuItem("Add node");
		mntmAddNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddNode addNode = new AddNode(MapFrame.this);
				addNode.setVisible(true);
			}
		});

		mnAdd.add(mntmAddNode);

		JMenuItem mntmAddLink = new JMenuItem("Add link");
		mntmAddLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddLink addLink = new AddLink(MapFrame.this);
				addLink.setVisible(true);
			}
		});
		mnAdd.add(mntmAddLink);

		JMenu mnRemove = new JMenu("Remove");
		menuBar.add(mnRemove);

		JMenuItem mntmRemoveNode = new JMenuItem("Remove node");
		mntmRemoveNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RemoveNode removeNode = new RemoveNode(MapFrame.this);
				removeNode.setVisible(true);
			}
		});
		mnRemove.add(mntmRemoveNode);

		JMenuItem mntmRemoveLink = new JMenuItem("Remove link");
		mntmRemoveLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RemoveLink removeLink = new RemoveLink(MapFrame.this);
				removeLink.setVisible(true);
			}
		});
		mnRemove.add(mntmRemoveLink);
		contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(1, 2));
		setContentPane(contentPane);
		sPanel = new SearchPanel(this);
		JScrollPane scrollPane = new JScrollPane(sPanel);
		// scrollPane.setPreferredSize(new Dimension(450, 150));
		contentPane.add(scrollPane);
		mPanel = new MapPanel();
		scrollPane1 = new JScrollPane(mPanel);
		contentPane.add(scrollPane1);

	}

	public MapPanel getMapPanel() {
		return mPanel;
	}

	public JScrollPane getScrollPaneMPanel() {
		return scrollPane1;
	}
}
