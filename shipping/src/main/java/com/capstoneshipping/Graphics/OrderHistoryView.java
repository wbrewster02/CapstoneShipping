// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.OrderHistoryView version: 1.1
// Date Modified: 4/17/2026


// NEEDS FIXING

package com.capstoneshipping.Graphics;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.capstoneshipping.dao.OrderHistoryDAOImpl;
import com.capstoneshipping.model.OrderHistory;
import com.capstoneshipping.util.ExportUtil;

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
public class OrderHistoryView extends BorderPane{

    private TableView<OrderHistory> tableView;
    private OrderHistoryDAOImpl orderHistoryDAO;

    private ObservableList<OrderHistory> masterList;
    private FilteredList<OrderHistory> filteredList;

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");

    private ViewController viewController;

    public OrderHistoryView(ViewController viewController) {
        this.viewController = viewController;

        this.tableView = new TableView<>();
        this.orderHistoryDAO = new OrderHistoryDAOImpl();

        TableColumn<OrderHistory, Integer> orderHistoryId = new TableColumn<>("Order History ID");
        orderHistoryId.setCellValueFactory(new PropertyValueFactory<>("orderHistoryId"));

        TableColumn<OrderHistory, Integer> orderId = new TableColumn<>("Order ID");
        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<OrderHistory, LocalDateTime> oldOrderStatus = new TableColumn<>("Old Order Status");
        oldOrderStatus.setCellValueFactory(new PropertyValueFactory<>("oldOrderStatus"));
            
        TableColumn<OrderHistory, String> newOrderStatus = new TableColumn<>("New Order Status");
        newOrderStatus.setCellValueFactory(new PropertyValueFactory<>("newOrderStatus"));

        TableColumn<OrderHistory, String> oldFulfillmentStatus = new TableColumn<>("Old Fulfillment Status");
        oldFulfillmentStatus.setCellValueFactory(new PropertyValueFactory<>("oldFulfillmentStatus"));

        TableColumn<OrderHistory, String> newFulfillmentStatus = new TableColumn<>("New Fulfillment Status");
        newFulfillmentStatus.setCellValueFactory(new PropertyValueFactory<>("newFulfillmentStatus"));

        TableColumn<OrderHistory, LocalDateTime> changedAt = new TableColumn<>("Changed At");
        changedAt.setCellValueFactory(new PropertyValueFactory<>("changedAt"));
        // changedAt.setCellFactory(col -> new TableCell<OrderHistory, LocalDateTime>() {
        //     @Override
        //     protected void updateItem(LocalDateTime item, boolean empty) {
        //         super.updateItem(item, empty);

        //         if (empty || item == null) {
        //             setText(null);
        //         } else {
        //             setText(item.format(FORMATTER));
        //         }
        //     }
        // });
        TableColumn<OrderHistory, LocalDateTime> notes = new TableColumn<>("Order History Notes");
        notes.setCellValueFactory(new PropertyValueFactory<>("notes"));


        tableView.getColumns().addAll(
            List.of(
                orderHistoryId,
                orderId,
                oldOrderStatus,
                newOrderStatus,
                oldFulfillmentStatus,
                newFulfillmentStatus,
                changedAt,
                notes
            )        
        );

        //Add button 
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(10));

        Button exportBtn = new Button("Export CSV");

        exportBtn.setOnAction(e -> {
            //OrderHistoryDAO dao = new OrderHistoryDAOImpl(DatabaseConnection.getConnection());
            //List<OrderHistory> list = getAllOrders();
             List<OrderHistory> list =  orderHistoryDAO.getAllOrders();

            ExportUtil.exportOrderHistoryToCSV(list, "exports/order_history.csv");
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
        System.out.println(filteredList);
        // ObservableList<Order> orderList = FXCollections.observableArrayList(orders);
        // tableView.setItems(orderList);
    }

    //applySearch uses "contains". this causes a lot of results/partial matches to show up when searching by ID. 
    // In the future, we may want to implement exact match for ID fields and "contains" for text fields.
    // @Override 
    // public void applySearch(String selectedField, String searchText) {
    //      //filter condition to filtered list based on search text and selected field
    //     filteredList.setPredicate(order -> {
    //         // If search text is empty, show all orders
    //         System.out.println("Checking order: " + order.getOrderId() + " against search text: " + searchText); //debugging line to confirm predicate is being evaluated

    //         if (searchText == null || searchText.isEmpty()) {
    //             return true;
    //         }

    //         String lowerCaseFilter = searchText.toLowerCase();
    //     // int orderHistoryId, int orderId, String oldOrderStatus, String newOrderStatus, String oldFulfillmentStatus, String newFulfillmentStatus, LocalDateTime changedAt, String notes

    //         //If no field selected, search everything
    //         if (selectedField == null || selectedField.isEmpty()) {
    //             return (order.getOrderHistoryId() + "").contains(lowerCaseFilter)
    //                     || (order.getOrderId() + "").contains(lowerCaseFilter)
    //                     || (order.getOrderStatus() != null && 
    //                         order.getOrderStatus().toString().toLowerCase().contains(lowerCaseFilter))
    //                     || (order.getFulfillmentStatus() != null && 
    //                         order.getFulfillmentStatus().toString().toLowerCase().contains(lowerCaseFilter));
    //         }

    //         // Field specific filtering
    //         switch (selectedField) {
    //             case "Order ID":
    //                 return (order.getOrderId() + "").contains(lowerCaseFilter);

    //             case "Customer ID":
    //                 return (order.getCustomerId() + "").contains(lowerCaseFilter);

    //             case "Order Status":
    //                 return order.getOrderStatus() != null &&
    //                         order.getOrderStatus().toString().toLowerCase().contains(lowerCaseFilter);

    //             case "Fulfillment Status":
    //                 return order.getFulfillmentStatus() != null &&
    //                         order.getFulfillmentStatus().toString().toLowerCase().contains(lowerCaseFilter);

    //             default:
    //                 return true;
    //         }
    //     });
    // }

    public TableView<OrderHistory> getTableView(){
        return this.tableView;
    }

    public OrderHistoryDAOImpl getOrderDAO(){
        return this.orderHistoryDAO;
    }
}


