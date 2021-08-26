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
		super("채팅방 참여자 - Client");
		setBounds(200, 180, 700, 700);
		setVisible(true);
		setLayout(null);
		setResizable(false);
		
		setIconImage(img.getImage());
		
		add(serverPanel);
	}

	public static void main(String[] args) {
		ClientGUI socket = new ClientGUI();
		socket.setDefaultCloseOperation(EXIT_ON_CLOSE); // 종료 이벤트
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

		// 채팅방 파트
		JLabel roomInfoTitle = new JLabel("접속할 채팅방", JLabel.LEFT);
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
		ipInput.setBounds(120, 80, 230, 28);

		TextHint nicknameTextHint = new TextHint(ipInput, "ip주소를 입력해주세요");

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
		portInput.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		portInput.setBounds(120, 115, 260, 28);

		TextHint portTextHint = new TextHint(portInput, "port를 입력해주세요");

		JLabel nicknameTitle = new JLabel("채팅방에서 사용할 닉네임", JLabel.LEFT);
		nicknameTitle.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		nicknameTitle.setBounds(30, 230, 240, 28);

		JTextField nicknameInput = new JTextField(null, JLabel.LEFT);
		nicknameInput.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		nicknameInput.setBounds(260, 230, 260, 28);
		
		JButton randomNickname = new JButton("랜덤");
		randomNickname.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
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

		TextHint nicknameTextHint2 = new TextHint(nicknameInput, "닉네임을 입력해주세요");

		JButton openRoomBtn = new JButton("채팅방 입장");
		openRoomBtn.setBounds(430, 30, 200, 50);
		openRoomBtn.setBackground(new Color(128, 5, 158));
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
							JOptionPane.showMessageDialog(null, "숫자로 ip주소를 입력해주세요", "IP주소 오류",
									JOptionPane.WARNING_MESSAGE);
						} else {
							if (countChar(ip, '.') != 3) { // 점 개수 확인
								JOptionPane.showMessageDialog(null, "4옥텟으로 된 ip주소를 입력해주세요", "IP주소 오류",
										JOptionPane.WARNING_MESSAGE);
							}
						}
						// 숫자인지 확인
						for (int i = 0; i < ipStrArray.length; i += 2) {
							int num;
							try {
								num = Integer.parseInt(ipStrArray[i]);
							} catch (NumberFormatException ne) {
								JOptionPane.showMessageDialog(null, "숫자로 ip주소를 입력해주세요", "IP주소 오류",
										JOptionPane.WARNING_MESSAGE);
								break;
							}
						}
					}
				}

				// port 번호
				int[] setted = { 0, 8, 20, 22, 23, 25, 53, 63, 69, 70, 79, 80, 110 };
				int numPort;
				try {
					numPort = Integer.parseInt(port);
					if (Arrays.asList(setted).contains(port)) {
						JOptionPane.showMessageDialog(null, port + "는 예약된 port번호입니다", "PORT번호 오류",
								JOptionPane.WARNING_MESSAGE);
					} else {
						if (nickname.equals("닉네임을 입력해주세요")) {
							int result = JOptionPane.showConfirmDialog(null, "닉네임을 입력하지않으셨습니다.\nip주소를 닉네임으로 사용할까요?",
									"닉네임", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
					JOptionPane.showMessageDialog(null, "port번호는 숫자만 가능합니다", "PORT번호 오류", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JButton colseRoomBtn = new JButton("채팅방 퇴장");
		colseRoomBtn.setBounds(430, 100, 200, 50);
		colseRoomBtn.setBackground(new Color(128, 5, 158));
		colseRoomBtn.setForeground(Color.WHITE);
		colseRoomBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
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

		// 내 정보 파트
		JLabel myInfoTitle = new JLabel("나의 정보", JLabel.LEFT);
		myInfoTitle.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		myInfoTitle.setBounds(30, 190, 250, 20);

		add(myInfoTitle);
		add(nicknameTitle);
		add(nicknameInput);

		// 채팅 파트
		chatArea = new JTextArea();
		chatArea.setBounds(30, 330, 610, 220);
		chatArea.setForeground(Color.WHITE);
		chatArea.setBackground(new Color(153, 68, 207));
		chatArea.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
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
		sendMsgField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

		sendMsgBtn = new JButton("전송");
		sendMsgBtn.setBounds(550, 570, 90, 40);
		sendMsgBtn.setBackground(new Color(128, 5, 158));
		sendMsgBtn.setForeground(Color.WHITE);
		sendMsgBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 18));

		// add(chatArea);
		add(sendMsgField);
		add(sendMsgBtn);
	}

	public static long countChar(String str, char ch) {
		return str.chars().filter(c -> c == ch).count();
	}
	
	public static String randomHangulName() {
	    List<String> lastName = Arrays.asList("김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신", "권", "황", "안",
	        "송", "류", "전", "홍", "고", "문", "양", "손", "배", "조", "백", "허", "유", "남", "심", "노", "정", "하", "곽", "성", "차", "주",
	        "우", "구", "신", "임", "나", "전", "민", "유", "진", "지", "엄", "채", "원", "천", "방", "공", "강", "현", "함", "변", "염", "양",
	        "변", "여", "추", "노", "도", "소", "신", "석", "선", "설", "마", "길", "주", "연", "방", "위", "표", "명", "기", "반", "왕", "금",
	        "옥", "육", "인", "맹", "제", "모", "장", "남", "탁", "국", "여", "진", "어", "은", "편", "구", "용");
	    List<String> firstName = Arrays.asList("가", "강", "건", "경", "고", "관", "광", "구", "규", "근", "기", "길", "나", "남", "노", "누", "다",
	        "단", "달", "담", "대", "덕", "도", "동", "두", "라", "래", "로", "루", "리", "마", "만", "명", "무", "문", "미", "민", "바", "박",
	        "백", "범", "별", "병", "보", "빛", "사", "산", "상", "새", "서", "석", "선", "설", "섭", "성", "세", "소", "솔", "수", "숙", "순",
	        "숭", "슬", "승", "시", "신", "아", "안", "애", "엄", "여", "연", "영", "예", "오", "옥", "완", "요", "용", "우", "원", "월", "위",
	        "유", "윤", "율", "으", "은", "의", "이", "익", "인", "일", "잎", "자", "잔", "장", "재", "전", "정", "제", "조", "종", "주", "준",
	        "중", "지", "진", "찬", "창", "채", "천", "철", "초", "춘", "충", "치", "탐", "태", "택", "판", "하", "한", "해", "혁", "현", "형",
	        "혜", "호", "홍", "화", "환", "회", "효", "훈", "휘", "희", "운", "모", "배", "부", "림", "봉", "혼", "황", "량", "린", "을", "비",
	        "솜", "공", "면", "탁", "온", "디", "항", "후", "려", "균", "묵", "송", "욱", "휴", "언", "령", "섬", "들", "견", "추", "걸", "삼",
	        "열", "웅", "분", "변", "양", "출", "타", "흥", "겸", "곤", "번", "식", "란", "더", "손", "술", "훔", "반", "빈", "실", "직", "흠",
	        "흔", "악", "람", "뜸", "권", "복", "심", "헌", "엽", "학", "개", "롱", "평", "늘", "늬", "랑", "얀", "향", "울", "련");
	    Collections.shuffle(lastName);
	    Collections.shuffle(firstName);
	    return lastName.get(0) + firstName.get(0) + firstName.get(1);
	}
}