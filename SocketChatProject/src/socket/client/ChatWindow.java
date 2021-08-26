package socket.client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import javax.swing.*;

public class ChatWindow {
    private String name;
    private JButton buttonSend;
    private JTextField textField;
    private JTextArea textArea;

    private Socket socket;

    public ChatWindow(String name, Socket socket) {
        this.name = name;
        buttonSend = ClientPanel.sendMsgBtn;
        textField = ClientPanel.sendMsgField;
        textArea = ClientPanel.chatArea;
        this.socket = socket;

        new ChatClientReceiveThread(socket).start();
    }

    public void show() {
        // Button
        buttonSend.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
            	System.out.println("Ŭ��");
                sendMessage();
            }
        });

        // Textfield
        textField.setColumns(80);
        textField.addKeyListener( new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                char keyCode = e.getKeyChar();
                if (keyCode == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });
    }

    // �����带 ���� ��ȭ�� ������
    public void sendMessage() {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            String inputed = textField.getText();
            if(100 < inputed.length()) {
				JOptionPane.showMessageDialog(null, "50�� ���Ϸ� �Է��ϼ���", "�޼��� ���� �ʰ�", JOptionPane.WARNING_MESSAGE);
            }else {
            	String message = textField.getText();
                String request = "message:" + message + "\r\n";
                pw.println(request);

                textField.setText("");
                textField.requestFocus();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ChatClientReceiveThread extends Thread{
        Socket socket = null;

        ChatClientReceiveThread(Socket socket){
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                while(true) {
                    String msg = br.readLine();
                    textArea.append(msg);
                    textArea.append("\n");
                    textArea.setCaretPosition(textArea.getDocument().getLength());
                }
            }catch(SocketException se) {
				JOptionPane.showMessageDialog(null, "������ ä�ù��� �ݾҽ��ϴ�\n��� �Ŀ� ä�ù��� �ݱ�ϴ�", "ä�ù� �ݱ�", JOptionPane.WARNING_MESSAGE);
				try {
					Thread.sleep(3000);
					System.exit(0);
					ClientGUI socket = new ClientGUI();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}