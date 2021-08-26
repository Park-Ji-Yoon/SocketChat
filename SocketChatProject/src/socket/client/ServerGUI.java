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
	ImageIcon img = new ImageIcon("D:\\2021_3학년\\컴퓨터 네트워크\\3210_socket\\3210\\image\\socket_logo.png");

	public ServerGUI() {
		super("채팅방 호스트 - Server");
		setBounds(200, 180, 700, 700);
		setVisible(true);
		setLayout(null);

		setIconImage(img.getImage());

		add(serverPanel);
	}

	public static void main(String[] args) {
		ServerGUI socket = new ServerGUI();
		socket.setDefaultCloseOperation(EXIT_ON_CLOSE); // 종료 이벤트
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

		// 채팅방 파트
		JLabel roomInfoTitle = new JLabel("채팅방 접속 정보", JLabel.LEFT);
		roomInfoTitle.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		roomInfoTitle.setBounds(30, 30, 180, 20);

		JLabel ipInputTitle = new JLabel("IP", JLabel.LEFT);
		ipInputTitle.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		ipInputTitle.setBounds(30, 80, 150, 20);

		JLabel portInputTitle = new JLabel("PORT", JLabel.LEFT);
		portInputTitle.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		portInputTitle.setBounds(30, 120, 150, 20);

		ipInput = new JTextField(null, JLabel.LEFT);
		ipInput.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		ipInput.setBounds(120, 80, 260, 28);

		TextHint ipTextHint = new TextHint(ipInput, "ip주소를 입력해주세요");

		String hostAddress;
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
			ipInput.setText(hostAddress);
	        ipInput.setEditable(false);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		JTextField portInput = new JTextField(null, JLabel.LEFT);
		portInput.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		portInput.setBounds(120, 115, 260, 28);

		TextHint portTextHint = new TextHint(portInput, "port를 입력해주세요");

		// 내 정보 파트
		JLabel myInfoTitle = new JLabel("나의 정보", JLabel.LEFT);
		myInfoTitle.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		myInfoTitle.setBounds(30, 200, 250, 20);

		JLabel nicknameTitle = new JLabel("채팅방에서 사용할 닉네임", JLabel.LEFT);
		nicknameTitle.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		nicknameTitle.setBounds(30, 240, 240, 28);

		JTextField nicknameInput = new JTextField(null, JLabel.LEFT);
		nicknameInput.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		nicknameInput.setBounds(260, 240, 260, 28);

		TextHint nicknameTextHint = new TextHint(nicknameInput, "닉네임을 입력해주세요");

		JButton nicknameChgBtn = new JButton("확인");
		nicknameChgBtn.setBounds(535, 235, 100, 35);
		nicknameChgBtn.setBackground(Color.DARK_GRAY);
		nicknameChgBtn.setForeground(Color.WHITE);
		nicknameChgBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		
		JButton openRoomBtn = new JButton("채팅방 열기");
		openRoomBtn.setBounds(430, 30, 200, 50);
		openRoomBtn.setBackground(Color.DARK_GRAY);
		openRoomBtn.setForeground(Color.WHITE);
		openRoomBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		openRoomBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = ipInput.getText();
				String port = portInput.getText();
				String nickname = nicknameInput.getText();
								
				// ip주소, port번호 미입력
				if (ip.equals("ip주소를 입력해주세요") && port.equals("port를 입력해주세요")) {
					JOptionPane.showMessageDialog(null, "ip주소와 port번호를 입력해주세요", "IP주소, PORT번호 미입력",
							JOptionPane.WARNING_MESSAGE);
				} else if (ip.equals("ip주소를 입력해주세요")) { // ip주소 미입력
					JOptionPane.showMessageDialog(null, "ip주소를 입력해주세요", "IP주소 미입력", JOptionPane.WARNING_MESSAGE);
				} else if (port.equals("port번호를 입력해주세요")) { // port번호 미입력
					JOptionPane.showMessageDialog(null, "port번호를 입력해주세요", "PORT번호 미입력", JOptionPane.WARNING_MESSAGE);
				} else { // ip주소, port번호 올바른지 확인
					int dotCount = 0;
					String[] ipStrArray = ip.split("\\.");
					System.out.println(Arrays.toString(ipStrArray));
					// 맨 앞 맨 뒤에 점 있는지 확인
					if (ipStrArray[0].equals(".") || ipStrArray[ipStrArray.length - 1].equals(".")) {
						JOptionPane.showMessageDialog(null, "마침표를 맨 앞, 뒤에 넣지 마세요", "IP주소 오류",
								JOptionPane.WARNING_MESSAGE);
					} else {
						if (ipStrArray.length != 4) { // 숫자 개수 확인
							JOptionPane.showMessageDialog(null, "숫자로 ip주소를 입력해주세요", "IP주소 오류", JOptionPane.WARNING_MESSAGE);
						} else {
							if (countChar(ip, '.') != 3) { // 점 개수 확인
								JOptionPane.showMessageDialog(null, "4옥텟으로 된 ip주소를 입력해주세요", "IP주소 오류", JOptionPane.WARNING_MESSAGE);
							}
						}
						// 숫자인지 확인
						for (int i = 0; i < ipStrArray.length; i += 2) {
							int num;
							try {
								num = Integer.parseInt(ipStrArray[i]);
							} catch (NumberFormatException ne) {
								JOptionPane.showMessageDialog(null, "숫자로 ip주소를 입력해주세요", "IP주소 오류", JOptionPane.WARNING_MESSAGE);
								break;
							}
						}
					}
				}
				
				// port 번호
				int[] setted = {0, 8, 20, 22, 23, 25, 53, 63, 69, 70, 79, 80, 110};
				int numPort;
				try {
					numPort = Integer.parseInt(port);
					if(Arrays.asList(setted).contains(port)) {
						JOptionPane.showMessageDialog(null, port + "는 예약된 port번호입니다", "PORT번호 오류", JOptionPane.WARNING_MESSAGE);
					}else {
						if(nickname.equals("닉네임을 입력해주세요")) {
							int result = JOptionPane.showConfirmDialog(null, "닉네임을 입력하지않으셨습니다.\nip주소를 닉네임으로 사용할까요?", "닉네임", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							if(result == 0) {
								try {
									nickname = InetAddress.getLocalHost().getHostAddress();
									ServerThread serverThread = new ServerThread();
									serverThread.main(numPort, nickname + "(방장)");
									
								} catch (UnknownHostException e1) {
									e1.printStackTrace();
								}
							}
						}else {
							ServerThread serverThread = new ServerThread();
							serverThread.main(numPort, nickname + "(방장)");
					
						}
					}
				}catch(NumberFormatException ne) {
					JOptionPane.showMessageDialog(null, "port번호는 숫자만 가능합니다", "PORT번호 오류", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JButton colseRoomBtn = new JButton("채팅방 닫기");
		colseRoomBtn.setBounds(430, 100, 200, 50);
		colseRoomBtn.setBackground(Color.DARK_GRAY);
		colseRoomBtn.setForeground(Color.WHITE);
		colseRoomBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 20));

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

		// 채팅 파트
		chatArea = new JTextArea();
		chatArea.setBounds(30, 300, 610, 250);
		chatArea.setForeground(Color.WHITE);
		chatArea.setBackground(Color.GRAY);
		chatArea.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		
		scrollPane = new JScrollPane(chatArea);
        scrollPane.setBounds(30, 330, 610, 220);
        scrollPane.setForeground(Color.WHITE);
        scrollPane.setBackground(Color.GRAY);
        add(scrollPane);

		sendMsgField = new JTextField("안녕하세요~");
		sendMsgField.setBounds(30, 570, 520, 40);
		sendMsgField.setBackground(Color.GRAY);
		sendMsgField.setForeground(Color.WHITE);
		sendMsgField.setBorder(null);
		sendMsgField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

		sendMsgBtn = new JButton("전송");
		sendMsgBtn.setBounds(550, 570, 90, 40);
		sendMsgBtn.setBackground(Color.DARK_GRAY);
		sendMsgBtn.setForeground(Color.WHITE);
		sendMsgBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 18));

//		add(chatArea);
		add(sendMsgField);
		add(sendMsgBtn);
	}

	public static long countChar(String str, char ch) {
		return str.chars().filter(c -> c == ch).count();
	}
}