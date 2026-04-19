// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.OrderHistoryView version: 1.1
// Date Modified: 4/17/2026


// NEEDS FIXING

package com.capstoneshipping.Graphics;

import com.capstoneshipping.model.Order;
import com.capstoneshipping.model.OrderHistory;
import com.capstoneshipping.dao.OrderDAOImpl;
import com.capstoneshipping.dao.OrderHistoryDAOImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class OrderHistoryView {

    private TableView<OrderHistory> tableView;
    private OrderHistoryDAOImpl OrderHistoryDAO;

    //holds the full list of orders(unfiltered) and the filtered list based on search criteria
    private ObservableList<OrderHistory> masterList;
    private FilteredList<OrderHistory> filteredList;

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");

    private ViewController viewController;

    public OrderHistoryView(ViewController viewController) {
        this.viewController = viewController;

        tableView = new TableView<>();
        OrderHistoryDAO = new OrderHistoryDAOImpl();

        TableColumn<OrderHistory, Integer> orderIdCol = new TableColumn<>("Order ID");
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<OrderHistory, Integer> customerIdCol = new TableColumn<>("Customer ID");
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        TableColumn<OrderHistory, LocalDateTime> orderDateCol = new TableColumn<>("Order Date");
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
            
        TableColumn<OrderHistory, String> orderStatusCol = new TableColumn<>("Order Status");
        orderStatusCol.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));

        TableColumn<OrderHistory, String> fulfillmentStatusCol = new TableColumn<>("Fulfillment Status");
        fulfillmentStatusCol.setCellValueFactory(new PropertyValueFactory<>("fulfillmentStatus"));

        TableColumn<OrderHistory, LocalDateTime> fulfilledAtCol = new TableColumn<>("Fulfilled At");
        fulfilledAtCol.setCellValueFactory(new PropertyValueFactory<>("fulfilledAt"));

        tableView.getColumns().addAll(
            List.of(
                orderIdCol,
                customerIdCol,
                orderDateCol,
                orderStatusCol,
                fulfillmentStatusCol,
                fulfilledAtCol
            )        
        );

    }
}
