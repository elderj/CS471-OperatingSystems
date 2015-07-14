import java.util.Vector;

import javax.swing.JOptionPane;


public class MainMemory {
	
    private int freeSpace = 1024;
    private int occuSpace = 0;
    
    public Vector<Integer> SpaceList = new Vector<Integer>();
    
    private int pc = 3; 
    
    public String MSG = "none selected";
    
    public Vector<Process> RunningProcesses = new Vector<Process>();  ;
	
	
	public MainMemory()
	{
		GenerateSystemProcesses();
	}
	
	
	

	/*   Generate System Processes 	 */
	public void GenerateSystemProcesses()
	{
		//Start with a blank list
		for(int i = 0 ; i < 1024; i++)
		{
			SpaceList.add(0);
		}
		
		//System.out.println("Generating System Processes");
		
		
		int sp0size = 55;
		int sp1size = 120;
		int sp2size = 88;
		
		Process sysPrc0 = new Process(0, sp0size, 5);  
		Process sysPrc1 = new Process(1, sp1size, 240);  
		Process sysPrc2 = new Process(2, sp2size, 500);
		
		RunningProcesses.add(sysPrc0);
		freeSpace = freeSpace - sp0size;
		occuSpace = occuSpace +  sp0size; 
		
		RunningProcesses.add(sysPrc1);
		freeSpace = freeSpace - sp1size;
		occuSpace = occuSpace +  sp1size;
		
		RunningProcesses.add(sysPrc2);
		freeSpace = freeSpace - sp1size;
		occuSpace = occuSpace +  sp1size;
		
		
		//mark allocated spaces for use later when adding processes
		//done manually unfortunately
		for(int i = 0; i < sp0size; i++)
		{
			int start = 5;
			start = start + i;
			SpaceList.set(start, 1);
		}
		
		
		for(int i = 0; i < sp1size; i++)
		{
			int start = 240;
			start = start + i;
			SpaceList.set(start, 1);
		}
		
		for(int i = 0; i < sp2size; i++)
		{
			int start = 500;
			start = start + i;
			SpaceList.set(start, 1);
		}
	}
	
	
	
	
	/*  Add a new process to the existing ones    */
	public void AddNewProcess(int sizeOfNewProcess)
	{
		//System.out.println("Attempting to add a new process " + sizeOfNewProcess + "KB " );
		
		if(sizeOfNewProcess > freeSpace)
		{
			JOptionPane.showMessageDialog(null, "Insufficient Memory Available");
		}
		else
		{

			boolean ProcessPlaced = false;
			int blockPos = 0;
			int blockSize = 0;
				
			//Check for a slot big enough
			for(int i = 0; i < 1024 ; i ++)
			{
				
				////System.out.println(i + " = " + SpaceList.get(i) );

					if(ProcessPlaced == false)
					{
					
						if(SpaceList.get(i) == 0)
						{
							if(blockSize == 0)
							{
								blockPos = i;
							}
							blockSize++;
						}
						else
						{
							blockSize = 0;
						}
	
						////System.out.println(i + " = " + SpaceList.get(i) + "    blockPos=" + blockPos + "   blockSize = " + blockSize);
					
						if(blockSize == sizeOfNewProcess)
						{
							ProcessPlaced = true;
							Process newGuy = new Process(pc, sizeOfNewProcess, blockPos);
							RunningProcesses.add(newGuy);
							pc++;
						}
					}
				
			}
			
			if(ProcessPlaced == true)
			{
				//System.out.println("Process was placed");
				

				int mark;
				
				//Mark on space list
				for(int i = 0; i < sizeOfNewProcess; i++)
				{
					mark = blockPos + i;
					//System.out.println("marking " + mark);
					SpaceList.set(mark, 1);
				}

				
				
				//Allocation removes freespace		
				freeSpace = freeSpace -  sizeOfNewProcess ; 
				occuSpace = occuSpace +  sizeOfNewProcess ; 
			}
			else
			{
				//System.out.println("Process was not placed, but should fit with garbage compaction");
				
				//Reset the SpaceList
				
				for(int i = 0; i < 1024; i++)
				{
					SpaceList.set(i, 0);
				}
				
				
				///Move existing processes to the beginning
				int whereWeAt = 0;
				
				//Actual moving of processes
				for(int i = 0; i < RunningProcesses.size(); i++)
				{
					//System.out.println("Moving pid" + RunningProcesses.get(i).getId() + " to " + whereWeAt  );
					RunningProcesses.get(i).setPos(whereWeAt);
					whereWeAt = whereWeAt + RunningProcesses.get(i).SizeInKB;
				}
				
				//Update SpaceList
				for(int i = 0; i < whereWeAt; i++)
				{
					SpaceList.set(i, 1);
				}
				
				Process newGuy = new Process(pc, sizeOfNewProcess, whereWeAt);
				pc++;
				RunningProcesses.add(newGuy);
				
				//Update SpaceList
				for(int i = whereWeAt; i < whereWeAt + sizeOfNewProcess ; i++)
				{
					SpaceList.set(i, 1);
				}
				
				
				//Allocation removes freespace		
				freeSpace = freeSpace -  sizeOfNewProcess ; 
				occuSpace = occuSpace +  sizeOfNewProcess ; 
				
				
			}
				
				
			
 
		}
		
		

		
		

		
	}
	
	
	/*  Get the status of a particular process    */
	public void GetProcessStatus(String s)
	{
		////System.out.println("Searching for PID " + s );
		int foo = Integer.parseInt(s);
		///Lookup check to see if id is in the list
		
		for(int i = 0; i < RunningProcesses.size(); i++)
		{
			
			
			if(RunningProcesses.get(i).getId()  == foo) 
			{
				////System.out.println("ID of " + RunningProcesses.get(i).getId() + " Match!" );
				////System.out.println("Calling get msg from the found process");
				MSG = RunningProcesses.get(i).getmsg();
				//System.out.println("\t" + MSG);
			}
			else
			{
				//System.out.println("ID of " + RunningProcesses.get(i).getId() );
			}
		}
		
		
		///Print the SpaceList
		for(int i = 0; i < 1024; i++)
		{
			//System.out.println(i + " = = " + SpaceList.get(i));
		}
		

	}
	
	
	
	/*   Terminate a process     */
	public void TerminateProcess(int whichPrcToTerminate)
	{
		//System.out.println("\nAttempting to terminating process of PID:" + whichPrcToTerminate);
	
		
		
		///Lookup check to see if id is in the list
		
		for(int i = 0; i < RunningProcesses.size(); i++)
		{
			
			
			if(RunningProcesses.get(i).getId()  == whichPrcToTerminate) 
			{
				//System.out.println("ID of " + RunningProcesses.get(i).getId() + " Match!....kill it!" );
				freeSpace = freeSpace + RunningProcesses.get(i).size();
				occuSpace = occuSpace -  RunningProcesses.get(i).size();
				
				
				
				//Remove from space map
				for(int j = RunningProcesses.get(i).pos(); j < RunningProcesses.get(i).pos() + RunningProcesses.get(i).size();  j++ )
				{
					SpaceList.set(j, 0);
				}
				
				
				RunningProcesses.remove(i);
				
				
				
				
			}
			else
			{
				//System.out.println("ID of " + RunningProcesses.get(i).getId() );
			}
		}
	
	}
	
	
	public int getFreeSpace()
	{
		return freeSpace;
	}
	
	
	public int getOccuSpace()
	{
		return occuSpace;
	}
	
	public String getMSG()
	{
		return MSG;
	}
	

}