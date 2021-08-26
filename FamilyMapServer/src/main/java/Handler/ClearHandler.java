package Handler;

import DataAccess.DataAccessException;
import RequestResult.ClearResult;
import Service.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                ReadWriteString stringHandler = new ReadWriteString();

                ClearResult resp = new ClearService().clear();

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
