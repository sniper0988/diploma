import org.opencv.core.Core;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUIFrame extends JFrame {
    private JPanel rootPanel;
    private JButton clickCameraButton;
    private JSlider brightnessValueSlider;
    private JLabel label;
    private JCheckBox blackWhiteCheckBox;
    private JSlider valueBlurSlider;
    private JCheckBox faceCheckBox;


    public static boolean isEnd = false;    //работа завершена?

    //подключение библиотеки
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }


    public GUIFrame() {
        //устанавливает содержимое окна
        setContentPane(rootPanel);
        //устанавливает видимость окна
        setVisible(true);

        //подгружаем изображение для иконки
        ImageIcon imageIcon = new ImageIcon("sources\\tree.jpg");
        //делаем изображение иконкой приложения
        setIconImage(imageIcon.getImage());

        //устанавливает действие при закрытии
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //        Подключение к камере
        //VideoCapture camera = new VideoCapture(0);
        CameraWrapper captureCamera = new CameraWrapper(0);

        //добавление прослушивания окна
        this.addWindowListener(new WindowAdapter() {
            //при закрытии окна
            @Override
            public void windowClosing(WindowEvent e) {
                //параметр работы камеры //false
                captureCamera.closeCamera();
                if (isEnd) {
                    System.exit(0);
                }
            }
        });


        // if (!camera.isOpened())
        if (!captureCamera.getCamera().isOpened()) {
            this.setTitle("Не удалось подключиться к камере");
            //если камера не запустилась, то флаг камеры false
            captureCamera.setRun(false);
            isEnd = true;
            return;
        }




        try {
            //установка размера кадра
            captureCamera.setFrameWidth(640);
            captureCamera.setFrameHeight(480);


            //ЧТЕНИЕ КАДРОВ
            Mat frame = new Mat();
            BufferedImage img = null;

            //прослушивание кнопки "Щелкнуть"
            clickCameraButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    BufferedImage saveImage= captureCamera.cameraSwitchCollector(frame);
                    File outputFile = new File("sources\\photo.jpg");
                    try {
                        ImageIO.write(saveImage, "jpg", outputFile);
                    } catch (IOException ioException) {
                        System.out.println("Не удалось сохраниь изображение");
                    }
                    System.out.println("Click");
                }
            });



            while (captureCamera.isRun()) {         //проверка, запущена ли камера
                if (captureCamera.getCamera().read(frame)) {
                    img = CvUtils.MatToBufferedImage(frame);

                    if (img != null) {
                        // вывод полученного изображения с камеры
                        //ImageIcon imageIconLabel = new ImageIcon(img);

                        // получение черно-белого изображения
                        //ImageIcon imageIconLabel = new ImageIcon(CvUtils.MatToBufferedImage(ColorsComponents.blackWhiteImage(frame, 100)));

                        // изменение яркости
                        //ImageIcon imageIconLabel = new ImageIcon(CvUtils.MatToBufferedImage(ColorsComponents.brightnessLevel(frame)));

                        //ImageIcon imageIconLabel = new ImageIcon(FindFace.faceSquare(frame));


                        captureCamera.setFaceFinder(faceCheckBox.isSelected());
                        captureCamera.setBlackWhite(blackWhiteCheckBox.isSelected());
                        captureCamera.setValueBrightness(brightnessValueSlider.getValue());
                        //captureCamera.setValueBlackWhite(BlackWhiteValueSlider.getValue())
                        captureCamera.setValueBlur(valueBlurSlider.getValue());
                        ImageIcon imageIconLabel = new ImageIcon(captureCamera.cameraSwitchCollector(frame));

                        label.setIcon(imageIconLabel);
                        label.repaint();
                        this.pack();
                    }
                    try {
                        //считывание кадров каждую 0.1 секунду
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }

                } else {
                    System.out.println("Не удалось захватить кадр");
                    break;
                }
            }
        } finally {

            captureCamera.closeCamera();
            isEnd = true;
        }

    }


    public static void main(String[] args) {
        new GUIFrame();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
