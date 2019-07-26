/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketprogramming;

import AES.Cryptography;
import DiffiHelman.DiffiHelman;
import com.sun.istack.internal.Nullable;
import java.io.*;
import java.net.*;
import java.util.Scanner;


/**
 *
 * @author Hasan Asadi
 */
public class MyServer extends javax.swing.JFrame{

    /**
     * Creates new form MyServer
     */
    private static ServerSocket serverSocket=null;
    private static Socket socket;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    private static final String Xb="5";//Private key of server
    private static String publicKey;
    private static String clientPublicKey;
    private static String sharedSecretKey;
    private static DiffiHelman diffiHelman=null;
    
    private static DataInputStream in;
    private static DataOutputStream out;
    
    public MyServer() {
        initComponents();
//        diffiHelman=new DiffiHelman(Xb);//Xb=serverPrivateKey
//        setupClient();
    }
    private static void disconnect() {
        try{
//          btnConnect.setText("Connect");
            socket.close();
            serverSocket.close();
            dataOutputStream.close();
            dataInputStream.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    
    private static String generatePublickey(){
        //Produce public key with diffi hellman Algorithm
        if(diffiHelman!=null)
            return String.valueOf(diffiHelman.generatePublicKey());
        else
            return "";
    }
    private static boolean exchangePublicKey(){
        
        boolean exchangedPublicKey=false;
        String myPublicKey;
        try{
            publicKey=dataInputStream.readUTF();
            txtArea.setText(txtArea.getText().trim()+"\n Recieved:client public key :"+publicKey);
            
            //Produce server's public key
            myPublicKey=generatePublickey();
            
            //Send server's public key to the client
            dataOutputStream.writeUTF(myPublicKey);
            txtArea.setText(txtArea.getText().trim()+"\n Server Public Key: "+myPublicKey);
            
            exchangedPublicKey=true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return exchangedPublicKey;
    } 
    private static int exchaneSharedKey() throws IOException{
        
        int p=in.read();
        int q=in.read();
        int publicclientkey=in.read();
        int privatekey=3;
        int publicserverkey=(int)(Math.pow(q,privatekey)%p);
        out.write(publicserverkey);

        System.out.println("Modulus "+p+" received from client");
        System.out.println("Base "+q+" received from client");
        System.out.println("Public server key "+publicserverkey+" sent to client");
        System.out.println("public client key "+publicclientkey+" received from client");
        int key=(int)(Math.pow(publicclientkey,privatekey)%p);
        System.out.println("Key:"+key);
        return key;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jEditorPaneMsg = new javax.swing.JEditorPane();
        txtMsg = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        lblReceivedMessage = new javax.swing.JLabel();
        lblCipherText = new javax.swing.JLabel();

        jEditorPaneMsg.setEditable(false);
        jEditorPaneMsg.setContentType("text/html"); // NOI18N
        jEditorPaneMsg.setToolTipText("");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TcpServer");

        txtMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMsgActionPerformed(evt);
            }
        });
        txtMsg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMsgKeyTyped(evt);
            }
        });

        btnSend.setText("send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        txtArea.setColumns(20);
        txtArea.setRows(5);
        jScrollPane1.setViewportView(txtArea);

        lblReceivedMessage.setText("Received Message");

        lblCipherText.setText("Cipher Text");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCipherText)
                    .addComponent(lblReceivedMessage)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(txtMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSend))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(lblCipherText)
                .addGap(36, 36, 36)
                .addComponent(lblReceivedMessage)
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMsgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMsgActionPerformed

    private void txtMsgKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMsgKeyTyped

    }//GEN-LAST:event_txtMsgKeyTyped

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        try {
            String msg=txtMsg.getText().trim();
           
            //encrypt Message
            Cryptography cryptography=new Cryptography();
            String cipherText=cryptography.encrypt(cryptography.getKey(),cryptography.getInitVector(),msg);
            
            lblCipherText.setText("Cipher Text: "+cipherText);
            lblReceivedMessage.setText("Received Message:");
                    
            dataOutputStream.writeUTF(cipherText);
            dataOutputStream.flush();
            
            txtMsg.setText("");
            txtArea.setText(txtArea.getText().trim()+ "\n You :"+msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }//GEN-LAST:event_btnSendActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){
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
            java.util.logging.Logger.getLogger(MyServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
      
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyServer().setVisible(true);
            }
        });
        try {
            ServerSocket server=new ServerSocket(9999);
            System.out.println("Waiting for client!");
            Socket socket=server.accept();
            in =new DataInputStream(socket.getInputStream());
            out=new DataOutputStream(socket.getOutputStream());
            //out.writeUTF("Welcome to Diffie Hellman!");
            System.out.println("Connected to:localhost portno:9999");
            
            int sharedKey=exchaneSharedKey();
            txtArea.setText(txtArea.getText().trim()+"shared key exchanged:"+sharedKey);
            
            //Start Send Recieve data
            while(true) {//!str.equals("stop"
                String str=in.readUTF();
                System.out.println("C : "+str);
                
            }
//            serverSocket = new ServerSocket(3422);
////          txtArea.setText("Server is  Ready...");
//            socket=serverSocket.accept();
//            dataInputStream =new DataInputStream(socket.getInputStream());
//            dataOutputStream=new DataOutputStream(socket.getOutputStream());
//            boolean p=exchangePublicKey();
//            if(p==true)
//            {
//                sharedSecretKey=String.valueOf(diffiHelman.generateSharedKey(publicKey));
//                txtArea.setText("shared Key is :"+sharedSecretKey);
//            }
//            disconnect();
//            String cipherMsg,plainMsg;
            /*boolean firstMessage=true;
           
            while(true) {//!str.equals("stop"
                cipherMsg=dataInputStream.readUTF();
//                if(firstMessage&&(!cipherMsg.equals(null)&&!cipherMsg.isEmpty())){
//                    firstMessage=false;
//                    publicKey=cipherMsg;
//                    
//                    //Produce Shared key
//                    sharedSecretKey=diffiHelman.sharedKey(publicKey).toString();
//                    txtArea.setText(txtArea.getText().trim()+"\n Client's Public Key="+publicKey);
//                    txtArea.setText(txtArea.getText().trim()+"\n Shared Secret Key="+sharedSecretKey);
//                }else if(!firstMessage&&(!cipherMsg.equals(null)&&!cipherMsg.isEmpty())){
                    Cryptography cryptography=new Cryptography();
                    plainMsg=cryptography.decrypt(cryptography.getKey(),cryptography.getInitVector(),cipherMsg);
                    lblCipherText.setText("Cipher Text:");
                    lblReceivedMessage.setText("Received Message :"+cipherMsg);
                    txtArea.setText(txtArea.getText().trim()+"\n Client: "+plainMsg);
//                }
            }   */
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JEditorPane jEditorPaneMsg;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JLabel lblCipherText;
    private static javax.swing.JLabel lblReceivedMessage;
    private static javax.swing.JTextArea txtArea;
    private javax.swing.JTextField txtMsg;
    // End of variables declaration//GEN-END:variables

    private void setupClient(){
        try{
            /*serverSocket = new ServerSocket(3422);
//          txtArea.setText("Server is  Ready...");
            socket=serverSocket.accept();
            dataInputStream =new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());*/

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
  
    
}
