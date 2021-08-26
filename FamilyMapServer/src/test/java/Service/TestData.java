package Service;

import DataAccess.DataAccessException;
import Model.AuthToken;
import RequestResult.LoadRequest;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestData {

    public LoadRequest getData() {
        try {
            String fileName = "passoffFiles/LoadData.json";
            Reader reader = Files.newBufferedReader(Paths.get(fileName));

            LoadRequest data = new Gson().fromJson(reader, LoadRequest.class);

            return data;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<AuthToken> getTokens(){
        List<AuthToken> tokens = new ArrayList<>();

        tokens.add(new AuthToken("hfjdhajhtuirheiuatre", "someone"));
        tokens.add(new AuthToken("hgojkrfahihuirhaegj", "someone else"));
        tokens.add(new AuthToken("uiotrusiohtrngkngmfdshjkf", "bless you"));

        return tokens;
    }
}
