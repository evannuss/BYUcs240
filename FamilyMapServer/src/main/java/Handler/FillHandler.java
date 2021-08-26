package Handler;

import DataAccess.DataAccessException;
import RequestResult.FillResult;
import Service.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            ReadWriteString stringHandler = new ReadWriteString();
            if (exchange.getRequestMethod().toLowerCase().equals("post")){
                String URI = exchange.getRequestURI().toString();
                String[] uriArray = URI.split("/");

                FillResult resp;
                if(uriArray.length == 4){ //Check if the request includes a specific amount of generations to fill.
                    resp = new FillService().fill(uriArray[2], Integer.parseInt(uriArray[3]));
                }
                else{ //If not, the default is 4 generations.
                    resp = new FillService().fill(uriArray[2], 4);
                }

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
