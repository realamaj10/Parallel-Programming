package Exercise2;

import java.awt.*;
import java.awt.event.*;
import static java.lang.Thread.sleep;
import javax.swing.*;

public class ResponsiveGUI extends JFrame {

    private boolean stop = false;
    private String print = "Good Morning!";

    public ResponsiveGUI() {

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 30));
        contentPane.add(new JLabel("Printer"));

        JButton startBtn = new JButton("Print");
        contentPane.add(startBtn);

        startBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //Boolean value set to false when printing starts
                stop = false;
                //New Thread created for printing
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; ++i) {
                            if (stop) {
                                break;
                            }
                            System.out.println("Good Morning!");

                            //after print,thread will sleep for 1000 miliseconds and give control to the other threads
                            try {
                                sleep(1000);  //this means 1000 milliseconds
                            } catch (InterruptedException ex) {
                            }
                        }
                    }
                };
                thread.start();
            }
        });

        //add stop button to pane
        JButton stopBtn = new JButton("Stop Printing");
        contentPane.add(stopBtn);

        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if stop is pressed, the bool variable must be set to true
                stop = true;
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Exercise 2");
        setSize(400, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Since swing objects are not thread-safe, we want the task to be executed on an AWT Event Dispatch thread. 
        //This way, we know that the task is asynchronously exxecuted.
        //this is a GUI-focused way of "implementing" the Runnable interface
        SwingUtilities.invokeLater(new Runnable() {
            //run method necessary to have threads function
            public void run() {
                //instance of class above
                new ResponsiveGUI();
            }
        });
    }
}
