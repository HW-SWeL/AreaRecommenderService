import com.jb.dissertation.controllers.Controller;

public class App 
{	
    public static void main(String[] args) throws Exception
    {    
    	long startTime = System.currentTimeMillis();
    	
    	Controller controller = new Controller(null);
    	controller.initialise();
    	
    	long estimatedTime = System.currentTimeMillis() - startTime;
    	
    	
    	System.out.println("Finished after "+estimatedTime / 1000 / 60+" minutes");
	}
}
