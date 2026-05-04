// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.OrderHistoryView version: 1.1
// Date Modified: 4/17/2026


// NEEDS FIXING

package com.capstoneshipping.Graphics;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.capstoneshipping.util.ExportUtil;

import com.capstoneshipping.dao.OrderHistoryDAOImpl;
import com.capstoneshipping.model.OrderHistory;
//import com.capstoneshipping.model.Order;
//import com.capstoneshipping.dao.OrderDAOImpl;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


import java.time.Duration;


public class OrderHistoryView extends BorderPane implements SearchableView {

    private TableView<OrderHistory> tableView;
    private OrderHistoryDAOImpl orderHistoryDAO;
    //private OrderDAOImpl orderDAO;

    private ObservableList<OrderHistory> masterList;
    private FilteredList<OrderHistory> filteredList;

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");

    private ViewController viewController;

    public OrderHistoryView(ViewController viewController) {
        this.viewController = viewController;

        this.tableView = new TableView<>();
        this.orderHistoryDAO = new OrderHistoryDAOImpl();
        //this.orderDAO = new OrderDAOImpl();

        TableColumn<OrderHistory, Integer> orderIdCol = new TableColumn<>("Order ID");
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<OrderHistory, Integer> customerIdCol = new TableColumn<>("Customer ID");
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        TableColumn<OrderHistory, LocalDateTime> orderDateCol = new TableColumn<>("Order Date");
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        TableColumn<OrderHistory, LocalDateTime> fulfilledAtCol = new TableColumn<>("Fulfilled At");
        fulfilledAtCol.setCellValueFactory(new PropertyValueFactory<>("fulfilledAt"));

        TableColumn<OrderHistory, String> timeToCompleteCol = new TableColumn<>("Time to Complete");
        timeToCompleteCol.setCellValueFactory(cellData -> {
            LocalDateTime orderDate = cellData.getValue().getOrderDate();
            LocalDateTime fulfilledAt = cellData.getValue().getFulfilledAt();

            if (orderDate != null && fulfilledAt != null) {
                Duration duration = Duration.between(orderDate, fulfilledAt);

                long hours = duration.toHours();
                long minutes = duration.toMinutes() % 60;

                return new SimpleStringProperty(hours + "h " + minutes + "m");
            } else {
                return new SimpleStringProperty("N/A");
            }
        });

        tableView.getColumns().addAll(
            List.of(
                orderIdCol,
                customerIdCol,
                orderDateCol,
                fulfilledAtCol,
                timeToCompleteCol
            )
        );

        //Add button 
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(10)); 
        bottomBox.setPrefHeight(50);

        Button exportBtn = new Button("Export CSV");

        exportBtn.setOnAction(e -> {
            //OrderHistoryDAO dao = new OrderHistoryDAOImpl(DatabaseConnection.getConnection());
            //List<OrderHistory> list = getAllOrders();
             List<OrderHistory> list =  orderHistoryDAO.getAllOrders();

            ExportUtil.exportOrderHistoryToCSV(list, "exports/order_history_", LocalDateTime.now());
        });

        bottomBox.getChildren().add(exportBtn);

        setBottom(bottomBox);
        
        loadOrders();

        setCenter(tableView);

    }

    public void loadOrders() {
        List<OrderHistory> orders = orderHistoryDAO.getAllOrders(); // all orders from DB using DAO

        masterList = FXCollections.observableArrayList(orders); // Wrap the list of orders in an ObservableList for JavaFX
        filteredList = new FilteredList<>(masterList, p -> true); //no filter applied, so all orders are shown

        tableView.setItems(filteredList);
        //System.out.println(filteredList);
        // ObservableList<Order> orderList = FXCollections.observableArrayList(orders);
        // tableView.setItems(orderList);
    }

    @Override
    public void applySearch(String selectedField, String searchText) {
        filteredList.setPredicate(history -> {

            if (searchText == null || searchText.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = searchText.toLowerCase();

            if (selectedField == null || selectedField.isEmpty()) {
                return (history.getOrderId() + "").contains(lowerCaseFilter)
                        || (history.getCustomerId() + "").contains(lowerCaseFilter)
                        || (history.getOrderDate() != null &&
                            history.getOrderDate().toString().toLowerCase().contains(lowerCaseFilter))
                        || (history.getFulfilledAt() != null &&
                            history.getFulfilledAt().toString().toLowerCase().contains(lowerCaseFilter));
            }

            switch (selectedField) {
                case "Order ID":
                    return (history.getOrderId() + "").contains(lowerCaseFilter);

                case "Customer ID":
                    return (history.getCustomerId() + "").contains(lowerCaseFilter);

                case "Order Date":
                    return history.getOrderDate() != null &&
                            history.getOrderDate().toString().toLowerCase().contains(lowerCaseFilter);

                case "Fulfilled At":
                    return history.getFulfilledAt() != null &&
                            history.getFulfilledAt().toString().toLowerCase().contains(lowerCaseFilter);

                default:
                    return true;
            }
        });
    }

    public TableView<OrderHistory> getTableView(){
        return this.tableView;
    }

    public OrderHistoryDAOImpl getOrderDAO(){
        return this.orderHistoryDAO;
    }
}


