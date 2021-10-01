package fuzz;


import app.GUI;
import jdk.jfr.StackTrace;

import java.time.Duration;
import java.util.Random;
import java.lang.IllegalArgumentException;
import java.lang.Thread.UncaughtExceptionHandler;

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
	
	private Random rand = new Random();
	
	
	
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
	
	private int[] moves = new int[]{upAction, downAction, leftAction, rightAction};
	
	
	
	@Test
    public void test1() {
		System.out.println("This is the test case 1 in this class for level 1");
		assertTimeoutPreemptively( Duration.ofSeconds(60), () -> {
			try {
				gui = new GUI();
        		Thread.sleep(2000);
        		clickMultipleButtons(new int[]{control, ctrl1Action});
    			while(true) {
    				clickButton(getRandomMove());
    				Thread.sleep(500);
    			}
	        }
	        catch(IOException e) {   
	        	String user = getRelevantUser(e.getStackTrace());
	        	raiseIssue(user, "GUI Failed to initialize. When calling 'new GUI()' constructor and IOException is thrown in "+user+"'s module where "+e.getMessage());
	        }
	        catch(IllegalArgumentException e) {
	        	String user = getRelevantUser(e.getStackTrace());
	        	raiseIssue(user, "Precondition issue caught by an Illegal Argument Exception in "+user+"'s module where "+e.getMessage());
	        }
	        catch(IllegalStateException e) { 
	        	String user = getRelevantUser(e.getStackTrace());
	        	raiseIssue(user, "Precondition issue caught by an Illegal State Exception in "+user+"'s module where "+e.getMessage());
	        }
	        catch(NullPointerException e) {
	        	String user = getRelevantUser(e.getStackTrace());
	        	raiseIssue(user, "General Programming Error caught by a Null Pointer Exception in "+user+"'s module where "+e.getMessage());
	        }
	        catch(AssertionError e) {
	        	String user = getRelevantUser(e.getStackTrace());
	        	raiseIssue(user, "Postcondition violation caught by an AssertionError in "+user+"'s module where "+e.getMessage());
	        }
		});
        
    }
	
	@Test
    public void test2() { 
        System.out.println("This is the test case 2 in this class for level 2");
        try {
        	assertTimeoutPreemptively( Duration.ofSeconds(60), () -> {
        		clickMultipleButtons(new int[]{control, ctrl2Action});
    			while(true) {
    				clickButton(getRandomMove());
    				Thread.sleep(500);
    			}
        	});
        	
        }
        catch(IllegalArgumentException e) {
        	String user = getRelevantUser(e.getStackTrace());
        	raiseIssue(user, "Precondition issue caught by an Illegal Argument Exception in "+user+"'s module where "+e.getMessage());
        }
        catch(IllegalStateException e) { 
        	String user = getRelevantUser(e.getStackTrace());
        	raiseIssue(user, "Precondition issue caught by an Illegal State Exception in "+user+"'s module where "+e.getMessage());
        }
        catch(NullPointerException e) {
        	String user = getRelevantUser(e.getStackTrace());
        	raiseIssue(user, "General Programming Error caught by a Null Pointer Exception in "+user+"'s module where "+e.getMessage());
        }
        catch(AssertionError e) {
        	String user = getRelevantUser(e.getStackTrace());
        	raiseIssue(user, "Postcondition violation caught by an AssertionError in "+user+"'s module where "+e.getMessage());
        }
        
        
    }
	
	
	//Determine username from the found package the issue is generated from
	
	private String getRelevantUser(StackTraceElement[] stack) {
		switch (stack[0].getClassName().subSequence(0, 3).toString()) {
		case "dom":
			return "baskaraari";
		case "app":
			return "brookystel";
		case "rec":
			return "nolanarno1";
		case "per":
			return "vuongkell";
		case "ren":
			return "puagevi";
		}
		return "edlinkale";
	}
	
	
	
	//Emulator helper methods for moving intelligently
	
	private void clickButton(int keyCode) {
		gui.designPanel.dispatchEvent(keyPress(keyCode));
		gui.designPanel.dispatchEvent(keyRelease(keyCode));
		
	}
	
	private void clickMultipleButtons(int[] keyCodes) {
		for(int keyCode : keyCodes) {
			gui.designPanel.dispatchEvent(keyPress(keyCode));
		}
		for(int keyCode : keyCodes) {
			gui.designPanel.dispatchEvent(keyRelease(keyCode));
		}
	}
	
	private void moveInDirection(int keyCode, Robot robot, int moves) {
		for(int i = 0; i<moves; i++) {
			clickButton(keyCode);
		}
	}
	
	private int getRandomMove() {
		int index = rand.nextInt((3 - 0) + 1) + 0;
		return moves[index];
	}
	
	
	private KeyEvent keyPress(int keyCode) {
		return new KeyEvent(gui.designPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keyCode, 'a');
	}
	
	private KeyEvent keyRelease(int keyCode) {
		return new KeyEvent(gui.designPanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, keyCode, 'a');
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
