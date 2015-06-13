package fr.haddad.mock.upload;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.apache.hadoop.fs.Path;

import fr.haddad.dfs.impl.UploadFileManagerImpl;

/**
 * Executes the file upload in a background thread and updates progress to
 * listeners that implement the java.beans.PropertyChangeListener interface.
 * 
 * @author www.codejava.net
 *
 */
public class UploadTask extends SwingWorker<Void, Void> implements Observer {

	
	private Path localpath;
	private Path remoteFilePath;
	private String usedMethode;
SwingFileUploadToHDFS  fileUploadToHDFS;
	public String getUsedMethode() {
		return usedMethode;
	}

	public void setUsedMethode(String usedMethode) {
		this.usedMethode = usedMethode;
	}

	public UploadTask(Path localpath, Path remoteFilePath,String usedMethode) {
		
		
		this.localpath = localpath;
		this.setRemoteFilePath(remoteFilePath);
		this.setUsedMethode(usedMethode);
	}

	/**
	 * Executed in background thread
	 */
	@Override
	protected Void doInBackground() throws Exception {
		fileUploadToHDFS=SwingFileUploadToHDFS.getInstance();
		UploadFileManagerImpl uploadFileManagerImpl = UploadFileManagerImpl.getInstance();
		System.out.println(uploadFileManagerImpl == null);
		try {
			if(getUsedMethode().equals("large_file")){
				uploadFileManagerImpl.addObserver(this);
				uploadFileManagerImpl.uploadLargeFile(localpath, remoteFilePath);
			}else{
				uploadFileManagerImpl.uploadtSmallFile(localpath, remoteFilePath);
				JOptionPane.showMessageDialog(null,
						uploadFileManagerImpl.getERROR_MESSAGE(), "Message",
						JOptionPane.INFORMATION_MESSAGE);
				cancel(true);
			
				
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					"Error uploading file: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
			setProgress(0);
			cancel(true);
		} finally {
		}

		return null;
	}

	/**
	 * Executed in Swing's event dispatching thread
	 */
	@Override
	protected void done() {
		if (!isCancelled()) {
			JOptionPane.showMessageDialog(null,
					"File has been uploaded successfully!", "Message",
					JOptionPane.INFORMATION_MESSAGE);

		}
	}

	public Path getRemoteFilePath() {
		return remoteFilePath;
	}

	public void setRemoteFilePath(Path remoteFilePath) {
		this.remoteFilePath = remoteFilePath;
	}

	@Override
	public void update(Observable obs, Object arg) {
		if (obs instanceof UploadFileManagerImpl) {
			setProgress((int) ((UploadFileManagerImpl) obs).delegate
					.getCurrentProgress());
		}
	}
}