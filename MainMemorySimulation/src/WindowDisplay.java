import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class WindowDisplay extends JFrame
     implements ActionListener {

     private boolean addOne = true;
     private boolean killOne = false;
     private boolean checkOne = false;
     private JButton addBtn;
     private JButton statusBtn;
     
     private JTextArea addTA;
     private JTextArea getStatusTA;
     private JTextArea terminateTA;
     private JButton terminateBtn;
     
     
     private JButton oval;
     private JButton rectangle;
     private JPanel buttonPanel;
     private DrawStuff drawPanel = new DrawStuff();
     
     
     public  MainMemory MGMT = new MainMemory(); 

     
     public WindowDisplay() 
     {
         super("Memory Management");
         setSize(1050, 380);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
         
        
         

         buttonPanel = new JPanel();
         buttonPanel.setLayout(new GridLayout(2, 3));
         
       //Color for panels
 		Color color = new Color(230, 230, 230);
         
         buttonPanel.setBackground(color);
         buttonPanel.setPreferredSize(new Dimension(100, 80));
         
         
         //Create Labels
         JLabel newLabel = new JLabel("New Process:");
         JLabel statusLabel = new JLabel("Get Status:");
         JLabel termLabel = new JLabel("Terminate Process");
         
         //Center them
         newLabel.setHorizontalAlignment(JLabel.CENTER);
         statusLabel.setHorizontalAlignment(JLabel.CENTER);
         termLabel.setHorizontalAlignment(JLabel.CENTER);
         
         //Add to control panel
         buttonPanel.add(newLabel);
         buttonPanel.add(statusLabel);
         buttonPanel.add(termLabel);
         
         
         
         //Panel for adding a new process
         JPanel addNewProcessPanel = new JPanel();
         addNewProcessPanel.setBackground(color);
         //tp.setLayout(new GridLayout(1, 3));
         
         
         //Size Label
         JLabel sizeofNewLabel = new JLabel("Size:");
         
         //Text Area for entering size of new process
         addTA = new JTextArea();
         addTA.setPreferredSize(new Dimension(40, 18));
         JLabel theUnits = new JLabel("Kb  ");
         
         ///Button
         addBtn = new JButton("Add");
         addBtn.setPreferredSize(new Dimension(60, 30));
         addBtn.addActionListener(this);
         
         addNewProcessPanel.add(sizeofNewLabel);
         addNewProcessPanel.add(addTA);
         addNewProcessPanel.add(theUnits);
         addNewProcessPanel.add(addBtn);
         
         buttonPanel.add(addNewProcessPanel);

         
         
       //Panel for getting process status
         JPanel getProcessStatusPanel = new JPanel();
         getProcessStatusPanel.setBackground(color);
         
         
         //id Label
         JLabel getstatusIDLabel = new JLabel("ID:");
         
         //Where user enters id
         getStatusTA = new JTextArea();
         getStatusTA.setPreferredSize(new Dimension(40, 18));
         statusBtn = new JButton("Get");
         statusBtn.setPreferredSize(new Dimension(60, 30));
         statusBtn.addActionListener(this);
  
       
         getProcessStatusPanel.add(getstatusIDLabel);
         getProcessStatusPanel.add(getStatusTA);
         getProcessStatusPanel.add(statusBtn);
         
         buttonPanel.add(getProcessStatusPanel);
         
         
         
         
         //Panel for terminating a process 
         JPanel terminateProcessPanel = new JPanel();
         terminateProcessPanel.setBackground(color);
         
         
         //id Label
         JLabel terminateIDLabel = new JLabel("ID:");
         
         //Where user enters id
         terminateTA = new JTextArea();
         terminateTA.setPreferredSize(new Dimension(40, 18));
         terminateBtn = new JButton("End");
         terminateBtn.setPreferredSize(new Dimension(60, 30));
         terminateBtn.addActionListener(this);
  
       
         terminateProcessPanel.add(terminateIDLabel );
         terminateProcessPanel.add(terminateTA);
         terminateProcessPanel.add(terminateBtn);
         
         buttonPanel.add(terminateProcessPanel);
         



         Container contentPane = this.getContentPane();
         contentPane.add(buttonPanel, BorderLayout.SOUTH);
         add(drawPanel);
    }

     
    /*
     * 
     */
     
     
    public void actionPerformed(ActionEvent event) 
    {
        Object source = event.getSource();

        if (source == addBtn) 
        {
        	addOne = true;
            String s = (addTA).getText(); 
            MGMT.AddNewProcess(Integer.parseInt(s));    	
            
            repaint();
        } 
        else if (source == statusBtn) 
        {
            checkOne = true;
            

            String s = (getStatusTA).getText(); 
            MGMT.GetProcessStatus(s);
            repaint();
            
        } 
        else if (source == terminateBtn) 
        {
            killOne = true;
            String s = (terminateTA).getText(); 
            MGMT.TerminateProcess(Integer.parseInt(s));  
            repaint();
        }
    }

    public static void main(String[] args) {
        WindowDisplay myTest = new WindowDisplay();
        myTest.setVisible(true);
    }

    class DrawStuff extends JPanel 
    {


    	
        @Override
        public void paintComponent(Graphics g) 
        {
            super.paintComponent(g);

            if (addOne) 
            {
            	DrawMemoryBar(g);
                addOne = false;
            } 
            else if (checkOne) 
            {
            	DrawMemoryBar(g);
                checkOne = false;
            } else if (killOne) 
            {
            	
                //Rectangle r = new Rectangle(10,100,400,40);
            	DrawMemoryBar(g);
                killOne = false;
            }
        }
    
        public void DrawMemoryBar(Graphics g){
        	
        	
        	///Main memory graphic + label
        	Color words = new Color(245,10,55);
        	g.setColor(words);
        	g.drawString("Main Memory Size: 1024MB (1GB)", 780, 130); //works!
            Color freeColor = new Color(37,94,105);
			g.setColor(freeColor);
            g.fillRect(10, 35, 1024, 80);  
            
            
            //Free vs occupied space graphics
            int i = 0;
            //Free Space
            g.fillRect(50, 130, 20, 20); 
            g.drawString("Free Space " + MGMT.getFreeSpace() + " MB", 77, 150); //works!
            
            //Occu Space
            Color occuColor = new Color(255,100,0);
			g.setColor(occuColor);

            g.fillRect(50, 160, 20, 20); 
            g.drawString("Occupied Space " + MGMT.getOccuSpace() + " MB", 77, 180); //works!
            
            
            
           
            
            //Draw all processes in main memory
            for(int Z = 0; Z < MGMT.RunningProcesses.size(); Z++)
            {
            	int position = MGMT.RunningProcesses.get(Z).pos() + 10;
            	int size = MGMT.RunningProcesses.get(Z).size();
            	
            	g.setColor(occuColor);
            	g.fillRect(position, 35, size, 80);
            	
            	int PID = MGMT.RunningProcesses.get(Z).getId();
            	int LabelPosition = MGMT.RunningProcesses.get(Z).pos() + (MGMT.RunningProcesses.get(Z).size()/2);
            	
            	g.setColor(Color.WHITE);
            	g.drawString(String.valueOf(PID), LabelPosition +8 , 70);
            }
            
            
            
            //Draw Status Components

            
            g.setColor(Color.BLACK);
            
            String statusMessage = "";
            statusMessage = MGMT.getMSG();
            
            g.drawString("STATUS: " + statusMessage , 430, 220); //works!
            
            
            
            
            
        }
        
    }
}