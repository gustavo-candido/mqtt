import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttPublishSample {

    public static void main(String[] args) {
        String topic        = "sensor/temperature";
        String content      = "Message from MqttPublishSample";
        int qos             = 2;
        String broker       = "tcp://127.0.0.1:1883";
        String clientId     = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

         try {
             MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
             MqttConnectOptions connOpts = new MqttConnectOptions();
             connOpts.setCleanSession(true);
             System.out.println("Connecting to broker: "+broker);
             sampleClient.connect(connOpts);
             System.out.println("Connected");
             System.out.println("Publishing message: "+content);

             while(true) {
                 Thread.sleep(1000);
                 int random_temp = 15 + (int) (Math.random()*30);
                 MqttMessage message = new MqttMessage(Integer.toString(random_temp).getBytes());
                 message.setQos(qos);
                 sampleClient.publish(topic, message);
                 System.out.println("Message published :" + random_temp);
             }
         } catch(MqttException me) {
             System.out.println("reason "+me.getReasonCode());
             System.out.println("msg "+me.getMessage());
             System.out.println("loc "+me.getLocalizedMessage());
             System.out.println("cause "+me.getCause());
             System.out.println("excep "+me);
             me.printStackTrace();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
    }
}