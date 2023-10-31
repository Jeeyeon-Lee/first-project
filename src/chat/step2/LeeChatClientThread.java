package chat.step2;



public class LeeChatClientThread extends Thread {
	LeeChatClientTest cc = null;
	String nickName = null;
	public LeeChatClientThread(LeeChatClientTest cc) {
		//아래 초기화를 생략하면 ChatClientThread클래스안에서 ChatClient
		//의 변수나 메소드를 호출할 때 NullPointerException발동
		this.cc = cc;
		this.nickName = cc.nickName;
	}
	//서버에서 말한 내용을 들어봅시다.
	//말하기는 ChatClient 의 actionPerformed에서 JTextField에 엔터 쳤을 때 처리합니다.
	public void run() {
		cc.jta_display.append(nickName+"님이 입장하였습니다.\n");
		boolean isStop = false;
		while(!isStop) {
			try {
				String msg = "";
				msg = (String)cc.ois.readObject();
				cc.jta_display.append("["+nickName+"]"+msg+"\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}