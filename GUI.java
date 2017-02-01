import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


/**
 * GUI is a class which sets up a Graphical User Interface for the user to input
 * various parameters such as the root note of the scale to be played, the type of scale
 * and the instrument through user-friendly UI Elements including drop down lists and radio buttons.
 * 
 * After the required values have been input and stored in specific variables, 
 * the constructor of the ScaleGenerator class is called.
 * 
 * The latest version of this project can be found at 
 * https://github.com/SupritBehera/Scale-Generator_WithGUI
 * 
 * @author Suprit Behera
 * @version 1.0.3 
 * Created on 10/31/2016 
 *
 */

@SuppressWarnings("serial")
public class GUI extends JFrame {

	private static String rootNote, scaleType;
	private static int octaveNumber, instrumentNumber;

	private JLabel rootNoteLabel, octaveLabel, instrumentLabel;
	private JButton setButton, launchButton;
	private JComboBox<String> rootNoteComboBox, octaveComboBox, instrumentsComboBox;
	private JTabbedPane tabbedPane;
	private Checkbox acousticGuitarCB, pianoCB, sitarCB, violinCB, metallicPadCB, banjoCB, trumpetCB, squareLeadCB,
	churchOrganCB;
	private CheckboxGroup instrumentsGroup = new CheckboxGroup();
	private ButtonGroup scalesGroup;
	private JRadioButton major, naturalMinor, harmonicMinor, ionian, dorian, phyrgian, lydian, myxolydian, aeolian,
	locrian, blues, minorPentatonic, majorPentatonic, wholeTone, wholeHalfDiminished, halfWholeDiminished;

	private String notes[] = { "C (Default)", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
	private String octaveNumbers[] = { "5 (Default)", "1", "2", "3", "4", "6", "7", "8" };
    private int runningTime; //stores the running time for each scale
	

    public GUI() {
		initialize();
		createUI();
		setVisible(true);
		setTitle("Scale Generator");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(520, 240));
		setLocationRelativeTo(null); // position the window at the center of the screen
	}



	private void initialize() {
		rootNoteLabel = new JLabel("Select the Root Note");
		octaveLabel = new JLabel("Select the Octave Number");

		rootNoteComboBox = new JComboBox<String>(notes);// JCombobox is nothing but a drop down list
		rootNoteComboBox.setEditable(false);
		octaveComboBox = new JComboBox<String>(octaveNumbers);
		octaveComboBox.setEditable(false);

		scalesGroup = new ButtonGroup();// A ButtonGroup contains numerous JComboBox
		major = new JRadioButton("Major");
		major.setSelected(true); // By default JRadioButton major will be selected
		naturalMinor = new JRadioButton("Natural Minor");
		harmonicMinor = new JRadioButton("Harmonic Minor");
		ionian = new JRadioButton("Ionian");
		dorian = new JRadioButton("Dorian");
		phyrgian = new JRadioButton("Phrygian");
		lydian = new JRadioButton("Lydian");
		myxolydian = new JRadioButton("Mixolydian");
		aeolian = new JRadioButton("Aeolian");
		locrian = new JRadioButton("Locrain");
		blues = new JRadioButton("Blues");
		minorPentatonic = new JRadioButton("Minor Pentatonic");
		majorPentatonic = new JRadioButton("Major Pentatonic");
		wholeTone = new JRadioButton("Whole Tone");
		wholeHalfDiminished = new JRadioButton("Whole Half Diminished");
		halfWholeDiminished = new JRadioButton("Half Whole Diminished");
		JRadioButton radioButtons[] = { major, naturalMinor, harmonicMinor, ionian, dorian, phyrgian, lydian,
				myxolydian, aeolian, locrian, blues, minorPentatonic, majorPentatonic, 
				wholeTone, wholeHalfDiminished,halfWholeDiminished };
		for (int i = 0; i < radioButtons.length; i++) { // Loop to add all JRadioButton to a ButtonGroup
			scalesGroup.add(radioButtons[i]);

		}

		instrumentsGroup = new CheckboxGroup(); // CheckboxGroup contains numerous Checkbox (awt)
		acousticGuitarCB = new Checkbox("Acoustic Guitar", instrumentsGroup, false);
		pianoCB = new Checkbox("Grand Piano", instrumentsGroup, false);
		instrumentsGroup.setSelectedCheckbox(pianoCB); // By default Checkbox pianoCB will be selected
		sitarCB = new Checkbox("Sitar", instrumentsGroup, false);
		violinCB = new Checkbox("Violin", instrumentsGroup, false);
		trumpetCB = new Checkbox("Trumpet", instrumentsGroup, false);
		metallicPadCB = new Checkbox("Metallic Pad", instrumentsGroup, false);
		banjoCB = new Checkbox("Banjo", instrumentsGroup, false);
		squareLeadCB = new Checkbox("Square Lead", instrumentsGroup, false);
		churchOrganCB = new Checkbox("Church Organ", instrumentsGroup, false);

		instrumentLabel = new JLabel("Enter Instrument Number");

		String instrumentNumbers[] = new String[128];// Array to store elements to be displayed 
		                                             // in instrumentsComboBox 
		for (int i = 0; i < 128; i++) { // Loop to add numbers from 1-128 at instrumentNumbers[]
			if (i == 0)
				instrumentNumbers[0] = "1 (Default)";
			else
				instrumentNumbers[i] = Integer.toString(i + 1);
		}
		instrumentsComboBox = new JComboBox<String>(instrumentNumbers);

		setButton = new JButton("Set Instrument");
		setButton.addActionListener(new ActionListener() { // Add an ActionListener to setButton
			// Anonymous Class to substantiate interface ActionListener()
			@Override
			public void actionPerformed(ActionEvent e) {
				String tempInstrumentNumber = instrumentsComboBox.getSelectedItem().toString();
				if (tempInstrumentNumber == "1 (Default)")
					instrumentNumber = 1;
				else
					instrumentNumber = Integer.parseInt(tempInstrumentNumber);

				// Disable all Checkboxes
				acousticGuitarCB.setEnabled(false);
				pianoCB.setEnabled(false);
				sitarCB.setEnabled(false);
				violinCB.setEnabled(false);
				trumpetCB.setEnabled(false);
				squareLeadCB.setEnabled(false);
				metallicPadCB.setEnabled(false);
				banjoCB.setEnabled(false);
				churchOrganCB.setEnabled(false);
			}
		});

		launchButton = new JButton("Launch !");
		launchButton.addActionListener(new ActionListener() { // Add an ActionListener to launchButton
			// Anonymous Class to substantiate interface ActionListener()
			@Override
			public void actionPerformed(ActionEvent e) {
				rootNote = rootNoteComboBox.getSelectedItem().toString();
				if (rootNote == "C (Default)")
					rootNote = "C";
				String tempOctaveNumber = octaveComboBox.getSelectedItem().toString();
				if (tempOctaveNumber == "5 (Default)")
					octaveNumber = 5;
				else
					octaveNumber = Integer.parseInt(tempOctaveNumber);

				// Loop to iterate through all JRadioButtons to see which one is selected and hence store the
				// label of that button to scaleType
				Enumeration<AbstractButton> allScaleRadioButton = scalesGroup.getElements();
				while (allScaleRadioButton.hasMoreElements()) {
					JRadioButton tempButton = (JRadioButton) allScaleRadioButton.nextElement();
					if (tempButton.isSelected()) {
						scaleType = tempButton.getText();
					}
				}

				// This is executed only when JButton setButton has not been clicked
				// (Clicking setButton disables all the Checkboxes)
				if (pianoCB.isEnabled()) {
					Checkbox selectedInstrument = instrumentsGroup.getSelectedCheckbox();
					String instrumentName = selectedInstrument.getLabel();
					if (instrumentName == "Grand Piano")
						instrumentNumber = 1;
					if (instrumentName == "Acoustic Guitar")
						instrumentNumber = 25;
					if (instrumentName == "Sitar")
						instrumentNumber = 105;
					if (instrumentName == "Violin")
						instrumentNumber = 41;
					if (instrumentName == "Trumpet")
						instrumentNumber = 57;
					if (instrumentName == "Banjo")
						instrumentNumber = 106;
					if (instrumentName == "Church Organ")
						instrumentNumber = 20;
					if (instrumentName == "Metallic Pad")
						instrumentNumber = 94;
					if (instrumentName == "Square Lead")
						instrumentNumber = 81;
				}
				launchButton.setEnabled(false); //disable the launchButton till the scale has been completely played
				runScaleGenerator();
				int numberOfNotes = ScaleGenerator.getNumberOfNotes(ScaleGenerator.numericValueOfScaleType(getScaleType()));
				if(numberOfNotes == 9){
					runningTime = 8500;
				}
				if(numberOfNotes == 8){
					runningTime = 7500;
				}
				if(numberOfNotes == 7){
					runningTime = 6500;
				}
				if(numberOfNotes == 6){
					runningTime = 5500;
				}
				
				// Sets up a timer of (runningTime) milliseconds. 
				// After the timer counts down to zero, the launchButton is enabled   
				Timer launchTimer = new Timer(runningTime, new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						launchButton.setEnabled(true);
						
					}
				});
				launchTimer.setRepeats(false); // Don't reset the timer once it counts down to zero
				launchTimer.start(); // Start the countdown
			}
		});

	}

	private void createUI() {
		JPanel panelOut = new JPanel(); // Outermost JPanel
		panelOut.setLayout(new BorderLayout());

		tabbedPane = new JTabbedPane(); // JTabbedPane arranged JPanels in a tabbed format
		panelOut.add(tabbedPane, BorderLayout.CENTER);

		// JPanel for the first tab in tabbedPane, "Root Note"
		JPanel panelTab1 = new JPanel();
		panelTab1.setLayout(new GridBagLayout());
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0; // Row number 0
		c1.gridy = 0; // Column number 0
		c1.insets = new Insets(5, 10, 5, 10); // To give a gap between two components
		c1.anchor = GridBagConstraints.LINE_START;// Align all components of a column to the left
		panelTab1.add(rootNoteLabel, c1); // Add rootNoteLabel to row 0, column 0
		c1.gridy++; // Column number 1
		panelTab1.add(octaveLabel, c1); // Add octaveLabel to row 0, column 1
		c1.gridx = 1; // Row number 1
		c1.gridy = 0; // Column number 0
		panelTab1.add(rootNoteComboBox, c1); // Add rootNoteComboBox to row 1, column 0
		c1.gridy++;
		panelTab1.add(octaveComboBox, c1);// Add octaveComboBox to row 1, column 1
		tabbedPane.add("Root Note", panelTab1);

		// JPanel for the second tab in tabbedPane, "Scales"
		JPanel panelTab2 = new JPanel();
		panelTab2.setLayout(new GridBagLayout());
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 0;
		c2.gridy = 0;
		c2.insets = new Insets(2, 2, 2, 2);
		c2.anchor = GridBagConstraints.LINE_START;
		panelTab2.add(major, c2);
		c2.gridy++;
		panelTab2.add(naturalMinor, c2);
		c2.gridy++;
		panelTab2.add(harmonicMinor, c2);
		c2.gridy++;
		panelTab2.add((ionian), c2);
		c2.gridx = 1;
		c2.gridy = 0;
		panelTab2.add((dorian), c2);
		c2.gridy++;
		panelTab2.add((phyrgian), c2);
		c2.gridy++;
		panelTab2.add((lydian), c2);
		c2.gridy++;
		panelTab2.add(myxolydian, c2);
		c2.gridx = 2;
		c2.gridy = 0;
		panelTab2.add(aeolian, c2);
		c2.gridy++;
		panelTab2.add(locrian, c2);
		c2.gridy++;
		panelTab2.add(blues, c2);
		c2.gridy++;
		panelTab2.add(minorPentatonic, c2);
		c2.gridx = 3;
		c2.gridy = 0;
		panelTab2.add(majorPentatonic, c2);
		c2.gridy++;
		panelTab2.add(wholeTone, c2);
		c2.gridy++;
		panelTab2.add(wholeHalfDiminished, c2);
		c2.gridy++;
		panelTab2.add(halfWholeDiminished, c2);
		tabbedPane.add("Scales", panelTab2);

		// JPanel for the third tab in tabbedPane, "Instrument"
		JPanel panelTab3 = new JPanel();
		panelTab3.setLayout(new GridBagLayout());
		GridBagConstraints c3 = new GridBagConstraints();
		c3.anchor = GridBagConstraints.LINE_START;
		c3.insets = new Insets(3, 5, 3, 5);
		c3.gridx = 0;
		c3.gridy = 0;
		panelTab3.add(instrumentLabel, c3);
		c3.gridy++;
		panelTab3.add(new JLabel("Or"), c3);
		c3.gridy++;
		panelTab3.add(pianoCB, c3);
		c3.gridy++;
		panelTab3.add(acousticGuitarCB, c3);
		c3.gridy++;
		panelTab3.add(sitarCB, c3);
		c3.gridy++;
		c3.gridx = 1;
		c3.gridy = 0;
		panelTab3.add(instrumentsComboBox, c3);
		c3.gridy++;
		c3.gridy++;
		panelTab3.add(violinCB, c3);
		c3.gridy++;
		panelTab3.add(trumpetCB, c3);
		c3.gridy++;
		panelTab3.add(banjoCB, c3);
		c3.gridx = 2;
		c3.gridy = 0;
		panelTab3.add(setButton, c3);
		c3.gridy++;
		c3.gridy++;
		panelTab3.add(churchOrganCB, c3);
		c3.gridy++;
		panelTab3.add(metallicPadCB, c3);
		c3.gridy++;
		panelTab3.add(squareLeadCB, c3);
		c3.gridy++;
		tabbedPane.add("Instrument", panelTab3);

		// JPanel for the fourth tab in tabbedPane, "Run"
		JPanel panelTab4 = new JPanel();
		panelTab4.setLayout(new GridBagLayout());
		GridBagConstraints c4 = new GridBagConstraints();
		c4.anchor = GridBagConstraints.LINE_START;
		c4.insets = new Insets(3, 5, 3, 5);
		c4.gridx = 0;
		c4.gridy = 0;
		panelTab4.add(launchButton, c4);
		tabbedPane.add("Run", panelTab4);

		add(panelOut); // Add panelOut to JFrame

	}

	String getRootNote() {
		return rootNote;
	}

	String getScaleType() {
		return scaleType;
	}

	int getOctaveNumber() {
		return octaveNumber;
	}

	int getInstrumentNumber() {
		return instrumentNumber;
	}

	public void runScaleGenerator() {
		new ScaleGenerator(this);
	}


	public static void main(String args[]) {
		// This is done to run the GUI Code in a separate thread
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new GUI();

			}
		});
	}

}
