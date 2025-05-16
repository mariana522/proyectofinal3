package com.mdmakeup.controllers;

import com.mdmakeup.models.Producto;
import com.mdmakeup.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class TiendaController implements Initializable {
    @FXML private ListView<Producto> listViewProductos;
    private Usuario usuarioActual;
    private MainController mainController;
    public TiendaController() {
   
}

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
        System.out.println("Usuario en tienda: " + usuario.getNombre());
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
        listViewProductos.setCellFactory(lv -> new ListCell<Producto>() {
            private final ImageView imageView = new ImageView();
            private final Label nombre = new Label();
            private final Label precio = new Label();
            private final HBox content = new HBox(10, imageView, new VBox(5, nombre, precio));

            {
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
            }

            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    try {
                        imageView.setImage(new Image(getClass().getResourceAsStream("/com/mdmakeup/images/" + item.getImagenUrl())));
                    } catch (Exception e) {
                        imageView.setImage(null);
                    }
                    nombre.setText(item.getNombre());
                    precio.setText("$" + item.getPrecio());
                    setGraphic(content);
                }
            }
        });

       
        if (mainController != null && mainController.getProductosDisponibles() != null) {
            listViewProductos.getItems().addAll(mainController.getProductosDisponibles());
        }
    }

    @FXML
    private void agregarAlCarrito(ActionEvent event) {
        Producto seleccionado = listViewProductos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            mainController.agregarAlCarrito(seleccionado, 1);
            mostrarAlerta("Éxito", seleccionado.getNombre() + " agregado al carrito");
        } else {
            mostrarAlerta("Error", "Selecciona un producto primero");
        }
    }

    @FXML
    private void verCarrito(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mdmakeup/views/CarritoView.fxml"));
            Parent root = loader.load();

            CarritoController controller = loader.getController();
            controller.setMainController(mainController);

            Stage stage = (Stage) listViewProductos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("MD Makeup Store - Carrito");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el carrito");
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mdmakeup/views/LoginView.fxml"));
        Parent root = loader.load();
        
        LoginController loginController = loader.getController();
        loginController.setMainController(mainController);  
        
        Stage stage = (Stage) listViewProductos.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("MD Makeup Store - Login");
        stage.show();  
    } catch (Exception e) {
        e.printStackTrace();
        mostrarAlerta("Error", "Error al cerrar sesión");
    }
}

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void inicializarDatos() {
        if (mainController == null || mainController.getProductosDisponibles() == null) {
            mostrarAlerta("Error", "No se pudieron cargar los productos");
        }
    }
}
