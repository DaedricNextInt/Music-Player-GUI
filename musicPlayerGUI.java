package experimentalProjects;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javazoom.jl.player.Player;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class musicPlayerGUI {
	private JFrame frameD;
	private JTextField songPathField;
	private File songFile;
	Player player = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					musicPlayerGUI window = new musicPlayerGUI();
					window.frameD.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public musicPlayerGUI() {
		initialize();
	}

	/* Initialize the contents of the frame.*/
	
	private void initialize() {
		frameD = new JFrame();
		frameD.getContentPane().setBackground(Color.DARK_GRAY);
		frameD.getContentPane().setForeground(Color.GRAY);
		frameD.setTitle("Daedric's Music");
		frameD.setBounds(100, 100, 527, 533);
		frameD.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameD.setLocationRelativeTo(null);
		frameD.getContentPane().setLayout(null);
		/*Rounding the outer frame of the application*/
		frameD.setUndecorated(true);
		frameD.setShape(new RoundRectangle2D.Double(0, 0, frameD.getWidth(), frameD.getHeight(), 30, 30));
		/*Text field*/
		songPathField = new JTextField();
		songPathField.setBackground(new Color(218, 165, 32));
		songPathField.setBounds(143, 68, 232, 79);
		frameD.getContentPane().add(songPathField);
		songPathField.setEditable(false);
		songPathField.setText("Song Title");
		songPathField.setColumns(10);
		TextField.roundTextField(songPathField);
		JButton btnOpen = new JButton("open");
		btnOpen.setBounds(222, 220, 66, 43);
		frameD.getContentPane().add(btnOpen);
		btnOpen.setBackground(Color.DARK_GRAY);
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(48, 346, 128, 36);
		frameD.getContentPane().add(btnStart);
		btnStart.setBackground(Color.DARK_GRAY);
		JButton btnStopButton_1 = new JButton("Stop");
		btnStopButton_1.setBounds(336, 346, 128, 36);
		frameD.getContentPane().add(btnStopButton_1);
		btnStopButton_1.setBackground(Color.DARK_GRAY);
		btnStopButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            if (player != null) {
		                player.close(); // Close the 'player'
		                player = null; // Reset the 'player' instance
		            } else {
		                JOptionPane.showMessageDialog(null, "No music is playing!", "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		});
		btnStart.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		        SwingWorker<Void, Void> musicPlayerWorker = new SwingWorker<Void, Void>() {
		            @Override
		            protected Void doInBackground() throws Exception {
		                try {
		                    // Initialize the 'player' when starting playback
		                    player = new Player(new FileInputStream(songFile));
		                    player.play();
		                } catch (Exception e) {
		                    JOptionPane.showMessageDialog(null, "Error playing the file!", "Error", JOptionPane.ERROR_MESSAGE);
		                }
		                return null;
		            }
		        };
		        musicPlayerWorker.execute();
		    }
		});
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //open file
				open();
				
			}
		});
		
		/*Action to START playing audio*/
	}
	/*OPEN FILE & SELECTION*/
	private void open() {
	    SwingWorker<File, Void> fileChooserWorker = new SwingWorker<File, Void>() {
	        @Override
	        protected File doInBackground() throws Exception {
	            JFileChooser chooser = new JFileChooser();
	            chooser.setDialogTitle("Choose song to load..");
	            int result = chooser.showOpenDialog(null);
	            if (result == JFileChooser.APPROVE_OPTION) {
	                File selectedFile = chooser.getSelectedFile();
	                return selectedFile;
	            }
	            return null;
	        }
/*Where the textField grabs the File*/
	        @Override
	        protected void done() {
	            try {
	                File selectedFile = get();
	                if (selectedFile != null) {
	                    songFile = selectedFile;
	                    songPathField.setText(songFile.getAbsolutePath());
	                    if (!(songFile.getName().endsWith(".mp3"))) {
	                        JOptionPane.showMessageDialog(null, "Invalid File Type Selected!", "Error", JOptionPane.ERROR_MESSAGE);
	                    }
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    };
	    fileChooserWorker.execute();
	}
}
