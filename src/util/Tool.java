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
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controller.portal.MainUI;
import controller.portal.RegisterUI;

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
            //System.out.println("tableModel 已成功儲存！");
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
    
    
    /*
	private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{1,8}$";
        return password.matches(regex);
    }
    
    if (!isValidPassword(password)) {
        JOptionPane.showMessageDialog(RegisterUI.this, "密碼必須包含至少1個英文、1個數字和1個符號，且長度不超過8個字！", "錯誤", JOptionPane.ERROR_MESSAGE);
        return;
    }
    */
    
    
}