
public class Process {
	
	public int ProcessID = 0;
	public int SizeInKB = 0;
	public int StartsAt = 0;

	public Process(int pid, int size, int str)
	{
		ProcessID = pid;
		StartsAt = str;
		SizeInKB = size;
	}
	
	public int size()
	{
		return SizeInKB;
	}
	
	public void setPos(int where)
	{
		StartsAt = where;
	}
	
	public int pos()
	{
		return StartsAt;
	}
	
	public int getId()
	{
		return ProcessID;
	}
	
	public String getmsg()
	{
		String msg = "" ;
		msg = "PID: " + msg + getId() + ",  Located at " + pos() + "KB,  "  + size() + " KB in size." ;  
		
		return msg;
	}
	

}
