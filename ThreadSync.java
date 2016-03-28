import java.lang.Thread;
import java.util.concurrent.*;

public class ThreadSync
{
	private volatile static Semaphore canPrintC = new Semaphore(1);
	private volatile static Semaphore canPrintD = new Semaphore(0);
	private volatile static Semaphore canPrintP = new Semaphore(0);
	
    private static boolean runFlag = true;
	
    public static void main( String[] args ) {

        // create and start each runnable
        Runnable task1 = new TaskPrintC();
        Runnable task2 = new TaskPrintD();
        Runnable task3 = new TaskPrintP();

        Thread thread1 = new Thread( task1 );
        Thread thread2 = new Thread( task2 );
        Thread thread3 = new Thread( task3 );

        thread1.start();
        thread2.start();
        thread3.start();

        // Let them run for 500ms
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        // put up the stop sign
        runFlag=false;
 
        thread3.interrupt();
        thread2.interrupt();
        thread1.interrupt();
 
    }
    
    public static class TaskPrintC implements Runnable 
    {
        public void run(){
    	    try{
    	    	while (runFlag) {
    	    
    	    		try {
    	    			canPrintC.acquire();
    	    		}
    	    		catch (InterruptedException ex) {
    	    			//ex.printStackTrace();
    	    		}
    	    		System.out.printf( "%s", ":");
    	    		canPrintD.release();}}
    	    catch (Exception ex) {//ex.printStackTrace();
    	    	
    	    }
        
    	}
    }
    
    public static class TaskPrintD implements Runnable 
    {
        public void run(){
        	try{
    	    	while (runFlag) {
    	    
    	    		try {
    	    			canPrintD.acquire();
    	    		}
    	    		catch (InterruptedException ex) {
    	    //			ex.printStackTrace();
    	    		}
    	    		System.out.printf( "%s", "-");
    	    		canPrintP.release();}}
    	    catch (Exception ex) {//ex.printStackTrace();
    	    }
        
    	}	
    }
    
    public static class TaskPrintP implements Runnable 
    {
        public void run(){
        	try{
    	    	while (runFlag) {
    	    
    	    		try {
    	    			canPrintP.acquire();
    	    		}
    	    		catch (InterruptedException ex) {
    	    			//ex.printStackTrace();
    	    		}
    	    		for (int i=0;i<5;i++){
    	    			System.out.printf( "%s", ")");}
   	    			canPrintC.release();}}
    	    catch (Exception ex) {//ex.printStackTrace();
    	    	
    	    }
        
    	}
    }

}
