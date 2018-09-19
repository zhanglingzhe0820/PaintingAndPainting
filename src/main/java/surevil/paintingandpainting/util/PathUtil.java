package surevil.paintingandpainting.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PathUtil {
    private final static String TEMP_DIR_NAME = "PaintingAndPainting";

    public static String getTmpPath() {
        java.util.Properties properties = System.getProperties();
        String tempFileName = properties.getProperty("java.io.tmpdir");
        return tempFileName + TEMP_DIR_NAME;
    }

    public static String getTmpPath(String fileName) {
        java.util.Properties properties = System.getProperties();
        String tempFileName = properties.getProperty("java.io.tmpdir");
        return tempFileName + TEMP_DIR_NAME + "/" + fileName;
    }

    public static String getDatabasePath(String fileName) {
        return ResourceUtil.getFilePathUnderRootDirOfJarFileOrClassDir("/data/" + fileName);
    }

    public static String getDatabasePath() {
        return ResourceUtil.getFilePathUnderRootDirOfJarFileOrClassDir("/data/");
    }

    public static String getSerPath() {
        return ResourceUtil.getFilePathUnderRootDirOfJarFileOrClassDir("/data/ser/");
    }

    public static void initDatabase() {
        String resourcePath = ResourceUtil.getFilePathUnderRootDirOfJarFileOrClassDir("/data");

        File dir = new File(resourcePath);
        assert !dir.exists() : dir.mkdir();

        dir = new File(resourcePath + "/ser");
        assert !dir.exists() : dir.mkdir();

        ArrayList<File> fileArrayList = new ArrayList<>();
        fileArrayList.add(new File(resourcePath + "/History.txt"));
        fileArrayList.add(new File(resourcePath + "/snapshot.txt"));

        for (File file : fileArrayList) {
            try {
                assert !file.exists() : file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
