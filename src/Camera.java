import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import windowsSwing.FindFace;

public class Camera {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static boolean isRun = true;
    public static boolean isEnd = false;

    public static void main(String[] args) {
        JFrame window = new JFrame(
                "Нажмите <Esc> для отключения камеры");

        window.setSize(640, 480);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setLocationRelativeTo(null);

        //обработка кнопки "Закрыть"
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isRun = false;
                if (isEnd) {
                    window.dispose();
                    System.exit(0);
                } else {
                    System.out.println("Сначала нажмите <Esc>, потом закрыть");
                }
            }
        });

        JLabel label = new JLabel();
        window.setContentPane(label);
        window.setVisible(true);
        //Подключение к камере
        VideoCapture camera = new VideoCapture(0);
        if (!camera.isOpened()) {
            window.setTitle("Не удалось подключиться к камере");
            isRun = false;
            isEnd = true;
            return;
        }

        try {
            //установка размера кадра
            camera.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
            camera.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);

            //ЧТЕНИЕ КАДРОВ

            //


            Mat frame = new Mat();
            BufferedImage img = null;

            while (isRun) {
                if (camera.read(frame)) {
                    //обработка кадра
                    img = CvUtils.MatToBufferedImage(frame);
                    if (img != null) {
                        // вывод полученного изображения с камеры
                         //ImageIcon imageIcon = new ImageIcon(img);

                        // получение черно-белого изображения
                        // ImageIcon imageIcon = new ImageIcon(windowsSwing.MainSwing.ColorsComponents.blackWhiteImage(frame));

                        // изменение яркости
                         //ImageIcon imageIcon = new ImageIcon(windowsSwing.MainSwing.ColorsComponents.brightnessLevel(frame));

                        ImageIcon imageIcon = new ImageIcon(FindFace.faceSquare(frame));

                        label.setIcon(imageIcon);
                        label.repaint();
                        window.pack();
                    }
                    try {
                        //считывание кадров каждую 0.1 секунду
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                    }

                } else {
                    System.out.println("Не удалось захватить кадр");
                    break;
                }
            }
        } finally {
            camera.release();
            isRun = false;
            isEnd = true;
        }
        window.setTitle("Камера отключена");
    }


}
