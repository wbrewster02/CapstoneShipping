package com.capstoneshipping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.capstoneshipping.DataBase.DB_Connection;
import com.capstoneshipping.DataBase.DB_Queries;
import com.capstoneshipping.model.Shipping;
import com.capstoneshipping.model.ShippingLabelData;
import com.capstoneshipping.model.ShippingStatus;

//add constants

public class ShippingDAOImpl implements ShippingDAO {
    private Connection connection;

    public ShippingDAOImpl() {
    }

    @Override
    public List<Shipping> getAllShipments() {
        List<Shipping> shipments = new ArrayList<>();

        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DB_Connection.getConnection();
            }
            try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.GET_ALL_SHIPPING);
            
                ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Shipping shipping = new Shipping(
                        rs.getInt("Shipping_ID"),
                        rs.getInt("Order_ID"),
                        rs.getDouble("Cost"),

                        rs.getTimestamp("Shipped_On") != null
                            ? rs.getTimestamp("Shipped_On").toLocalDateTime()
                            : null,

                        rs.getTimestamp("Expected_By") != null
                            ? rs.getTimestamp("Expected_By").toLocalDateTime()
                            : null,

                        ShippingStatus.fromDbValue(rs.getString("Ship_Status")),
                        rs.getString("Carrier"),
                        rs.getString("Tracking_Number"),

                        rs.getTimestamp("Created_At") != null
                            ? rs.getTimestamp("Created_At").toLocalDateTime()
                            : null,

                        rs.getTimestamp("Updated_At") != null
                            ? rs.getTimestamp("Updated_At").toLocalDateTime()
                            : null,

                        rs.getTimestamp("Status_Updated_At") != null
                            ? rs.getTimestamp("Status_Updated_At").toLocalDateTime()
                            : null,

                        rs.getString("Shipment_Notes"),
                        rs.getString("Return_Reason"),

                        rs.getInt("Shipping_Address_ID"),
                        rs.getInt("Billing_Address_ID")
                    );

                    shipments.add(shipping);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return shipments;
    }

    @Override
    public void updateShippingStatus(int shippingId, ShippingStatus status) {
        try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.UPDATE_SHIPPING_STATUS)) {

            stmt.setString(1, status.toDbValue());
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, shippingId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        
        }
    }

    //NEEDED Queries for UodateExpectedBy and UpdateShippedOn.
    @Override
    public void updateShippedOn(int shippingId, LocalDateTime shippedOn) {
        try (PreparedStatement stmt = connection.prepareStatement(
             "UPDATE Shipping SET Shipped_On = ? WHERE Shipping_ID = ?")) {

            if (shippedOn != null) {
                stmt.setTimestamp(1, Timestamp.valueOf(shippedOn));
            } else {
                stmt.setNull(1, Types.TIMESTAMP);
            }

            stmt.setInt(2, shippingId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    @Override
    public void updateExpectedBy(int shippingId, LocalDateTime expectedBy) {
        try ( PreparedStatement stmt = connection.prepareStatement(
             "UPDATE Shipping SET Expected_By = ? WHERE Shipping_ID = ?")) {

            if (expectedBy != null) {
                stmt.setTimestamp(1, Timestamp.valueOf(expectedBy));
            } else {
                stmt.setNull(1, Types.TIMESTAMP);
            }

            stmt.setInt(2, shippingId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean shippingExistsForOrder(int orderId) {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DB_Connection.getConnection();
            }

            try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.SHIPPING_EXISTS)) {

                stmt.setInt(1, orderId);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void updateShippingStatusByOrderId(int orderId, ShippingStatus status) {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DB_Connection.getConnection();
            }

            try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.UPDATE_SHIPPING_STATUS_BY_ORDER_ID)) {

                stmt.setString(1, status.toDbValue());
                stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                stmt.setInt(3, orderId);

                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetShippingForOrder(int orderId) {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DB_Connection.getConnection();
            }

            try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.RESET_SHIPPING_BY_ORDER_ID)) {
                stmt.setString(1, ShippingStatus.PENDING.toDbValue());
                stmt.setNull(2, Types.TIMESTAMP);
                stmt.setNull(3, Types.TIMESTAMP);
                stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                stmt.setInt(5, orderId);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ShippingLabelData getShippingLabelData(int shippingId) {
        ShippingLabelData labelData = null;

        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DB_Connection.getConnection();
            }

            try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.GET_SHIPPING_LABEL_DATA)) {

                stmt.setInt(1, shippingId);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {

                String customerName = rs.getString("First_Name") + " " + rs.getString("Last_Name");

                String shippingAddress =
                    rs.getString("Street") + "\n" +
                    rs.getString("City") + ", " +
                    rs.getString("State") + " " +
                    rs.getString("Zip_Code");

                String returnAddress = """
                    Elevate Retail
                    123 Warehouse Lane
                    Winston-Salem, NC 27101
                    """;

                labelData = new ShippingLabelData(
                    rs.getInt("Order_ID"),
                    customerName,
                    shippingAddress,
                    returnAddress,
                    rs.getString("Tracking_Number"),
                    rs.getString("Carrier")
                );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return labelData;
    }
    // @Override
    // public ShippingLabelData getShippingLabelData(int shippingId) {

    //     try {
    //         if (this.connection == null || this.connection.isClosed()) {
    //             this.connection = DB_Connection.getConnection();
    //         }

    //         // TEMP DEBUG CODE
    //         PreparedStatement stmt = connection.prepareStatement(
    //             "SELECT * FROM Customer_Address LIMIT 1"
    //         );

    //         ResultSet rs = stmt.executeQuery();

    //         ResultSetMetaData meta = rs.getMetaData();
    //         int columnCount = meta.getColumnCount();

    //         System.out.println("Customer_Address columns:");

    //         for (int i = 1; i <= columnCount; i++) {
    //             System.out.println(meta.getColumnName(i));
    //         }

    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }

    //     return null;
    // }

}


