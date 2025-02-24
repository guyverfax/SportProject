package controller.porder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.Font;
import java.awt.GridLayout;

import dao.EmployDao;
import dao.impl.EmployDaoImpl;
import dao.MemberDao;
import dao.PorderDao;
import dao.ProductDao;
import dao.impl.MemberDaoImpl;
import dao.impl.PorderDaoImpl;
import dao.impl.ProductDaoImpl;
import model.Employ;
import model.Member;
import model.Porder;
import model.Product;
import util.Tool;
import java.awt.Component;

public class PorderCreate3 extends JFrame{
    private static JTable orderTable;
    private JLabel lblTotalAmount; //顥示總金額
    private int totalAmount=0; //計算總金額
    
    //Order No
    private String orderNo;
    
    //for selected product
    private Product selectedProduct; 
    private String selectedProductno;
    private String selectedProductname;
    private int selectedProductprice;
    private int selectedProductquantity;
    
    Employ employ=(Employ) Tool.read("employ.txt");
    private String loginEmployno=employ.getEmployno();
    private String loginEmployname=employ.getName();
    private String loginEmployusername;
    private String infoEmployrole;
    
    Member member=(Member) Tool.read("member.txt");
    private String selectMemberno=member.getMemberno();
    private String selectMembername=member.getName();
    private String selectMemberphone=member.getPhone();
    private String selectMemberaddress=member.getAddress();
    
    private MemberDao memberDao=new MemberDaoImpl();
    private EmployDao employDao=new EmployDaoImpl();
    private ProductDao productDao=new ProductDaoImpl();
    private PorderDao porderDao=new PorderDaoImpl();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PorderCreate3().setVisible(true));
    }
    
    public PorderCreate3() {
        setTitle("新增訂單 - 成立訂單");
        setSize(600, 521);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 586, 20);
        topPanel.setBackground(new Color(255, 255, 204));
        getContentPane().add(topPanel);
        topPanel.setLayout(new GridLayout(1, 0, 0, 0));
        
        JLabel lblSelectMember = new JLabel("1.選擇客戶");
        lblSelectMember.setBackground(Color.LIGHT_GRAY);
        lblSelectMember.setHorizontalAlignment(SwingConstants.CENTER);
        lblSelectMember.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblSelectMember.setOpaque(true);
        topPanel.add(lblSelectMember);
        
        JLabel lblSelectProduct = new JLabel("2.挑選產品");
        lblSelectProduct.setBackground(Color.LIGHT_GRAY);
        lblSelectProduct.setHorizontalAlignment(SwingConstants.CENTER);
        lblSelectProduct.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblSelectProduct.setOpaque(true);
        topPanel.add(lblSelectProduct);
        
        JLabel lblCheckOut = new JLabel("3.成立訂單");
        lblCheckOut.setBackground(Color.GREEN);
        lblCheckOut.setHorizontalAlignment(SwingConstants.CENTER);
        lblCheckOut.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblCheckOut.setOpaque(true);
        topPanel.add(lblCheckOut);

        JPanel infoPanel = new JPanel();
        infoPanel.setBounds(0, 20, 585, 234);
        infoPanel.setBackground(SystemColor.inactiveCaptionBorder);
        getContentPane().add(infoPanel);
        infoPanel.setLayout(new GridLayout(6, 2, 10, 10));
        
        JLabel lblOrderno = new JLabel("訂單號碼: ");
        lblOrderno.setForeground(Color.BLACK);
        lblOrderno.setBackground(Color.WHITE);
        lblOrderno.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        infoPanel.add(lblOrderno);
        orderNo=Tool.generateOrderNumber();
        lblOrderno.setText("訂單號碼: "+orderNo);
        
        JLabel lblOrdertime = new JLabel("訂單時間: ");
        lblOrdertime.setForeground(Color.BLACK);
        lblOrdertime.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblOrdertime.setBackground(Color.WHITE);
        infoPanel.add(lblOrdertime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateTime = sdf.format(new Date());
		lblOrdertime.setText("訂單時間: "+currentDateTime);
        
        JLabel lblEmployno = new JLabel("員工編號: ");
        lblEmployno.setForeground(Color.BLACK);
        lblEmployno.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblEmployno.setBackground(Color.WHITE);
        infoPanel.add(lblEmployno);
        lblEmployno.setText("員工編號: "+loginEmployno);
        
        JLabel lblEmployname = new JLabel("員工名字: ");
        lblEmployname.setForeground(Color.BLACK);
        lblEmployname.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblEmployname.setBackground(Color.WHITE);
        infoPanel.add(lblEmployname);
        lblEmployname.setText("員工名字: "+loginEmployname);
        
        JLabel lblMemberno = new JLabel("客戶編號: ");
        lblMemberno.setForeground(Color.BLACK);
        lblMemberno.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblMemberno.setBackground(Color.WHITE);
        infoPanel.add(lblMemberno);
        lblMemberno.setText("客戶編號: "+selectMemberno);
        
        JLabel lblMembername = new JLabel("客戶名字: ");
        lblMembername.setForeground(Color.BLACK);
        lblMembername.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblMembername.setBackground(Color.WHITE);
        infoPanel.add(lblMembername);
        lblMembername.setText("客戶名字: "+selectMembername);
        
        JLabel lblMemberphone = new JLabel("客戶電話: ");
        lblMemberphone.setForeground(Color.BLACK);
        lblMemberphone.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblMemberphone.setBackground(Color.WHITE);
        infoPanel.add(lblMemberphone);
        lblMemberphone.setText("客戶電話: "+selectMemberphone);
        
        JLabel lblMemberaddress = new JLabel("客戶地址: ");
        lblMemberaddress.setForeground(Color.BLACK);
        lblMemberaddress.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblMemberaddress.setBackground(Color.WHITE);
        infoPanel.add(lblMemberaddress);
        lblMemberaddress.setText("客戶地址: "+selectMemberaddress);
              
        JPanel orderPanel = new JPanel();
        orderPanel.setBounds(0, 250, 586, 234);
        getContentPane().add(orderPanel);
        orderPanel.setBackground(SystemColor.inactiveCaptionBorder);
        orderPanel.setLayout(null);
        
        orderTable = new JTable();
        orderTable.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
        orderTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"產品編號", "產品名稱", "產品價格", "購買數量", "小計金額"})
        {
        	//惟讀
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}	
        });
        JScrollPane orderSL = new JScrollPane(orderTable);
        orderSL.setBounds(0, 10, 586, 156);
        orderPanel.add(orderSL);
        
      
        JPanel orderPN = new JPanel();
        orderPN.setBackground(SystemColor.inactiveCaptionBorder);
        orderPN.setBounds(0, 172, 586, 41);
        orderPanel.add(orderPN);
        orderPN.setLayout(null);
        
        lblTotalAmount = new JLabel("總金額:");
        lblTotalAmount.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblTotalAmount.setBounds(10, 10, 112, 30);
        orderPN.add(lblTotalAmount);
        
        JButton btnSubmit = new JButton("送出訂單");
        btnSubmit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (totalAmount<=0)
        		{
        			JOptionPane.showMessageDialog(null, "請先增加產品", "提示", JOptionPane.INFORMATION_MESSAGE);
        			return ;
        		}
        		
        		DefaultTableModel orderModel = (DefaultTableModel) orderTable.getModel();
        		
        		//整筆判斷
        		List<Porder> orders=new ArrayList();
        		for (int i = 0; i < orderModel.getRowCount(); i++) {
					String productno=(String) orderModel.getValueAt(i, 0);
					int amount=(int) orderModel.getValueAt(i, 3);					
					Porder porder=new Porder(orderNo,productno,selectMemberno,loginEmployno,amount);
					orders.add(porder);			
        		}
        		
        		boolean success = porderDao.createUpdateStock(orders);
        		if (success) {
        			JOptionPane.showMessageDialog(null, "訂單成立，庫存已更新，回到主頁！", "提示", JOptionPane.INFORMATION_MESSAGE);
        			Tool.goMainUI(PorderCreate3.this);
        		} else {
                	JOptionPane.showMessageDialog(null, "訂單建立失敗，可能是庫存不足或系統錯誤！", "錯誤", JOptionPane.ERROR_MESSAGE);
                }
        		
        		/*單筆判斷
        		for (int i = 0; i < orderModel.getRowCount(); i++) {
					String productno=(String) orderModel.getValueAt(i, 0);
					int amount=(int) orderModel.getValueAt(i, 3);					
					Porder porder=new Porder(orderNo,productno,infoMemberno,infoEmployno,amount);
					boolean success=porderDao.createUpdateStock(porder);			
        		}		
        		JOptionPane.showMessageDialog(null, " 訂單成立，庫存已更新，回到主頁！", "提示", JOptionPane.INFORMATION_MESSAGE);
				
				Tool.goMainUI(PorderCreate3.this);
				*/
				return ;			
        	}
        });
        btnSubmit.setBounds(356, 10, 90, 30);
        orderPN.add(btnSubmit);
        btnSubmit.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        
        JButton btnBack = new JButton("回到主頁");
        btnBack.setBounds(470, 10, 90, 30);
        orderPN.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Tool.goMainUI(PorderCreate3.this);
        		dispose();
        	}
        });
        btnBack.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        
        JButton btnWord = new JButton("列印Word");
        btnWord.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		exportToWord(new JLabel[]{lblOrderno, 
        								  lblOrdertime, 
        								  lblEmployno, 
        								  lblEmployname,
        								  lblMemberno,
        								  lblMembername,
        								  lblMemberphone,
        								  lblMemberaddress,
        								  lblTotalAmount,
        								  },
        								  orderTable);
        	}
        });
        btnWord.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnWord.setBounds(132, 10, 100, 30);
        orderPN.add(btnWord);
        
        JButton btnExcel = new JButton("列印Excel");
        btnExcel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		exportToExcel(orderTable,
        				lblOrderno.getText(),
        				lblOrdertime.getText(),
        				lblEmployno.getText(),
        				lblEmployname.getText(),
        				lblMemberno.getText(),
        				lblMembername.getText(),
        				lblMemberphone.getText(),
        				lblMemberaddress.getText()      				        				
        				);
        	}
        });
        btnExcel.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnExcel.setBounds(242, 10, 100, 30);
        orderPN.add(btnExcel);
         
        loadOrderTable();
    	calculateTotalAmount();
    }

    private void sortOrderTable() {
    	DefaultTableModel orderModel = (DefaultTableModel) orderTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(orderModel);
        orderTable.setRowSorter(sorter);
        sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING))); // 0 表示第一欄 productno
    }
    
    public void calculateTotalAmount() {
    	DefaultTableModel orderModel = (DefaultTableModel) orderTable.getModel();
    	totalAmount=0;
    	for (int i = 0; i < orderModel.getRowCount(); i++) {
            totalAmount+=(int)orderModel.getValueAt(i, 4);
        }
    	lblTotalAmount.setText("總金額: "+totalAmount+" 元");
    }
    
    public void saveOrderTable() {
    	DefaultTableModel orderModel = (DefaultTableModel) orderTable.getModel();
    	DefaultTableModel orderModel2 = new DefaultTableModel(
    		    new String[]{"產品編號", "產品名稱", "產品價格", "購買數量", "小計金額"}, 0
    		);
    	int rowCount = orderModel.getRowCount();
    	int colCount = orderModel.getColumnCount();
    	for (int i = 0; i < rowCount; i++) {
    	    Object[] rowData = new Object[colCount]; // 建立存放單列資料的陣列
    	    for (int j = 0; j < colCount; j++) {
    	        rowData[j] = orderModel.getValueAt(i, j); // 逐個欄位複製
    	    }
    	    orderModel2.addRow(rowData); // 將資料加入新的 TableModel
    	}
    	Tool.saveOrderModel(orderModel2, "orderModel.txt");
    }
    
    public void loadOrderTable() {
    	DefaultTableModel loadedOrderModel = Tool.loadOrderModel("orderModel.txt");
    	if (loadedOrderModel != null) {
    	    //System.out.println("orderModel 已成功讀取！");
    	}
    	
    	DefaultTableModel orderModel = new DefaultTableModel(
    		    new String[]{"產品編號", "產品名稱", "產品價格", "購買數量", "小計金額"}, 0
    		) {
    		    @Override
    		    public boolean isCellEditable(int row, int column) {
    		        return false; // 讓所有儲存格不可編輯
    		    }
    		};
    	int rowCount = loadedOrderModel.getRowCount();
    	int colCount = loadedOrderModel.getColumnCount();
    	for (int i = 0; i < rowCount; i++) {
    	    Object[] rowData = new Object[colCount]; // 建立存放單列資料的陣列
    	    for (int j = 0; j < colCount; j++) {
    	        rowData[j] = loadedOrderModel.getValueAt(i, j); // 逐個欄位複製
    	    }
    	    orderModel.addRow(rowData); // 將資料加入新的 TableModel
    	}
    	orderTable.setModel(orderModel);
    }  
    
    public static void exportToWord(JLabel[] labels, JTable table ) {

		String filepath="新的訂單內容.docx";
		try (XWPFDocument document = new XWPFDocument()) {

            // JLabel 標題
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
    
    public static void exportToExcel(JTable orderTable, String orderNo, String orderTime, String Employno, String Employname, String Memberno, String Membername, String Memberphone, String Memberaddress) {
    	String filepath="新的訂單內容.xlsx";
    	Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("新訂單內容");
        
        // 設定欄位寬度
        sheet.setColumnWidth(0, 25 * 256); // 描述
        sheet.setColumnWidth(1, 15 * 256); // 金額

        // 樣式
        CellStyle titleStyle = createStyle(workbook, true, 16);
        CellStyle textStyle = createStyle(workbook, false, 12);
        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.setDataFormat(workbook.createDataFormat().getFormat("NT$#,##0.00"));

        // 公司名稱 + 發票
        Row row = sheet.createRow(0);
        createCell(row, 0, "新訂單內容", titleStyle); //主標題
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 2));

        // 發票資訊
        createRow(sheet, 1, orderNo, "", textStyle);
        createRow(sheet, 2, orderTime, "", textStyle);
        createRow(sheet, 3, Employno, "", textStyle);
        createRow(sheet, 4, Employname, "", textStyle);

        // 地址與聯絡資訊
        createRow(sheet, 5, Memberno, "", textStyle);
        createRow(sheet, 6, Membername, "", textStyle);
        createRow(sheet, 7, Memberphone, "", textStyle);
        createRow(sheet, 8, Memberaddress, "", textStyle);

        // 購買明細標題
        row = sheet.createRow(9);
        createCell(row, 0, "描述", titleStyle);
        createCell(row, 1, "金額", titleStyle);

        // 讀取 JTable 數據
        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
        int rowCount = model.getRowCount();
        double subtotal = 0;

        for (int i = 0; i < rowCount; i++) {
            row = sheet.createRow(10 + i);
            String itemName = model.getValueAt(i, 1).toString(); // 假設第 1 欄是描述
            double amount = Double.parseDouble(model.getValueAt(i, 4).toString()); // 假設第 2 欄是金額
            createCell(row, 0, itemName, textStyle);
            createCell(row, 1, amount, currencyStyle);
            subtotal += amount;
        }

        // 小計
        createRow(sheet, 10 + rowCount, "總計", subtotal, currencyStyle);

        /*
        // 稅率計算
        double taxRate = 0.086;
        double taxAmount = subtotal * taxRate;
        createRow(sheet, 11 + rowCount, "稅率", "8.60%", textStyle);
        createRow(sheet, 12 + rowCount, "營業稅", taxAmount, currencyStyle);
        createRow(sheet, 13 + rowCount, "其他", 0, currencyStyle);

        // 總計
        double total = subtotal + taxAmount;
        row = sheet.createRow(14 + rowCount);
        createCell(row, 0, "總計", titleStyle);
        createCell(row, 1, total, currencyStyle);

		*/
        
        // 感謝語
        //createRow(sheet, 16 + rowCount, "感謝您的惠顧！", "", textStyle);
        //createRow(sheet, 17 + rowCount, "支票抬頭請一律填寫 貴公司名稱.", "如有問題請聯絡我們", textStyle);

        // 儲存檔案
        try (FileOutputStream fos = new FileOutputStream(filepath)) {
            workbook.write(fos);
            workbook.close();
            JOptionPane.showMessageDialog(null, "列印 "+filepath+" 成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Excel 匯出失敗！", "錯誤", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static void createRow(Sheet sheet, int rowIndex, String col1, String col2, CellStyle style) {
        Row row = sheet.createRow(rowIndex);
        createCell(row, 0, col1, style);
        createCell(row, 1, col2, style);
    }

    private static void createRow(Sheet sheet, int rowIndex, String col1, double col2, CellStyle style) {
        Row row = sheet.createRow(rowIndex);
        createCell(row, 0, col1, style);
        createCell(row, 1, col2, style);
    }

    private static void createCell(Row row, int col, String value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private static void createCell(Row row, int col, double value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private static CellStyle createStyle(Workbook workbook, boolean bold, int fontSize) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setBold(bold);
        font.setFontHeight(fontSize);
        style.setFont(font);
        return style;
    }
}
