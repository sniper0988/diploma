import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.awt.image.BufferedImage;

//класс-обертка для камеры
public class CameraWrapper {

    private VideoCapture camera;    // камера
    private int frameWidth = 640;   // ширина кадра
    private int frameHeight = 480;  // высота кадра
    private boolean isRun = true;  // состояние камеры

    //фильтры-переключатели
    private boolean isBlackWhite = false;
    private boolean isBrightness = true;
    private boolean isBlur = false;
    private boolean isContrast = false;

    //уровни фильтров
    private int valueBlackWhite = 0;
    private int valueBrightness = 0;
    private int valueBlur = 0;
    private int valueContrast = 0;

    //конструктор внутри создает новый объект камеры
    public CameraWrapper(int index) {
        this.camera = new VideoCapture(index);
    }

    public CameraWrapper() {
        this.camera = new VideoCapture();
    }

    //возвращает камеру
    public VideoCapture getCamera() {
        return camera;
    }

    //присваивает камеру
    public void setCamera(VideoCapture camera) {
        this.camera = camera;
    }

    //возвращает ширину кадра
    public int getFrameWidth() {
        return frameWidth;
    }

    //настраивает ширину кадра
    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
        camera.set(Videoio.CAP_PROP_FRAME_WIDTH, frameWidth);
    }

    //возвращает высоту кадра
    public int getFrameHeight() {
        return frameHeight;
    }

    //настраивает высоту кадра
    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
        camera.set(Videoio.CAP_PROP_FRAME_WIDTH, frameHeight);
    }

    //возвращает состояние камеры
    public boolean isRun() {
        return isRun;
    }

    //определяет, запущена ли камера
    public void setRun(boolean run) {
        isRun = run;
    }

    //включен ли фильтр черно-белого
    public boolean isBlackWhite() {
        return isBlackWhite;
    }

    public void setBlackWhite(boolean blackWhite) {
        isBlackWhite = blackWhite;
    }

    // включена ли настройка яркости
    public boolean isBrightness() {
        return isBrightness;
    }

    public void setBrightness(boolean brightness) {
        isBrightness = brightness;
    }

    //возвращает уровень черно-белого
    public int getValueBlackWhite() {
        return valueBlackWhite;
    }

    //устанавливает уровень черно-белого
    public void setValueBlackWhite(int valueBlackWhite) {
        this.valueBlackWhite = valueBlackWhite;
    }

    //возвращает уровень яркости
    public int getValueBrightness() {
        return valueBrightness;
    }

    //устанавливает уровень яркости
    public void setValueBrightness(int valueBrightness) {
        this.valueBrightness = valueBrightness;
    }

    //метод закрывает камеру
    public void closeCamera() {
        this.camera.release();
        setRun(false);
    }

    //метод-коллектор. собирает все настройки и уровни перед выдачей в окно приложения
    public BufferedImage cameraSwitchCollector(Mat mat){
        if(isBlackWhite) mat = ColorsComponents.blackWhiteImage(mat,valueBlackWhite);
        if (isBrightness) mat = ColorsComponents.brightnessLevel(mat, valueBrightness);

        return CvUtils.MatToBufferedImage(mat);
    }

}
