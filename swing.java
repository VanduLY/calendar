import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;

public class CalendarApp {
    private JFrame frame;
    private JPanel calendarPanel;
    private JLabel monthLabel;
    private LocalDate currentDate;

    public CalendarApp() {
        currentDate = LocalDate.now();
        createUI();
        displayCalendar();
    }

    private void createUI() {
        frame = new JFrame("Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Top panel with month navigation
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        monthLabel = new JLabel("", SwingConstants.CENTER);

        prevButton.addActionListener(e -> navigateMonth(-1));
        nextButton.addActionListener(e -> navigateMonth(1));

        topPanel.add(prevButton, BorderLayout.WEST);
        topPanel.add(monthLabel, BorderLayout.CENTER);
        topPanel.add(nextButton, BorderLayout.EAST);

        frame.add(topPanel, BorderLayout.NORTH);

        // Calendar panel for displaying days
        calendarPanel = new JPanel();
        frame.add(calendarPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void displayCalendar() {
        calendarPanel.removeAll();

        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue(); // 1 (Monday) to 7 (Sunday)

        calendarPanel.setLayout(new GridLayout(0, 7)); // 7 columns for days of the week

        // Add day labels
        String[] dayLabels = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (String label : dayLabels) {
            JLabel dayLabel = new JLabel(label, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 12));
            calendarPanel.add(dayLabel);
        }

        // Fill empty slots before the first day
        for (int i = 1; i < startDayOfWeek; i++) {
            calendarPanel.add(new JLabel());
        }

        // Add days of the month
        for (int day = 1; day <= daysInMonth; day++) {
            JLabel dayLabel = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            dayLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            calendarPanel.add(dayLabel);
        }

        // Update the month label
        monthLabel.setText(yearMonth.getMonth() + " " + yearMonth.getYear());

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void navigateMonth(int offset) {
        currentDate = currentDate.plusMonths(offset);
        displayCalendar();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalendarApp::new);
    }
}
