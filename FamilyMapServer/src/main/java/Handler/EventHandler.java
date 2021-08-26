package Handler;

import DataAccess.DataAccessException;
import RequestResult.AllEventsResult;
import RequestResult.SingleEventResult;
import Service.AllEventsService;
import Service.SingleEventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            ReadWriteString stringHandler = new ReadWriteString();
            if (exchange.getRequestMethod().toLowerCase().equals("get")){
                Headers reqHeaders = exchange.getRequestHeaders();

                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    String URI = exchange.getRequestURI().toString();
                    String[] uriArray = URI.split("/");

                    String jsonStr;
                    if(uriArray.length == 3){ //Check if we were given a specific event ID
                        SingleEventResult resp = new SingleEventService().getSingleEvent(uriArray[2], authToken);
                        jsonStr = new Gson().toJson(resp);
                        if(resp.isSuccess()) {
                            System.out.println(jsonStr);
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else{
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                        }
                    }
                    else{ //If not, the request for the list of all events, so call that service.
                        AllEventsResult resp = new AllEventsService().getAllEvents(authToken);
                        jsonStr = new Gson().toJson(resp);
                        if(resp.isSuccess()) {
                            System.out.println(jsonStr);
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else{
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                        }
                    }
                    OutputStream respBody = exchange.getResponseBody();
                    stringHandler.writeString(jsonStr, respBody);
                    respBody.close();
                }
            }
        } catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
