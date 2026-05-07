package com.capstoneshipping.Graphics;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.capstoneshipping.dao.ShippingHistoryDAO;
import com.capstoneshipping.dao.ShippingHistoryDAOImpl;
import com.capstoneshipping.model.OrderHistory;
import com.capstoneshipping.model.ShippingHistory;
import com.capstoneshipping.util.ExportUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ShippingHistoryView extends BorderPane implements SearchableView {

    private TableView<ShippingHistory> tableView;
    private ShippingHistoryDAOImpl shippingHistoryDAO;
    private ObservableList<ShippingHistory> masterList;
    private FilteredList<ShippingHistory> filteredList;

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");

    private ViewController viewController;

    public ShippingHistoryView(ViewController viewController) {
        this.viewController = viewController;

        this.tableView = new TableView<>();
        this.shippingHistoryDAO = new ShippingHistoryDAOImpl();

        TableColumn<ShippingHistory, Integer> ShippingIdCol = new TableColumn<>("Shipping ID");
        ShippingIdCol.setCellValueFactory(new PropertyValueFactory<>("shippingId"));

        TableColumn<ShippingHistory, Integer> orderIdCol = new TableColumn<>("Order ID");
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<ShippingHistory, String> carrierCol = new TableColumn<>("Carrier");
        carrierCol.setCellValueFactory(new PropertyValueFactory<>("carrier"));

        TableColumn<ShippingHistory, String> trackingNumberCol = new TableColumn<>("Tracking Number");
        trackingNumberCol.setCellValueFactory(new PropertyValueFactory<>("trackingNumber"));

        TableColumn<ShippingHistory, String> shipStatusCol = new TableColumn<>("Ship Status");
        shipStatusCol.setCellValueFactory(new PropertyValueFactory<>("shippingStatus"));

        TableColumn<ShippingHistory, LocalDateTime> shippedOnCol = new TableColumn<>("Shipped On");
        shippedOnCol.setCellValueFactory(new PropertyValueFactory<>("shippedOn"));

        TableColumn<ShippingHistory, LocalDateTime> expectedByCol = new TableColumn<>("Expected By");
        expectedByCol.setCellValueFactory(new PropertyValueFactory<>("expectedBy"));

        tableView.getColumns().addAll(
            List.of(
                ShippingIdCol,
                orderIdCol,
                carrierCol,
                trackingNumberCol,
                shipStatusCol,
                shippedOnCol,
                expectedByCol
            )        
        );


        //Add button 
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(5));  
        bottomBox.setPrefHeight(30);
        bottomBox.setId("side-bar");

        Button exportBtn = new Button("Export CSV");

        exportBtn.setOnAction(e -> {
            DateTimeFormatter ExportFormat = DateTimeFormatter.ofPattern("M_d_yyyy");
            ShippingHistoryDAO dao = new ShippingHistoryDAOImpl();

            List<ShippingHistory> list =  shippingHistoryDAO.getAllShippingHistory();
            String filePath = this.viewController.fileChooser("shipping_history_" + LocalDateTime.now().format(ExportFormat) + ".csv");

            if (filePath != null){
                ExportUtil.exportShippingHistoryToCSV(list, filePath);
                
            } else{
                Alert FileError = new Alert(AlertType.ERROR);
                FileError.setTitle("File selection Error.");
                FileError.setHeaderText("An Error Occurred");
                FileError.setContentText("Please check your input and try again.");
                FileError.showAndWait();
            }

        });

        bottomBox.getChildren().add(exportBtn);

        setBottom(bottomBox);
        
        loadShippingHistory();

        setCenter(tableView);

    }

    @Override
    public void applySearch(String selectedField, String searchText) {
        filteredList.setPredicate(history -> {

        if (searchText == null || searchText.isEmpty()) {
            return true;
        }

        String lowerCaseFilter = searchText.toLowerCase();

        if (selectedField == null || selectedField.isEmpty()) {
            return (history.getShippingId() + "").contains(lowerCaseFilter)
                    || (history.getOrderId() + "").contains(lowerCaseFilter)
                    || (history.getCarrier() != null &&
                        history.getCarrier().toLowerCase().contains(lowerCaseFilter))
                    || (history.getTrackingNumber() != null &&
                        history.getTrackingNumber().toLowerCase().contains(lowerCaseFilter))
                    || (history.getShippingStatus() != null &&
                        history.getShippingStatus().toString().toLowerCase().contains(lowerCaseFilter)); 
        }

        switch (selectedField) {
            case "Shipping ID":
                return (history.getShippingId() + "").contains(lowerCaseFilter);

            case "Order ID":
                return (history.getOrderId() + "").contains(lowerCaseFilter);

            case "Carrier":
                return history.getCarrier() != null &&
                        history.getCarrier().toLowerCase().contains(lowerCaseFilter);

            case "Tracking Number":
                return history.getTrackingNumber() != null &&
                        history.getTrackingNumber().toLowerCase().contains(lowerCaseFilter);

            case "Ship Status":
                return history.getShippingStatus() != null &&
                        history.getShippingStatus().toString().toLowerCase().contains(lowerCaseFilter);

            default:
                return true;
        }
        });
    }

    public void loadShippingHistory() {
        List<ShippingHistory> history = shippingHistoryDAO.getAllShippingHistory();

        masterList = FXCollections.observableArrayList(history);
        filteredList = new FilteredList<>(masterList, p -> true);

        tableView.setItems(filteredList);
    }

}
