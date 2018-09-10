package surevil.paintingandpainting.util;

import java.text.DecimalFormat;

public class FormatUtil {
    public static double formatDouble(double s) {
        DecimalFormat fmt = new DecimalFormat("##0.0");
        return Double.parseDouble(fmt.format(s));
    }
}
