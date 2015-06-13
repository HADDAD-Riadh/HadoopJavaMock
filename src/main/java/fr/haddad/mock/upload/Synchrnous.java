package fr.haddad.mock.upload;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.sound.sampled.DataLine;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.haddad.dfs.control.ProgressDelegate;
import fr.haddad.dfs.impl.UploadFileManagerImpl;
import fr.haddad.dfs.impl.UploadFileManagerImpl;
import fr.haddad.mock.upload.UploadTask;
import fr.riadh.control.UserPermissionHdfsException;

import org.apache.hadoop.fs.Path;
public class Synchrnous extends JFrame implements
		PropertyChangeListener {
	 private static Synchrnous uniqueInstance;
	 public static synchronized Synchrnous getInstance()
				throws IOException {
			if (uniqueInstance == null) {
				uniqueInstance = new Synchrnous();
			}
			return uniqueInstance;
		}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static JTextField labelProgress;
	private JProgressBar progressBar;
	private UploadFileManagerImpl test;
	private static List<Path> list;
	private Button button2;
	private JTextArea field;

    

    
	final JLabel lebs = new JLabel("TEst");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Synchrnous() {

		super("Swing File Upload to HDFS server");
	    
		button2 = new Button("write to file");
		field = new JTextArea("write text here.......");
		field.setSize(400,400);
		labelProgress = new JTextField("make path here ....");
		
		try {
			test = UploadFileManagerImpl.getInstance();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		

		
		
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Path path = new Path(labelProgress.getText());
				
					
					try {
						test.writeFile(field.getText().getBytes(), path);
					} catch (UserPermissionHdfsException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					
					
					
					
					
					//fieldUploadPath.setText(field.getText());
				}
			}
		});
		

		// set up layout
		setLayout(new GridBagLayout());

	

		
		
		add(button2);
		add(field);
		add(labelProgress);
		
		
		
	
		
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	

	/**
	 * Update the progress bar's state whenever the progress of upload changes.
	 */
	// @Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("value changed");
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);

		}
	}

	/**
	 * Launch the application
	 */
	public static void main(String[] args) {
		try {
			// set look and feel to system dependent
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			// @Override
			public void run() {
				new Synchrnous().setVisible(true);
			}
		});
	}

}
