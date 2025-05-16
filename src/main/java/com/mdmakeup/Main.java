package com.mdmakeup;

import com.mdmakeup.controllers.LoginController;
import com.mdmakeup.controllers.MainController;
import com.mdmakeup.models.Producto;
import com.mdmakeup.models.Usuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
       
        MainController mainController = new MainController();
        
  
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mdmakeup/views/LoginView.fxml"));
        Parent root = loader.load();
        
        
        LoginController loginController = loader.getController();
        loginController.setMainController(mainController);
        
      
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MD Makeup Store - Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}