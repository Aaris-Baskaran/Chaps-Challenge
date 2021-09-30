package fuzz;


import app.GUI;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import java.awt.Robot;


/**
 * @author Kalem edlinkale.
 *
 */
public class FuzzTest {
	
	private String accessToken = "n3CwsL-tmEHwxHKts-yc";

	private GUI gui; 
	
	
	
	private int upAction = KeyEvent.VK_UP;
	private int downAction = KeyEvent.VK_DOWN;
	private int leftAction = KeyEvent.VK_LEFT;
	private int rightAction = KeyEvent.VK_RIGHT;
	private int spaceAction = KeyEvent.VK_SPACE;
	private int escAction = KeyEvent.VK_ESCAPE;
	private int control = KeyEvent.VK_CONTROL;
	private int ctrlSAction = 83; //To be combined with control int
	private int ctrlXAction = 88; //To be combined with control int
	private int ctrlRAction = 82; //To be combined with control int
	private int ctrl1Action = 49; //To be combined with control int
	private int ctrl2Action = 50; //To be combined with control int
	
	
	
	@Test
    public void test1() {
		System.out.println("This is the test case 1 in this class for level 1");
		try {
			gui = new GUI();
        	Robot robot = new Robot();
        	assertTimeoutPreemptively( Duration.ofSeconds(60), () -> {
        		Thread.sleep(2000);
        		clickMultipleButtons(new int[]{control, ctrl1Action}, robot);
        		int count = 0;
    			while(true) {
    				Thread.sleep(1000);
    				System.out.println(count++);
    			}
        	});
        	
        }
        catch(IOException e) {
        	//GUI not working
        }
        catch(AWTException e) {
        	//Robot not working
        }
        catch(IllegalArgumentException e) {
        	//A precondition violation
//        	raiseIssue("", "");
        }
        catch(IllegalStateException e) {
        	//A precondition violation
//        	raiseIssue("", "");
        }
        catch(NullPointerException e) {
        	//general programming error (issue where an object is null and is trying to be utilized)
//        	raiseIssue("", "");
        }
        catch(AssertionError e) {
        	//A postcondition violation determined by the asserts in the module code.
//        	raiseIssue("", "");
        }
        
        
    }
	
	@Test
    public void test2() { 
        System.out.println("This is the test case 2 in this class for level 2");
        try {
        	Robot robot = new Robot();
        	assertTimeoutPreemptively( Duration.ofSeconds(60), () -> {
        		clickMultipleButtons(new int[]{control, ctrl2Action}, robot);
        		int count = 0;
    			while(true) {
    				Thread.sleep(1000);
    				System.out.println(count++);
    			}
        	});
        	
        }
        catch(AWTException e) {
        	//Robot not working
        }
        catch(IllegalArgumentException e) {
        	//A precondition violation
//        	raiseIssue("", "");
        }
        catch(IllegalStateException e) {
        	//A precondition violation
//        	raiseIssue("", "");
        }
        catch(NullPointerException e) {
        	//general programming error (issue where an object is null and is trying to be utilized)
//        	raiseIssue("", "");
        }
        catch(AssertionError e) {
        	//A postcondition violation determined by the asserts in the module code.
//        	raiseIssue("", "");
        }
        
        
    }
	
	private void clickButton(int keyCode, Robot robot) {
		robot.keyPress(keyCode);
		robot.keyRelease(keyCode);
	}
	
	private void clickMultipleButtons(int[] keyCodes, Robot robot) {
		for(int keyCode : keyCodes) {
			robot.keyPress(keyCode);
		}
		for(int keyCode : keyCodes) {
			robot.keyRelease(keyCode);
		}
	}
	

	
	
	private void raiseIssue(String user, String title) {
		try {
			URL url = new URL("https://gitlab.ecs.vuw.ac.nz/api/v4/projects/11025/issues");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			
			http.setRequestProperty("Authorization","Bearer "+accessToken);
			
			
			String jsonInputString = "{\"assignee_username\": \""+user+"\", \"title\": \""+title+"\", \"labels\": \"#detectedByFuzzer\"}";
			
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type", "application/json; utf-8");
			http.setRequestProperty("Accept", "application/json");
			http.setDoOutput(true);
			http.connect();
			
			try(OutputStream os = http.getOutputStream()) {
			    byte[] input = jsonInputString.getBytes("utf-8");
			    os.write(input, 0, input.length);			
			}
			
			try(BufferedReader br = new BufferedReader(
				new InputStreamReader(http.getInputStream(), "utf-8"))) {
			    	StringBuilder response = new StringBuilder();
			    	String responseLine = null;
			    	while ((responseLine = br.readLine()) != null) {
			    		response.append(responseLine.trim());
			    	}
			    	System.out.println(response.toString());
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
}
