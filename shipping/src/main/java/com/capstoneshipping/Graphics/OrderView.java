// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.OrderView version: 1.0
// Date Modified: 4/3/2026

package com.capstoneshipping.Graphics;

import com.capstoneshipping.dao.OrderDAOImpl;
import com.capstoneshipping.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import java.util.List;


public class OrderView extends BorderPane {

    private TableView<Order> tableView;
    private OrderDAOImpl orderDAO;

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
        List<Order> orders = orderDAO.getAllOrders();
        ObservableList<Order> orderList = FXCollections.observableArrayList(orders);
        tableView.setItems(orderList);
    }
}
