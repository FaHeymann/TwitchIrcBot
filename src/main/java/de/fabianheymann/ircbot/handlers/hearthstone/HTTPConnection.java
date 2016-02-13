package de.fabianheymann.ircbot.handlers.hearthstone;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class HTTPConnection {
    
    public static String sendRequest(String targetURL, String urlParameters) {
        
        URL url;
        HttpURLConnection con = null;
        try {
            url = new URL(targetURL);
            con =(HttpURLConnection)url.openConnection();
            
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            con.setRequestProperty("Content-Language", "en-US");  
                 
            con.setUseCaches (false);
            con.setDoInput(true);
            con.setDoOutput(true);
            
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = br.readLine()) != null) {
                response.append(line);
                response.append("\r\n");
            }
            br.close();
            return response.toString();
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(con != null) {
                con.disconnect();
            }
        }
    }

}
