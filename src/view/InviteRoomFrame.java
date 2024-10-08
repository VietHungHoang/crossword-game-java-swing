package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InviteRoomFrame extends JFrame {  
	
	JLabel Player1lb, Player2lb, Name1lb, Name2lb;
	JButton Startbtn, Leavebtn;
	JPanel Buttonpn, Pairpn, friendListpn;
	JTable friendTable;
	JLabel friendTitle;

	void panel() {
		Border innerBorder = BorderFactory.createEtchedBorder();
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 10, 10);
		
		Pairpn = new JPanel();
		Pairpn.setBackground(new Color(255, 255, 255));
		Pairpn.setOpaque(true);
		Pairpn.setLayout(new GridBagLayout());
		
		Buttonpn = new JPanel();
		Buttonpn.setBackground(new Color(200, 200, 200));
		Buttonpn.setOpaque(true);
		Buttonpn.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
		
		friendListpn = new JPanel();
		friendListpn.setPreferredSize(new Dimension(250, 400));
		friendListpn.setBorder(BorderFactory.createCompoundBorder(innerBorder, outerBorder));
		friendListpn.setLayout(new BorderLayout());
		
		// Thêm nhãn "DANH SÁCH BẠN BÈ"
		friendTitle = new JLabel("DANH SÁCH BẠN BÈ", JLabel.CENTER);
		friendTitle.setFont(new Font("Serif", Font.BOLD, 18));
		friendTitle.setOpaque(true);
		friendTitle.setBackground(new Color(173, 216, 230));  // Màu xanh nhạt
		friendTitle.setPreferredSize(new Dimension(250, 30));
		friendListpn.add(friendTitle, BorderLayout.NORTH);

		// Tạo bảng danh sách bạn bè
		String[] columnNames = {"Tên", "Trạng thái"};
		Object[][] data = {
			{"Hoang", "Online"},
			{"Bao", "Online"},
			{"Vtda", "Online"},
			{"Duy", "Offline"},
			{"HATXA", "Offline"},
			{"Meomeo", "Offline"}
		};
		friendTable = new JTable(data, columnNames);
		friendTable.setPreferredScrollableViewportSize(new Dimension(250, 100));
		friendTable.setFillsViewportHeight(true);

		// Thêm bảng vào panel
		JScrollPane scrollPane = new JScrollPane(friendTable);
		friendListpn.add(scrollPane, BorderLayout.CENTER);
	}

	void button() {
		Startbtn = new JButton();
		Startbtn.setText("BẮT ĐẦU TRÒ CHƠI");
		Startbtn.setFont(new Font("Serif", Font.BOLD, 18));
		Startbtn.setBackground(new Color(255, 165, 0));
		Startbtn.setOpaque(true);
		Startbtn.setPreferredSize(new Dimension(250, 50)); 
		Startbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MatchFrame();
				
			}
		});

		Leavebtn = new JButton();
		Leavebtn.setText("RỜI");
		Leavebtn.setFont(new Font("Serif", Font.BOLD, 18));
		Leavebtn.setBackground(new Color(135, 206, 250));  
		Leavebtn.setOpaque(true);
		Leavebtn.setPreferredSize(new Dimension(250, 50)); 
	}

	void label() {
		Player1lb = new JLabel("Người chơi 1", JLabel.CENTER);
		Player1lb.setFont(new Font("Serif", Font.BOLD, 18));
		Player1lb.setOpaque(true);
		Player1lb.setBackground(new Color(173, 216, 230));  
		Player1lb.setPreferredSize(new Dimension(250, 50));

		Player2lb = new JLabel("Người chơi 2", JLabel.CENTER);
		Player2lb.setFont(new Font("Serif", Font.BOLD, 18));
		Player2lb.setOpaque(true);
		Player2lb.setBackground(new Color(173, 216, 230));  
		Player2lb.setPreferredSize(new Dimension(250, 50));

		Name1lb = new JLabel("Dxvjka (Bạn)", JLabel.CENTER);
		Name1lb.setFont(new Font("Serif", Font.BOLD, 18));
		Name1lb.setOpaque(true);
		Name1lb.setBackground(new Color(144, 238, 144));  
		Name1lb.setPreferredSize(new Dimension(250, 50));

		Name2lb = new JLabel("Trống (Mời)", JLabel.CENTER);
		Name2lb.setFont(new Font("Serif", Font.BOLD, 18));
		Name2lb.setOpaque(true);
		Name2lb.setBackground(new Color(192, 192, 192)); 
		Name2lb.setPreferredSize(new Dimension(250, 50));
	}

	InviteRoomFrame() {
		button();
		panel();
		label();
		setTitle("Giao diện phòng mời");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 400);
		setLayout(new BorderLayout());

		add(Pairpn, BorderLayout.CENTER);
		add(Buttonpn, BorderLayout.SOUTH);
		add(friendListpn, BorderLayout.EAST);

		Buttonpn.add(Startbtn);
		Buttonpn.add(Leavebtn);

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 0, 10, 0);

		// Thêm Player1lb
		gc.gridx = 0;
		gc.gridy = 0;
		Pairpn.add(Player1lb, gc);

		// Thêm Name1lb
		gc.gridx = 0;
		gc.gridy = 1;
		Pairpn.add(Name1lb, gc);

		// Thêm Player2lb
		gc.gridx = 0;
		gc.gridy = 2;
		Pairpn.add(Player2lb, gc);

		// Thêm Name2lb
		gc.gridx = 0;
		gc.gridy = 3;
		Pairpn.add(Name2lb, gc);

		setVisible(true);
	}
    
	public static void main(String[] args) {
		new InviteRoomFrame();
	}
}
