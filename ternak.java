/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adithyo,Alfian,Ardyanreza
 */

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;


public class ternak extends javax.swing.JFrame {
    
    
    /**
     * Creates new form ternak
     */
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/ternak_db";
    static final String DB_USERNAME = "root";
    static final String DB_PASSWORD = "";
    static Statement stmt; 
    static Connection conn;
    static ResultSet rs;
    
    static void createStmt() throws SQLException {
        stmt = (Statement) conn.createStatement();
    }
    
    int no=0;
    String id;
    String jenis;
    String daya;
    String waktu;
    String pelihara;
    String biaya;
    String hasil;
    
    public ternak() {
        initComponents();
        getData();
        System.out.println(no);
        
    }
    
    void setData(int id, String jenis, String daya,String waktu,String pelihara,String biaya, String kate, String hasil){
        try {

            conn = konek();

            if (conn != null) {
                System.out.println("koneksi sukses!");
            }  

            createStmt();
            String sql = "INSERT INTO `table 1` "
                    + "(`id_ternak`, `jenis_ternak`, `daya_tahan`, `waktu`, `pemeliharaan`, `biaya`, `kat_biaya`,`hasil`)"
                    + " VALUES "
                    + "('"+id+"','"+jenis+"','"+daya+"','"+waktu+"','"+pelihara+"','"+biaya+"','"+kate+"','"+hasil+"')";

            if(stmt.executeUpdate(sql) > 0){
                System.out.println("Tambah berhasil");
            }
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    void getData(){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        try {
            
            conn = konek();
            
            if (conn != null) {
                System.out.println("koneksi sukses!");
            }  
            
            createStmt();
            String sql,kate;
            sql = "SELECT * from `table 1`";
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {                
                id = rs.getString("id_ternak");
                jenis = rs.getString("jenis_ternak");
                daya = rs.getString("daya_tahan");
                waktu = rs.getString("waktu");
                pelihara = rs.getString("pemeliharaan");
                biaya = rs.getString("biaya");
                hasil = rs.getString("hasil");
                Object[] row = {id, jenis, daya, waktu, pelihara, biaya, hasil};
                model.addRow(row);
                
                no++;
            }
            
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    String Hitung(String daya,String waktu,String pelihara,String biaya){
        float cocok = 0;
        float tcocok = 0;
        float dayaC = 0;float waktuC = 0;float peliharaC = 0;float biayaC = 0;
        float dayaT = 0;float waktuT = 0;float peliharaT = 0;float biayaT = 0;
        int a = Integer.parseInt(biaya);
        String kate;
        if (a <= 5000000) {
            kate = "Murah";
        } else if (a<=10000000) {
            kate = "Sedang";
        }else{
            kate = "Mahal";
        }
        try {
            
            conn = konek();
            
            if (conn != null) {
                System.out.println("koneksi sukses!");
            }  
            
            
            String[] sql = new String[10];
            sql[1] = "SELECT id_ternak from `table 1` where hasil='cocok'";
            sql[2] = "SELECT id_ternak from `table 1` where hasil='tidak cocok'";
            sql[3] = "SELECT id_ternak from `table 1` where hasil='cocok' and daya_tahan='"+daya+"'";
            sql[4] = "SELECT id_ternak from `table 1` where hasil='tidak cocok' and daya_tahan='"+daya+"'";
            sql[5] = "SELECT id_ternak from `table 1` where hasil='cocok' and waktu='"+waktu+"'";
            sql[6] = "SELECT id_ternak from `table 1` where hasil='tidak cocok' and waktu='"+waktu+"'";
            sql[7] = "SELECT id_ternak from `table 1` where hasil='cocok' and pemeliharaan='"+pelihara+"'";
            sql[8] = "SELECT id_ternak from `table 1` where hasil='tidak cocok' and pemeliharaan='"+pelihara+"'";
            sql[9] = "SELECT id_ternak from `table 1` where hasil='cocok' and kat_biaya='"+kate+"'";
            sql[0] = "SELECT id_ternak from `table 1` where hasil='tidak cocok' and kat_biaya='"+kate+"'";
            
            for (int i = 0; i < 10; i++) {
                createStmt();
                ResultSet rs = stmt.executeQuery(sql[i]);
                
                switch(i){
                    case 0:
                        while (rs.next()) {                
                            biayaT++;
                        }
                    case 1:
                        while (rs.next()) {                
                            cocok++;
                        }
                    case 2:
                        while (rs.next()) {                
                            tcocok++;
                        }
                    case 3:
                        while (rs.next()) {                
                            dayaC++;
                        }
                    case 4:
                        while (rs.next()) {                
                            dayaT++;
                        }
                    case 5:
                        while (rs.next()) {                
                            waktuC++;
                        }
                    case 6:
                        while (rs.next()) {                
                            waktuT++;
                        }
                    case 7:
                        while (rs.next()) {                
                            peliharaC++;
                        }
                    case 8:
                        while (rs.next()) {                
                            peliharaT++;
                        }
                    case 9:
                        while (rs.next()) {                
                            biayaC++;
                        }
                }
            }
            rs.close();//rs2.close();rs3.close();rs4.close();rs6.close();rs7.close();rs8.close();//rs9.close();rs0.close();
            stmt.close();
            conn.close();
            System.out.println("cocok = "+cocok+ "\n" +"Tidak cocok = "+tcocok+ "\n" +
                                " Daya cocok = "+dayaC+ "\n" +" dayaT cocok = "+dayaT+ "\n" +
                                        "waktuC cocok = "+waktuC+"\n"+"waktuT cocok = "+waktuT+"\n"+" pelihara cocok = "+peliharaC+"\n"+"peliharaT cocok = "+
                                peliharaT+"\n"+"biaya cocok = "+biayaC+"\n"+"biayaT cocok = "+biayaT+"\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String HC = "COCOK";
        String HT = "TIDAK COCOK";
        String HA = null;
        float HasilSC,HasilST,Hcocok,HTcocok,Hdaya,HTdaya,Hwaktu,HTwaktu,Hpelihara,HTpelihara,Hbiaya,HTbiaya;
        Hcocok = cocok/no;
        HTcocok = tcocok/no;
        System.out.println("COCOK = "+Hcocok);
        System.out.println("TIDAK COCOK = "+HTcocok);
        Hdaya = dayaC/cocok;
        HTdaya = dayaT/tcocok;
        System.out.println("DAYA TAHAN = "+ daya +"HASIL COCOK = " + Hdaya );
        System.out.println("DAYA TAHAN = "+ daya +"HASIL TIDAK COCOK= " + HTdaya);
        Hwaktu = waktuC/cocok;
        HTwaktu = waktuT/tcocok;
        System.out.println("WAKTU PERTUMBUHAN = " + waktu +"HASIL COCOK = " + Hwaktu);
        System.out.println("WAKTU PERTUMBUHAN = " + waktu +"HASIL TIDAK COCOK= " + HTwaktu);
         Hpelihara = peliharaC/cocok;
        HTpelihara = peliharaT/tcocok;
        System.out.println("PEMELIHARAAN = " + pelihara +"HASIL COCOK = " + Hpelihara);
        System.out.println("PEMELIHARAAN = " + pelihara +"HASIL TIDAK COCOK = " + HTpelihara);
        Hbiaya = biayaC/cocok;
        HTbiaya = biayaT/tcocok;
        System.out.println("BIAYA = " + kate +"HASIL COCOK = " + Hbiaya);
        System.out.println("BIAYA = " + kate +"HASIL TIDAK COCOK = " + HTbiaya);
        HasilSC = (Hdaya*Hwaktu*Hpelihara*Hbiaya);
        System.out.println("HASIL KESELURUHAN (COCOK) = "+HasilSC);
        HasilST = (HTdaya*HTwaktu*HTpelihara*HTbiaya);
        System.out.println("HASIL KESELURUHAN (TIDAK COCOK) = "+HasilST);
        if(HasilSC > HasilST){
            System.out.println("Kesimpulan = " + HC );
            HA = HC;
        }else{
            System.out.println("kesimpulan = " + HT);
            HT = HA;
        }
        
        return HA;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        cbDaya = new javax.swing.ButtonGroup();
        cbWaktu = new javax.swing.ButtonGroup();
        cbPelihara = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtJenis = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtBiaya = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        cmbDaya = new javax.swing.JComboBox<>();
        cmbWaktu = new javax.swing.JComboBox<>();
        cmbPelihara = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "no", "Jenis Ikan", "daya Tahan", "Waktu", "Pemeliharaan", "Biaya", "Hasil"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(10);
        }

        jLabel1.setText("Jenis :");

        jLabel2.setText("Daya tahan :");

        jLabel3.setText("Waktu :");

        jLabel4.setText("Pemeliharaan :");

        jLabel5.setText("Biaya :");

        jButton1.setText("HITUNG");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cmbDaya.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rendah", "Sedang", "Tinggi" }));
        cmbDaya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDayaActionPerformed(evt);
            }
        });

        cmbWaktu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cepat", "Lambat" }));
        cmbWaktu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbWaktuActionPerformed(evt);
            }
        });

        cmbPelihara.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mudah", "Sulit" }));
        cmbPelihara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPeliharaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(txtJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBiaya, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDaya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbWaktu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPelihara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(484, 484, 484))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtJenis, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbDaya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbWaktu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbPelihara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtBiaya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        no+=1;
        jenis = txtJenis.getText();
        daya = (String) cmbDaya.getSelectedItem();
        waktu = (String) cmbWaktu.getSelectedItem();
        pelihara = (String) cmbPelihara.getSelectedItem();
        biaya = txtBiaya.getText();
        hasil = "";
        int a = Integer.parseInt(biaya);
        String kate;
        if (a>0) {            
            if (a <= 5000000) {
                kate = "Murah";
            } else if (a<=10000000) {
                kate = "Sedang";
            }else{
                kate = "Mahal";
            }
            //Hitung
            
            setData(no, jenis, daya, waktu, pelihara, biaya, kate, Hitung(daya, waktu, pelihara, biaya));
            getData();
            txtJenis.setText("");
            txtBiaya.setText("");
        }
    }                                        

    private void cmbDayaActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void cmbWaktuActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void cmbPeliharaActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           
    public static Connection konek(){
        Connection conn = null;
        
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Coba koneksi DB");
            conn = (Connection) DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ternak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ternak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ternak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ternak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ternak().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.ButtonGroup cbDaya;
    private javax.swing.ButtonGroup cbPelihara;
    private javax.swing.ButtonGroup cbWaktu;
    private javax.swing.JComboBox<String> cmbDaya;
    private javax.swing.JComboBox<String> cmbPelihara;
    private javax.swing.JComboBox<String> cmbWaktu;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBiaya;
    private javax.swing.JTextField txtJenis;
    // End of variables declaration                   
}
