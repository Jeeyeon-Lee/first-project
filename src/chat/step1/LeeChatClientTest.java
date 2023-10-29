package chat.step1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class ClientSender extends Thread{
	/*선언부*/
	Socket socket; 
	DataOutputStream out;
	public ClientSender(Socket socket) {

	}
	@Override
	public void run() {
		LeeChatClientTest lcct = new LeeChatClientTest();
		
	}
}
public class LeeChatClientTest{
	
	/*생성자*/
	public LeeChatClientTest() {
	}
	/*메인*/
	public static void main(String[] args) {
		LeeChatClientTest lcct = new LeeChatClientTest();
		
		////////////////통신과 관련한 전역변수 추가 시작//////////////
		try {
			Socket socket = new Socket("127.0.0.1",3000);
			System.out.println("연결되었습니다."+"\n");
			InputStream is = socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			System.out.println("서버메세지 : "+dis.readUTF()+"\n");
			System.out.println("연결을 종료합니다.\n");
			dis.close();
			socket.close();
			System.out.println("연결이 종료되었습니다.\n");
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
