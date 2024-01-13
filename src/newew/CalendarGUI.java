/* this class is the main class of the project, this is where the logger, main algorithm and java swing compononts are being utilized in. 
 * @author Omar Dukureh, Charith Jayasekerage, Yared Pena
 * @Version 1.01, Dec, 2023
 */

package newew;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

public class CalendarGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(CalendarGUI.class.getName());
    DefaultTableModel model;
    Calendar cal = new GregorianCalendar();
    JLabel label;
    JTextField dateInput;
    
    

    CalendarGUI() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("/Users/omardukureh12/Downloads/calendar.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Calendar");
        setSize(600, 400);
        getContentPane().setLayout(new BorderLayout());
        setVisible(true);
        TodayPanel date = new TodayPanel();
        date.setBackground(new Color(216, 92, 109));

        label = new JLabel();
        label.setForeground(new Color(0, 0, 0));
        label.setBackground(new Color(49, 49, 49));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton prevButton = new JButton("prev"); //creates a button that will be used to go to the previous month
        prevButton.setBackground(new Color(8, 8, 8));
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cal.add(Calendar.MONTH, -1);
                monthUpdate();
            }
        });

        JButton nextButton = new JButton("next"); //creates a button that will be used to go to the next month. 
        nextButton.setBackground(new Color(8, 8, 8));
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cal.add(Calendar.MONTH, +1);
                monthUpdate();
            }
        });

        dateInput = new JTextField();
        JButton generateButton = new JButton("Go to Date");
        Dimension textFieldSize = new Dimension(100, 25);
        dateInput.setPreferredSize(textFieldSize);
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) throws NumberFormatException { 
                try {
                    String[] inputParts = dateInput.getText().split(" ");

                    if (inputParts.length != 2) {
                        throw new IllegalArgumentException("Error. Please enter month and year with 1 space in between them. Ex:(2023 12)");
                    }

                    int year = Integer.parseInt(inputParts[0]);
                    int month = Integer.parseInt(inputParts[1]) - 1;

                    if (month < 0 || month > 11) {
                        throw new IllegalArgumentException("Error. Months should be between 1-12");
                    }

                    if (year < 0) {
                        throw new IllegalArgumentException("Error. year cannot be a negative number.");
                    }

                    cal.set(year, month, 1);
                    monthUpdate();

                    // Log the user input and the successfully generated calendar 
                    logger.info("Calendar generated for year " + year + " and month " + (month + 1));

                } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException ex) {
                    // If the user input has an error that is also recorded 
                    logger.log(Level.SEVERE, "Error in input or calendar generation", ex);
                    JOptionPane.showMessageDialog(CalendarGUI.this, ex.getMessage());
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(prevButton, BorderLayout.WEST);
        panel.add(label, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.EAST);

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(220, 61, 56));
        inputPanel.setForeground(new Color(228, 194, 164));

        JLabel lblNewLabel = new JLabel("Enter Date: yyyy mm");
        lblNewLabel.setFont(new Font("Monaco", Font.PLAIN, 13));
        inputPanel.add(lblNewLabel);
        inputPanel.add(dateInput);
        inputPanel.add(generateButton);

        panel.add(inputPanel, BorderLayout.SOUTH);

        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        model = new DefaultTableModel(null, days);
        JTable table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setBackground(new Color(137, 162, 227));
        JScrollPane pane = new JScrollPane(table);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        table.setDefaultEditor(Object.class, null);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setFocusable(false);
        table.setDefaultEditor(Object.class, null);
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);

        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(pane, BorderLayout.CENTER);
        getContentPane().add(date, BorderLayout.EAST);

        monthUpdate();
    }

    
    void monthUpdate() { //main algorithm to update months, 
        cal.set(Calendar.DAY_OF_MONTH, 1);

        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        int year = cal.get(Calendar.YEAR);
        label.setText(month + " " + year);

        // the following 3 lines gets the days, weeks and number of days in months. 
        int dayOfMonth = cal.get(Calendar.DAY_OF_WEEK); 
        int numDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int weeks = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
       
        model.setRowCount(0);
        model.setRowCount(weeks);

        int i = dayOfMonth - 1;
        
        for (int day = 1; day <= numDays; day++) { //used to populate the table with dates
            model.setValueAt(day, i / 7, i % 7);
            i = i + 1;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            new CalendarGUI();
        });
    }
}