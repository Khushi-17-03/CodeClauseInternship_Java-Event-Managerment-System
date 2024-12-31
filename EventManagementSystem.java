import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Event class to store event details
class Event {
    private String eventName;
    private String date;
    private String time;
    private ArrayList<String> attendees;

    public Event(String eventName, String date, String time) {
        this.eventName = eventName;
        this.date = date;
        this.time = time;
        this.attendees = new ArrayList<>();
    }

    public String getEventName() {
        return eventName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public ArrayList<String> getAttendees() {
        return attendees;
    }

    public void addAttendee(String attendee) {
        attendees.add(attendee);
    }
}

public class EventManagementSystem {

    private JFrame frame;
    private DefaultListModel<Event> eventListModel;
    private JList<Event> eventList;

    public EventManagementSystem() {
        frame = new JFrame("Event Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Event list model and list
        eventListModel = new DefaultListModel<>();
        eventList = new JList<>(eventListModel);
        eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Buttons
        JButton createEventButton = new JButton("Create Event");
        JButton manageAttendeesButton = new JButton("Manage Attendees");
        JButton viewScheduleButton = new JButton("View Schedule");

        createEventButton.addActionListener(e -> createEvent());
        manageAttendeesButton.addActionListener(e -> manageAttendees());
        viewScheduleButton.addActionListener(e -> viewSchedule());

        // Layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createEventButton);
        buttonPanel.add(manageAttendeesButton);
        buttonPanel.add(viewScheduleButton);

        frame.add(new JScrollPane(eventList), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void createEvent() {
        JTextField eventNameField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();

        Object[] inputFields = {
            "Event Name:", eventNameField,
            "Date (dd-mm-yyyy):", dateField,
            "Time (hh:mm):", timeField
        };

        int option = JOptionPane.showConfirmDialog(frame, inputFields, "Create Event", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String eventName = eventNameField.getText();
            String date = dateField.getText();
            String time = timeField.getText();

            if (!eventName.isEmpty() && !date.isEmpty() && !time.isEmpty()) {
                Event event = new Event(eventName, date, time);
                eventListModel.addElement(event);
                JOptionPane.showMessageDialog(frame, "Event created successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void manageAttendees() {
        Event selectedEvent = eventList.getSelectedValue();

        if (selectedEvent == null) {
            JOptionPane.showMessageDialog(frame, "Please select an event to manage attendees.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField attendeeField = new JTextField();
        Object[] inputFields = {
            "Attendee Name:", attendeeField
        };

        int option = JOptionPane.showConfirmDialog(frame, inputFields, "Add Attendee", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String attendeeName = attendeeField.getText();

            if (!attendeeName.isEmpty()) {
                selectedEvent.addAttendee(attendeeName);
                JOptionPane.showMessageDialog(frame, "Attendee added successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Attendee name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewSchedule() {
        StringBuilder schedule = new StringBuilder();

        for (int i = 0; i < eventListModel.size(); i++) {
            Event event = eventListModel.getElementAt(i);
            schedule.append("Event: ").append(event.getEventName()).append("\n")
                    .append("Date: ").append(event.getDate()).append("\n")
                    .append("Time: ").append(event.getTime()).append("\n")
                    .append("Attendees: ").append(event.getAttendees()).append("\n\n");
        }

        JTextArea scheduleArea = new JTextArea(schedule.toString());
        scheduleArea.setEditable(false);

        JOptionPane.showMessageDialog(frame, new JScrollPane(scheduleArea), "Event Schedule", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EventManagementSystem::new);
    }
}
