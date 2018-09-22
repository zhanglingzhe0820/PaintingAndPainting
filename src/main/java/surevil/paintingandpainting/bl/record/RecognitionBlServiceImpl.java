package surevil.paintingandpainting.bl.record;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import surevil.paintingandpainting.blservice.record.RecognitionBlService;
import surevil.paintingandpainting.publicdata.perfect.ShapeKind;

import java.util.ArrayList;
import java.util.List;

public class RecognitionBlServiceImpl implements RecognitionBlService {

    /**
     * 通过图像识别出图形
     *
     * @param path@return
     */
    @Override
    public ShapeKind recognizeShapeByImage(String path) {
        ShapeKind shapeKind = ShapeKind.RECTANGLE;
//        System.out.println(System.getProperties().getProperty("java.library.path"));
//        System.load(PathUtil.getDatabasePath("lib/opencv-macosx-x86_64.jar"));
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

//        Mat srcMat = Imgcodecs.imread(PathUtil.getDatabasePath(path));
//        List<ShapeKind> shapeKinds = graphicDetection(srcMat);

//        System.load(PathUtil.getDatabasePath("opencv_java310.dll"));
//        Mat src = Imgcodecs.imread(path);
//        Mat dst = src.clone();
//        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
//
//        Mat circles = new Mat();
//
//        Imgproc.HoughCircles(dst, circles, Imgproc.HOUGH_GRADIENT, 1, 300, 400, 100, 0, 0);
//        Imgproc.HoughLines
//        if (circles.cols() >= 0) {
//            shapeKind = ShapeKind.CIRCLE;
//        }
//        //获取图像的宽度和高度
//        int width = bufferedImage.getWidth();
//        int height = bufferedImage.getHeight();
//        Mat imageMat = new Mat();
//
//        //扫描图片
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {//行扫描
//                int dip = bufferedImage.getRGB(j, i);
//                if (dip == -1) System.out.print(" ");
//                else System.out.print("♦");
//            }
//            System.out.println();//换行
//        }
        return shapeKind;
    }

    /**
     * 描述：用于识别和统计形状数量
     *
     * @param CLImage
     * @return
     */
    private List<ShapeKind> graphicDetection(Mat CLImage) {
        List<ShapeKind> shapeKinds = new ArrayList<>();

        Mat outImage = CLImage.clone();
        //边缘检测
        Imgproc.Canny(CLImage, outImage, 10, 100);

        List<MatOfPoint> contours = new ArrayList<>(100);
        Mat hierarchy = new Mat(outImage.rows(), outImage.cols(), CvType.CV_8UC1, new Scalar(0));
        //查找轮廓
        Imgproc.findContours(outImage, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
        Point[] approx;

        boolean isSquare = false;//是否是正方形
        boolean isTriangle = false;//是否是三角形
        boolean isCircle = false;//是否是圆形

        for (int i = 0; i < contours.size(); i++) {
            //计算轮廓矩
            MatOfPoint2f mPoint2f1 = new MatOfPoint2f();
            MatOfPoint2f mPoint2f2 = new MatOfPoint2f();
            contours.get(i).convertTo(mPoint2f1, CvType.CV_32FC2);
            //绘出多边形
            Imgproc.approxPolyDP(mPoint2f1, mPoint2f2, Imgproc.arcLength(mPoint2f1, true) * 0.02, true);
            mPoint2f2.convertTo(contours.get(i), CvType.CV_32S);
            approx = mPoint2f2.toArray();

            if (approx.length == 4 && Math.abs(Imgproc.contourArea(new MatOfPoint(approx))) > 1000 && Imgproc.isContourConvex(new MatOfPoint(approx))) {
                isSquare = true;
            }
            if (approx.length == 3) //三边形
            {
                isTriangle = true;
            }
        }

        //检测圆
        Mat circles = new Mat();
        //高斯滤波
        Imgproc.GaussianBlur(CLImage, CLImage, new Size(9, 9), 0, 0);
        //霍夫圆检测
        Imgproc.HoughCircles(CLImage, circles, Imgproc.CV_HOUGH_GRADIENT, 1.5, 10, 200, 100, 0, 0);
        if (circles.cols() > 0) {
            isCircle = true;
        }

        if (isCircle) {
            shapeKinds.add(ShapeKind.CIRCLE);
        }
        if (isTriangle) {
            shapeKinds.add(ShapeKind.TRIANGLE);
        }
        if (isSquare) {
            shapeKinds.add(ShapeKind.SQUARE);
        }
        return shapeKinds;
    }
}
