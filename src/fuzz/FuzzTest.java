package fuzz;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * @author Kalem.
 *
 */
public class FuzzTest {
	
	public String accessToken = "n3CwsL-tmEHwxHKts-yc";
	
	
	@Test
    public void test1() {
        System.out.println("This is the testcase in this class");
//        String str1="This is the testcase in this class";
//        assertEquals("This is the testcase in this class", str1);
        
        try {
        	
        	
        	
        }
        catch(IllegalArgumentException e) {
        	
        }
        catch(IllegalStateException e) {
        	
        }
        catch(NullPointerException e) {
        	
        }
        catch(AssertionError e) {
        	
        }
        
        
    }
	
	@Test
    public void test2() {
        System.out.println("This is the testcase in this class");
//        String str1="This is the testcase in this class";
//        assertEquals("This is the testcase in this class", str1);
        
        try {
        	
        	
        	
        }
        catch(IllegalArgumentException e) {
        	raiseIssue("", "");
        }
        catch(IllegalStateException e) {
        	raiseIssue("", "");
        }
        catch(NullPointerException e) {
        	raiseIssue("", "");
        }
        catch(AssertionError e) {
        	raiseIssue("", "");
        }
        
        
    }
	

	
	
	private void raiseIssue(String user, String title) {
		try {
			URL url = new URL("https://gitlab.ecs.vuw.ac.nz/api/v4/projects/11025/issues");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setDoOutput(true);
			http.setRequestProperty("Authorization","Bearer "+accessToken);
			
			Map<String,String> arguments = new HashMap<>();
			arguments.put("assignee_id", user);
			arguments.put("title", title);
			arguments.put("labels", "#detectedByFuzzer");
			
			StringJoiner sj = new StringJoiner("&");
			
			for(Map.Entry<String,String> entry : arguments.entrySet())
			    sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" 
			         + URLEncoder.encode(entry.getValue(), "UTF-8"));
			byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
			
			System.out.println(sj);
			
			
			int length = out.length;
			
			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			http.connect();
			
			try(OutputStream os = http.getOutputStream()) {
			    os.write(out);
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
}
