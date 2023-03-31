package org.heino.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Description:
 * <br/>
 *
 * @author 吴晗
 * @date 2023/3/29 18:24
 */
public class Progress {
    //定时器
    Timer timer;
    //任务线程
    private static class SimulatorActivity implements Runnable {
        //内存可见
        private volatile int current = 0;
        private int amount;
        public SimulatorActivity(int amount) {
            this.amount = amount;
        }
        public int getCurrent() {
            return current;
        }
        public void setCurrent(int current) {
            this.current = current;
        }
        public int getAmount() {
            return amount;
        }
        public void setAmount(int amount) {
            this.amount = amount;
        }
        @Override
        public void run() {
            //通过循环,不断的修改current的值,模拟任务我完成量
            while (current < amount) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                current++;
            }
        }
    }

    private Timer initTimer(ProgressMonitor monitor, SimulatorActivity simulatorActivity) {
        //设置定时任务
        return new Timer(100, e -> {
            //读取当前任务量,修改进度
            int current = simulatorActivity.getCurrent();
            monitor.setProgress(current);
            //判断用户是否点击了取消按钮,停止定时任务,关闭对话框,退出程序
            if (monitor.isCanceled()) {
                timer.stop();
                monitor.close();
                System.exit(0);
            }
            if (current == 100) {
                System.exit(0);
            }
        });
    }

    public void init(Component component) {
        //创建view
        ProgressMonitor monitor = new ProgressMonitor(component, "等待任务完成", "已完成", 0, 100);
        //创建任务model
        SimulatorActivity simulaterActivity = new SimulatorActivity(100);
        //执行任务model
        new Thread(simulaterActivity).start();
        //开启监听controller
        initTimer(monitor, simulaterActivity).start();
    }

}
