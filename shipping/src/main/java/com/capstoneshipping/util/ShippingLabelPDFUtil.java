package com.capstoneshipping.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.capstoneshipping.model.ShippingLabelData;

public class ShippingLabelPDFUtil {
    public static File createLabel(ShippingLabelData data) {
        //File file = new File("shipping-label-" + data.getOrderId() + ".pdf");
        File file = null;

        try (PDDocument document = new PDDocument()) {

            file = File.createTempFile("shipping-label-" + data.getOrderId(), ".pdf");
            file.deleteOnExit(); // optional: deletes when app closes

            PDPage page = new PDPage(PDRectangle.LETTER);
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);

            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.setLeading(14.5f);
            content.newLineAtOffset(50, 700);

            content.showText("SHIPPING LABEL");
            content.newLine();

            content.newLine();
            content.showText("FROM:");
            content.newLine();
            for (String line : data.getReturnAddress().split("\n")) {
                content.showText(line);
                content.newLine();
            }

            content.newLine();
            content.newLine();
            content.showText("TO:");
            content.newLine();
            content.showText(data.getCustomerName());
            content.newLine();
            for (String line : data.getShippingAddress().split("\n")) {
                content.showText(line);
                content.newLine();
            }

            content.newLine();
            content.newLine();
            content.showText("Order ID: " + data.getOrderId());
            content.newLine();
            content.showText("Carrier: " + data.getCarrier());
            content.newLine();
            content.showText("Tracking #: " + data.getTrackingNumber());

            content.endText();
            content.close();

            document.save(file);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    public static void openPDF(File file) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
