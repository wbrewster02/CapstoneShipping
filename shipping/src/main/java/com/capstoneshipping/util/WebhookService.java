package com.capstoneshipping.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebhookService {
    //create variable for webhook URL, add to gitignore
    private static final String WEBHOOK_URL = "https://mobile.csc-289-760b.org/api/webhooks/emit";

    public void notifyShippingStatusUpdated(int orderId) {
        String json = """
        {
          "event": "shipping.status.updated",
          "payload": {
            "orderId": %d
          }
        }
        """.formatted(orderId);

        //debug print to verify payload and URL
        System.out.println("Sending webhook to: " + WEBHOOK_URL);
        System.out.println(json);

        //POST logic
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(WEBHOOK_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Webhook response: " + response.statusCode());
            System.out.println("Response body: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
