package chat.step1;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LeeServerThread extends Thread {
	/*선언부*/
	Socket client = null;
	LeeServer leeServer = null;
	ObjectOutputStream oos = null;//말할때 사용
	ObjectInputStream ois = null;//들을 때 사용
	String nickName = null;//현재 서버에 입장한 클라이언트 스레드의 닉네임 저장
	/*생성자*/
	public LeeServerThread(LeeServer leeServer) {
		this.leeServer = leeServer;
		this.client = leeServer.socket;
		System.out.println("client "+client +"\n");
		try {
			oos = new ObjectOutputStream(client.getOutputStream());
			ois = new ObjectInputStream(client.getInputStream());
			String msg = (String)ois.readObject();
		} catch (Exception e) {
		}
	}
	
	/*정의메소드*/

	/*메인메소드*/

}
