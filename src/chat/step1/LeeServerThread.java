package chat.step1;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LeeServerThread extends Thread{

	/*선언부*/
    Socket client;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    /*생성자*/
    public LeeServerThread(Socket client) {
        this.client = client;
        try {
            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());
            String msg = (String) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**/
	public LeeServerThread(LeeServer leeServer) {
		// TODO Auto-generated constructor stub
	}
}