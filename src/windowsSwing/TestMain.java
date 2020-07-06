package windowsSwing;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;

public class TestMain {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat img = Imgcodecs.imread("sources\\test1.jpg");
        BufferedImage bufferedImage = faceSquare(img);
        Imgcodecs.imwrite("sources\\test2.jpg", CvUtils.BufferedImageToMat(bufferedImage));

    }

    public static BufferedImage faceSquare(Mat img) {
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
            return null;
        }

        CascadeClassifier face_detector = new CascadeClassifier();
        String path = "C:\\opencv_3_3\\build\\java\\opencv\\sources\\data\\haarcascades\\";
        String name = "haarcascade_frontalface_alt2.xml";

        if (!face_detector.load(path + name)) {
            System.out.println("не удалось загрузить классификатор " + name);
            return null;
        }

        MatOfRect faces = new MatOfRect();
        face_detector.detectMultiScale(img, faces);
        for (Rect r : faces.toList()) {
            Imgproc.rectangle(img, new Point(r.x, r.y),
                    new Point(r.x + r.width, r.y + r.height),
                    CvUtils.COLOR_WHITE, 2);
        }
        return CvUtils.MatToBufferedImage(img);
    }
}
