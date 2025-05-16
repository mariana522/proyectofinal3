package com.mdmakeup.controllers;

import com.mdmakeup.models.Usuario;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class RegistroController {
    
    private MainController mainController;

    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtContraseña;
    public RegistroController() {
   
}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    

    @FXML
    private void registrarUsuario() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String contraseña = txtContraseña.getText();

        if (nombre.isEmpty() || email.isEmpty() || contraseña.isEmpty()) {
            mostrarAlerta("Error", "Por favor completa todos los campos");
            return;
        }

        if (mainController.registrarUsuario(nombre, email, contraseña)) {
            mostrarAlerta("Éxito", "Registro exitoso. Serás redirigido a la tienda.");
            cargarVistaTienda(); // Redirige directamente a la tienda
        } else {
            mostrarAlerta("Error", "El correo ya está registrado");
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

            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("MD Makeup Store - Tienda");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la tienda");
        }
    }
    
    @FXML
private void cargarVistaLogin() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mdmakeup/views/LoginView.fxml"));
        Parent root = loader.load();
        
        LoginController controller = loader.getController();
        controller.setMainController(mainController);
        
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("MD Makeup Store - Login");
    } catch (IOException e) {
        e.printStackTrace();
        mostrarAlerta("Error", "No se pudo cargar la vista de login");
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
