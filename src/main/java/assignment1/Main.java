package assignment1;

import assignment1.measurement.model.MeasurementService;
import assignment1.measurement.model.dto.MeasurementDto;
import assignment1.reader.Reader;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class Main {
    private final static String QUEUE_NAME = "MessageQueue";
    private final static String HOST = "dwyyosnw";
    private final static Integer PORT = 5672;
    private final static String PASSWORD = "Y42r8rUEy5VpeYLYo3JnuLEOUE965BJ2";
    private final static String URL = "amqps://dwyyosnw:Y42r8rUEy5VpeYLYo3JnuLEOUE965BJ2@goose.rmq2.cloudamqp.com/dwyyosnw";

    private static MeasurementService measurementService;

    public static void main(String[] args) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {

        ApplicationContext app = SpringApplication.run(Main.class, args);
        measurementService = app.getBean(MeasurementService.class);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setPassword(PASSWORD);
        factory.setUri(URL);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicConsume(QUEUE_NAME, true, callBackFunction, consumerTag -> {
        });
    }

    private static final DeliverCallback callBackFunction = ((consumerTag, message) -> {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        JSONParser parser = new JSONParser();
        System.out.println("Received: " + msg);

        try {
            JSONObject data = (JSONObject) parser.parse(msg);
            Float consumption = Float.valueOf((String) data.get("measurement_value"));
            Long deviceId = (Long) data.get("device_id");
            Timestamp date = Timestamp.valueOf((String) data.get("timestamp"));

            System.out.println("Value is+ "+consumption);

            MeasurementDto measurementDto = MeasurementDto.builder()
                    .date(date)
                    .deviceId(deviceId)
                    .reading_value(consumption).
                    build();

            measurementService.create(measurementDto);
            measurementService.checkMeasurementLimitForDevice(measurementDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Received: " + msg);
    });
}
