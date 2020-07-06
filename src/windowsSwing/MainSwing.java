package windowsSwing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class MainSwing {

    public static void run(){
        // создание окна и заголовка
        final JFrame window = new JFrame("Caption");

        //Подключаем иконку из корня папки проекта
        ImageIcon img = new ImageIcon("sources\\tree.jpg");
        window.setIconImage(img.getImage());

        //Событие "закрыть" при нажатии по крестику окна
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Текстовое поле
        JTextField textField = new JTextField();
        //цвет текстового поля
        textField.setBackground(Color.WHITE);
        //ширина поля(в количестве символов)
        textField.setColumns(14);

        //Создание панели
        JPanel panel = new JPanel();

        //Создание кнопок
        JButton minButton = new JButton("Свернуть");
        JButton maxButton = new JButton("Растянуть");
        JButton normalButton = new JButton("Оригинал");
        JButton exitButton = new JButton("Выход");
        JButton helloButton = new JButton("Hello");

        //Создание прочего


        //Событие для кнопки "Свернуть"
        minButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setState(JFrame.ICONIFIED);
            }
        });

        //Событие для кнопки "Растянуть"
        maxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });

        //Событие для кнопки "Оригинал"
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setExtendedState(JFrame.NORMAL);
            }
        });

        //Событие для кнопки "Закрыть"
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                System.exit(0);
            }
        });

        //Событие для кнопки "Hello"
        helloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("HELLO WORLD!");
            }
        });

        //добавил кнопки на панель
        panel.add(minButton);
        panel.add(maxButton);
        panel.add(normalButton);
        panel.add(textField);
        panel.add(helloButton);
        panel.add(exitButton);

        //добавил панель в окно
        window.getContentPane().add(panel);

        window.pack();

        //разместить программу по центру
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }

    public static void main(String[] args) {
        run();
    }


}
