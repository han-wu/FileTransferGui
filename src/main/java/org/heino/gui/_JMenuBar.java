package org.heino.gui;

import com.formdev.flatlaf.IntelliJTheme;
import org.heino.Main;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.prefs.Preferences;

/**
 * Description: 菜单栏
 * <br/>
 *
 * @author 吴晗
 * @date 2023/3/29 09:51
 */
public class _JMenuBar extends JMenuBar {
    public _JMenuBar() {
        init();
    }

    public void init() {
        //文件菜单项
        JMenu fileMenu = buildFileMenu();

        //换肤菜单项
        JMenu skinMenu = buildSkinMenu();

        //将所有菜单项添加到JMenuBar
        this.add(fileMenu);
        this.add(skinMenu);
    }

    private JMenu buildFileMenu () {
        JMenu fileMenu = new JMenu("文件");
        JMenuItem openMenuItem = new JMenuItem("打开");
        JMenuItem saveMenuItem = new JMenuItem("保存");
        fileMenu.add(openMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(saveMenuItem);
        openMenuItem.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(openMenuItem)==JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try {
                    System.out.println("文件名：" + file.getName() + " ，文件大小：" + file.length()/1024 + "kb，路径：" + file.getAbsolutePath());
                    long fileSize = file.length();
                    if (fileSize > Integer.MAX_VALUE) {
                        System.out.println("file too big...");
                        return;
                    }
                    FileInputStream fi = new FileInputStream(file);
                    byte[] buffer = new byte[(int) fileSize];
                    int offset = 0;
                    int numRead;
                    while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
                        offset += numRead;
                    }
                    // 确保所有数据均被读取
                    if (offset != buffer.length) {
                        throw new IOException("Could not completely read file "
                                + file.getName());
                    }
                    fi.close();
                    //将file写入到注册表
                    Preferences preferences = Preferences.userNodeForPackage(Main.class);
                    preferences.putByteArray("heino", buffer);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return fileMenu;
    }

    private JMenu buildSkinMenu () {
        JMenu skinMenu = new JMenu("换肤");
        JMenuItem lightMenuItem = new JMenuItem("白色");
        JMenu darkMenu = new JMenu("暗色");
        JMenuItem darkPurpleMenuItem = new JMenuItem("DarkPurple");
        JMenuItem icebergMenuItem = new JMenuItem("Iceberg");
        JMenuItem moonlightMenuItem = new JMenuItem("moonlight");
        skinMenu.add(lightMenuItem);
        skinMenu.addSeparator();
        skinMenu.add(darkMenu);
        darkMenu.add(darkPurpleMenuItem);
        darkMenu.add(icebergMenuItem);
        darkMenu.add(moonlightMenuItem);
        lightMenuItem.addActionListener(e -> changeSkin(this.getParent(), Skin.RoboticketLight));
        darkPurpleMenuItem.addActionListener(e -> changeSkin(this.getParent(), Skin.DarkPurple));
        icebergMenuItem.addActionListener(e -> changeSkin(this.getParent(), Skin.Iceberg));
        moonlightMenuItem.addActionListener(e -> changeSkin(this.getParent(), Skin.moonlight));
        return skinMenu;
    }

    /**
    * Description: 换肤
    * <br/>
    *
    * @author 吴晗
    * @date 2023/3/29 16:23
    * @param container 换肤的容器
    * @param skin 皮肤枚举
    */
    public static void changeSkin(Container container, Skin skin) {
        String path = "/skin/dark/Iceberg.theme.json";
        switch (skin) {
            case RoboticketLight:
                path = "/skin/light/RoboticketLight.theme.json";
                break;
            case DarkPurple:
                path = "/skin/dark/DarkPurple.theme.json";
                break;
            case Iceberg:
                break;
            case moonlight:
                path = "/skin/dark/moonlight-theme.theme.json";
                break;
            default:
                break;
        }
        IntelliJTheme.setup(_JMenuBar.class.getResourceAsStream(path));
        if (null != container)
            SwingUtilities.updateComponentTreeUI(container);
    }
}
