package ro.feedershop.utile;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by nevastuica on 12/22/2014.
 */
public class FileUtil {

    public static void saveWorkbookOnDisk(String path, String filename, Workbook workbook) {
        File dir = new File(path);
        final File file = new File(path + "/" + filename);
        try {
            FileUtils.forceMkdir(dir);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);

            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
