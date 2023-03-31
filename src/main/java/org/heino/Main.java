package org.heino;

import org.heino.gui.Skin;
import org.heino.gui._JFrame;
import org.heino.gui._JMenuBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * Description: 程序启动类
 * <br/>
 *
 * @author 吴晗
 * @date 2023/3/29 09:00
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        LOGGER.info("当前使用的LAF：{}", UIManager.getLookAndFeel().getName());
        _JMenuBar.changeSkin(null, Skin.moonlight);
        //窗口
        JFrame frame = new _JFrame("File Transfer Gui", 900, 800);
        //菜单栏
        JMenuBar toolBar = new _JMenuBar();
        //panel面板
        JPanel jPanel = new JPanel();

        //将菜单栏设置到窗口
        frame.setJMenuBar(toolBar);
        //将面板设置到窗口
        frame.add(jPanel);
        //该方法必须放到最后
        frame.setVisible(true);
    }
}