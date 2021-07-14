package com.microservices.gmail.listener;

import com.microservices.gmail.model.dto.StatusMessageDto;
import com.microservices.gmail.service.EmailSenderService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @Autowired
    private EmailSenderService service;

    @KafkaListener(topics = "TransactionTopic", groupId = "group_transaction")
    public void consume(String transaction){
        StatusMessageDto result = new StatusMessageDto<>();

        JSONObject transactionJson = new JSONObject(transaction);

        System.out.println("TransactionTopic : \n" + transaction);
        System.out.println("Data Customer    : \n" + transactionJson.getJSONObject("customerEntity"));
        System.out.println("Email Customer   : \n" + transactionJson.getJSONObject("customerEntity").getString("email"));

        service.sendSimpleEmail(transactionJson.getJSONObject("customerEntity").getString("email"),
                "Terima Kasih Telah Berbelanja! \n\n" +
                        "Halo " + transactionJson.getJSONObject("customerEntity").getString("name") + "\n" +
                        "Kamu baru saja membeli " + transactionJson.getJSONObject("productEntity").getString("productName") +
                        " sebanyak " + transactionJson.getInt("amount") + " buah, " +
                        "dengan total biaya sebesar Rp " + transactionJson.getDouble("cost") + ". \n" +
                        "Ditunggu transaksi selanjutnya bersama kami!\n" +
                        "With love, \nmicroservice-project.com.",
                "Microservices Project");
    }
}
