package android.decompiler;

import com.googlecode.dex2jar.tools.Dex2jarCmd;

/**
 * @author lhuang
 */
public class Main {
    public static void decompile(String apkFile, String targetPath) {
        try {
            brut.apktool.Main.main(new String[]{"d", apkFile, "-o", targetPath + "/res", "--force"});
            UnzipUtility.unzip(apkFile, "classes.dex");
            Dex2jarCmd.main("classes.dex", "-o", targetPath + "/classes.jar", "--force");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}