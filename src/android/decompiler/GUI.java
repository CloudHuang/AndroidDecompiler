package android.decompiler;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * @author lhuang
 */
public class GUI {
    TextField apkFileText;
    TextField targetText;
    JLabel message = new JLabel();;

    public static void main(String[] args) {
        new GUI().execute();
    }

    public void execute() {
        final JFrame mw = new JFrame("Android Decompiler");
        mw.setSize(500, 200);
        mw.setResizable(false);
        mw.setLocationRelativeTo(null); // centered
        mw.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mw.getContentPane().setLayout(new FlowLayout());

        // APK file path
        mw.getContentPane().add(new Label("APK File:"));
        apkFileText = new TextField();
        apkFileText.setColumns(40);
        mw.getContentPane().add(apkFileText);
        Button sourceChooseButton = new Button("Choose...");
        sourceChooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser(".");
                fc.addChoosableFileFilter(new ExtensionsFilter(".apk"));
                fc.setAcceptAllFileFilterUsed(false);

                if (fc.showOpenDialog(mw) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fc.getSelectedFile();
                    apkFileText.setText(selectedFile.getAbsolutePath());
                    targetText.setText(selectedFile.getParent());
                }
            }
        });
        mw.getContentPane().add(sourceChooseButton);

        // Target folder
        mw.getContentPane().add(new Label("Target:"));
        targetText = new TextField();
        targetText.setColumns(40);
        mw.getContentPane().add(targetText);
        Button targetChooseButton = new Button("Choose...");
        targetChooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser(".");
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setAcceptAllFileFilterUsed(false);

                if (fc.showOpenDialog(mw) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fc.getSelectedFile();
                    targetText.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        mw.getContentPane().add(targetChooseButton);

        // Decompiler Button
        Button decompileButton = new Button("Decompile");
        decompileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(apkFileText.getText() == null || "".equals(apkFileText.getText())) {
                    showMessageDialog(null, "Please choose apk file.");
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        message.setText("Starting decompile " + apkFileText.getText() + " to  " + targetText.getText());
                        Main.decompile(apkFileText.getText(), targetText.getText());
                        showMessageDialog(null, "Decompile finished.");
                    }
                }).start();

            }
        });
        mw.getContentPane().add(decompileButton);

        // Status Panel
        JPanel statusPanel = new JPanel();
        statusPanel.add(message);
        mw.getContentPane().add(statusPanel);

        mw.setVisible(true);
    }

    private class ExtensionsFilter extends FileFilter {
        private String[] extensions;

        public ExtensionsFilter(String... extensions) {
            this.extensions = extensions;
        }

        @Override
        public boolean accept(java.io.File f) {
            if (f.isDirectory()) {
                return true;
            } else {
                String sFileName = f.getName().toLowerCase();
                for (String s : extensions) {
                    if (sFileName.endsWith(s)) {
                        return true;
                    }
                }
                return false;
            }
        }

        @Override
        public String getDescription() {
            return "APK File";
        }
    }
}
