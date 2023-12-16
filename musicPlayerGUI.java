package experimentalProjects;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import java.awt.SystemColor;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JToggleButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import java.awt.Font;

public class musicPlayerGUI {

	private JFrame frameD;
	private JTextField songPathField;
	private File songFile;
	private JButton btnStopButton;
	private JTextField txtHello;
	
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

	/**
	 * Create the application.
	 */
	public musicPlayerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameD = new JFrame();
		frameD.getContentPane().setBackground(Color.DARK_GRAY);
		frameD.getContentPane().setForeground(Color.GRAY);
		frameD.setTitle("Daedric's Music");
		frameD.setBounds(100, 100, 645, 533);
		frameD.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameD.setLocationRelativeTo(null);
		frameD.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 0));
		panel.setBounds(295, 11, 324, 459);
		frameD.getContentPane().add(panel);
		panel.setLayout(null);
		
		txtHello = new JTextField();
		txtHello.setFont(new Font("Yu Gothic", Font.PLAIN, 11));
		txtHello.setHorizontalAlignment(SwingConstants.CENTER);
		txtHello.setText("\r\n");
		txtHello.setBounds(28, 11, 267, 213);
		panel.add(txtHello);
		txtHello.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(128, 128, 0));
		panel_1.setBounds(10, 11, 275, 459);
		frameD.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		/*Open File*/
		JButton btnOpen = new JButton("open");
		btnOpen.setBackground(Color.DARK_GRAY);
		btnOpen.setBounds(77, 219, 128, 67);
		panel_1.add(btnOpen);
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //open file
				open();
				
			}
		});
		/*Close File*/
		JButton btnStopButton_1 = new JButton("Stop");
		btnStopButton_1.setBackground(Color.DARK_GRAY);
		btnStopButton_1.setBounds(77, 371, 128, 36);
		panel_1.add(btnStopButton_1);
		btnStopButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				//STOPPPPPPPPPPPPPPPPP
		try {
			Player p = new Player( new FileInputStream(songFile));
			p.close();
		} catch (Exception n) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "No File Slected!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
				
		
			}
		});
		
		songPathField = new JTextField();
		songPathField.setBounds(21, 11, 228, 36);
		panel_1.add(songPathField);
		songPathField.setEditable(false);
		songPathField.setText("Song Title");
		songPathField.setColumns(10);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBackground(Color.DARK_GRAY);
		btnStart.setBounds(77, 313, 128, 36);
		panel_1.add(btnStart);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		panel_2.setBounds(10, 58, 255, 150);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		/*Action to play audio*/
		btnStart.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		        SwingWorker<Void, Void> musicPlayerWorker = new SwingWorker<Void, Void>() {
		            @Override
		            protected Void doInBackground() throws Exception {
		                try {
		                    Player p = new Player(new FileInputStream(songFile));
		                    p.play();
		                    p.close();
		                } catch (Exception e) {
		                    // Handle exceptions if any
		                    SwingUtilities.invokeLater(() ->
		                            JOptionPane.showMessageDialog(null, "Error playing the file!", "Error", JOptionPane.ERROR_MESSAGE));
		                }
		                return null;
		            }
		        };
		        musicPlayerWorker.execute();
		    }
		});

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
