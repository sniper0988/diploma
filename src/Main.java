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

public class Main extends JFrame {
    private JPanel rootPanel;
    private JButton clickCameraButton;
    private JSlider brightnessValueSlider;
    private JLabel label;
    private JCheckBox blackWhiteCheckBox;
    private JSlider valueBlurSlider;
    private JCheckBox faceCheckBox;
    private JLabel rChannel;
    private JLabel gChannel;
    private JLabel bChannel;
    private JCheckBox splitColorsCheckBox;

    //подключение библиотеки
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }


    public Main() {
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

        //Подключение к камере
        CameraWrapper captureCamera = new CameraWrapper(0);

        //добавление прослушивания окна
        this.addWindowListener(new WindowAdapter() {
            //при закрытии окна
            @Override
            public void windowClosing(WindowEvent e) {
                //параметр работы камеры //false
                captureCamera.closeCamera();
            }
        });

        if (!captureCamera.getCamera().isOpened()) {
            this.setTitle("Не удалось подключиться к камере");
            //если камера не запустилась, то флаг камеры false
            captureCamera.setRun(false);
            return;
        }

        try {
            //установка размера кадра
            captureCamera.setFrameWidth(480);
            captureCamera.setFrameHeight(360);


            //ЧТЕНИЕ КАДРОВ
            Mat frame = new Mat();
            //BufferedImage img = null;

            //прослушивание кнопки "Щелкнуть"
            clickCameraButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //создать изображение
                    BufferedImage saveImage = captureCamera.cameraCollector(frame);
                    int num = 0;
                    while (true) {
                        //создать файл
                        File outputFile = new File("sources\\photo" + num + ".jpg");
                        if (!outputFile.exists()) {   //если файла нет, то создать его и сохранить изображение и разорвать цикл
                            try {
                                ImageIO.write(saveImage, "jpg", outputFile);
                            } catch (IOException ioException) {
                                System.out.println("Не удалось сохраниь изображение");
                            }
                            System.out.println("Click");
                            break;
                        } else {  //если есть, то изменить имя файла num++ и сначала
                            num++;
                            continue;
                        }
                    }
                }
            });


            while (captureCamera.isRun()) {         //проверка, запущена ли камера
                if (captureCamera.getCamera().read(frame)) {
                    //необходимо конвертировать для проверки
                    //img = CvUtils.MatToBufferedImage(frame);
                    //
                    if (frame != null) {    //проверка изображения на !null
                       /* // вывод полученного изображения с камеры
                        //ImageIcon imageIconLabel = new ImageIcon(img);

                        // получение черно-белого изображения
                        //ImageIcon imageIconLabel = new ImageIcon(CvUtils.MatToBufferedImage(ColorsComponents.blackWhiteImage(frame, 100)));

                        // изменение яркости
                        //ImageIcon imageIconLabel = new ImageIcon(CvUtils.MatToBufferedImage(ColorsComponents.brightnessLevel(frame)));

                        //ImageIcon imageIconLabel = new ImageIcon(FindFace.faceSquare(frame));
*/

                        //включает используемые фильтры в зависимости от состояния чекбокса\слайдера
                        captureCamera.setFaceFinder(faceCheckBox.isSelected());
                        captureCamera.setBlackWhite(blackWhiteCheckBox.isSelected());
                        captureCamera.setValueBrightness(brightnessValueSlider.getValue());
                        captureCamera.setValueBlur(valueBlurSlider.getValue());

                        //устанавливается картинка, которая предварительно собирается в методе cameraCollector
                        ImageIcon imageIconLabel = new ImageIcon(captureCamera.cameraCollector(frame));


                        if (splitColorsCheckBox.isSelected()) {

                            rChannel.setVisible(true);
                            gChannel.setVisible(true);
                            bChannel.setVisible(true);

                            ImageIcon imageIconRed = new ImageIcon(
                                    CvUtils.MatToBufferedImage(
                                            ColorsComponents.splitImage(frame).get(0)));

                            ImageIcon imageIconGreen = new ImageIcon(
                                    CvUtils.MatToBufferedImage(
                                            ColorsComponents.splitImage(frame).get(1)));

                            ImageIcon imageIconBlue = new ImageIcon(
                                    CvUtils.MatToBufferedImage(
                                            ColorsComponents.splitImage(frame).get(2)));

                            rChannel.setIcon(imageIconRed);
                            gChannel.setIcon(imageIconGreen);
                            bChannel.setIcon(imageIconBlue);

                            rChannel.repaint();
                            gChannel.repaint();
                            bChannel.repaint();
                        } else {
                            rChannel.setVisible(false);
                            gChannel.setVisible(false);
                            bChannel.setVisible(false);
                            captureCamera.setFrameWidth(640);
                            captureCamera.setFrameHeight(480);

                        }

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
            captureCamera.setRun(false);
        }

    }


    public static void main(String[] args) {
        new Main();
    }


}
