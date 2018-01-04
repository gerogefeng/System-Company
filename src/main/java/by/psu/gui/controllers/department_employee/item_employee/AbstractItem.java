package by.psu.gui.controllers.department_employee.item_employee;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;

public abstract class AbstractItem {

    protected void animationElement(Node node){
        Task<Void> animation = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Platform.runLater(()-> node.setStyle("-jfx-unfocus-color: brown"));
                    Thread.sleep(2000);
                    Platform.runLater(()-> node.setStyle("-jfx-unfocus-color: #c3c3c3"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Thread thread = new Thread(animation);
        thread.start();
    }

    protected abstract int checkData();

    protected abstract void display(int valueWarning);
}
