package Handler;

import DataAccess.DataAccessException;
import RequestResult.LoginRequest;
import RequestResult.LoginResult;
import Service.LoginService;
import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            ReadWriteString stringHandler = new ReadWriteString();
            if (exchange.getRequestMethod().toLowerCase().equals("post")){

                InputStream reqBody = exchange.getRequestBody();
                String reqData = stringHandler.readString(reqBody);

                System.out.println(reqData);

                Gson decoder = new Gson();
                LoginRequest req = decoder.fromJson(reqData, LoginRequest.class);

                LoginResult resp = new LoginService().login(req);

                String jsonStr = new Gson().toJson(resp);

                if(resp.isSuccess()) {
                    System.out.println(jsonStr);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                }
                OutputStream respBody = exchange.getResponseBody();
                stringHandler.writeString(jsonStr, respBody);
                respBody.close();
            }
        }
        catch(IOException | DataAccessException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
