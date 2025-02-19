package controller.customer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import dao.ProductDao;
import dao.impl.ProductDaoImpl;
import model.Member;
import model.Porder;
import model.PorderSummary;

public class MemberReadDialog extends JDialog {
	private int totalAmount;
	private JTable detailsTable;
	private ProductDao productDao=new ProductDaoImpl();
	
    public MemberReadDialog(JFrame parent, String porderno, Member member, List<Porder> porders) {

    	super(parent, "客戶訂單詳情", true);
        setSize(500, 300);
        setLocationRelativeTo(parent);

        // 設定表格
        totalAmount=0;
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"產品料號", "產品名稱", "數量", "小計"}, 0);
        for (Porder p : porders) {
        	tableModel.addRow(new Object[]{
            		p.getProductno(),
            		productDao.readByProductno(p.getProductno()).getProductname(),
            		p.getAmount(),
            		productDao.readByProductno(p.getProductno()).getPrice()*p.getAmount()
            		});
            totalAmount+=productDao.readByProductno(p.getProductno()).getPrice()*p.getAmount();
        }
	    
        detailsTable = new JTable(tableModel);
        detailsTable.setEnabled(false); // 設唯讀
        JScrollPane scrollPane = new JScrollPane(detailsTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel panel1 = new JPanel();
        getContentPane().add(panel1, BorderLayout.NORTH);
        panel1.setLayout(new GridLayout(3, 5, 0, 0));
        
        JLabel lblPorderno = new JLabel("訂單編號: ");
        lblPorderno.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        panel1.add(lblPorderno);
        lblPorderno.setText("訂單編號: "+porderno);
        
        JLabel lblNewLabel1 = new JLabel("");
        panel1.add(lblNewLabel1);
        
        JLabel lblMembername = new JLabel("名字: ");
        lblMembername.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        panel1.add(lblMembername);
        lblMembername.setText("名字: "+member.getName());
        
        JLabel lblNewLabel2 = new JLabel("");
        panel1.add(lblNewLabel2);
        
        JLabel lblMemberphone = new JLabel("電話: ");
        lblMemberphone.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        panel1.add(lblMemberphone);
        lblMemberphone.setText("電話: "+member.getPhone());
        
        JLabel lblMemberaddress = new JLabel("地址: ");
        lblMemberaddress.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        panel1.add(lblMemberaddress);
        lblMemberaddress.setText("地址: "+member.getAddress());
        
        JPanel panel2 = new JPanel();
        
        JButton btnClose = new JButton("關閉");
        btnClose.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnClose.addActionListener(e -> dispose());
        
        
        JButton btnPrint = new JButton("列印");
        btnPrint.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        panel2.add(btnPrint);
        btnPrint.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                try (PrintWriter writer = new PrintWriter(fileChooser.getSelectedFile())) {
                	writer.write('\ufeff');
                    for (int i = 0; i < tableModel.getColumnCount(); i++) {
                        writer.print(tableModel.getColumnName(i) + (i == tableModel.getColumnCount() - 1 ? "\n" : ","));
                    }
                    for (int row = 0; row < tableModel.getRowCount(); row++) {
                        for (int col = 0; col < tableModel.getColumnCount(); col++) {
                            writer.print(tableModel.getValueAt(row, col) + (col == tableModel.getColumnCount() - 1 ? "\n" : ","));
                        }
                    }
                    JOptionPane.showMessageDialog(this, "資料已成功匯出！");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "匯出失敗：" + ex.getMessage());
                }
            }
        });
        panel2.add(btnClose);
        
        JLabel lblTotalAmount = new JLabel("總金額: ");
        lblTotalAmount.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        panel2.add(lblTotalAmount);
        getContentPane().add(panel2, BorderLayout.SOUTH);
        lblTotalAmount.setText("總金額: "+totalAmount);
        
        
    }
    
    // 匯出 Word
    public static void exportToWord(String porderno, String productno, String productname, String memberno, String employno, double price, int amount, double subtotal, int quantity) {
        try {
            File file = new File("訂單_" + porderno + ".doc");
            FileWriter fw = new FileWriter(file);
            fw.write("訂單詳細\n");
            fw.write("=======================\n");
            fw.write("訂單編號: " + porderno + "\n");
            fw.write("產品編號: " + productno + "\n");
            fw.write("產品名稱: " + productname + "\n");
            fw.write("客戶編號: " + memberno + "\n");
            fw.write("員工編號: " + employno + "\n");
            fw.write("產品單價: " + price + "\n");
            fw.write("購買數量: " + amount + "\n");
            fw.write("金額小計: " + subtotal + "\n");
            fw.write("庫存數量: " + quantity + "\n");
            fw.flush();
            fw.close();
            JOptionPane.showMessageDialog(null, "Word 檔案已匯出: " + file.getAbsolutePath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "匯出 Word 失敗: " + ex.getMessage());
        }
    }

}
