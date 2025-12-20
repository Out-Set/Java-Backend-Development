package com.savan.bulkRequestProcess.helpers;

import com.savan.bulkRequestProcess.processors.PrepareRequestProcessor;
import jakarta.jms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActiveMQReader {

    @Value("${activemq.read.message.batchSize}")
    private Integer BATCH_SIZE;

    @Autowired
    private Connection connection;

    @Autowired
    private PrepareRequestProcessor prepareRequestProcessor;

    // Get Messages
    public void dequeueMessages(String QUEUE_NAME, Integer QUEUE_SIZE) {

        int batchSizeToRead = Math.min(QUEUE_SIZE, BATCH_SIZE);
        List<String> messages = new ArrayList<>();

        // Set session to be transacted (true)
        try (Session session = connection.createSession(true, Session.SESSION_TRANSACTED)) {

            connection.start();
            Queue queue = session.createQueue(QUEUE_NAME);
            MessageConsumer consumer = session.createConsumer(queue);

            int count = 0;
            while (count < batchSizeToRead) {
                Message message = consumer.receive(1000); // Non-blocking read
                if (message == null) break;  // Exit if no more messages

                if (message instanceof TextMessage) {
                    messages.add(((TextMessage) message).getText());
                }
                count++;
            }

            // Create exception
            // int res = 10/0;

            // Read-Messages and Prepare-Request-Model
            prepareRequestProcessor.readMessagesAndPrepareRequestModel(messages);

            // Commit after processing batch
            session.commit();

        } catch (Exception e) {
            messages.clear();
            System.out.println("An exception occurred, rolling back the transaction ... : "+e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Messages in AMQ Reader function: "+messages);
    }
}

/*
    // Get Messages
    public List<String> dequeueMessages(String QUEUE_NAME, Integer QUEUE_SIZE) {

        int batchSizeToRead = Math.min(QUEUE_SIZE, BATCH_SIZE);

        List<String> messages = new ArrayList<>();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);


        // Set session to be transacted (true)
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(true, Session.SESSION_TRANSACTED)) {

            connection.start();
            Queue queue = session.createQueue(QUEUE_NAME);
            MessageConsumer consumer = session.createConsumer(queue);

            int count = 0;
            while (count < batchSizeToRead) {
                Message message = consumer.receive(1000); // Non-blocking read
                if (message == null) break;  // Exit if no more messages

                if (message instanceof TextMessage) {
                    messages.add(((TextMessage) message).getText());
                }
                count++;
            }

            // Read-Messages and Prepare-Request-Model
            prepareRequestProcessor.readMessagesAndPrepareRequestModel(messages);

            // Commit after processing batch
            session.commit();

        } catch (Exception e) {
            messages.clear();
            System.out.println("An exception occurred, rolling back the transaction ... : "+e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Messages in AMQ Reader function: "+messages);
        return messages;
    }

     */


