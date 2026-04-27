package com.capstoneshipping.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.capstoneshipping.model.OrderHistory;

public class ExportUtil {

    public static void exportOrderHistoryToCSV(List<OrderHistory> list, String filePath) {

        try (FileWriter writer = new FileWriter(filePath)) {

            // Header row
            writer.append("OrderHistoryID, OrderID, OldOrderStatus, NewOrderStatus, OldFulfillmentStatus, NewFulfillmentStatus, ChangedAt, Notes\n");

            // Data rows
            for (OrderHistory o : list) {
                writer.append(String.valueOf(o.getOrderHistoryId())).append(",");
                writer.append(String.valueOf(o.getOrderId())).append(",");
                writer.append(String.valueOf(o.getOldOrderStatus())).append(",");
                writer.append(String.valueOf(o.getNewOrderStatus())).append(",");
                writer.append(String.valueOf(o.getOldFulfillmentStatus())).append(",");
                writer.append(String.valueOf(o.getNewFulfillmentStatus())).append(",");
                writer.append(String.valueOf(o.getChangedAt())).append(",");
                writer.append(String.valueOf(o.getNotes())).append("\n");
            }

            System.out.println("Order history exported successfully to " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
