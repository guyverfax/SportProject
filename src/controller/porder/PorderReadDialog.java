package controller.porder;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Porder;

public class PorderReadDialog extends JDialog {
    private JTable detailsTable;

    public PorderReadDialog(JFrame parent, List<Porder> orderDetails) {
        super(parent, "訂單詳情", true);
        setSize(500, 300);
        setLocationRelativeTo(parent);

        // 設定表格
        DefaultTableModel model = new DefaultTableModel(new String[]{"訂單號碼", "產品編號", "客戶編號", "員工編號", "數量"}, 0);
        for (Porder order : orderDetails) {
            model.addRow(new Object[]{order.getPorderno(), order.getProductno(), order.getMemberno(), order.getEmployno(), order.getAmount()});
        }

        detailsTable = new JTable(model);
        detailsTable.setEnabled(false); // 設唯讀
        detailsTable.getColumnModel().getColumn(0).setPreferredWidth(120);  // 訂單號碼
        detailsTable.getColumnModel().getColumn(1).setPreferredWidth(80); // 客戶編號
        detailsTable.getColumnModel().getColumn(2).setPreferredWidth(80); // 客戶名字
        detailsTable.getColumnModel().getColumn(3).setPreferredWidth(80); // 員工編號
        JScrollPane scrollPane = new JScrollPane(detailsTable);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        JButton btnClose = new JButton("關閉");
        btnClose.addActionListener(e -> dispose());

        JPanel panel = new JPanel();
        panel.add(btnClose);
        getContentPane().add(panel, BorderLayout.SOUTH);
    }
}
