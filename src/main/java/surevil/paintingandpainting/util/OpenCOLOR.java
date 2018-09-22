package surevil.paintingandpainting.util;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Junys
 */
public class OpenCOLOR {

    /**
     * 主程序
     *
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("Opencv版本:" + Core.VERSION + "\n");
        new OpenCOLOR().ShowHelpText();
        // 读取图像路径自行修改
        Mat srcMat = Imgcodecs.imread("D://JavaWorkspace//HelloJava//src//ch0//16.jpg");
        Mat dstMat = srcMat.clone();

        Mat Rdst = new OpenCOLOR().findColor(srcMat, 0, 24);//红 原范围值 0~20 由于图片色彩达不到标准修改 0~24
        Mat Gdst = new OpenCOLOR().findColor(srcMat, 60, 80);//绿 原范围值 60~80
        Mat Ydst = new OpenCOLOR().findColor(srcMat, 30, 50);//黄  原范围值 23~38 由于图片色彩达不到标准修改 28~50

        Mat RdstImage = new OpenCOLOR().Graphicdetection(srcMat, Rdst, dstMat, "R");
        Mat GdstImage = new OpenCOLOR().Graphicdetection(srcMat, Gdst, dstMat, "G");
        Mat YdstImage = new OpenCOLOR().Graphicdetection(srcMat, Ydst, dstMat, "Y");

        // 储存图像路径自行修改
        Imgcodecs.imwrite("D://JavaWorkspace//HelloJava//src//ch0//17.jpg", RdstImage);
        Imgcodecs.imwrite("D://JavaWorkspace//HelloJava//src//ch0//18.jpg", GdstImage);
        Imgcodecs.imwrite("D://JavaWorkspace//HelloJava//src//ch0//19.jpg", YdstImage);
    }

    /**
     * 描述：用于判断颜色识别基于HSV模式
     *
     * @param srcMat
     * @param min
     * @param max
     * @return
     */
    public Mat findColor(Mat srcMat, int min, int max) {
        Mat srcImage = srcMat.clone();
        Mat thresholded = new Mat();
        Mat hsv_image = new Mat();
        Imgproc.GaussianBlur(srcImage, srcImage, new Size(9, 9), 0, 0);
        Imgproc.cvtColor(srcImage, hsv_image, Imgproc.COLOR_BGR2HSV);
        Core.inRange(hsv_image, new Scalar(min, 90, 90), new Scalar(max, 255, 255), thresholded);
        return thresholded;
    }

    /**
     * 描述：用于求证正方形
     *
     * @param pt1
     * @param pt2
     * @param pt0
     * @return
     */
    public double angle(Point pt1, Point pt2, Point pt0) {
        double dx1 = pt1.x - pt0.x;
        double dy1 = pt1.y - pt0.y;
        double dx2 = pt2.x - pt0.x;
        double dy2 = pt2.y - pt0.y;
        double ratio;//边长平方的比
        ratio = (dx1 * dx1 + dy1 * dy1) / (dx2 * dx2 + dy2 * dy2);
        if (ratio < 0.8 || 1.2 < ratio) {//根据边长平方的比过小或过大提前淘汰这个四边形，如果淘汰过多，调整此比例数字

            return 1.0;//根据边长平方的比过小或过大提前淘汰这个四边形
        }
        return (dx1 * dx2 + dy1 * dy2) / Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2) + 1e-10);
    }

    /**
     * 描述：用于识别和统计形状数量
     *
     * @param srcImage
     * @param CLImage
     * @param outImage
     * @param str
     * @return
     */
    public Mat Graphicdetection(Mat srcImage, Mat CLImage, Mat outImage, String str) {
        Mat bjImage = srcImage.clone();
        //边缘检测
        Imgproc.Canny(CLImage, outImage, 10, 100);

        List<MatOfPoint> contours = new ArrayList<>(100);
        Mat hierarchy = new Mat(outImage.rows(), outImage.cols(), CvType.CV_8UC1, new Scalar(0));
        //查找轮廓
        Imgproc.findContours(outImage, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
        Moments[] mu = new Moments[contours.size()];
        Point[] mc = new Point[contours.size()];
        Point[] approx;

        int zfcount = 0;//定义一个正方形计数器
        int sjcount = 0;//定义一个三角形计数器
        Point[] jx;
        Point[] sjx;

        for (int i = 0; i < contours.size(); i++) {
            //计算轮廓矩
            mu[i] = Imgproc.moments(contours.get(i), false);
            //计算轮廓的质心
            mc[i] = new Point(mu[i].get_m10() / mu[i].get_m00(), mu[i].get_m01() / mu[i].get_m00());
            //画出中心点
            Imgproc.circle(bjImage, mc[i], 5, new Scalar(0), -1, 8, 0);

            MatOfPoint2f mPoint2f1 = new MatOfPoint2f();
            MatOfPoint2f mPoint2f2 = new MatOfPoint2f();
            contours.get(i).convertTo(mPoint2f1, CvType.CV_32FC2);
            //绘出多边形
            Imgproc.approxPolyDP(mPoint2f1, mPoint2f2, Imgproc.arcLength(mPoint2f1, true) * 0.02, true);
            mPoint2f2.convertTo(contours.get(i), CvType.CV_32S);
            approx = mPoint2f2.toArray();

            if (approx.length == 4 && Math.abs(Imgproc.contourArea(new MatOfPoint(approx))) > 1000 && Imgproc.isContourConvex(new MatOfPoint(approx))) {
                double maxCosine = 0;
                for (int j = 2; j < 5; j++) {
                    double cosine = Math.abs(angle(approx[j % 4], approx[j - 2], approx[j - 1]));//正方形
                    maxCosine = Math.max(maxCosine, cosine);
                }
                if (maxCosine < 0.3) {
                    jx = approx;
                    //绘制出正方形边框
                    Imgproc.line(bjImage, jx[0], jx[1], new Scalar(0, 0, 0), 3);
                    Imgproc.line(bjImage, jx[1], jx[2], new Scalar(0, 0, 0), 3);
                    Imgproc.line(bjImage, jx[2], jx[3], new Scalar(0, 0, 0), 3);
                    Imgproc.line(bjImage, jx[3], jx[0], new Scalar(0, 0, 0), 3);

                    String string = "Square " + str;
                    Imgproc.putText(bjImage, string, new Point(mc[i].x, mc[i].y - 10), Core.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 0, 0), 1);
                    zfcount++;
                }
            }
            if (approx.length == 3) //三边形
            {
                sjx = approx;
                //绘制出三角形边框
                Imgproc.line(bjImage, sjx[0], sjx[1], new Scalar(0, 0, 0), 3);
                Imgproc.line(bjImage, sjx[1], sjx[2], new Scalar(0, 0, 0), 3);
                Imgproc.line(bjImage, sjx[2], sjx[0], new Scalar(0, 0, 0), 3);

                String string = "Triangle " + str;
                Imgproc.putText(bjImage, string, new Point(mc[i].x, mc[i].y - 10), Core.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 0, 0), 1);
                sjcount++;
            }
        }
        //===============================================下面是霍夫检测圆======================================================//
        Mat circles = new Mat();
        //高斯滤波
        Imgproc.GaussianBlur(CLImage, CLImage, new Size(9, 9), 0, 0);
        //霍夫圆检测
        Imgproc.HoughCircles(CLImage, circles, Imgproc.CV_HOUGH_GRADIENT, 1.5, 10, 200, 100, 0, 0);
        for (int i = 0; i < circles.cols(); i++) {
            double vCircle[] = circles.get(0, i);
            Point center = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
            int radius = (int) Math.round(vCircle[2]);
            String string = "Round " + str;
            Imgproc.putText(bjImage, string, new Point(center.x, center.y - 10), Core.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 0, 0), 1);
            Imgproc.circle(bjImage, center, 3, new Scalar(0, 0, 0), -1, 8, 0);
            //绘制圆轮廓
            Imgproc.circle(bjImage, center, radius, new Scalar(0, 0, 0), 3, 8, 0);
        }
        if (str == "R") {
            str = "红色";
            System.out.printf("%s 正方形 有 %d 个\n", str, zfcount);
            System.out.printf("%s 三角形 有 %d 个\n", str, sjcount);
            System.out.printf("%s 圆     有 %d 个\n\n", str, circles.cols());
        } else if (str == "G") {
            str = "绿色";
            System.out.printf("%s 正方形 有 %d 个\n", str, zfcount);
            System.out.printf("%s 三角形 有 %d 个\n", str, sjcount);
            System.out.printf("%s 圆     有 %d 个\n\n", str, circles.cols());
        } else {
            str = "黄色";
            System.out.printf("%s 正方形 有 %d 个\n", str, zfcount);
            System.out.printf("%s 三角形 有 %d 个\n", str, sjcount);
            System.out.printf("%s 圆     有 %d 个\n\n", str, circles.cols());
        }
        return bjImage;
    }

    /**
     * 描述：输出一些帮助信息
     */
    void ShowHelpText() {
        //输出欢迎信息和OpenCV版本
        System.out.printf("\n\n\t\t\t项目说明：这是一道比赛题目！\n");
        System.out.printf("\n\n\t\t\t欢迎使用OpenCV！本群号：226503332，欢迎学习交流！\n");
        System.out.printf("\n\n\t\t\t本程序由Junys编写，可共享使用禁止售卖！\n");
        System.out.printf("\n\n  ----------------------------------------------------------------------------\n\n");
    }
}
