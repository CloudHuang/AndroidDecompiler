package android.decompiler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipInputStream;

/**
 * @author lhuang
 */
public class UnzipUtility {
    /**
     * 从压缩文件中解压缩指定的文件
     *
     * @param zipFilePath
     * @param fileName
     */
    public static void unzip(String zipFilePath, String fileName) {

        try {
            FileInputStream fin = new FileInputStream(zipFilePath);
            ZipInputStream zin = new ZipInputStream(fin);

            do {
                // no-op
            } while (!zin.getNextEntry().getName().equals(fileName));

            OutputStream os = new FileOutputStream(fileName);

            byte[] buffer = new byte[1024];
            int length;

            //read the entry from zip file and extract it to disk
            while ((length = zin.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.close();
            zin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
