package Exercise2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//Rather than the phrase "Good Morning" gets printed by 10 at a time,
//we want it to print one by one and stop when we push the stop button. 
public class UnresponsiveGUI extends JFrame {

    private boolean stop = false;  //this will control the stopping of the program
    private String print = "Good Morning!";

    public UnresponsiveGUI() {

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 30));
        contentPane.add(new JLabel("Printer"));

        JButton startBtn = new JButton("Print");
        contentPane.add(startBtn);

        startBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                stop = false;
                for (int i = 0; i < 10; ++i) {
                    if (stop) {
                        break;
                    }
                    System.out.println("Good Morning!");
                }
            }
        });

        JButton stopBtn = new JButton("Stop Printing");
        contentPane.add(stopBtn);

        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop = true;
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Exercise 2");
        setSize(400, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UnresponsiveGUI();
            }
        });
    }
}
