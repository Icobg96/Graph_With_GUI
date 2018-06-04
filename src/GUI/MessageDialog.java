package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MessageDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public MessageDialog(JFrame frame, String message, boolean successOrError) {
		super(frame);
		if (successOrError) {
			setTitle("Message");
		} else {
			setTitle("Error!");
		}
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 450, 116);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(204, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		{
			JTextArea textArea_Error = new JTextArea();
			textArea_Error.setBackground(new Color(240, 240, 240));
			textArea_Error.setEditable(false);
			if (successOrError) {
				textArea_Error.setForeground(new Color(0, 102, 0));
			} else {
				textArea_Error.setForeground(new Color(204, 0, 0));
			}
			textArea_Error.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
			textArea_Error.setText(message);
			contentPanel.add(textArea_Error);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						MessageDialog.this.setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
