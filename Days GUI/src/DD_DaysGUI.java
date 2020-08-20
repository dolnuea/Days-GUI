import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;

/**  @author Dolunay Dagci
 * Date: 3/10/19
 *  Course: CISS 111-360
 * Assignment Name = Days as GUI
 * This program uses a scroll bar for the month and a TextField for the year that must be filled in if the month is February, and displays a line from the poem.
 */

public class DD_DaysGUI extends JFrame {

    private JPanel monthPanel;
    private JLabel label;
    private JPanel panel;
    private JTextField yearText, poemLine;
    private String[] monthsArray = {"January", "February", "March",
            "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
    private JScrollPane jScrollPane;
    private JList monthList;
    private int number_Of_DaysInMonth = 0; //this is just used to determine which months have the same number of days

    /**
     * constructor
     */
    public DD_DaysGUI() {
        setTitle("Days");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(300, 200);

        buildMonthPanel();
        buildPanel();

        add(monthPanel, BorderLayout.CENTER); add(panel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    private void buildMonthPanel() {
        monthPanel = new JPanel();
        monthList = new JList(monthsArray);
        monthList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        monthList.setVisibleRowCount(6);
        jScrollPane = new JScrollPane(monthList);
        monthList.addListSelectionListener(new ListListener());
        monthPanel.add(jScrollPane, BorderLayout.WEST);
    }

    private void buildPanel() {
        panel = new JPanel();
        label = new JLabel(" <= Enter Year Here For February");
        label.setBorder(BorderFactory.createLineBorder(Color.RED));
        yearText = new JTextField(6);
        yearText.setToolTipText("Press Enter!");
        poemLine = new JTextField();
        poemLine.setEditable(false); //can't be edited

        panel.add(yearText); panel.add(label);
    }

    private class ListListener implements ListSelectionListener {

        /**
         *
         * @param year input
         * @return the leap/not leap year
         */
        public boolean isLeapYear(int year) { //this will be used to determine if a year is leap
            boolean status;
            if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
                status = true; }
            else { status = false; }
            return status;
        }

        int year;
        Font font = new Font("Monotype Corsiva", Font.BOLD, 24);

        @Override
        public void valueChanged(ListSelectionEvent e) {

            String selectedMonth = (String) monthList.getSelectedValue(); //get the string value from array list

            switch (selectedMonth) {
                case "January": case "March": case "May": case "July": case "August": case "October": case "December":

                    number_Of_DaysInMonth = 31;
                    poemLine.setText("all the rest have thirty-one"); //set lines to the lines in the poem corresponding to their months

                    break;

                case "February":
                    poemLine.setText(""); //enter year first to have the line displayed
                    Action action = new AbstractAction() { //when user presses enter key, year and the line will be updated
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (monthList.getSelectedValue() == "February") {
                                try {
                                    year = Integer.parseInt(yearText.getText()); //parse year to int
                                    if (year == 0) poemLine.setText("");  //to ensure year is not  a null/0 value
                                    if (isLeapYear(year)) {
                                        number_Of_DaysInMonth = 29;
                                        poemLine.setText("Leap Year happening one in four, Gives February one day more.");
                                    } else {
                                        number_Of_DaysInMonth = 28;
                                        poemLine.setText("with February's 28 to make it fun.");
                                    }
                                } catch (RuntimeException a) { //catch any exceptions during runtime. For instances of empty year field
                                    poemLine.setText("");
                                    JOptionPane.showMessageDialog(null, "Enter the year please.", "Input Year", JOptionPane.WARNING_MESSAGE);
                                }
                            } pack();
                        }
                    }; yearText.addActionListener(action); //add listener to yearText
                    break;
                case "April": case "June": case "November":

                    number_Of_DaysInMonth = 30;
                    poemLine.setText("April, June, and November");
                    break;
                case "September":

                    number_Of_DaysInMonth = 30;
                    poemLine.setText("Thirty days hath September");
                    break;
            }

            poemLine.setFont(font);
            poemLine.setBackground(Color.blue);
            poemLine.setForeground(Color.white);

            add(poemLine, BorderLayout.NORTH);
            pack(); }
    }

    public static void main(String[] args) { new DD_DaysGUI(); }
}