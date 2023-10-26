package chat.step1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class LeeServer{
	/*선언부*/
	JFrame jf = new JFrame();
	JTextArea jta_log = new JTextArea(10,60);
	JScrollPane jsp_log = new JScrollPane(jta_log);
	ServerSocket server = null;
	Socket socket = null;
	Thread tst = null;
	List<LeeServerThread> userList = null;
	/*생성자*/
	public LeeServer() {
		initDisplay();
		start();
	}
	/*정의메소드*/
	//스타트 -> 서버열기, 유저받기, 로그 띄우기, 서버스레드로 보내기 + 예외처리 넣기 
	public void start() {
		userList = new Vector<>();
		boolean isStop = false;
		while (!isStop) {
			try {
			server = new ServerSocket(3000);
			jta_log.append(getTime() + " | Server Ready....\n");
				try {
					jta_log.append(getTime() + " | client  연결 요청 대기 중...\n");
					socket = server.accept();
					jta_log.append(getTime() + " | client info : " + socket.getPort() + "접속하였습니다.\n");
					tst = new LeeServerThread(this);
					tst.start();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (Exception e2) {
				e2.getStackTrace();
			}
		}
	}
	/*메인메소드*/
	public static void main(String[] args) {
		LeeServer ls = new LeeServer();
	}

	/*시간 가져오기 */
	private String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	}
	/*창구현 메소드*/
	public void initDisplay() {
		jf.add("Center", jsp_log);
		jf.setTitle("서버측 로그 출력화면 제공...");
		jf.setSize(400, 400);
		jf.setVisible(true);	
		jf.setLocation(200,400);
	}
}
