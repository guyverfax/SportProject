package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controller.portal.MainUI;
import controller.portal.RegisterUI;
import model.PorderSummary;

public class Tool {

	public static void save(Object s,String filename)
	{	
		try {
			
			FileOutputStream fos=new FileOutputStream(filename);
			ObjectOutputStream oos=new ObjectOutputStream(fos);		
			oos.writeObject(s);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Object read(String filename)
	{
		Object o=null;		
		try {
			FileInputStream fis=new FileInputStream(filename);
			ObjectInputStream ois=new ObjectInputStream(fis);
			o=ois.readObject();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return o;
	}

    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
    
    public static void updateDateTime(JLabel label) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateTime = sdf.format(new Date());
		label.setText(currentDateTime);
	}
    
    public static void goMainUI(JFrame jframe)
	{
		MainUI mainui=new MainUI();
		mainui.setVisible(true);		
		jframe.dispose();
	}
    
    // 自動產生訂單號碼
    public static synchronized String generateOrderNumber() {
    	int sequence = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        sequence++;
        return "O" + timestamp + String.format("%02d", sequence);
    }
    
    public static void saveOrderModel(DefaultTableModel tableModel, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(tableModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static DefaultTableModel loadOrderModel(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (DefaultTableModel) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    //正規化
	public boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{1,8}$";
        return password.matches(regex);
    }
    
	/*
    if (!isValidPassword(password)) {
        JOptionPane.showMessageDialog(RegisterUI.this, "密碼必須包含至少1個英文、1個數字和1個符號，且長度不超過8個字！", "錯誤", JOptionPane.ERROR_MESSAGE);
        return;
    }
    */
    
	//JTable table.getColumnModel().getColumn(0).setPreferredWidth(110);
	//find keyword in JTable
	public boolean isExistOnJtable(DefaultTableModel model, int index, String keyword)
	{
		boolean isExisted = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            String searchIndex = (String) model.getValueAt(i, index);
            if (keyword.equals(searchIndex)) {
                isExisted = true;
                break;
            }
        }
		return isExisted;
	}
	
	//計算總和
	public int calculateTotalAmount(DefaultTableModel model, int index) {
    	int totalAmount=0;
    	for (int i = 0; i < model.getRowCount(); i++) {
            totalAmount+=(int)model.getValueAt(i, index);
        }
    	return totalAmount;
    }
	
	/*load Data from MySQL
	 * private void loadPorderData() {
        tableModel.setRowCount(0);
        if ("Admin".equals(loginEmployRole)) {
            allPorders = pordersummaryDao.readAll();
        } else {
            allPorders = pordersummaryDao.readAllByEmployno(loingEmployno);
        }
        for (PorderSummary p : allPorders) {
            tableModel.addRow(new Object[]{p.getPorderno(), p.getMemberno(), p.getMembername(), p.getEmployno(), p.getEmployname(), p.getProducts(), p.getTotalprice()});
        }
     }
	*/
    
	/*
	 * private void searchPorder() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            loadPorderData(); // 如果沒輸入則顯示全部
            return;
        }

        List<PorderSummary> filteredPorders = allPorders.stream()
                .filter(p -> p.getPorderno().toLowerCase().contains(keyword) ||
                		     p.getMemberno().toLowerCase().contains(keyword) ||
                             p.getMembername().toLowerCase().contains(keyword) ||
                             p.getEmployno().toLowerCase().contains(keyword) ||
                             p.getProducts().toLowerCase().contains(keyword) ||
                             String.valueOf(p.getTotalprice()).toLowerCase().contains(keyword) ||
                             p.getEmployname().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        tableModel.setRowCount(0);
        for (PorderSummary p : filteredPorders) {
            tableModel.addRow(new Object[]{p.getPorderno(), p.getMemberno(), p.getMembername(), p.getEmployno(), p.getEmployname(), p.getProducts(), p.getTotalprice()});
        }
    }
	*/
	
	/*
	 * JComboBox<String> jcbProduct = new JComboBox<>(productDao.readByProductnoAndProductname());
       JComboBox<Integer> jcbAmount = new JComboBox<>(new Integer[] {1,2,3,4,5,6,7,8,9,10});
    */
	
	/*
    //orderModel 存 String, String, Integer(int->Integer, Integer, Integer(int), 
		        orderModel.addRow(new Object[] {
		        	selectedProductno, selectedProductname, selectedProductprice, 
		        	amount, selectedProductprice*amount
		        });
	 */
}