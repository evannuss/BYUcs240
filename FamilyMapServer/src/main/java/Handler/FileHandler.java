package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try{
            if (exchange.getRequestMethod().toLowerCase().equals("get")){
                String urlPath = exchange.getRequestURI().toString();

                if(urlPath == null || urlPath.equals("/")){
                    urlPath = "/index.html"; //This is the default path if only a / is given.
                }

                String filePath = "web" + urlPath;
                File requestedFile = new File(filePath);

                if(requestedFile.exists()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(requestedFile.toPath(), respBody);
                    respBody.close();

                    success = true;
                }
                else{
                    filePath = "web/HTML/404.html";
                    requestedFile = new File(filePath);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND,0);
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(requestedFile.toPath(), respBody);
                    respBody.close();
                    throw new IOException("404 error!");
                }
            }
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                exchange.getResponseBody().close();
            }
        }
        catch(IOException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }
}
