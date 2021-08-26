package Handler;

import DataAccess.DataAccessException;
import RequestResult.LoadRequest;
import RequestResult.LoadResult;
import Service.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            if (exchange.getRequestMethod().toLowerCase().equals("post")){
                ReadWriteString stringHandler = new ReadWriteString();
                InputStream reqBody = exchange.getRequestBody();
                String reqData = stringHandler.readString(reqBody);

                System.out.println(reqData);

                Gson decoder = new Gson();
                LoadRequest req = decoder.fromJson(reqData, LoadRequest.class);

                LoadResult resp = new LoadService().load(req);

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
