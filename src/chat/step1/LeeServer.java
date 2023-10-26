package chat.step1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class LeeServer extends Thread{
	/*선언부*/
	JFrame jf = new JFrame();
	JScrollPane jsp_log = new JScrollPane();
	List<LeeChatTest> userList = null;
	ServerSocket server = null;
	Socket socket = null;
	Thread tst = null;
	/*생성자*/
	public LeeServer() {
		initDisplay();
		run();
	}
	/*정의메소드*/
	//런 -> 서버열기, 유저받기, 로그 띄우기, 서버스레드 열기 -> 예외처리 넣기 
	@Override
	public void run() {
		userList = new Vector<>();
		boolean isStop = false;
		try {
			server = new ServerSocket(1004);
			System.out.println("Server Ready....\n");
			while(true) {
				System.out.println("client info:" + socket +"\n") ;
				tst = new LeeServerThread(this);
				tst.start();
			}
		} catch (NullPointerException ne) {
			ne.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*메인메소드*/
	public static void main(String[] args) {
		LeeServer ls = new LeeServer();
	}
	public void initDisplay() {
		jf.add("Center", jsp_log);
		jf.setTitle("서버측 로그 출력화면 제공...");
		jf.setSize(800, 600);
		jf.setVisible(true);		
	}
}
