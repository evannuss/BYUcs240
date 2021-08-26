package net;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerProxy {

    public static String serverHostName;
    public static int serverPortNumber;

    public ServerProxy(String hostName, int portNumber){
        serverHostName = hostName;
        serverPortNumber = portNumber;
    }

    public LoginResult login(LoginRequest req){

        try{
            URL url = new URL("http://" + serverHostName + ":" + serverPortNumber + "/user/login");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();

            String reqData = new Gson().toJson(req);
            OutputStream reqBody = http.getOutputStream();
            new ReadWriteStrings().writeString(reqData, reqBody);
            reqBody.close();

            InputStream response;
            if(http.getResponseCode() != 200){
                response = http.getErrorStream();
            }
            else {
                response = http.getInputStream();
            }
            String respBody = new ReadWriteStrings().readString(response);

            return new Gson().fromJson(respBody, LoginResult.class);
        }
        catch(IOException e){
            e.printStackTrace();
            return new LoginResult("IOException detected. Login attempt failed.", false);
        }
    }

    public RegisterResult register(RegisterRequest req){
        try{
            URL url = new URL("http://" + serverHostName + ":" + serverPortNumber + "/user/register");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();

            String reqData = new Gson().toJson(req);
            OutputStream reqBody = http.getOutputStream();
            new ReadWriteStrings().writeString(reqData, reqBody);
            reqBody.close();

            InputStream response;
            if(http.getResponseCode() != 200){
                response = http.getErrorStream();
            }
            else {
                response = http.getInputStream();
            }
            String respBody = new ReadWriteStrings().readString(response);

            return new Gson().fromJson(respBody, RegisterResult.class);
        }
        catch(IOException e){
            e.printStackTrace();
            return new RegisterResult("IOException detected. Register attempt failed.", false);
        }
    }

    // GetAllPeople
    public AllPeopleResult getAllPeople(String authToken){
        try{
            URL url = new URL("http://" + serverHostName + ":" + serverPortNumber + "/person");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.addRequestProperty("Authorization", authToken);
            http.setDoOutput(false);
            http.connect();

            InputStream response;
            if(http.getResponseCode() != 200){
                response = http.getErrorStream();
            }
            else {
                response = http.getInputStream();
            }
            String respBody = new ReadWriteStrings().readString(response);

            return new Gson().fromJson(respBody, AllPeopleResult.class);
        }
        catch(IOException e){
            e.printStackTrace();
            return new AllPeopleResult(null, "IOException detected. Attempt to get all people failed.",
                                        false);
        }
    }

    // GetAllEvents
    public AllEventsResult getAllEvents(String authToken){
        try{
            URL url = new URL("http://" + serverHostName + ":" + serverPortNumber + "/event");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.addRequestProperty("Authorization", authToken);
            http.setDoOutput(false);
            http.connect();

            InputStream response;
            if(http.getResponseCode() != 200){
                response = http.getErrorStream();
            }
            else {
                response = http.getInputStream();
            }
            String respBody = new ReadWriteStrings().readString(response);

            return new Gson().fromJson(respBody, AllEventsResult.class);
        }
        catch(IOException e){
            e.printStackTrace();
            return new AllEventsResult(null, "IOException detected. Attempt to get all events failed.",
                    false);
        }
    }
}
