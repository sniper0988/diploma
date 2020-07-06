import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;

abstract class ColorsComponents {

    //получение черно-белого изображения
    public static Mat blackWhiteImage(Mat mat, int maxVal) {
        //проверка матрицы на пустоту
        if (mat.empty()) {
            System.out.println("Не удалось загрузить изображение");
            return null;
        }

        Mat img2 = new Mat();
        Imgproc.cvtColor(mat, img2, Imgproc.COLOR_BGR2GRAY);

        Mat img3 = new Mat();
        // 1 - исходная матрица
        // 2 - матрица, в которую будет записано изображение
        // 3 - пороговое значение
        // 4 - максимальное значение
        // 5 - int type, тип преобразоавния
        double thresh = Imgproc.threshold(img2, img3, 50, maxVal,
                Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        //System.out.println(thresh);

        return img3;
    }

    public static Mat brightnessLevel(Mat mat, int value) {
        if (mat.empty()) {
            System.out.println("Не удалось загрузить изображение");
            return null;
        }

        Mat imgHSV = new Mat();
        Imgproc.cvtColor(mat, imgHSV, Imgproc.COLOR_BGR2HSV);
        //Увеличение яркости
        Core.add(imgHSV, new Scalar(0, 0, value), imgHSV);
        //Уменьшение яркости
        //        Core.add(imgHSV, new Scalar(0,0,-40), imgHSV);
        Mat imgBGR = new Mat();
        Imgproc.cvtColor(imgHSV, imgBGR, Imgproc.COLOR_HSV2BGR);

        return imgBGR;
    }


}
