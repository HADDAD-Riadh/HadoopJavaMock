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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.haddad.dfs.control.ProgressDelegate;
import fr.haddad.dfs.impl.UploadFileManagerImpl;
import fr.haddad.mock.upload.UploadTask;
import fr.riadh.control.FileInHdfsAlreadyExistException;
import fr.riadh.control.UserPermissionHdfsException;

import org.apache.hadoop.fs.Path;
public class SwingFileUploadToHDFS extends JFrame implements
		PropertyChangeListener {
	 private static SwingFileUploadToHDFS uniqueInstance;
	 public static synchronized SwingFileUploadToHDFS getInstance()
				throws IOException {
			if (uniqueInstance == null) {
				uniqueInstance = new SwingFileUploadToHDFS();
			}
			return uniqueInstance;
		}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelUploadPath;
	@SuppressWarnings("rawtypes")
	private DefaultListModel model;
	@SuppressWarnings("rawtypes")
	JList dalaList;
	private JTextField fieldUploadPath;
	private JButton buttonUpload;
	private static JLabel labelProgress;
	private JProgressBar progressBar;
	private JFilePicker filePicker;
	private UploadTask task;
	private UploadFileManagerImpl uploadFileManagerImpl;
	private static List<Path> list;
	private JScrollPane scrollPane;
	private Button button,button2,button3;
	private JTextField field;
	final DefaultComboBoxModel typeName = new DefaultComboBoxModel();

    

    final JComboBox MethodeCombo ; 
    
	final JLabel lebs = new JLabel("uploadFileManagerImpl");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SwingFileUploadToHDFS() {

		super("Swing File Upload to HDFS server");
		///////4fruitsName.addElement("Apple");
		typeName.addElement("small_file");
		typeName.addElement("large_file");
	   
	    MethodeCombo = new JComboBox(typeName); 
	    ////////////
		button = new Button("create file");
		button2 = new Button("delete file");
		button3 = new Button("write file");
		filePicker = new JFilePicker("Choose a file: ", "Browse");
		field = new JTextField("Enter folderName");
		progressBar = new JProgressBar(0, 100);
		fieldUploadPath = new JTextField(30);
		buttonUpload = new JButton("Upload");
		labelProgress = new JLabel("Progress:");
		labelUploadPath = new JLabel("Upload path:");
		try {
			uploadFileManagerImpl = UploadFileManagerImpl.getInstance();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		model = new DefaultListModel();

		try {
			list = uploadFileManagerImpl.getListFile();
			for (Path path : list) {
				model.addElement(path.getName());

			}
		} catch (IOException | URISyntaxException e1) {
			e1.printStackTrace();
		}

		dalaList = new JList(model);
		Border lineBorder = BorderFactory.createLineBorder(Color.BLUE, 1);
		dalaList.setBorder(lineBorder);
		dalaList.setSize(300,300);
		scrollPane = new JScrollPane(dalaList);
		lebs.setSize(50, 20);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Path path = new Path("/" + field.getText());
				try {
					if (field.getText() != null /*&& !uploadFileManagerImpl.ifExistsInHDFS(path)*/) {
						uploadFileManagerImpl.mkdir(path);
						model.addElement(field.getText());
						fieldUploadPath.setText(field.getText());
					}
				} catch (IOException e1) {
					e1.printStackTrace();
					lebs.setText(field.getText());
				}
			}
		});
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Path path = new Path(fieldUploadPath.getText());
				System.out.println(fieldUploadPath.getText() != null);
				model.removeElement(fieldUploadPath.getText());
				try {
					uploadFileManagerImpl.delete(path);
				} catch (FileInHdfsAlreadyExistException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//fieldUploadPath.setText(field.getText());
			}
		});
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Synchrnous synchrnous=new Synchrnous();
				synchrnous.setVisible(true);
			}
		});
		dalaList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					fieldUploadPath.setEnabled(false);
					fieldUploadPath.setEditable(false);
					fieldUploadPath.setText("/"
							+ dalaList.getSelectedValue().toString());
				}
			}
		});

		// set up layout
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(5, 5, 5, 5);

		// set up components
		filePicker.setMode(JFilePicker.MODE_OPEN);

		buttonUpload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				buttonUploadActionPerformed(event);
			}
		});
		

		progressBar.setPreferredSize(new Dimension(250, 30));
		progressBar.setStringPainted(true);
		add(scrollPane);
		add(button2);
		add(MethodeCombo);
		add(button3);
		/*constraints.gridx = 0;
		constraints.gridy = 1;
		add(button, constraints);*/
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(field,constraints);
		constraints.gridx = 0;
		constraints.gridy = 3;
		add(button, constraints);
		constraints.gridx = 0;
		constraints.gridy = 4;
		add(labelUploadPath, constraints);

		constraints.gridx = 1;
		
		add(fieldUploadPath, constraints);

		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.gridy = 5;
		constraints.anchor = GridBagConstraints.WEST;

		add(filePicker, constraints);

		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.NONE;
		add(buttonUpload, constraints);

		constraints.gridx = 0;
		constraints.gridy = 7;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.WEST;
		add(labelProgress, constraints);

		constraints.gridx = 1;
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(progressBar, constraints);
		
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * handle click event of the Upload button
	 */
	private void buttonUploadActionPerformed(ActionEvent event) {
		String usedMethode=MethodeCombo.getSelectedItem().toString();
		String uploadPath = fieldUploadPath.getText();
		String filePath = filePicker.getSelectedFilePath();

		Path RemoteFilePath = new Path(uploadPath);
		Path localpath = new Path(filePath);
		progressBar.setValue(0);

		task = new UploadTask(localpath, RemoteFilePath,usedMethode);
		task.addPropertyChangeListener(this);
		task.execute();

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
				new SwingFileUploadToHDFS().setVisible(true);
			}
		});
	}

}
