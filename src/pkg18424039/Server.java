/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg18424039;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author user
 */
public class Server extends javax.swing.JFrame
{
    
    ServerSocket ss;
    HashMap clientColl = new HashMap();

    /**
     * Creates new form Server
     */
    public Server()
    {
        try
        {
            initComponents();
            
            ss = new ServerSocket(2089);
            this.lblServerStatus.setText("Server Started");
            
            new ClientAccept().start();
        }
        catch (Exception ex)
        {
            
        }
    }
    
    class ClientAccept extends Thread
    {
        
        public void run()
        {
            while (true)
            {
                try
                {
                    Socket s = ss.accept();
                    String tmp = new DataInputStream(s.getInputStream()).readUTF();
                    if (clientColl.containsKey(tmp))
                    {
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        dout.writeUTF("You are already registered....");
                    }
                    else
                    {
                        clientColl.put(tmp, s);
                        txtAreaMain.append(tmp + " Joined !\n");
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        dout.writeUTF("");
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    class MsgRead extends Thread
    {
        
        Socket s;
        String ID;
        
        MsgRead(Socket s, String ID)
        {
            this.s = s;
            this.ID = ID;
        }
        
        public void run()
        {
            while (!clientColl.isEmpty())
            {
                try
                {
                    String str = new DataInputStream((s.getInputStream())).readUTF();
                    if (str.equals("abcd"))
                    {
                        clientColl.remove(ID);
                        txtAreaMain.append(ID + " : Removed\n");
                        new PrepareClientList().start();
                        Set<String> setStr = clientColl.keySet();
                        
                        Iterator itr = setStr.iterator();
                        while (itr.hasNext())
                        {
                            String key = itr.next().toString();
                            if (key.equalsIgnoreCase(ID) == false)
                            {
                                try
                                {
                                    new DataOutputStream(((Socket) clientColl.get(key)).getOutputStream()).writeUTF("< " + ID + " to ALL > " + str);
                                }
                                catch (Exception ex)
                                {
                                    clientColl.remove(key);
                                    txtAreaMain.append(key + ": removed");
                                    new PrepareClientList().start();
                                }
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    class PrepareClientList extends Thread
    {
        
        public void run()
        {
            try
            {
                String IDs = "";
                Set k = clientColl.keySet();
                Iterator itr = k.iterator();
                while (itr.hasNext())
                {
                    String str = itr.next().toString();
                    IDs += str + ",";
                }
                if (IDs.length() != 0)
                {
                    IDs = IDs.substring(0, IDs.length() - 1);
                }
                
                itr = k.iterator();
                
                while (itr.hasNext())
                {
                    String key = itr.next().toString();
                    try
                    {
                        new DataOutputStream(((Socket) clientColl.get(key)).getOutputStream()).writeUTF(":;.,/=" + IDs);
                    }
                    catch (Exception e)
                    {
                        clientColl.remove(key);
                        txtAreaMain.append(key + ": removed");
                    }
                    
                }
            }
            catch (Exception e)
            {
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblServerStatus = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaMain = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MyServer");

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Server Status: ");

        lblServerStatus.setText("..............................................................");

        txtAreaMain.setColumns(20);
        txtAreaMain.setRows(5);
        jScrollPane1.setViewportView(txtAreaMain);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblServerStatus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblServerStatus))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblServerStatus;
    private javax.swing.JTextArea txtAreaMain;
    // End of variables declaration//GEN-END:variables
}
