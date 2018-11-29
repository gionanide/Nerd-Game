package nerdgame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import static nerdgame.PracticeDifficultyGUI.correctanswerflag;

/**
 *
 * @author Jay
 */
public class RankedGamePlay extends javax.swing.JFrame {

    /**
     * Creates new form RankedGamePlay
     * 
     */
    
    //initialize connections with database
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    int points = 0;//points
    
    //IMAGES
    ImageIcon Quest1 = new ImageIcon(getClass().getResource("/Images/Questions/Question_Background_Correct.png"));
    ImageIcon Quest2 = new ImageIcon(getClass().getResource("/Images/Questions/Question_Background_Left_Correct.png"));
    
    
    
    public boolean flag=true;
    public  int counterQuestion = 0;
    public  int correctQuestions = 0;//sum of the correct questions
    int lives = 3;//every player starts with 3 lives
    int difficultyLevel = 1;//difficulty level
    
        Timer t = new Timer(1000, new TimerListener());//initialize timer
    
        class TimerListener implements ActionListener{
            int elapsedSeconds = 15;

            public void actionPerformed(ActionEvent evt){
                elapsedSeconds--;

                jLabel1.setText(""+elapsedSeconds);


                if(elapsedSeconds <= 0){


                    t.stop();
                    setClockText(""+15);
                    try {
                        pushRadioButton();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(RankedGamePlay.class.getName()).log(Level.SEVERE, null, ex);
                    }



                }
            }
        }    
    
    public RankedGamePlay() {
        initComponents();
        t.start();//start timer
    }

    public void pushRadioButton() throws ClassNotFoundException{
    int choice=0;//player's choice
       
       
       if(Answer1Button.isSelected())
       {
           choice = 1;
       }
       else if(Answer2Button.isSelected())
       {
           choice = 2;
       }
       else if(Answer3Button.isSelected())
       {
           choice = 3;
       }
       else if(Answer4Button.isSelected())
       {
           choice = 4;
       }
       else
       {
           //JOptionPane.showMessageDialog(null, "Timer beat you");
           lives--;
       }
       
       if(choice == PracticeDifficultyGUI.correctanswerflag)
       {
            try{
                         points = difficultyLevel*2 + points;
                         String sql1 = "select * from user where Username=?";
                         Class.forName("java.sql.Driver");
                         con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?zeroDateTimeBehavior=convertToNull","root","8520");
                         pst = con.prepareStatement(sql1);
                         pst.setString(1,LoginGUI.un);
                         
                         rs=pst.executeQuery();
                         String highScore = null;
                         if(rs.next())
                         {
                            highScore = rs.getString("Highscore");
                            System.out.println(highScore);
                         }
                            int high = Integer.valueOf(highScore);
                                    if(points > high)
                                    {
                                            String sql = "update user set Highscore=? where Username=?";
                                            try{
                                                Class.forName("java.sql.Driver");
                                                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?zeroDateTimeBehavior=convertToNull","root","8520");
                                                pst = con.prepareStatement(sql);
                                                String p = Integer.toString(points);
                                                //System.out.println(p);
                                                pst.setString(1,p);
                                                pst.setString(2,LoginGUI.un);
                                                pst.execute();
                                            }
                                            catch(Exception e){
                                            }
                                    }
                         
                         correctQuestions++;//correct answer
                     }
                     catch(SQLException ex){
            Logger.getLogger(RankedGamePlay.class.getName()).log(Level.SEVERE, null, ex);

                     }
       }
       else if(!(choice == 0))
       {
           //wrong answer
           lives--;
       }
     
       buttonGroup1.clearSelection();

        
        
        String arr = null;
      
        try {
            while (ModeGUI.rsRanked.next() && lives > 0) 
            { 
                t=new Timer(1000,new TimerListener());
                t.start();
                String em = ModeGUI.rsRanked.getString("Question");
                arr = em.replace("\n", ",");
                setQuestion(arr);
                
                
                
                int randomNum = ThreadLocalRandom.current().nextInt(0,4);
                int x = randomNum;
                int x1=x;
                int x2=x;
                int x3=x;
                
                while(x1==x)
                {
                    randomNum = ThreadLocalRandom.current().nextInt(0,4);
                    x1 = randomNum;
                }
                while(x2==x || x2==x1)
                {
                    randomNum = ThreadLocalRandom.current().nextInt(0,4);
                    x2 = randomNum;
                }
                while(x3==x || x3==x1 || x3==x2)
                {
                    randomNum = ThreadLocalRandom.current().nextInt(0,4);
                    x3 = randomNum;
                }
                
                
                if(x==0)
                {
                    em = ModeGUI.rsRanked.getString("Answercorrect");
                    arr = em.replace("\n", ",");
                    setAnswer1Button(arr);
                    correctanswerflag =1;//BUTTON ANSWER 1
                    
                }
                else if(x==1)
                {
                    em = ModeGUI.rsRanked.getString("Answercorrect");
                    arr = em.replace("\n", ",");
                    setAnswer2Button(arr);
                    correctanswerflag =2;//BUTTON ANSWER 2
                }
                else if(x==2)
                {
                    em = ModeGUI.rsRanked.getString("Answercorrect");
                    arr = em.replace("\n", ",");
                    setAnswer3Button(arr);
                    correctanswerflag =3;//BUTTON ANSWER 3
                }
                else
                {
                    em = ModeGUI.rsRanked.getString("Answercorrect");
                    arr = em.replace("\n", ",");
                    setAnswer4Button(arr);
                    correctanswerflag =4;//BUTTON ANSWER 4
                }
                
                if(x1==0)
                {
                    em = ModeGUI.rsRanked.getString("AnswerA");
                    arr = em.replace("\n", ",");
                    setAnswer1Button(arr);
                }
                else if(x1==1)
                {
                    em = ModeGUI.rsRanked.getString("AnswerA");
                    arr = em.replace("\n", ",");
                    setAnswer2Button(arr);
                }
                else if(x1==2)
                {
                    em = ModeGUI.rsRanked.getString("AnswerA");
                    arr = em.replace("\n", ",");
                    setAnswer3Button(arr);
                }
                else
                {
                    em = ModeGUI.rsRanked.getString("AnswerA");
                    arr = em.replace("\n", ",");
                    setAnswer4Button(arr);
                }
                
                if(x2==0)
                {
                    em = ModeGUI.rsRanked.getString("AnswerB");
                    arr = em.replace("\n", ",");
                    setAnswer1Button(arr);
                }
                else if(x2==1)
                {
                    em = ModeGUI.rsRanked.getString("AnswerB");
                    arr = em.replace("\n", ",");
                    setAnswer2Button(arr);
                }
                else if(x2==2)
                {
                    em = ModeGUI.rsRanked.getString("AnswerB");
                    arr = em.replace("\n", ",");
                    setAnswer3Button(arr);
                }
                else
                {
                    em = ModeGUI.rsRanked.getString("AnswerB");
                    arr = em.replace("\n", ",");
                    setAnswer4Button(arr);
                }
                
                if(x3==0)
                {
                    em = ModeGUI.rsRanked.getString("AnswerC");
                    arr = em.replace("\n", ",");
                    setAnswer1Button(arr);
                }
                else if(x3==1)
                {
                    em = ModeGUI.rsRanked.getString("AnswerC");
                    arr = em.replace("\n", ",");
                    setAnswer2Button(arr);
                }
                else if(x3==2)
                {
                    em = ModeGUI.rsRanked.getString("AnswerC");
                    arr = em.replace("\n", ",");
                    setAnswer3Button(arr);
                }
                else
                {
                    em = ModeGUI.rsRanked.getString("AnswerC");
                    arr = em.replace("\n", ",");
                    setAnswer4Button(arr);
                }
        break;
       }
            counterQuestion++;
        if(counterQuestion == 4)
            {
              //System.out.println(" "+counterQuestion+" "+correctQuestions);
              double per = (correctQuestions*100.0/counterQuestion);//percentage of correct questions
              
              int i=1;
              int n = JOptionPane.showConfirmDialog(null,per+"% success at level: " +i,"Ranked",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
              
              if ( n == JOptionPane.YES_OPTION)
              {
                  ModeGUI mgui = new ModeGUI();
                   dispose();
                    mgui.setVisible(true);

                    mgui.setSize(400, 500);
                    mgui.setLocationRelativeTo(null);

                    mgui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              }
              else
              {
                 PracticeDifficultyGUI pdg = new PracticeDifficultyGUI ();
                dispose();
                pdg.setVisible(true);
        
                pdg.setSize(400, 500);
                pdg.setLocationRelativeTo(null);
        
                pdg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              }
            }
        if(lives == 0){
           int n = JOptionPane.showOptionDialog(null,"You ran out of lives","Game over",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null);
             if ( n == JOptionPane.OK_OPTION){
                dispose();
                ModeGUI mgui = new  ModeGUI();
                mgui.setVisible(true);
        
                mgui.setSize(350, 500);
                mgui.setLocationRelativeTo(null);
        
                mgui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             }
        }
            
        } catch(Exception e){}
       
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        ExitButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Answer2Button = new javax.swing.JRadioButton();
        Answer4Button = new javax.swing.JRadioButton();
        Answer3Button = new javax.swing.JRadioButton();
        Answer1Button = new javax.swing.JRadioButton();
        QuestionArea = new javax.swing.JLabel();
        Bg3 = new javax.swing.JLabel();
        Bg4 = new javax.swing.JLabel();
        Bg1 = new javax.swing.JLabel();
        Bg2 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ranked ");
        setMinimumSize(new java.awt.Dimension(500, 500));
        setPreferredSize(new java.awt.Dimension(500, 500));
        getContentPane().setLayout(null);

        ExitButton.setText("Exit");
        ExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButtonActionPerformed(evt);
            }
        });
        getContentPane().add(ExitButton);
        ExitButton.setBounds(10, 470, 51, 23);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("15");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jLabel1);
        jLabel1.setBounds(233, 330, 30, 30);

        buttonGroup1.add(Answer2Button);
        Answer2Button.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Answer2Button.setContentAreaFilled(false);
        Answer2Button.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Answer2Button.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Answer2Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Answer2ButtonActionPerformed(evt);
            }
        });
        getContentPane().add(Answer2Button);
        Answer2Button.setBounds(0, 390, 150, 30);

        buttonGroup1.add(Answer4Button);
        Answer4Button.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Answer4Button.setContentAreaFilled(false);
        Answer4Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Answer4ButtonActionPerformed(evt);
            }
        });
        getContentPane().add(Answer4Button);
        Answer4Button.setBounds(330, 390, 170, 30);

        Answer3Button.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(Answer3Button);
        Answer3Button.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Answer3Button.setContentAreaFilled(false);
        Answer3Button.setFocusPainted(false);
        Answer3Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Answer3ButtonActionPerformed(evt);
            }
        });
        getContentPane().add(Answer3Button);
        Answer3Button.setBounds(330, 270, 170, 30);

        buttonGroup1.add(Answer1Button);
        Answer1Button.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Answer1Button.setContentAreaFilled(false);
        Answer1Button.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Answer1Button.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Answer1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Answer1ButtonActionPerformed(evt);
            }
        });
        getContentPane().add(Answer1Button);
        Answer1Button.setBounds(0, 270, 160, 30);

        QuestionArea.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        QuestionArea.setText("question");
        getContentPane().add(QuestionArea);
        QuestionArea.setBounds(0, 140, 500, 100);

        Bg3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Questions/Question_Background.png"))); // NOI18N
        Bg3.setText("jLabel2");
        getContentPane().add(Bg3);
        Bg3.setBounds(300, 240, 200, 90);

        Bg4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Questions/Question_Background.png"))); // NOI18N
        Bg4.setText("jLabel2");
        getContentPane().add(Bg4);
        Bg4.setBounds(300, 360, 200, 90);

        Bg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Questions/Question_Background_Left.png"))); // NOI18N
        Bg1.setText("jLabel3");
        getContentPane().add(Bg1);
        Bg1.setBounds(-10, 240, 200, 90);

        Bg2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Questions/Question_Background_Left.png"))); // NOI18N
        Bg2.setText("jLabel4");
        getContentPane().add(Bg2);
        Bg2.setBounds(-10, 360, 200, 90);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/180logo.png"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(160, 0, 180, 130);

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Orange wpp.jpg"))); // NOI18N
        getContentPane().add(Background);
        Background.setBounds(0, 0, 500, 510);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Answer4ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Answer4ButtonActionPerformed
        // Button Answer 4
        
        if(correctanswerflag == 4)
         {
            ImageIcon Quest = new ImageIcon(getClass().getResource("/Images/Questions/Question_Background_Correct.png"));
            Bg4.setIcon(Quest);
             
         }
         else
         {
            ImageIcon Quest = new ImageIcon(getClass().getResource("/Images/Questions/Question_Wrong.png"));
            Bg4.setIcon(Quest);
             
             if(correctanswerflag == 1)
              //  ImageIcon Quest = new ImageIcon(getClass().getResource("/Images/Questions/Question_Background_Correct.png"));
                Bg1.setIcon(Quest2);
             else if (correctanswerflag == 2)
                Bg2.setIcon(Quest2);
             else if (correctanswerflag == 3)
                Bg3.setIcon(Quest1);
         }
        
        //Timer manos = new Timer(1000,new TimerListener());
        
        t.stop();
        
        Timer timer = new Timer(3000,new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
               // Color color = UIManager.getColor ( "Panel.background" );
                ImageIcon Ranked = new ImageIcon(getClass().getResource("/Images/Questions/Question_Background_Left.png"));
                ImageIcon Ranked2 = new ImageIcon(getClass().getResource("/Images/Questions/Question_Background.png"));
                
                
                Bg1.setIcon(Ranked);
                Bg2.setIcon(Ranked);
                Bg3.setIcon(Ranked2);
                Bg4.setIcon(Ranked2);
                
                
                setClockText(""+15);
                try 
                {
                    //manos.start();
                    pushRadioButton();
                    
                    //buttonGroup1.clearSelection();
                } 
                catch (ClassNotFoundException ex) 
                {
                    Logger.getLogger(RankedGamePlay.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        });
        
        timer.setRepeats(false);
        
        timer.start();
         
       // pushRadioButton();
    }//GEN-LAST:event_Answer4ButtonActionPerformed

    private void Answer2ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Answer2ButtonActionPerformed
        // Button Answer 2
        if(correctanswerflag == 2)
         {
            ImageIcon Quest = new ImageIcon(getClass().getResource("/Images/Questions/Question_Background_Left_Correct.png"));
            Bg2.setIcon(Quest);
         }
         else
         {
            ImageIcon Quest = new ImageIcon(getClass().getResource("/Images/Questions/Question_Left_Wrong.png"));
            Bg2.setIcon(Quest);
             
             if(correctanswerflag == 1)
                Bg1.setIcon(Quest2);
             else if (correctanswerflag == 3)
                Bg3.setIcon(Quest1);
             else if (correctanswerflag == 4)
                Bg4.setIcon(Quest1);
         }
        t.stop();
        
        Timer timer = new Timer(3000,new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                //Color color = UIManager.getColor ( "Panel.background" );
                ImageIcon Ranked = new ImageIcon(getClass().getResource("/Images/Questions/Question_Background_Left.png"));
                ImageIcon Ranked2 = new ImageIcon(getClass().getResource("/Images/Questions/Question_Background.png"));
                
                Bg1.setIcon(Ranked);
                Bg2.setIcon(Ranked);
                Bg3.setIcon(Ranked2);
                Bg4.setIcon(Ranked2);
                
                
                setClockText(""+15);
                try 
                {
                    pushRadioButton();
                    //buttonGroup1.clearSelection();
                } 
                catch (ClassNotFoundException ex) 
                {
                    Logger.getLogger(RankedGamePlay.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        timer.setRepeats(false);
        
        timer.start();
        
       // pushRadioButton();
        
        
    }//GEN-LAST:event_Answer2ButtonActionPerformed

    private void Answer1ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Answer1ButtonActionPerformed

        if(correctanswerflag == 1)
         {
            ImageIcon Quest = new ImageIcon(getClass().getResource("/Images/Questions/Question_Background_Left_Correct.png"));
            Bg1.setIcon(Quest);
             
         }
         else
         {
            ImageIcon Quest = new ImageIcon(getClass().getResource("/Images/Questions/Question_Left_Wrong.png"));
            Bg1.setIcon(Quest);
             
             if(correctanswerflag == 3)
                Bg3.setIcon(Quest1);
             else if (correctanswerflag == 2)
                Bg2.setIcon(Quest2);
             else if (correctanswerflag == 4)
                Bg4.setIcon(Quest1);
         }
        t.stop();
        Timer timer = new Timer(3000,new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                //Color color = UIManager.getColor ( "Panel.background" );
                
                ImageIcon Ranked = new ImageIcon(getClass().getResource("/Images/Questions/Question_Background_Left.png"));
                ImageIcon Ranked2 = new ImageIcon(getClass().getResource("/Images/Questions/Question_Background.png"));
                
                Bg1.setIcon(Ranked);
                Bg2.setIcon(Ranked);
                Bg3.setIcon(Ranked2);
                Bg4.setIcon(Ranked2);
                
                
                setClockText(""+15);
                try 
                {
                    pushRadioButton();
                    //buttonGroup1.clearSelection();
                }
                catch (ClassNotFoundException ex)
                {
                    Logger.getLogger(RankedGamePlay.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        timer.setRepeats(false);
        
        timer.start();
        
         //pushRadioButton();
         
         
    }//GEN-LAST:event_Answer1ButtonActionPerformed

    private void Answer3ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Answer3ButtonActionPerformed
        // Button Answer 3
        if(correctanswerflag == 3)
         {
            ImageIcon Quest = new ImageIcon(getClass().getResource("/Images/Questions/Question_Background_Correct.png"));
            Bg3.setIcon(Quest);
             
         }
         else
         {
            ImageIcon Quest = new ImageIcon(getClass().getResource("/Images/Questions/Question_Wrong.png"));
            Bg3.setIcon(Quest);
             
             if(correctanswerflag == 1)
                Bg1.setIcon(Quest2);
             else if (correctanswerflag == 2)
                Bg2.setIcon(Quest2);
             else if (correctanswerflag == 4)
                Bg4.setIcon(Quest1);
         }
        t.stop();
        
        Timer timer = new Timer(3000,new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                //Color color = UIManager.getColor ( "Panel.background" );
                
                ImageIcon Ranked = new ImageIcon(getClass().getResource("/Images/Questions/Question_Background_Left.png"));
                ImageIcon Ranked2 = new ImageIcon(getClass().getResource("/Images/Questions/Question_Background.png"));
                
                Bg1.setIcon(Ranked);
                Bg2.setIcon(Ranked);
                Bg3.setIcon(Ranked2);
                Bg4.setIcon(Ranked2);
                
                
                setClockText(""+15);
                try
                {
                    pushRadioButton();
                    //buttonGroup1.clearSelection();
                } 
                catch (ClassNotFoundException ex)
                {
                    Logger.getLogger(RankedGamePlay.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        timer.setRepeats(false);
        
        timer.start();
        
        // pushRadioButton();
    }//GEN-LAST:event_Answer3ButtonActionPerformed

    private void ExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitButtonActionPerformed
        // Exit Button
        dispose();
        t.stop();
        ModeGUI mgui = new ModeGUI();
        mgui.setVisible(true);
        
        mgui.setSize(400, 500);
        mgui.setLocationRelativeTo(null);
        
        mgui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }//GEN-LAST:event_ExitButtonActionPerformed

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
            java.util.logging.Logger.getLogger(RankedGamePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RankedGamePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RankedGamePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RankedGamePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RankedGamePlay().setVisible(true);
            }
        });
    }

    //set answers and question to the text areas
    public void setQuestion(String question)
    {
        QuestionArea.setText(question);
    }
    public void setAnswer1Button(String question)
    {
        Answer1Button.setText(question);
    }
    public void setAnswer2Button(String question)
    {
        Answer2Button.setText(question);
    }
    public void setAnswer3Button(String question)
    {
        Answer3Button.setText(question);
    }
    public void setAnswer4Button(String question)
    {
        Answer4Button.setText(question);
    }
    
    public void setClockText(String text)
    {
        jLabel1.setText(text);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Answer1Button;
    private javax.swing.JRadioButton Answer2Button;
    private javax.swing.JRadioButton Answer3Button;
    private javax.swing.JRadioButton Answer4Button;
    private javax.swing.JLabel Background;
    private javax.swing.JLabel Bg1;
    private javax.swing.JLabel Bg2;
    private javax.swing.JLabel Bg3;
    private javax.swing.JLabel Bg4;
    private javax.swing.JButton ExitButton;
    private javax.swing.JLabel QuestionArea;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
