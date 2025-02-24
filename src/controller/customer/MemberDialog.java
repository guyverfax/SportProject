package controller.customer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import dao.ProductDao;
import dao.impl.ProductDaoImpl;
import model.Member;
import model.Porder;
import model.PorderSummary;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MemberDialog extends JDialog {
	private int totalAmount;
	private JTable detailsTable;
	private ProductDao productDao=new ProductDaoImpl();
	
    public MemberDialog(JFrame parent, String porderno, Member member, List<Porder> porders) {

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
        
        JLabel lblTotalAmount = new JLabel("總金額: ");
        lblTotalAmount.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        panel2.add(lblTotalAmount);
        lblTotalAmount.setText("總金額: "+totalAmount);
        
        JButton btnWord = new JButton("列印Word");
        btnWord.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		exportToWord(new JLabel[]{ 
        				  lblPorderno, 
        				  lblMembername,
						  lblMemberphone,
						  lblMemberaddress,
						  lblTotalAmount,
						  },
        				detailsTable);
        	}
        });
        btnWord.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        panel2.add(btnWord);
        panel2.add(btnClose);
        getContentPane().add(panel2, BorderLayout.SOUTH);
        
        
    }
    
    public static void exportToWord(JLabel[] labels, JTable table ) {

		String filepath="客戶自己查詢的訂單內容.docx";
		try (XWPFDocument document = new XWPFDocument()) {

            // 依序添加 JLabel 標題
            for (JLabel label : labels) {
                XWPFParagraph title = document.createParagraph();
                title.setAlignment(ParagraphAlignment.LEFT); // 靠左
                XWPFRun titleRun = title.createRun();
                titleRun.setText(label.getText()); // 讀取 JLabel 內容
                titleRun.setBold(true);
                titleRun.setFontSize(14);
            }

            // 讀取 JTable 數據
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int rowCount = model.getRowCount();
            int colCount = model.getColumnCount();

            // 建立 Word 表格，先創建第一列（標題列）
            XWPFTable wordTable = document.createTable();
            wordTable.setWidth("100%");  // 設定表格寬度

            // **標題列**
            XWPFTableRow headerRow = wordTable.getRow(0); // 取得第一行（已自動建立）
            for (int col = 0; col < colCount; col++) {
                XWPFTableCell cell = headerRow.getCell(col);
                if (cell == null) {
                    cell = headerRow.createCell(); // 確保 Cell 存在
                }
                cell.setText(model.getColumnName(col));

                // 設定背景顏色（深灰色）
                cell.getCTTc().addNewTcPr().addNewShd().setFill("CCCCCC");

                // 設定字體加粗
                XWPFParagraph p = cell.getParagraphs().get(0);
                XWPFRun r = p.createRun();
                r.setBold(true);
            }

            // **填充表格內容**
            for (int row = 0; row < rowCount; row++) {
                XWPFTableRow tableRow = wordTable.createRow(); // 正確方式：**建立新行**
                for (int col = 0; col < colCount; col++) {
                    XWPFTableCell cell = tableRow.getCell(col);
                    if (cell == null) {
                        cell = tableRow.createCell(); // 確保 Cell 存在
                    }
                    Object value = model.getValueAt(row, col);
                    cell.setText(value != null ? value.toString() : ""); // 避免 NullPointerException
                }
            }

            // 儲存 Word 檔案
            try (FileOutputStream out = new FileOutputStream(filepath)) {
                document.write(out);
                JOptionPane.showMessageDialog(null, "建立 " + filepath + " 成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Word 匯出失敗！", "錯誤", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
