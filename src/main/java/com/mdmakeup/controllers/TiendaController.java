package com.mdmakeup.controllers;

import com.mdmakeup.models.Producto;
import com.mdmakeup.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TiendaController implements Initializable {
    @FXML private ListView<Producto> listViewProductos;
    @FXML private ListView<Producto> listViewCarrito;
    @FXML private ListView<Producto> listViewHistorial;
    @FXML private ListView<Producto> listViewFavoritos;

    private ObservableList<Producto> carrito = FXCollections.observableArrayList();
    private ObservableList<Producto> historial = FXCollections.observableArrayList();
    private ObservableList<Producto> favoritos = FXCollections.observableArrayList();

    private Usuario usuarioActual;
    private MainController mainController;

    public TiendaController() {}

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
        System.out.println("Usuario en tienda: " + usuario.getNombre());
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ------- Productos de ejemplo -------
        if (mainController == null || mainController.getProductosDisponibles() == null || mainController.getProductosDisponibles().isEmpty()) {
            listViewProductos.getItems().addAll(
                new Producto("1", "Labial Matte", 20000.0, "image 10.png", "Labios"),
                new Producto("2", "Delineador Pro", 32000.0, "image 11.png", "Ojos"),
                new Producto("3", "Base Natural", 230.00, "image 12.png", "Rostro"),
                new Producto("4", "Corrector Pro", 99.00, "image 14.png", "Rostro"),
                new Producto("5", "Rubor Rosado", 130.00, "image 2.png", "Rostro"),
                new Producto("6", "Sombra 4 tonos", 110.00, "image 3.png", "Ojos"),
                new Producto("7", "Máscara Volumen", 145.00, "image 4.png", "Ojos"),
                new Producto("8", "Brillo Labial", 90.00, "image 5.png", "Labios"),
                new Producto("9", "Delineador Líquido", 100.00, "image 6.png", "Ojos"),
                new Producto("10", "Polvo Compacto", 125.00, "image 7.png", "Rostro"),
                new Producto("11", "Primer Iluminador", 155.00, "image 8.png", "Rostro"),
                new Producto("12", "Paleta Contorno", 210.00, "image 9.png", "Rostro")
            );
        } else {
            listViewProductos.getItems().addAll(mainController.getProductosDisponibles());
        }

        // Productos: con imagen
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

        // Carrito: solo nombre y precio
        listViewCarrito.setCellFactory(lv -> new ListCell<Producto>() {
            private final Label nombre = new Label();
            private final Label precio = new Label();
            private final VBox content = new VBox(3, nombre, precio);

            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    nombre.setText(item.getNombre());
                    precio.setText("$" + item.getPrecio());
                    setGraphic(content);
                }
            }
        });
        listViewCarrito.setItems(carrito);
        listViewCarrito.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Historial de compras
        listViewHistorial.setItems(historial);
        listViewHistorial.setCellFactory(lv -> new ListCell<Producto>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNombre() + " - $" + item.getPrecio());
            }
        });

        // Favoritos
        listViewFavoritos.setItems(favoritos);
        listViewFavoritos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listViewFavoritos.setCellFactory(lv -> new ListCell<Producto>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNombre() + " - $" + item.getPrecio());
            }
        });
    }

    // Agregar al carrito
    @FXML
    private void agregarAlCarrito(ActionEvent event) {
        Producto seleccionado = listViewProductos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            carrito.add(seleccionado);
            mostrarAlerta("Éxito", seleccionado.getNombre() + " agregado al carrito");
        } else {
            mostrarAlerta("Error", "Selecciona un producto primero");
        }
    }

    // Agregar a favoritos
    @FXML
    private void agregarAFavoritos(ActionEvent event) {
        Producto seleccionado = listViewProductos.getSelectionModel().getSelectedItem();
        if (seleccionado != null && !favoritos.contains(seleccionado)) {
            favoritos.add(seleccionado);
            mostrarAlerta("Favoritos", seleccionado.getNombre() + " agregado a favoritos");
        } else if (favoritos.contains(seleccionado)) {
            mostrarAlerta("Favoritos", "Este producto ya está en tus favoritos");
        } else {
            mostrarAlerta("Error", "Selecciona un producto primero");
        }
    }

    // Eliminar del carrito
    @FXML
    private void eliminarDelCarrito(ActionEvent event) {
        ObservableList<Producto> seleccionados = listViewCarrito.getSelectionModel().getSelectedItems();
        if (seleccionados != null && !seleccionados.isEmpty()) {
            carrito.removeAll(FXCollections.observableArrayList(seleccionados));
            mostrarAlerta("Eliminados", "Producto(s) eliminado(s) del carrito");
        } else {
            mostrarAlerta("Error", "Selecciona uno o más productos del carrito para eliminar");
        }
    }

    // Eliminar de favoritos
    @FXML
    private void eliminarFavorito(ActionEvent event) {
        ObservableList<Producto> seleccionados = listViewFavoritos.getSelectionModel().getSelectedItems();
        if (seleccionados != null && !seleccionados.isEmpty()) {
            favoritos.removeAll(FXCollections.observableArrayList(seleccionados));
            mostrarAlerta("Favorito eliminado", "Producto(s) eliminado(s) de favoritos.");
        } else {
            mostrarAlerta("Error", "Selecciona uno o más productos favoritos para eliminar.");
        }
    }

    // Finalizar compra (agrega al historial y limpia carrito)
    @FXML
    private void finalizarCompra(ActionEvent event) {
        if (!carrito.isEmpty()) {
            double total = 0;
            for (Producto p : carrito) {
                total += p.getPrecio();
            }
            historial.addAll(carrito);
            carrito.clear();
            mostrarAlerta("¡Compra exitosa!", "Gracias por tu compra.\nTotal gastado: $" + String.format("%.2f", total) + "\nTu carrito ha sido vaciado.\nHistorial actualizado.");
        } else {
            mostrarAlerta("Carrito vacío", "No hay productos en el carrito.");
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
