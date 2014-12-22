package ro.feedershop.html.util;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author <a href="mailto:elena.banu@endava.com">Elena Banu</a>
 * @Revision $Rev$
 * @since 0.0.1
 */
public class FileUtil {

    public static void saveFileOnDisk(String path, String filename, String data) {
        File dir = new File(path);
        final File file = new File(path + "/" + filename);
        try {
            FileUtils.forceMkdir(dir);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void writeFileOnDiscJ7(String path){
//        try {
//
////            Path target = Paths.get("D:\\cucu4.txt");
//            Path target = Paths.get(path);
//            Path file = Files.createFile(target);
//
//            Charset charset = Charset.forName("UTF-8");
//            String s = FirstTest.getData();
//            try {
//                BufferedWriter writer = Files.newBufferedWriter(file, charset);
//                writer.write(s, 0, s.length());
//                writer.close();
//            } catch (IOException x) {
//                System.err.format("IOException: %s%n", x);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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
