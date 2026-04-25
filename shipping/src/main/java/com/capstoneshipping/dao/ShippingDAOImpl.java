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
import com.capstoneshipping.DataBase.DB_Constants;
import com.capstoneshipping.DataBase.DB_Queries;
import com.capstoneshipping.model.ShippingStatus;
import com.capstoneshipping.model.Shipping;

//add constants

public class ShippingDAOImpl implements ShippingDAO {
    private Connection connection;

    public ShippingDAOImpl() {
    }

    @Override
    public List<Shipping> getAllShipments() {
        List<Shipping> shipments = new ArrayList<>();

        if (this.connection == null){
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

}


