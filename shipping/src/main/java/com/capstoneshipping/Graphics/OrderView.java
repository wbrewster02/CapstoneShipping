// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.OrderView version: 1.1
// Date Modified: 4/17/2026

package com.capstoneshipping.Graphics;

import com.capstoneshipping.model.FulfillmentStatus;
import com.capstoneshipping.model.Order;
import com.capstoneshipping.model.OrderStatus;
import com.capstoneshipping.dao.OrderDAOImpl;

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


public class OrderView extends BorderPane implements SearchableView {

    private TableView<Order> tableView;
    private OrderDAOImpl orderDAO;

    //holds the full list of orders(unfiltered) and the filtered list based on search criteria
    private ObservableList<Order> masterList;
    private FilteredList<Order> filteredList;

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");

    private ViewController viewController;

    public OrderView(ViewController viewController) {
        this.viewController = viewController;

        tableView = new TableView<>();
        orderDAO = new OrderDAOImpl();

        TableColumn<Order, Integer> orderIdCol = new TableColumn<>("Order ID");
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<Order, Integer> customerIdCol = new TableColumn<>("Customer ID");
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        //TableColumn<Order, Object> orderDateCol = new TableColumn<>("Order Date"); //here
        //orderDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        TableColumn<Order, LocalDateTime> orderDateCol = new TableColumn<>("Order Date");
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        orderDateCol.setCellFactory(col -> new TableCell<Order, LocalDateTime>() {
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


        TableColumn<Order, OrderStatus> orderStatusCol = new TableColumn<>("Order Status");
        orderStatusCol.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        orderStatusCol.setCellFactory(col -> new TableCell<Order, OrderStatus>() {
            @Override
            protected void updateItem(OrderStatus item, boolean empty) {
                super.updateItem(item, empty);

                // Clear previous state
                setId(null);
                
                if (empty || item == null){
                    setText(null);
                    return;
                }
                // Get the Order object from the row
                Order order = getTableView().getItems().get(getIndex());
                if (order == null) return;

                switch (item) {
                    case PENDING -> {
                        setText(item.toDbValue()); 
                        setId("status-pending");
                    }
                    case PAID -> { 
                        setText(item.toDbValue()); 
                        setId("status-paid");
                    }
                    case READY_FOR_FULFILLMENT -> {
                        setText(item.toDbValue()); 
                        setId("status-ready_for_fulfillment");
                    }
                    case FULFILLED ->{ 
                        setText(item.toDbValue()); 
                        setId("status-fulfilled");
                    }
                    case CANCELLED ->{ 
                        setText(item.toDbValue()); 
                        setId("status-cancelled");
                    }
                }
            }
        });
        TableColumn<Order, FulfillmentStatus> fulfillmentStatusCol = new TableColumn<>("Fulfillment Status");
        fulfillmentStatusCol.setCellValueFactory(new PropertyValueFactory<>("fulfillmentStatus"));
        fulfillmentStatusCol.setCellFactory(col -> new TableCell<Order, FulfillmentStatus>() {
            @Override
            protected void updateItem(FulfillmentStatus item, boolean empty) {
                super.updateItem(item, empty);

                // Clear previous state
                setId(null);
                
                if (empty || item == null){
                    setText(null);
                    return;
                }
                // Get the Order object from the row
                Order order = getTableView().getItems().get(getIndex());
                
                if (order == null) return;

                setText(item.toDbValue());

                switch (item) {
                    case PENDING -> { 
                        setId("status-pending");
                    }
                    case PROCESSING -> { 
                        setId("status-processing");
                    }
                    case PACKED -> { 
                        setId("status-packed");
                    }
                    case READY_TO_SHIP ->{ 
                        setId("status-ready");
                    }
                    case FULFILLED ->{ 
                        setId("status-fulfilled");
                    }
                }
            }
        });

        TableColumn<Order, LocalDateTime> fulfilledAtCol = new TableColumn<>("Fulfilled At");
        fulfilledAtCol.setCellValueFactory(new PropertyValueFactory<>("fulfilledAt"));
        fulfilledAtCol.setCellFactory(col -> new TableCell<Order, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);

                // Clear previous state
                setText(null);
                setId(null);

                if (empty || item == null) return;

                setText(item.format(FORMATTER));
                
            }
        });
        
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


        //---------------------- Selecting a row and opening details view ----------------------
        tableView.setRowFactory(tv -> {
            TableRow<Order> row = new TableRow<>();
<<<<<<< HEAD
            
            // Highlight fulfilled orders in light green for easy identification
=======

            //Highlight fulfilled orders in light green for easy identification
>>>>>>> main
            row.itemProperty().addListener((obs, oldOrder, newOrder) -> {
                
                row.setId(null);
                // Get the Order object from the row
                if (newOrder == null) return;

                FulfillmentStatus fulfillmentStatus = newOrder.getFulfillmentStatus();
                OrderStatus orderStatus = newOrder.getOrderStatus();

                if (orderStatus == OrderStatus.CANCELLED) {
                    row.setId("status-cancelled");

                } else if (fulfillmentStatus == FulfillmentStatus.FULFILLED
                        && orderStatus == OrderStatus.FULFILLED) {
                    row.setId("status-complete");

                } else if (fulfillmentStatus == FulfillmentStatus.PENDING
                        && orderStatus == OrderStatus.FULFILLED) {
                    row.setId("status-pending");
                }

                
            });

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Order selectedOrder = row.getItem();
                    System.out.println(selectedOrder.getOrderId());
                    this.viewController.openOrderDetailView(selectedOrder);
                }
            });

            return row;
        });
        //----------------------

        

        loadOrders();

        setCenter(tableView);
    }

    public void loadOrders() {
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
                        || (order.getOrderStatus() != null && 
                            order.getOrderStatus().toString().toLowerCase().contains(lowerCaseFilter))
                        || (order.getFulfillmentStatus() != null && 
                            order.getFulfillmentStatus().toString().toLowerCase().contains(lowerCaseFilter));
            }

            // Field specific filtering
            switch (selectedField) {
                case "Order ID":
                    return (order.getOrderId() + "").contains(lowerCaseFilter);

                case "Customer ID":
                    return (order.getCustomerId() + "").contains(lowerCaseFilter);

                case "Order Status":
                    return order.getOrderStatus() != null &&
                            order.getOrderStatus().toString().toLowerCase().contains(lowerCaseFilter);

                case "Fulfillment Status":
                    return order.getFulfillmentStatus() != null &&
                            order.getFulfillmentStatus().toString().toLowerCase().contains(lowerCaseFilter);

                default:
                    return true;
            }
        });
    }

    public TableView<Order> getTableView(){
        return this.tableView;
    }

    public OrderDAOImpl getOrderDAO(){
        return this.orderDAO;
    }


}




// psuedocode for handling row selection. Check testing file in resources as well!
//setRowFactory() -> click listener
//event.getClickCount() == 2 (double click) -> get selected order -> open new window with order details and option to update status.
//row.getItem() -> get the order associated with the clicked row.
//handleRowSelection(Order order) -> open new window (OrderDetailView) passing the selected order as a parameter.

// ensure that state changes happen in the database and not just locally.