package surevil.paintingandpainting.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PathUtil {
    public final static String TEMP_FILE_NAME = "PaintingAndPainting";

    public static String getTmpPath() {
        java.util.Properties properties = System.getProperties();
        String tempFileName = properties.getProperty("java.io.tmpdir");
        return tempFileName + TEMP_FILE_NAME;
    }

    public static String getDatabasePath() {
        return ResourceUtil.getFilePathUnderRootDirOfJarFileOrClassDir("/data/");
    }

    public static String getSerPath() {
        return ResourceUtil.getFilePathUnderRootDirOfJarFileOrClassDir("/data/ser/");
    }

    public static void initDatabase() {
        String resourcePath = ResourceUtil.getFilePathUnderRootDirOfJarFileOrClassDir("/data");
        System.out.println(resourcePath);

        File dir = new File(resourcePath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        dir = new File(resourcePath + "/ser");
        if (!dir.exists()) {
            dir.mkdir();
        }

        ArrayList<File> fileArrayList = new ArrayList<>();
        fileArrayList.add(new File(resourcePath + "/User.txt"));
        fileArrayList.add(new File(resourcePath + "/FormulaLiquid.txt"));
        fileArrayList.add(new File(resourcePath + "/FormulaOil.txt"));
        fileArrayList.add(new File(resourcePath + "/ProductionBillLiquid.txt"));
        fileArrayList.add(new File(resourcePath + "/ProductionBillOil.txt"));

        try (FileWriter writer = new FileWriter(getDatabasePath() + "user.txt")) {
            writer.write("{\"id\":1,\"username\":\"admin\",\"password\":\"57578971\"}\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(getDatabasePath() + "topic.txt")) {
            writer.write("{\"topicId\":1,\"value\":\"123123\"}\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (File file : fileArrayList) {
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
