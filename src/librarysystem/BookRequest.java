/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package librarysystem;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author lenovo
 */
public class BookRequest extends javax.swing.JFrame {

    /**
     * Creates new form BookRequest
     */
    public BookRequest() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(500, 300, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/librarysystem/Images/manual.png"))); // NOI18N
        jButton1.setText("Request Book");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/librarysystem/Images/search.png"))); // NOI18N
        jButton2.setText("View Requested Books");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/librarysystem/Images/delete.png"))); // NOI18N
        jButton3.setText("Delete requested Book");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon("C:\\Users\\lenovo\\Desktop\\final\\LibrarySystem\\Images\\back.png")); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(148, 148, 148))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(133, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addGap(114, 114, 114))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1))
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        playMusic("sound\\click.wav");
        String bookName = JOptionPane.showInputDialog(null, "Enter the book name:");
        String bookAuthor = JOptionPane.showInputDialog(null, "Enter the book author:");

        String[] bookTypes = {"Fiction", "Non-fiction", "Fantasy", "Science Fiction", "Biography", "History", "Children"};
        String bookType = (String) JOptionPane.showInputDialog(null, "Select the book type:", "Book Type", JOptionPane.QUESTION_MESSAGE, null, bookTypes, bookTypes[0]);

        if (bookName == null || bookName.isEmpty() || bookAuthor == null || bookAuthor.isEmpty() || bookType == null || bookType.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return;
        }

        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "123");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT book_requests_seq.NEXTVAL FROM dual");
            int requestId = 0;
            if (rs.next()) {
                requestId = rs.getInt(1);
            }

            java.sql.Date todayDate = new java.sql.Date(System.currentTimeMillis());

            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO book_requests (request_id, book_name, book_author, book_type, request_date) VALUES (?, ?, ?, ?, ?)"
            );
            pstmt.setInt(1, requestId);
            pstmt.setString(2, bookName);
            pstmt.setString(3, bookAuthor);
            pstmt.setString(4, bookType);
            pstmt.setDate(5, todayDate);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Book request added successfully.");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding book request: " + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        playMusic("sound\\click.wav");

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "123");

            PreparedStatement pstmt = conn.prepareStatement("SELECT request_id, book_name, book_author, book_type FROM book_requests");
            ResultSet rs = pstmt.executeQuery();

            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html>");
            htmlContent.append("<head>");
            htmlContent.append("<title>Book Requests Report</title>");
            htmlContent.append("<style>");
            htmlContent.append("body { font-family: Arial, sans-serif; background-color: #000; margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: 100vh; color: #fff; }");
            htmlContent.append(".container { width: 80%; max-width: 800px; background-color: #222; padding: 20px; box-shadow: 0 0 15px rgba(0,0,0,0.2); border-radius: 8px; border: 1px solid #444; }");
            htmlContent.append("h1 { text-align: center; color: #fff; margin-top: 0; }");
            htmlContent.append("h2 { text-align: center; color: #ccc; margin-bottom: 20px; }");
            htmlContent.append(".table-wrapper { overflow-x: auto; }");
            htmlContent.append("table { width: 100%; border-collapse: collapse; margin: 20px 0; background-color: #333; }");
            htmlContent.append("table, th, td { border: 1px solid #555; }");
            htmlContent.append("th, td { padding: 12px; text-align: center; color: #fff; cursor: pointer; }");
            htmlContent.append("th { background-color: #444; color: #fff; }");
            htmlContent.append("tr:nth-child(even) { background-color: #222; }");
            htmlContent.append("tr:nth-child(odd) { background-color: #333; }");
            htmlContent.append("tr:hover { background-color: #555; }");
            htmlContent.append(".print-button { display: block; width: 150px; padding: 10px; margin: 20px auto; text-align: center; border: none; border-radius: 5px; cursor: pointer; background-color: #4CAF50; color: white; }");
            htmlContent.append(".print-button:hover { background-color: #45a049; }");
            htmlContent.append("</style>");
            htmlContent.append("</head>");
            htmlContent.append("<body>");
            htmlContent.append("<div class='container'>");
            htmlContent.append("<h1>Book Requests Report</h1>");
            htmlContent.append("<button class='print-button' onclick='window.print()'>Print</button>");
            htmlContent.append("<div class='table-wrapper'>");
            htmlContent.append("<table>");
            htmlContent.append("<thead>");
            htmlContent.append("<tr><th>Book Name</th><th>Author</th><th>Type</th></tr>");
            htmlContent.append("</thead>");
            htmlContent.append("<tbody>");

            while (rs.next()) {
                int requestId = rs.getInt("request_id");
                String bookName = rs.getString("book_name");
                String bookAuthor = rs.getString("book_author");
                String bookType = rs.getString("book_type");
                htmlContent.append("<tr>");
                htmlContent.append("<td>").append(bookName).append("</td>");
                htmlContent.append("<td>").append(bookAuthor).append("</td>");
                htmlContent.append("<td>").append(bookType).append("</td>");
                htmlContent.append("</tr>");
            }

            htmlContent.append("</tbody>");
            htmlContent.append("</table>");
            htmlContent.append("</div>");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(new Date());
            htmlContent.append("<h2>Report Date: ").append(currentDate).append("</h2>");

            htmlContent.append("</div>");
            htmlContent.append("</body>");
            htmlContent.append("</html>");

            File tempHtmlFile = File.createTempFile("book_requests_report", ".html");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempHtmlFile))) {
                writer.write(htmlContent.toString());
            }

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(tempHtmlFile.toURI());
            } else {
                System.out.println("Desktop or browse action not supported");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        playMusic("sound\\click.wav");

        String[] options = {"Delete by Book Name", "Delete by Date"};
        int choice = JOptionPane.showOptionDialog(null, "Choose the deletion method:",
                "Delete Book",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "123");

            if (choice == 0) {
                String bookName = JOptionPane.showInputDialog(null, "Enter the book name to delete:");

                if (bookName == null || bookName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a book name.");
                    return;
                }

                String sql = "DELETE FROM book_requests WHERE book_name = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, bookName);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Book(s) deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "No book found with the specified name.");
                }

            } else if (choice == 1) {
                String dateInput = JOptionPane.showInputDialog(null, "Enter the date to delete books (dd/M/yyyy):");

                if (dateInput == null || dateInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a date.");
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date;
                try {
                    date = sdf.parse(dateInput);
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null, "Invalid date format. Please use dd/MM/yyyy.");
                    return;
                }

                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                String checkSql = "SELECT COUNT(*) FROM book_requests WHERE TRUNC(request_date) = TRUNC(?)";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setDate(1, sqlDate);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    String deleteSql = "DELETE FROM book_requests WHERE TRUNC(request_date) = TRUNC(?)";
                    PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                    deleteStmt.setDate(1, sqlDate);

                    int rowsAffected = deleteStmt.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Book(s) deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No books found for the specified date.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No books found for the specified date.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid choice.");
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        playMusic("sound\\click.wav");
        new BooksMain().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    public void playMusic(String file) {
        try {
            // حدد مسار ملف الصوت هنا
            File soundFile = new File(file);
            if (!soundFile.exists()) {
                throw new IOException("File not found: " + soundFile.getAbsolutePath());
            }
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.start();
        } catch (UnsupportedAudioFileException ex) {
            System.err.println("Unsupported audio file format.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.err.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("I/O error while playing the audio file.");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.err.println("An unexpected error occurred.");
            ex.printStackTrace();
        }

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
            java.util.logging.Logger.getLogger(BookRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookRequest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    // End of variables declaration//GEN-END:variables
}
