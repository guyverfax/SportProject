package controller.portal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PortalUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PortalUI frame = new PortalUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PortalUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("首頁");
		setBounds(100, 100, 726, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(0, 0, 0));
		panel1.setBounds(0, 10, 702, 65);
		contentPane.add(panel1);
		panel1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("體育用品店首頁");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("微軟正黑體", Font.BOLD, 40));
		lblNewLabel.setBounds(105, 10, 461, 45);
		panel1.add(lblNewLabel);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(0, 255, 255));
		panel2.setBounds(0, 85, 702, 315);
		contentPane.add(panel2);
		panel2.setLayout(null);
		
		JButton btnEmploy = new JButton("員工由此登入");
		btnEmploy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginUI frame=new LoginUI();
				frame.setVisible(true);			
				dispose();
			}
		});
		btnEmploy.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		btnEmploy.setBounds(191, 50, 260, 50);
		panel2.add(btnEmploy);
		
		JButton btnMember = new JButton("客戶由此登入");
		btnMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemberLoginUI frame=new MemberLoginUI();
				frame.setVisible(true);
				dispose();
			}
		});
		btnMember.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		btnMember.setBounds(191, 163, 260, 50);
		panel2.add(btnMember);
	}
}
