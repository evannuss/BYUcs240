package net;

import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.MainThread;

import java.nio.channels.AsynchronousChannelGroup;

import model.DataCache;
import ui.TokenHostPort;

public class DataRetrievalTask extends AsyncTask<TokenHostPort, Integer, Boolean> {

    private AllPeopleResult peopleResult;

    public interface Listener{
        void happyToast(String firstName, String lastName);
        void switchFragment();
    }

    private Listener listener;

    public DataRetrievalTask(Listener l){
        listener = l;
    }

    @Override
    protected void onPostExecute(Boolean success){
        if(success){
            listener.happyToast(peopleResult.getData().get(0).getFirstName(),
                                peopleResult.getData().get(0).getLastName());
            listener.switchFragment();
        }
    }


    @Override
    protected Boolean doInBackground(TokenHostPort... thp) {
        TokenHostPort info = thp[0];
        ServerProxy proxy = new ServerProxy(info.getHost(), info.getPort());

        peopleResult = proxy.getAllPeople(info.getToken());
        DataCache.getInstance().setPeople(peopleResult.getData());

        AllEventsResult eventsResult = proxy.getAllEvents(info.getToken());
        DataCache.getInstance().setEvents(eventsResult.getData());

        return peopleResult.isSuccess() && eventsResult.isSuccess();
    }
}
