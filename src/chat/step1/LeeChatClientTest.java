package chat.step1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LeeChatClientTest {
    private JFrame frame;
    private JTextArea chatTextArea;
    private JTextField messageTextField;
    private JButton sendButton;

    public LeeChatClientTest() {
        frame = new JFrame("Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatTextArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        messageTextField = new JTextField();
        frame.getContentPane().add(messageTextField, BorderLayout.SOUTH);
        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        frame.getContentPane().add(sendButton, BorderLayout.EAST);

        initDisplay();

        frame.setVisible(true);
    }

    public void initDisplay() {
        frame.setTitle("클라이언트 채팅창");
        frame.setBackground(Color.orange);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // 화면 중앙에 표시
    }

    private void sendMessage() {
        try {
            Socket socket = new Socket("localhost", 1004);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            String message = messageTextField.getText();
            outputStream.writeObject(message);
            outputStream.flush();

            chatTextArea.append("나: " + message + "\n");

            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LeeChatClientTest client = new LeeChatClientTest();
    }
}

