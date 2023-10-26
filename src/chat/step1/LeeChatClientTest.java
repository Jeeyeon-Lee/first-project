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
	Socket socket; 
	DataOutputStream out;
	public ClientSender(Socket socket) {
		this.socket = socket;
		try {
			out = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	@Override
	public void run() {
		LeeChatClientTest lcct = new LeeChatClientTest();
		
	}
}
public class LeeChatClientTest{
	/*선언부*/
	JFrame jf = new JFrame();
	static JTextArea jta_log = new JTextArea(10,60);
	JScrollPane jsp_log = new JScrollPane(jta_log);
	
	/*생성자*/
	public LeeChatClientTest() {
		initDisplay();
	}
	/*메인*/
	public static void main(String[] args) {
		LeeChatClientTest lcct = new LeeChatClientTest();
		////////////////통신과 관련한 전역변수 추가 시작//////////////
		try {
			Socket socket = new Socket("127.0.0.1",3000);
			jta_log.append("연결되었습니다."+"\n");
			InputStream is = socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			jta_log.append("서버메세지 : "+dis.readUTF()+"\n");
			jta_log.append("연결을 종료합니다.\n");
			dis.close();
			socket.close();
			jta_log.append("연결이 종료되었습니다.\n");
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*창구현 메소드*/
	public void initDisplay() {
		jf.add("Center", jsp_log);
		jf.setTitle("클라이언트측 테스트 화면 제공...");
		jf.setSize(400, 400);
		jf.setVisible(true);	
		jf.setLocation(600,400);
	}
}
