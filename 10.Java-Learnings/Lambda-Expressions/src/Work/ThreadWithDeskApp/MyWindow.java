package Work.ThreadWithDeskApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyWindow {
    public static void main(String[] args) {


        // Window object: JFrame
        JFrame frame = new JFrame("My Window");
        frame.setSize(400, 400);
        frame.setLayout(new FlowLayout());

        // Create button and add JFrame
        JButton button = new JButton("Click me !!");

        // Normal
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                System.out.println("Button Click");
//                JOptionPane.showMessageDialog(null, "Hey Button Clicked.");
//            }
//        });
//        frame.add(button);


        // Lambda Expression
//        button.addActionListener((ActionEvent e) -> {
//            System.out.println("Button Click");
//            JOptionPane.showMessageDialog(null, "Hey Button Clicked.");
//        });
//        frame.add(button);

        // Or
        button.addActionListener( e -> {
            System.out.println("Button Click");
            JOptionPane.showMessageDialog(null, "Hey Button Clicked.");
        });
        frame.add(button);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
