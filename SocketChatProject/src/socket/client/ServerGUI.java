package socket.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.swing.*;

public class ServerGUI extends JFrame {

	ServerPanel serverPanel = new ServerPanel();
	ImageIcon img = new ImageIcon("D:\\2021_3�г�\\��ǻ�� ��Ʈ��ũ\\3210_socket\\3210\\image\\socket_logo.png");

	public ServerGUI() {
		super("ä�ù� ȣ��Ʈ - Server");
		setBounds(200, 180, 700, 700);
		setVisible(true);
		setLayout(null);

		setIconImage(img.getImage());

		add(serverPanel);
	}

	public static void main(String[] args) {
		ServerGUI socket = new ServerGUI();
		socket.setDefaultCloseOperation(EXIT_ON_CLOSE); // ���� �̺�Ʈ
	}
}

class ServerPanel extends JPanel {

	static JTextField ipInput;
	
	public static JTextArea chatArea;
	public static JButton sendMsgBtn;
	public static JTextField sendMsgField;
	public static JScrollPane scrollPane;

	public ServerPanel() {
		setSize(700, 700);
		setLayout(null);
		setBackground(Color.WHITE);

		// ä�ù� ��Ʈ
		JLabel roomInfoTitle = new JLabel("ä�ù� ���� ����", JLabel.LEFT);
		roomInfoTitle.setFont(new Font("���� ���", Font.BOLD, 22));
		roomInfoTitle.setBounds(30, 30, 180, 20);

		JLabel ipInputTitle = new JLabel("IP", JLabel.LEFT);
		ipInputTitle.setFont(new Font("���� ���", Font.PLAIN, 18));
		ipInputTitle.setBounds(30, 80, 150, 20);

		JLabel portInputTitle = new JLabel("PORT", JLabel.LEFT);
		portInputTitle.setFont(new Font("���� ���", Font.PLAIN, 18));
		portInputTitle.setBounds(30, 120, 150, 20);

		ipInput = new JTextField(null, JLabel.LEFT);
		ipInput.setFont(new Font("���� ���", Font.PLAIN, 18));
		ipInput.setBounds(120, 80, 260, 28);

		TextHint ipTextHint = new TextHint(ipInput, "ip�ּҸ� �Է����ּ���");

		String hostAddress;
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
			ipInput.setText(hostAddress);
	        ipInput.setEditable(false);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		JTextField portInput = new JTextField(null, JLabel.LEFT);
		portInput.setFont(new Font("���� ���", Font.PLAIN, 18));
		portInput.setBounds(120, 115, 260, 28);

		TextHint portTextHint = new TextHint(portInput, "port�� �Է����ּ���");

		// �� ���� ��Ʈ
		JLabel myInfoTitle = new JLabel("���� ����", JLabel.LEFT);
		myInfoTitle.setFont(new Font("���� ���", Font.BOLD, 22));
		myInfoTitle.setBounds(30, 200, 250, 20);

		JLabel nicknameTitle = new JLabel("ä�ù濡�� ����� �г���", JLabel.LEFT);
		nicknameTitle.setFont(new Font("���� ���", Font.PLAIN, 18));
		nicknameTitle.setBounds(30, 240, 240, 28);

		JTextField nicknameInput = new JTextField(null, JLabel.LEFT);
		nicknameInput.setFont(new Font("���� ���", Font.PLAIN, 18));
		nicknameInput.setBounds(260, 240, 260, 28);

		TextHint nicknameTextHint = new TextHint(nicknameInput, "�г����� �Է����ּ���");

		JButton nicknameChgBtn = new JButton("Ȯ��");
		nicknameChgBtn.setBounds(535, 235, 100, 35);
		nicknameChgBtn.setBackground(Color.DARK_GRAY);
		nicknameChgBtn.setForeground(Color.WHITE);
		nicknameChgBtn.setFont(new Font("���� ���", Font.PLAIN, 20));
		
		JButton openRoomBtn = new JButton("ä�ù� ����");
		openRoomBtn.setBounds(430, 30, 200, 50);
		openRoomBtn.setBackground(Color.DARK_GRAY);
		openRoomBtn.setForeground(Color.WHITE);
		openRoomBtn.setFont(new Font("���� ���", Font.PLAIN, 20));
		openRoomBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = ipInput.getText();
				String port = portInput.getText();
				String nickname = nicknameInput.getText();
								
				// ip�ּ�, port��ȣ ���Է�
				if (ip.equals("ip�ּҸ� �Է����ּ���") && port.equals("port�� �Է����ּ���")) {
					JOptionPane.showMessageDialog(null, "ip�ּҿ� port��ȣ�� �Է����ּ���", "IP�ּ�, PORT��ȣ ���Է�",
							JOptionPane.WARNING_MESSAGE);
				} else if (ip.equals("ip�ּҸ� �Է����ּ���")) { // ip�ּ� ���Է�
					JOptionPane.showMessageDialog(null, "ip�ּҸ� �Է����ּ���", "IP�ּ� ���Է�", JOptionPane.WARNING_MESSAGE);
				} else if (port.equals("port��ȣ�� �Է����ּ���")) { // port��ȣ ���Է�
					JOptionPane.showMessageDialog(null, "port��ȣ�� �Է����ּ���", "PORT��ȣ ���Է�", JOptionPane.WARNING_MESSAGE);
				} else { // ip�ּ�, port��ȣ �ùٸ��� Ȯ��
					int dotCount = 0;
					String[] ipStrArray = ip.split("\\.");
					System.out.println(Arrays.toString(ipStrArray));
					// �� �� �� �ڿ� �� �ִ��� Ȯ��
					if (ipStrArray[0].equals(".") || ipStrArray[ipStrArray.length - 1].equals(".")) {
						JOptionPane.showMessageDialog(null, "��ħǥ�� �� ��, �ڿ� ���� ������", "IP�ּ� ����",
								JOptionPane.WARNING_MESSAGE);
					} else {
						if (ipStrArray.length != 4) { // ���� ���� Ȯ��
							JOptionPane.showMessageDialog(null, "���ڷ� ip�ּҸ� �Է����ּ���", "IP�ּ� ����", JOptionPane.WARNING_MESSAGE);
						} else {
							if (countChar(ip, '.') != 3) { // �� ���� Ȯ��
								JOptionPane.showMessageDialog(null, "4�������� �� ip�ּҸ� �Է����ּ���", "IP�ּ� ����", JOptionPane.WARNING_MESSAGE);
							}
						}
						// �������� Ȯ��
						for (int i = 0; i < ipStrArray.length; i += 2) {
							int num;
							try {
								num = Integer.parseInt(ipStrArray[i]);
							} catch (NumberFormatException ne) {
								JOptionPane.showMessageDialog(null, "���ڷ� ip�ּҸ� �Է����ּ���", "IP�ּ� ����", JOptionPane.WARNING_MESSAGE);
								break;
							}
						}
					}
				}
				
				// port ��ȣ
				int[] setted = {0, 8, 20, 22, 23, 25, 53, 63, 69, 70, 79, 80, 110};
				int numPort;
				try {
					numPort = Integer.parseInt(port);
					if(Arrays.asList(setted).contains(port)) {
						JOptionPane.showMessageDialog(null, port + "�� ����� port��ȣ�Դϴ�", "PORT��ȣ ����", JOptionPane.WARNING_MESSAGE);
					}else {
						if(nickname.equals("�г����� �Է����ּ���")) {
							int result = JOptionPane.showConfirmDialog(null, "�г����� �Է����������̽��ϴ�.\nip�ּҸ� �г������� ����ұ��?", "�г���", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							if(result == 0) {
								try {
									nickname = InetAddress.getLocalHost().getHostAddress();
									ServerThread serverThread = new ServerThread();
									serverThread.main(numPort, nickname + "(����)");
									
								} catch (UnknownHostException e1) {
									e1.printStackTrace();
								}
							}
						}else {
							ServerThread serverThread = new ServerThread();
							serverThread.main(numPort, nickname + "(����)");
					
						}
					}
				}catch(NumberFormatException ne) {
					JOptionPane.showMessageDialog(null, "port��ȣ�� ���ڸ� �����մϴ�", "PORT��ȣ ����", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JButton colseRoomBtn = new JButton("ä�ù� �ݱ�");
		colseRoomBtn.setBounds(430, 100, 200, 50);
		colseRoomBtn.setBackground(Color.DARK_GRAY);
		colseRoomBtn.setForeground(Color.WHITE);
		colseRoomBtn.setFont(new Font("���� ���", Font.PLAIN, 20));

		add(roomInfoTitle);
		add(ipInputTitle);
		add(portInputTitle);

		add(ipInput);
		add(portInput);

		add(openRoomBtn);
		add(colseRoomBtn);

		add(myInfoTitle);
		add(nicknameTitle);
		add(nicknameInput);
		add(nicknameChgBtn);

		// ä�� ��Ʈ
		chatArea = new JTextArea();
		chatArea.setBounds(30, 300, 610, 250);
		chatArea.setForeground(Color.WHITE);
		chatArea.setBackground(Color.GRAY);
		chatArea.setFont(new Font("���� ���", Font.PLAIN, 16));
		
		scrollPane = new JScrollPane(chatArea);
        scrollPane.setBounds(30, 330, 610, 220);
        scrollPane.setForeground(Color.WHITE);
        scrollPane.setBackground(Color.GRAY);
        add(scrollPane);

		sendMsgField = new JTextField("�ȳ��ϼ���~");
		sendMsgField.setBounds(30, 570, 520, 40);
		sendMsgField.setBackground(Color.GRAY);
		sendMsgField.setForeground(Color.WHITE);
		sendMsgField.setBorder(null);
		sendMsgField.setFont(new Font("���� ���", Font.PLAIN, 16));

		sendMsgBtn = new JButton("����");
		sendMsgBtn.setBounds(550, 570, 90, 40);
		sendMsgBtn.setBackground(Color.DARK_GRAY);
		sendMsgBtn.setForeground(Color.WHITE);
		sendMsgBtn.setFont(new Font("���� ���", Font.PLAIN, 18));

//		add(chatArea);
		add(sendMsgField);
		add(sendMsgBtn);
	}

	public static long countChar(String str, char ch) {
		return str.chars().filter(c -> c == ch).count();
	}
}