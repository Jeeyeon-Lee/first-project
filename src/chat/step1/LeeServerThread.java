package chat.step1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LeeServerThread extends Thread{

	/*선언부*/
    Socket client;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    List<ObjectOutputStream> outputStreams = new ArrayList<>();

    /*생성자*/
    public LeeServerThread(Socket client, List<ObjectOutputStream> outputStreams) {
        this.client = client;
        this.outputStreams = outputStreams;
        try {
            ois = new ObjectInputStream(client.getInputStream());
            oos = new ObjectOutputStream(client.getOutputStream());
            outputStreams.add(oos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }		
	/*사용자 메소드*/
    @Override
    public void run() {
            try {
                while (true) {
                	try {
						
                    String message = (String) ois.readObject();
                    broadcastMessage(message);
                	} catch (Exception e) {
                		System.out.println("클라이언트와 연결이 끊어졌습니다. ");
                		break;
                	}
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ois != null) {
                        ois.close();
                    }
                    if (oos != null) {
                        oos.close();
                    }
                    if (client != null) {
                        client.close();
                    }
                    outputStreams.remove(oos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    //발송 메소드
	public void broadcastMessage(String message) {
	    for (ObjectOutputStream oos : outputStreams) {
            try {
                oos.writeObject(message);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}