package com.hugoruiz.acontrol;

import com.hugoruiz.acontrol.controller.SceneController;
import com.hugoruiz.acontrol.util.HibernateUtil;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import org.hibernate.Session;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        SceneController.getInitialScene(stage);
    }
    
    @Override
    public void init() throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.close();
    }
    
    @Override
    public void stop() throws Exception {
        super.stop();
        HibernateUtil.shutdown();
    }
    
    public static void main(String[] args) {
        launch();
    }

}