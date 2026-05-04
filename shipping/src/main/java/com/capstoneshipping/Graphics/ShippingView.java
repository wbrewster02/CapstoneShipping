package com.capstoneshipping.Graphics;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.capstoneshipping.dao.ShippingDAOImpl;
import com.capstoneshipping.model.Shipping;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class ShippingView extends BorderPane implements SearchableView {

    private TableView<Shipping> tableView;
    private ShippingDAOImpl shippingDAO;

    // holds the full list of shipments (unfiltered) and the filtered list based on search criteria
    private ObservableList<Shipping> masterList;
    private FilteredList<Shipping> filteredList;

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");

    private ViewController viewController;

    public ShippingView(ViewController viewController) {
        this.viewController = viewController;

        tableView = new TableView<>();
        shippingDAO = new ShippingDAOImpl();

        TableColumn<Shipping, Integer> shippingIdCol = new TableColumn<>("Shipping ID");
        shippingIdCol.setCellValueFactory(new PropertyValueFactory<>("shippingId"));

        TableColumn<Shipping, Integer> orderIdCol = new TableColumn<>("Order ID");
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<Shipping, String> shipStatusCol = new TableColumn<>("Ship Status");
        shipStatusCol.setCellValueFactory(new PropertyValueFactory<>("shipStatus"));

        TableColumn<Shipping, String> carrierCol = new TableColumn<>("Carrier");
        carrierCol.setCellValueFactory(new PropertyValueFactory<>("carrier"));

        TableColumn<Shipping, String> trackingNumberCol = new TableColumn<>("Tracking Number");
        trackingNumberCol.setCellValueFactory(new PropertyValueFactory<>("trackingNumber"));

        TableColumn<Shipping, LocalDateTime> shippedOnCol = new TableColumn<>("Shipped On");
        shippedOnCol.setCellValueFactory(new PropertyValueFactory<>("shippedOn"));
        shippedOnCol.setCellFactory(col -> new TableCell<Shipping, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(FORMATTER));
                }
            }
        });

        TableColumn<Shipping, LocalDateTime> expectedByCol = new TableColumn<>("Expected By");
        expectedByCol.setCellValueFactory(new PropertyValueFactory<>("expectedBy"));
        expectedByCol.setCellFactory(col -> new TableCell<Shipping, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(FORMATTER));
                }
            }
        });

        tableView.getColumns().addAll(
            List.of(
                shippingIdCol,
                orderIdCol,
                shipStatusCol,
                carrierCol,
                trackingNumberCol,
                shippedOnCol,
                expectedByCol
            )
        );

        
        // Uncomment later for ShippingDetailView
        
        tableView.setRowFactory(tv -> {
            TableRow<Shipping> row = new TableRow<>();

            //Highlight completed shipments in light green for easy identification
            row.itemProperty().addListener((obs, oldShipping, newShipping) -> {
                row.setId(null);
                if (newShipping == null) return;

                switch (newShipping.getShipStatus()) {
                    case DELIVERED -> row.setId("row-complete");
                    case RETURNED  -> row.setId("row-cancelled");
                    case SHIPPED   -> row.setId("row-awaiting");
                    case PENDING   -> row.setId("row-pending");
                    default        -> row.setId(null);
                }
            });
            row.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected) {
                    row.setStyle("-fx-background-color: #52796f;");
                    // Override status cell backgrounds on selection
                    row.lookupAll(".table-cell").forEach(cell -> 
                        cell.setStyle("-fx-background-color: #52796f; -fx-text-fill: white;")
                    );
                } else {
                    row.setStyle("");
                    // Revert cell styles so CSS ID rules apply again
                    row.lookupAll(".table-cell").forEach(cell -> 
                        cell.setStyle("")
                    );
                }
            });

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Shipping selectedShipping = row.getItem();
                    System.out.println(selectedShipping.getShippingId() + " was double-clicked.");
                    this.viewController.openShippingDetailView(selectedShipping);
                }
            });

            return row;
        });
        
        // ----------------------
        //add bottom hbox for consisteny
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(10));
        bottomBox.setPrefHeight(30);
        bottomBox.setId("side-bar");

        setBottom(bottomBox);


        loadShipments();

        setCenter(tableView);
    }

    public void loadShipments() {
        List<Shipping> shipments = shippingDAO.getAllShipments();

        masterList = FXCollections.observableArrayList(shipments);
        filteredList = new FilteredList<>(masterList, p -> true);

        tableView.setItems(filteredList);
    }

    @Override
    public void applySearch(String selectedField, String searchText) {
        filteredList.setPredicate(shipping -> {
            //System.out.println("Checking shipment: " + shipping.getShippingId() + " against search text: " + searchText);

            if (searchText == null || searchText.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = searchText.toLowerCase();

            // If no field selected, search everything
            if (selectedField == null || selectedField.isEmpty()) {
                return (shipping.getShippingId() + "").contains(lowerCaseFilter)
                        || (shipping.getOrderId() + "").contains(lowerCaseFilter)
                        || (shipping.getShipStatus() != null &&
                            shipping.getShipStatus().toString().toLowerCase().contains(lowerCaseFilter))
                        || (shipping.getCarrier() != null &&
                            shipping.getCarrier().toLowerCase().contains(lowerCaseFilter))
                        || (shipping.getTrackingNumber() != null &&
                            shipping.getTrackingNumber().toLowerCase().contains(lowerCaseFilter));
            }

            switch (selectedField) {
                case "Shipping ID":
                    return (shipping.getShippingId() + "").contains(lowerCaseFilter);

                case "Order ID":
                    return (shipping.getOrderId() + "").contains(lowerCaseFilter);

                case "Ship Status":
                    return shipping.getShipStatus() != null &&
                            shipping.getShipStatus().toString().toLowerCase().contains(lowerCaseFilter);

                case "Carrier":
                    return shipping.getCarrier() != null &&
                            shipping.getCarrier().toLowerCase().contains(lowerCaseFilter);

                case "Tracking Number":
                    return shipping.getTrackingNumber() != null &&
                            shipping.getTrackingNumber().toLowerCase().contains(lowerCaseFilter);

                default:
                    return true;
            }
        });
    }

    public TableView<Shipping> getTableView() {
        return this.tableView;
    }

    public ShippingDAOImpl getShippingDAO() {
        return this.shippingDAO;
    }
}
