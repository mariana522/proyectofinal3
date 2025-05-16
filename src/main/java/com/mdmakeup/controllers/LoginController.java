package com.mdmakeup.controllers;

import com.mdmakeup.models.Usuario;
import java.io.IOException;
import java.io.InputStream;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LoginController {
    
    private MainController mainController;
    
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public LoginController() {
   
}
    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        
        if (email.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Error", "Por favor ingresa email y contraseña");
            return;
        }
        
        if (mainController.autenticarUsuario(email, password)) {
            cargarVistaTienda();
        } else {
            mostrarAlerta("Error", "Credenciales incorrectas");
        }
    }
    
    @FXML
   
    private void handleSignup() {
    try {
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mdmakeup/views/RegistroView.fxml"));
        Parent root = loader.load();
        
       
        RegistroController controller = loader.getController();
        controller.setMainController(mainController);
        
        
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("MD Makeup Store - Registro");
        
    } catch (IOException e) {
        e.printStackTrace();
        mostrarAlerta("Error", "No se pudo cargar la vista de registro: " + e.getMessage());
    }
}
    
    private void cargarVistaTienda() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mdmakeup/views/TiendaView.fxml"));
            Parent root = loader.load();
            
            TiendaController tiendaController = loader.getController();
            tiendaController.setMainController(mainController);
            tiendaController.setUsuarioActual(mainController.getUsuarioActual());
            tiendaController.inicializarDatos();
            
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("MD Makeup Store - Tienda");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la tienda");
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}