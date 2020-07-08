package windowsSwing;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.awt.image.BufferedImage;

public abstract class FindFace {
    public static Mat faceSquare(Mat img) {
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
        return img;
    }
}
