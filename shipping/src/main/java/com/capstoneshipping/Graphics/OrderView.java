// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.OrderView version: 1.0
// Date Modified: 4/3/2026

package com.capstoneshipping.Graphics;

import com.capstoneshipping.dao.OrderDAOImpl;
import com.capstoneshipping.model.Order;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.List;


public class OrderView extends BorderPane implements SearchableView {

    private TableView<Order> tableView;
    private OrderDAOImpl orderDAO;

    //holds the full list of orders(unfiltered) and the filtered list based on search criteria
    private ObservableList<Order> masterList;
    private FilteredList<Order> filteredList;

    public OrderView() {
        tableView = new TableView<>();
        orderDAO = new OrderDAOImpl();

        TableColumn<Order, Integer> orderIdCol = new TableColumn<>("Order ID");
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<Order, Integer> customerIdCol = new TableColumn<>("Customer ID");
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        TableColumn<Order, Object> orderDateCol = new TableColumn<>("Order Date");
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        TableColumn<Order, String> orderStatusCol = new TableColumn<>("Order Status");
        orderStatusCol.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));

        TableColumn<Order, String> fulfillmentStatusCol = new TableColumn<>("Fulfillment Status");
        fulfillmentStatusCol.setCellValueFactory(new PropertyValueFactory<>("fulfillmentStatus"));

        TableColumn<Order, Object> fulfilledAtCol = new TableColumn<>("Fulfilled At");
        fulfilledAtCol.setCellValueFactory(new PropertyValueFactory<>("fulfilledAt"));

        tableView.getColumns().addAll(
                orderIdCol,
                customerIdCol,
                orderDateCol,
                orderStatusCol,
                fulfillmentStatusCol,
                fulfilledAtCol
        );

        loadOrders();

        setCenter(tableView);
    }

    private void loadOrders() {
        List<Order> orders = orderDAO.getAllOrders(); // all orders from DB using DAO

        masterList = FXCollections.observableArrayList(orders); // Wrap the list of orders in an ObservableList for JavaFX
        filteredList = new FilteredList<>(masterList, p -> true); //no filter applied, so all orders are shown

        tableView.setItems(filteredList);

        // ObservableList<Order> orderList = FXCollections.observableArrayList(orders);
        // tableView.setItems(orderList);
    }

    //applySearch uses "contains". this causes a lot of results/partial matches to show up when searching by ID. 
    // In the future, we may want to implement exact match for ID fields and "contains" for text fields.
    @Override 
    public void applySearch(String selectedField, String searchText) {
         //filter condition to filtered list based on search text and selected field
        filteredList.setPredicate(order -> {
            // If search text is empty, show all orders
            System.out.println("Checking order: " + order.getOrderId() + " against search text: " + searchText); //debugging line to confirm predicate is being evaluated

            if (searchText == null || searchText.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = searchText.toLowerCase();

            //If no field selected, search everything
            if (selectedField == null || selectedField.isEmpty()) {
                return (order.getOrderId() + "").contains(lowerCaseFilter)
                        || (order.getCustomerId() + "").contains(lowerCaseFilter)
                        || (order.getOrderStatus() != null && order.getOrderStatus().toLowerCase().contains(lowerCaseFilter))
                        || (order.getFulfillmentStatus() != null && order.getFulfillmentStatus().toLowerCase().contains(lowerCaseFilter));
            }

            // Field specific filtering
            switch (selectedField) {
                case "Order ID":
                    return (order.getOrderId() + "").contains(lowerCaseFilter);

                case "Customer ID":
                    return (order.getCustomerId() + "").contains(lowerCaseFilter);

                case "Order Status":
                    return order.getOrderStatus() != null &&
                            order.getOrderStatus().toLowerCase().contains(lowerCaseFilter);

                case "Fulfillment Status":
                    return order.getFulfillmentStatus() != null &&
                            order.getFulfillmentStatus().toLowerCase().contains(lowerCaseFilter);

                default:
                    return true;
            }
        });
    }
        

}




// psuedocode for handling row selection. Check testing file in resources as well!
//setRowFactory() -> click listener
//event.getClickCount() == 2 (double click) -> get selected order -> open new window with order details and option to update status.
//row.getItem() -> get the order associated with the clicked row.
//handleRowSelection(Order order) -> open new window (OrderDetailView) passing the selected order as a parameter.

// ensure that state changes happen in the database and not just locally.