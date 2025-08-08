package chat;

import java.sql.*;
import java.util.Vector;
import javax.swing.*;

import chat.ui.FontLoader;

import java.awt.*;
import java.awt.event.*;

public class ChatLogin extends JFrame implements MouseListener {
	private static final long serialVersionUID = 1L; 
	
	static JTextField txtID;
	static JPasswordField login_pwField;
	
	JFrame LoginFrame;
	ActionListener listener_login;
	public JPanel contentPane;
	static ChatLogin Login = new ChatLogin();
	private JLabel lblSearch;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login.LoginFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public class ClientLoginActionListener implements ActionListener {
		Connection mysqlConn = null;
		ResultSet rs;
		PreparedStatement pstmt = null;
		String sql;

		String input_ID;
		String input_PW;
		String client_ID;
		String client_PW;
		String client_NAME;
		char[] pw_client;

		/*** EVENT LISTENER ***/
		public void actionPerformed(ActionEvent a) {
			input_ID = txtID.getText();
			pw_client = login_pwField.getPassword();
			input_PW = String.valueOf(pw_client);

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				mysqlConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat_db", "root", "wpfflWpf2!");

				sql = "SELECT ID,PASSWORD,NAME FROM CLIENT WHERE ID=?";
				pstmt = mysqlConn.prepareStatement(sql);
				pstmt.setString(1, input_ID);
				
				rs = pstmt.executeQuery();

				while (rs.next()) {
					client_ID = rs.getString(1);
					client_PW = rs.getString(2);
					client_NAME = rs.getString(3);
				}
				if ((input_ID.equals(client_ID)) & (input_PW.equals(client_PW))) {
					ChatClient N_Client = new ChatClient(input_ID, client_NAME);
					N_Client.ClientFrame(input_ID, client_NAME);
					Login.LoginFrame.setVisible(false);
					
					N_Client.ClientServer_Start();

					Server.ChatClient_V.add(N_Client);
					ChatRoom.Client_V.addElement(N_Client);
				}
				else {
					JOptionPane.showMessageDialog(null, "아이디/비밀번호가 틀렸습니다");
					txtID.setText(null);
					login_pwField.setText(null);
				}
			} catch (Exception e) {
				System.out.println("LOGIN ERROR :: " + e.getMessage());
			} finally {
				try {
					rs.close();
					pstmt.close();
					mysqlConn.close();
				} catch (Exception ex) {
				}
			}
		}
	}

	/******* Create the frame. ************/
	public ChatLogin() {

		LoginFrame = new JFrame("JavaTalk");
		LoginFrame.setBounds(0, 0, 400, 600);
		LoginFrame.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 17));
		LoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LoginFrame.getContentPane().setLayout(null);

		ImageIcon icon = new ImageIcon("/Users/eunae/Library/Mobile Documents/com~apple~CloudDocs/eclipse-workspace/eunae-workspace/ChattinProgramm/resource/img/logo.svg");
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
//				Dimension d = getSize();
				g.drawImage(icon.getImage(), 100, 100, 200, 200, null); 
			}
		};
		panel.setBounds(0, 0, 400, 600);
		LoginFrame.getContentPane().add(panel);
		panel.setLayout(null);

		txtID = new JTextField();
		txtID.setBounds(77, 357, 210, 40);
		panel.add(txtID);
		txtID.setColumns(10);

		login_pwField = new JPasswordField();
		login_pwField.setEchoChar('*');
		login_pwField.setBounds(100, 334, 200, 37);
		panel.add(login_pwField);
		listener_login = new ClientLoginActionListener();

		JButton btnJoin = new JButton("회원가입");
		btnJoin.setForeground(Color.darkGray);
		btnJoin.setBounds(290, 520, 95, 35);
		btnJoin.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatJoin Join = new ChatJoin();
				Join.JoinFrame.setVisible(true);
			}
		});
		panel.add(btnJoin);

		JButton btnLogin = new JButton("로그인");
		btnLogin.setForeground(Color.darkGray);
		btnLogin.setBounds(290, 357, 80, 82);
		btnLogin.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 15));
		panel.add(btnLogin);
		btnLogin.addActionListener(listener_login);

		JLabel lblID = new JLabel("ID");
		lblID.setBounds(30, 357, 61, 35);
		lblID.setHorizontalAlignment(SwingConstants.CENTER);
		lblID.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 15));
		panel.add(lblID);

		JLabel lblPW = new JLabel("PW");
		lblPW.setBounds(30, 400, 61, 35);
		lblPW.setHorizontalAlignment(SwingConstants.CENTER);
		lblPW.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 15));
		panel.add(lblPW);

		JButton btnAdmin = new JButton("관리자");
		btnAdmin.setForeground(Color.darkGray);
		btnAdmin.setBounds(14, 520, 95, 35);
		btnAdmin.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChatAdminLogin.main(null);
				Login.LoginFrame.setVisible(false);
			}
		});
		panel.add(btnAdmin);

		lblSearch = new JLabel("아이디/비밀번호 찾기");
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearch.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 13));
		lblSearch.setBounds(100, 450, 200, 35);
		panel.add(lblSearch);
		lblSearch.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
//		lblSearch.setForeground(Color.PINK);
		Search searchInfo = new Search();
	}
}
