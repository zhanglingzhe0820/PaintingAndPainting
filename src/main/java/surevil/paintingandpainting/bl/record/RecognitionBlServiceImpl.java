package surevil.paintingandpainting.bl.record;

import sun.misc.BASE64Encoder;
import surevil.paintingandpainting.blservice.record.RecognitionBlService;
import surevil.paintingandpainting.publicdata.perfect.ShapeKind;
import surevil.paintingandpainting.util.ApiUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RecognitionBlServiceImpl implements RecognitionBlService {
    private static final String AK_ID = "LTAIfeJPa5mmsKhL";
    private static final String AK_SECRET = "Dfz3Tj7Tyuojl84vSBHX7E9eluivj5";
    private static final String IMAGE_HOST_URL = "https://dtplus-cn-shanghai.data.aliyuncs.com/image/tag";

    private static final String[] CIRCLE_OBEJCT = {"盘子", "项链", "饰品", "编钟"};
    private static final String[] TRIANGLE_OBEJCT = {"台灯", "风筝", "圆规", "秋千"};
    private static final String[] SQUARE_OBEJCT = {"台灯", "信封", "相框", "显示器", "镜子"};

    /**
     * 通过图像识别出图形
     *
     * @param path@return
     */
    @Override
    public ShapeKind recognizeShapeByImage(String path, double width, double height) {
        ShapeKind shapeKind = ShapeKind.RECTANGLE;

        String bodys = "{\"type\":1," +
                "\"content\":\"" + getImageStr(path) + "\"}";
        try {
            String response = ApiUtil.sendPost(IMAGE_HOST_URL, bodys, AK_ID, AK_SECRET);
            int circleHit = 0;
            int triangleHit = 0;
            int squareHit = 0;
            for (String object : CIRCLE_OBEJCT) {
                if (response.contains(object)) {
                    circleHit++;
                }
            }
            for (String object : TRIANGLE_OBEJCT) {
                if (response.contains(object)) {
                    triangleHit++;
                }
            }
            for (String object : SQUARE_OBEJCT) {
                if (response.contains(object)) {
                    squareHit++;
                }
            }

            int max = 0;
            if (max < circleHit) {
                shapeKind = ShapeKind.CIRCLE;
                max = circleHit;
            }
            if (max < triangleHit) {
                shapeKind = ShapeKind.TRIANGLE;
                max = triangleHit;
            }
            if (max < squareHit) {
                shapeKind = ShapeKind.SQUARE;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (shapeKind == ShapeKind.SQUARE) {
            if (width > 1.5 * height || height > 1.5 * width) {
                shapeKind = ShapeKind.RECTANGLE;
            }
        }
        return shapeKind;
    }

    //图片转化成base64字符串
    public static String getImageStr(String path) {
        InputStream in;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        assert data != null;
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    /**
     * 描述：用于识别和统计形状数量
     *
     * @param CLImage
     * @return
     */
//    private List<ShapeKind> graphicDetection(Mat CLImage) {
//        List<ShapeKind> shapeKinds = new ArrayList<>();
//
//        Mat outImage = CLImage.clone();
//        //边缘检测
//        Imgproc.Canny(CLImage, outImage, 10, 100);
//
//        List<MatOfPoint> contours = new ArrayList<>(100);
//        Mat hierarchy = new Mat(outImage.rows(), outImage.cols(), CvType.CV_8UC1, new Scalar(0));
//        //查找轮廓
//        Imgproc.findContours(outImage, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
//        Point[] approx;
//
//        boolean isSquare = false;//是否是正方形
//        boolean isTriangle = false;//是否是三角形
//        boolean isCircle = false;//是否是圆形
//
//        for (int i = 0; i < contours.size(); i++) {
//            //计算轮廓矩
//            MatOfPoint2f mPoint2f1 = new MatOfPoint2f();
//            MatOfPoint2f mPoint2f2 = new MatOfPoint2f();
//            contours.get(i).convertTo(mPoint2f1, CvType.CV_32FC2);
//            //绘出多边形
//            Imgproc.approxPolyDP(mPoint2f1, mPoint2f2, Imgproc.arcLength(mPoint2f1, true) * 0.02, true);
//            mPoint2f2.convertTo(contours.get(i), CvType.CV_32S);
//            approx = mPoint2f2.toArray();
//
//            if (approx.length == 4 && Math.abs(Imgproc.contourArea(new MatOfPoint(approx))) > 1000 && Imgproc.isContourConvex(new MatOfPoint(approx))) {
//                isSquare = true;
//            }
//            if (approx.length == 3) //三边形
//            {
//                isTriangle = true;
//            }
//        }
//
//        //检测圆
//        Mat circles = new Mat();
//        //高斯滤波
//        Imgproc.GaussianBlur(CLImage, CLImage, new Size(9, 9), 0, 0);
//        //霍夫圆检测
//        Imgproc.HoughCircles(CLImage, circles, Imgproc.CV_HOUGH_GRADIENT, 1.5, 10, 200, 100, 0, 0);
//        if (circles.cols() > 0) {
//            isCircle = true;
//        }
//
//        if (isCircle) {
//            shapeKinds.add(ShapeKind.CIRCLE);
//        }
//        if (isTriangle) {
//            shapeKinds.add(ShapeKind.TRIANGLE);
//        }
//        if (isSquare) {
//            shapeKinds.add(ShapeKind.SQUARE);
//        }
//        return shapeKinds;
//    }
}
