package chat.step3;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
//인터페이스는 여러개 사용가능!
public class ZipCodeView extends JFrame implements ActionListener, FocusListener,MouseListener {
	//선언부
	String zdo = null;
	//물리적으로 떨어져 있는 db서버와 연결통로 만들기
	Connection 			con 	= null;
	//위에서 연결되면 쿼리문을 전달할 전령의 역할을 하는 인터페이스 객체 생성하기
	PreparedStatement 	pstmt 	= null;
	//조회된 결과를 화면에 처리해야 하므로 오라클에 커서를 조작하기 위해 ResultSet추가
	ResultSet 			rs 		= null;
	//JFrame 은 기본적으로 BorderLayout이다(동,서,남,북,중앙 배치)
	//디폴트는 FlowLayout, ->  BorderLayout -> setLayout
	//jp_north.add("Center", jtf_dong)
	//jp_north.add("West",jbtn_search)
	JPanel jp_north = new JPanel();//Div태그 span생각
	//insert here
	String zdos[] = {"전체","서울","경기","강원"};
	String zdos2[] = {"전체","부산","전남","대구"};
	Vector<String> vzdos = new Vector<>();//vzdos.size()==>0
	JComboBox jcb_zdo = new JComboBox(zdos);//West
	JComboBox jcb_zdo2 = null;//West
	JTextField jtf_search = new JTextField("동이름을 입력하세요.");//Center
	JButton jbtn_search = new JButton("조회");//East
	String cols[] = {"우편번호","주소"};
	String data[][] = new String[0][2];
	DefaultTableModel dtm_zipcode = new DefaultTableModel(data,cols);
	JTable jtb_zipcode = new JTable(dtm_zipcode);
	JTableHeader jth = jtb_zipcode.getTableHeader();
	JScrollPane jsp_zipcode = new JScrollPane(jtb_zipcode
			,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	String zdos3[] = null;
	DBConnectionMgr dbMgr = null;//싱글톤 패턴으로 관리한다. 복제본을 만들지 않는다. 절대로....
	MemberShipView memberShipView = null;
	/*생성자*/
	public ZipCodeView() {}
	public ZipCodeView(MemberShipView memberShipView) {
		this.memberShipView = memberShipView;
	}
	//화면처리부
	public void initDisplay() {
		jtb_zipcode.requestFocus();
		jtb_zipcode.addMouseListener(this);
		jtf_search.addFocusListener(this);
		jbtn_search.addActionListener(this);
		jtf_search.addActionListener(this);
		//북쪽 배치하는 속지를 FlowLayout-> 동서남북중앙 - UI솔루션
		jp_north.setLayout(new BorderLayout());
		/*	*/
		//vzdos.copyInto(zdos2);
		for(int x=0;x<zdos2.length;x++) {
			vzdos.add(zdos2[x]);
		}
		for(String s:vzdos) {
			System.out.println("s===>"+s);
		}
		//jcb_zdo2 = new JComboBox(zdos3);//West
		//jp_north.add("East",jcb_zdo2);
		jp_north.add("Center",jtf_search);
		jp_north.add("East",jbtn_search);
		this.add("North",jp_north);
		this.add("Center",jsp_zipcode);
		this.setTitle("우편번호 검색");
		this.setSize(430, 400);
		this.setVisible(true);
	}
	//메인메소드
	public static void main(String[] args) {
		ZipCodeView zcs = new ZipCodeView();
		zcs.initDisplay();//화면이 먼저 열리도록 하고 오라클서버를 나중에 연결하자
		//zcs.refreshData("가산");
	}
	/* ***********************************************************************************
	사용자로부터 동이름 입력받아서 조건 검색을 구현하기
	@param dong
	화면이 존재하는 경우, DB서버에서 가져온 정보를 가지고 후처리 해야함.
	후처리(화면에 뿌리는 일), 전처리(오라클과 커넥션)
	return 타입을 void로 했으니까 이 메소드에서 화면처리까지 진행 함.
	UI - react - 화면과 로직(모델-UI&데이터셋)의 분리
	데이터셋에 데이터를 추가하는 것은 row 단위(1차배열, Vector)로 처리됨. 
	DefaultTableModel -> addRow(Object[])->1개의 로우, addRow(Vector)
	* sql문
  		select  zipcode, address - 두개의 컬럼 DTO or Map -> List에 담기 ->row레벨 담기 
		from zipcode_t
		where dong like '당산' || '%';
		
	* 집에서 발생하는 오류들
	* ClassNotFountException -> jar -> build path
	* NullPointerException발동 -> con.prepareStatement("SELECT문") - con이 널이다. ip오류(1521 ++)
	* scott/tiger 인증실패
	* sid가 이미 설치된 컴퓨터
	* SQLException 코드에서 단위테스트 해볼 것!
	//java 디버깅 이클립스에서, sql에러는 토드에서 확인하기
	************************************************************************************/
	//데이터 파라미터값으로 새로고침 
	public void refreshData(String dong) {
		List<Map<String, Object>> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();           
		sql.append("SELECT zipcode, address    ");
		sql.append("  FROM zipcode_t           ");
		sql.append(" WHERE dong LIKE ?||'%'");
		dbMgr = DBConnectionMgr.getInstance();
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dong); //물음표 자리로 값이 치환이 됨. 
			rs = pstmt.executeQuery();
			Map<String, Object> rmap = null;
			while(rs.next()) {
				//Map 사용하여 넣어보자
				rmap = new HashMap<>();
				rmap.put("zipcode", rs.getInt("zipcode"));
				rmap.put("address", rs.getString("address"));
				list.add(rmap);
			}
			System.out.println(list); //단위테스트 : 당산의 로우는 33개였는데 저장되는것도 갯수가 맞는지 확인
			//메소드 설계에서 리턴타입 빠져있으므로 화면처리까지 여기서 해야함. 
			for(int i=0;i<list.size();i++) {
				Map<String, Object> map = list.get(i);
				Vector<Object> v = new Vector<>();
				v.add(0, map.get("zipcode"));
				v.add(1, map.get("address"));
				dtm_zipcode.addRow(v);
			}
			
		} catch(SQLException se) { 
			System.out.println(sql.toString()); //출력된 쿼리문 갈무리해서 토드에서 확인해보기. 
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == jbtn_search || obj == jtf_search) { 
			System.out.println(jtf_search.getText()); //입력받은 텍스트 가져와보기
			String dong = jtf_search.getText();
			refreshData(dong);//이벤트 들어올 때마다 새로고침
		}
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		Object obj = e.getSource();
		//서치에 커서를 두었니?  커서 놓는 칸을 빈 칸으로 만들어줘
		if(obj==jtf_search) {
			jtf_search.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/*마우스 이벤트*/
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getClickCount()==2) { //더블클릭했니?
			//화면에서 더블클릭한 행의 인덱스값 반환
			int index = jtb_zipcode.getSelectedRow();
			int zipcode = (int) dtm_zipcode.getValueAt(index, 0);
			String address = (String)dtm_zipcode.getValueAt(index, 1);
			System.out.println(zipcode + ", " + address);
			//부모의 주소번지가 필요함. 
			//insert here 회원가입창에서 우편번호와 주소자리 조회된 결과를 자동으로 출력해줌
			//MembershipView인스턴스화하면 그 때 즉시 ZipCodeView 객체가 생성되고 이 때 파라미터 자리에
			//this는 앞에 인스턴스화를 통해 현재 로딩 중인 그 원본이므로 복사본이 아니다...
			memberShipView.jtf_zipcode.setText(String.valueOf(zipcode));
			memberShipView.jtf_address.setText(String.valueOf(address));
			
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}