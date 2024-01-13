/*
 * This class makes a new panel that tells the user the time and the date. The panel is added to the right of the main frame. 
 * Gives the user the ability to know what day it is.
 * @author Omar Dukureh, Charith Jayasekerage, Yared Pena
 * @version 1.01, Dec, 2023
 */

package newew;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;

public class TodayPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JLabel dayLabel;
    private JLabel timeLabel;
    private JLabel additionalLabel;

    public TodayPanel() {
        setBackground(new Color(175, 231, 201));
        Panel();
        startTimer();
    }

    private void Panel() {
        setLayout(new FlowLayout());
        
        JLabel todayLabel = new JLabel("Today is: ");
        todayLabel.setFont(new Font("Monaco", Font.PLAIN, 19));
        dayLabel = new JLabel();
        timeLabel = new JLabel();
        additionalLabel = new JLabel();

        add(todayLabel);
        add(dayLabel);
        add(timeLabel);
        add(additionalLabel);
    }

   
   /*
    * The following code creates the format of the time that will show up on the 
    * new panel called TodayPanel. it uses Calendar and Date to get the current date and time so that 
    * it can be very accurate. 
    */
    private void startTimer() {
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Calendar calendar = Calendar.getInstance();
                Date now = new Date();

                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE"); // EEE sets us the day name. 
                String currentDay = dayFormat.format(now);
                dayLabel.setText(currentDay);

                SimpleDateFormat monthDisplay = new SimpleDateFormat("MMMM"); // MMMM sets up the month name.
                String currentMonth = monthDisplay.format(now);

                SimpleDateFormat yearDisplay = new SimpleDateFormat("YYYY"); //YYYY sets up the full year. 
                String currentYear = yearDisplay.format(now);

                SimpleDateFormat timeDisplay = new SimpleDateFormat("hh:mm:ss a"); //sets format for the time. 
                String timeFormat = timeDisplay.format(now);
                timeLabel.setText(timeFormat);

         
                dayLabel.setText(currentDay + ", " + currentMonth + " " + calendar.get(Calendar.DAY_OF_MONTH) + ", " + currentYear); //displays the date. 

                
            }
        });
        timer.start();
    }
}