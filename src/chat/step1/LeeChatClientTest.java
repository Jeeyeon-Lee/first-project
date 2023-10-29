package chat.step1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

class ClientSender extends Thread implements ActionListener{
	/*선언부*/
	Scanner sc = new Scanner(System.in);
	Socket socket; 
	String 				nickName= null;//닉네임 등록
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	/*메소드 - 초기화, 연결*/
	public void init() {
		try {
			socket = new Socket("127.0.0.1",3000);
			System.out.println("연결되었습니다."+"\n");
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			oos.writeObject(100+"|"+nickName);//말하기 시전 - 서버한테 - 듣고 말하기
			oos.flush();
			System.out.println("연결을 종료합니다.\n");
			socket.close();
			System.out.println("연결이 종료되었습니다.\n");
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//말하기 구현 - > oos.writeObject("200|kiwi|tomato|오늘 스터디할까?");//프로토콜설계
		String msg = sc.nextLine();
		try {
			oos.writeObject(200+"|"+nickName+"|"+msg);
			oos.flush();
		} catch (Exception e2) {
			e2.getStackTrace();
		}
	}
}
public class LeeChatClientTest{
	/*메인*/
	public static void main(String[] args) {
		ClientSender clientSender = new ClientSender();
		System.out.print("닉네임을 입력하세요: ");
        try {
             clientSender.nickName = new BufferedReader(new InputStreamReader(System.in)).readLine();
             clientSender.init();

             while (true) {
                 clientSender.actionPerformed(null);
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}