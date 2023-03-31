package org.heino.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Description: 程序主框体
 * <br/>
 *
 * @author 吴晗
 * @date 2023/3/29 09:29
 */
public class _JFrame extends JFrame {
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;
    private String title = "";

    public _JFrame() throws HeadlessException {
    }

    public _JFrame(String title) throws HeadlessException {
        this.title = title;
        init();
    }

    public _JFrame(String title, int x, int y, int width, int height) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        init();
    }

    public _JFrame(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        init();
        this.setLocationRelativeTo(null);
    }

    public void init() {
        this.setTitle(title);
        this.setBounds(x, y, width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
