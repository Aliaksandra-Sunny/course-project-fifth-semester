package client.view.workerView;

import client.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainWorkerView extends JFrame {
   public static MainWorkerView jframe;

   private Controller controller;

   public MainWorkerView(Controller controller) {
      jframe = this;
      this.setBackground(Color.PINK);
      this.setResizable(false);
      this.controller = controller;
      JOptionPane.showMessageDialog(this, "Разработчик еще не сделал ваше меню!\nОжидайте...");

   }
}
