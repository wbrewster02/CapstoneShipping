package com.capstoneshipping.dao;

import com.capstoneshipping.DataBase.DB_Connection;
import com.capstoneshipping.DataBase.DB_Constants;
import com.capstoneshipping.DataBase.DB_Queries;
import com.capstoneshipping.model.ShippingHistory;
import com.capstoneshipping.model.ShippingStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class ShippingHistoryDAOImpl implements ShippingHistoryDAO {

    private Connection connection;

    public ShippingHistoryDAOImpl() {}

    @Override
    public List<ShippingHistory> getAllShippingHistory() {
        List<ShippingHistory> historyList = new ArrayList<>();

        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DB_Connection.getConnection();
            }

            try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.GET_ALL_SHIPPING_HISTORY);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    ShippingHistory history = new ShippingHistory(
                        rs.getInt(DB_Constants.SHIPPING_HISTORY_ID),
                        rs.getInt(DB_Constants.SHIPPING_HISTORY_SHIPPING_ID),

                        rs.getString(DB_Constants.SHIPPING_HISTORY_OLD_STATUS),
                        rs.getString(DB_Constants.SHIPPING_HISTORY_NEW_STATUS),

                        rs.getTimestamp(DB_Constants.SHIPPING_HISTORY_CHANGED_AT) != null
                            ? rs.getTimestamp(DB_Constants.SHIPPING_HISTORY_CHANGED_AT).toLocalDateTime()
                            : null,

                        rs.getString(DB_Constants.SHIPPING_HISTORY_NOTES),

                        rs.getInt(DB_Constants.SHIPPING_HISTORY_ORDER_ID),
                        rs.getString(DB_Constants.SHIPPING_HISTORY_CARRIER),
                        rs.getString(DB_Constants.SHIPPING_HISTORY_TRACKING_NUMBER),

                        ShippingStatus.fromDbValue(rs.getString(DB_Constants.SHIPPING_HISTORY_SHIPPED_STATUS)),

                        rs.getTimestamp(DB_Constants.SHIPPING_HISTORY_SHIPPED_ON) != null
                            ? rs.getTimestamp(DB_Constants.SHIPPING_HISTORY_SHIPPED_ON).toLocalDateTime()
                            : null,

                        rs.getTimestamp(DB_Constants.SHIPPING_HISTORY_EXPECTED_BY) != null
                            ? rs.getTimestamp(DB_Constants.SHIPPING_HISTORY_EXPECTED_BY).toLocalDateTime()
                            : null
                    );

                    historyList.add(history);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historyList;
    }

    @Override
    public void insertShippingHistory(ShippingHistory history) {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DB_Connection.getConnection();
            }

            try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.INSERT_SHIPPING_HISTORY)) {

                stmt.setInt(1, history.getShippingId());
                stmt.setString(2, history.getOldStatus());
                stmt.setString(3, history.getNewStatus());

                if (history.getChangedAt() != null) {
                    stmt.setTimestamp(4, Timestamp.valueOf(history.getChangedAt()));
                } else {
                    stmt.setNull(4, Types.TIMESTAMP);
                }

                stmt.setString(5, history.getNotes());

                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean shippingHistoryExists(int shippingId) {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DB_Connection.getConnection();
            }

            try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.SHIPPING_HISTORY_EXISTS)) {

                stmt.setInt(1, shippingId);
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
}
