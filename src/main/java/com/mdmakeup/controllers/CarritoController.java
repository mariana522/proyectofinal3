package com.mdmakeup.controllers;

import com.mdmakeup.models.Producto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CarritoController implements Initializable {

    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> columnaNombre;
    @FXML private TableColumn<Producto, Double> columnaPrecio;
    @FXML private TableColumn<Producto, Integer> columnaCantidad;
    @FXML private TableColumn<Producto, Void> columnaAcciones;
    @FXML private Label lblSubtotal;
    @FXML private Label lblEnvio;
    @FXML private Label lblTotal;

    private MainController mainController;
    public CarritoController() {
   
}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        cargarProductos();
        actualizarTotales();
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    private void configurarTabla() {
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        
        columnaCantidad.setCellFactory(tc -> new TableCell<>() {
            private final Spinner<Integer> spinner = new Spinner<>(1, 10, 1);

            {
                spinner.valueProperty().addListener((obs, oldVal, newVal) -> {
                    if (getTableRow() != null && getTableRow().getItem() != null) {
                        Producto producto = getTableRow().getItem();
                        mainController.actualizarCantidadCarrito(producto.getId(), newVal);
                        actualizarTotales();
                    }
                });
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    spinner.getValueFactory().setValue(item);
                    setGraphic(new HBox(spinner));
                }
            }
        });

        
        columnaAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnEliminar = new Button("Eliminar");

            {
                btnEliminar.setOnAction(event -> {
                    Producto producto = getTableView().getItems().get(getIndex());
                    mainController.eliminarDelCarrito(producto.getId());
                    cargarProductos();
                    actualizarTotales();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEliminar);
                }
            }
        });
    }

    private void cargarProductos() {
        tablaProductos.getItems().clear();
        Map<Producto, Integer> productos = mainController.getProductosConCantidad();

        productos.forEach((producto, cantidad) -> {
            producto.setCantidad(cantidad); //
            tablaProductos.getItems().add(producto);
        });
    }

    private void actualizarTotales() {
        double subtotal = mainController.getTotalCarrito();
        double envio = subtotal > 0 ? 20000 : 0;
        double total = subtotal + envio;

        lblSubtotal.setText(String.format("$%,.0f", subtotal));
        lblEnvio.setText(String.format("$%,.0f", envio));
        lblTotal.setText(String.format("$%,.0f", total));
    }

    @FXML
    private void procederAlPago() {
        if (mainController.getTotalCarrito() > 0) {
            mostrarAlerta("Pago", "Procediendo al pago...");
        } else {
            mostrarAlerta("Error", "El carrito está vacío");
        }
    }

    @FXML
    private void volverATienda() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mdmakeup/views/TiendaView.fxml"));
            Parent root = loader.load();

            TiendaController controller = loader.getController();
            controller.setMainController(mainController);

            Stage stage = (Stage) tablaProductos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("MD Makeup Store - Tienda");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo volver a la tienda");
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
