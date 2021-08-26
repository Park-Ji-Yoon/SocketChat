package socket.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

public class ClientGUI extends JFrame {

	ClientPanel serverPanel = new ClientPanel();
	ImageIcon img = new ImageIcon("./image/socket_logo02.png");

	public ClientGUI() {
		super("ä�ù� ������ - Client");
		setBounds(200, 180, 700, 700);
		setVisible(true);
		setLayout(null);
		setResizable(false);
		
		setIconImage(img.getImage());
		
		add(serverPanel);
	}

	public static void main(String[] args) {
		ClientGUI socket = new ClientGUI();
		socket.setDefaultCloseOperation(EXIT_ON_CLOSE); // ���� �̺�Ʈ
	}
}

class ClientPanel extends JPanel {

	public static JTextArea chatArea;
	public static JButton sendMsgBtn;
	public static JTextField sendMsgField;
	public static JScrollPane scrollPane;
	public static JTextField ipInput;

	public ClientPanel() {
		setSize(700, 700);
		setLayout(null);
		setBackground(Color.WHITE);

		// ä�ù� ��Ʈ
		JLabel roomInfoTitle = new JLabel("������ ä�ù�", JLabel.LEFT);
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
		ipInput.setBounds(120, 80, 230, 28);

		TextHint nicknameTextHint = new TextHint(ipInput, "ip�ּҸ� �Է����ּ���");

		String hostAddress;
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
			ipInput.setText(hostAddress);
			ipInput.setEditable(false);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		Icon editIcon = new ImageIcon("./image/edit_2.png");
		JButton ipDirectInputBtn = new JButton(editIcon);
		ipDirectInputBtn.setBackground(new Color(128, 5, 158));
		ipDirectInputBtn.setBounds(350, 80, 28, 28);
		ipDirectInputBtn.setBorderPainted(false);
		ipDirectInputBtn.setFocusPainted(false);
		add(ipDirectInputBtn);
		ipDirectInputBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ipInput.setEditable(true);
			}
		});

		JTextField portInput = new JTextField(null, JLabel.LEFT);
		portInput.setFont(new Font("���� ���", Font.PLAIN, 18));
		portInput.setBounds(120, 115, 260, 28);

		TextHint portTextHint = new TextHint(portInput, "port�� �Է����ּ���");

		JLabel nicknameTitle = new JLabel("ä�ù濡�� ����� �г���", JLabel.LEFT);
		nicknameTitle.setFont(new Font("���� ���", Font.PLAIN, 18));
		nicknameTitle.setBounds(30, 230, 240, 28);

		JTextField nicknameInput = new JTextField(null, JLabel.LEFT);
		nicknameInput.setFont(new Font("���� ���", Font.PLAIN, 18));
		nicknameInput.setBounds(260, 230, 260, 28);
		
		JButton randomNickname = new JButton("����");
		randomNickname.setFont(new Font("���� ���", Font.PLAIN, 18));
		randomNickname.setBounds(530, 230, 100, 30);
		randomNickname.setBackground(new Color(128, 5, 158));
		randomNickname.setForeground(Color.WHITE);
		add(randomNickname);
		randomNickname.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nicknameInput.setText("");
				nicknameInput.setText(randomHangulName());
			}
		});

		TextHint nicknameTextHint2 = new TextHint(nicknameInput, "�г����� �Է����ּ���");

		JButton openRoomBtn = new JButton("ä�ù� ����");
		openRoomBtn.setBounds(430, 30, 200, 50);
		openRoomBtn.setBackground(new Color(128, 5, 158));
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
							JOptionPane.showMessageDialog(null, "���ڷ� ip�ּҸ� �Է����ּ���", "IP�ּ� ����",
									JOptionPane.WARNING_MESSAGE);
						} else {
							if (countChar(ip, '.') != 3) { // �� ���� Ȯ��
								JOptionPane.showMessageDialog(null, "4�������� �� ip�ּҸ� �Է����ּ���", "IP�ּ� ����",
										JOptionPane.WARNING_MESSAGE);
							}
						}
						// �������� Ȯ��
						for (int i = 0; i < ipStrArray.length; i += 2) {
							int num;
							try {
								num = Integer.parseInt(ipStrArray[i]);
							} catch (NumberFormatException ne) {
								JOptionPane.showMessageDialog(null, "���ڷ� ip�ּҸ� �Է����ּ���", "IP�ּ� ����",
										JOptionPane.WARNING_MESSAGE);
								break;
							}
						}
					}
				}

				// port ��ȣ
				int[] setted = { 0, 8, 20, 22, 23, 25, 53, 63, 69, 70, 79, 80, 110 };
				int numPort;
				try {
					numPort = Integer.parseInt(port);
					if (Arrays.asList(setted).contains(port)) {
						JOptionPane.showMessageDialog(null, port + "�� ����� port��ȣ�Դϴ�", "PORT��ȣ ����",
								JOptionPane.WARNING_MESSAGE);
					} else {
						if (nickname.equals("�г����� �Է����ּ���")) {
							int result = JOptionPane.showConfirmDialog(null, "�г����� �Է����������̽��ϴ�.\nip�ּҸ� �г������� ����ұ��?",
									"�г���", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							if (result == 0) {
								try {
									nickname = InetAddress.getLocalHost().getHostAddress();
									ChatClientApp chatClientApp = new ChatClientApp();
									chatClientApp.main(numPort, nickname);
									
									openRoomBtn.setEnabled(false);
									randomNickname.setEnabled(false);
									ipInput.setEditable(false);
								} catch (UnknownHostException e1) {
									e1.printStackTrace();
								}
							}
						} else {
							ChatClientApp chatClientApp = new ChatClientApp();
							chatClientApp.main(numPort, nickname);
							
							openRoomBtn.setEnabled(false);
							randomNickname.setEnabled(false);
						}
					}
				} catch (NumberFormatException ne) {
					JOptionPane.showMessageDialog(null, "port��ȣ�� ���ڸ� �����մϴ�", "PORT��ȣ ����", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JButton colseRoomBtn = new JButton("ä�ù� ����");
		colseRoomBtn.setBounds(430, 100, 200, 50);
		colseRoomBtn.setBackground(new Color(128, 5, 158));
		colseRoomBtn.setForeground(Color.WHITE);
		colseRoomBtn.setFont(new Font("���� ���", Font.PLAIN, 20));
		colseRoomBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMsgField.setText("quit");
				sendMsgBtn.doClick();
				
				System.exit(0);
			}
		});

		add(roomInfoTitle);
		add(ipInputTitle);
		add(portInputTitle);

		add(ipInput);
		add(portInput);

		add(openRoomBtn);
		add(colseRoomBtn);

		// �� ���� ��Ʈ
		JLabel myInfoTitle = new JLabel("���� ����", JLabel.LEFT);
		myInfoTitle.setFont(new Font("���� ���", Font.BOLD, 22));
		myInfoTitle.setBounds(30, 190, 250, 20);

		add(myInfoTitle);
		add(nicknameTitle);
		add(nicknameInput);

		// ä�� ��Ʈ
		chatArea = new JTextArea();
		chatArea.setBounds(30, 330, 610, 220);
		chatArea.setForeground(Color.WHITE);
		chatArea.setBackground(new Color(153, 68, 207));
		chatArea.setFont(new Font("���� ���", Font.PLAIN, 16));
		chatArea.setCaretPosition(chatArea.getDocument().getLength());
		scrollPane = new JScrollPane(chatArea);
		scrollPane.setBounds(30, 330, 610, 220);
		scrollPane.setForeground(Color.WHITE);
		scrollPane.setBackground(new Color(153, 68, 207));
		add(scrollPane);

		sendMsgField = new JTextField();
		sendMsgField.setBounds(30, 570, 520, 40);
		sendMsgField.setBackground(new Color(153, 68, 207));
		sendMsgField.setForeground(Color.WHITE);
		sendMsgField.setBorder(null);
		sendMsgField.setFont(new Font("���� ���", Font.PLAIN, 16));

		sendMsgBtn = new JButton("����");
		sendMsgBtn.setBounds(550, 570, 90, 40);
		sendMsgBtn.setBackground(new Color(128, 5, 158));
		sendMsgBtn.setForeground(Color.WHITE);
		sendMsgBtn.setFont(new Font("���� ���", Font.PLAIN, 18));

		// add(chatArea);
		add(sendMsgField);
		add(sendMsgBtn);
	}

	public static long countChar(String str, char ch) {
		return str.chars().filter(c -> c == ch).count();
	}
	
	public static String randomHangulName() {
	    List<String> lastName = Arrays.asList("��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "Ȳ", "��",
	        "��", "��", "��", "ȫ", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	        "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "ä", "��", "õ", "��", "��", "��", "��", "��", "��", "��", "��",
	        "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "ǥ", "��", "��", "��", "��", "��",
	        "��", "��", "��", "��", "��", "��", "��", "��", "Ź", "��", "��", "��", "��", "��", "��", "��", "��");
	    List<String> firstName = Arrays.asList("��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	        "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	        "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	        "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	        "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	        "��", "��", "��", "��", "â", "ä", "õ", "ö", "��", "��", "��", "ġ", "Ž", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	        "��", "ȣ", "ȫ", "ȭ", "ȯ", "ȸ", "ȿ", "��", "��", "��", "��", "��", "��", "��", "��", "��", "ȥ", "Ȳ", "��", "��", "��", "��",
	        "��", "��", "��", "Ź", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	        "��", "��", "��", "��", "��", "��", "Ÿ", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	        "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��");
	    Collections.shuffle(lastName);
	    Collections.shuffle(firstName);
	    return lastName.get(0) + firstName.get(0) + firstName.get(1);
	}
}