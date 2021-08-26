package net;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import ui.LoginRegInfo;
import ui.TokenHostPort;

public class LoginRegisterTask extends AsyncTask<LoginRegInfo, Integer, LoginResult> {

    public interface Listener{
        void onError(Error e);
        void sadToast();
    }

    private Listener listener;
    private LoginRegInfo info;

    public LoginRegisterTask(Listener l){
        listener = l;
    }

    @Override
    protected void onPostExecute(LoginResult result) {
        if(!result.isSuccess()){
            listener.sadToast();
        }
        else{
            DataRetrievalTask task = new DataRetrievalTask((DataRetrievalTask.Listener) listener);
            TokenHostPort thp = new TokenHostPort(result.getAuthToken(), info.getHostName(), info.getPort());
            task.execute(thp);
        }
    }

    @Override
    protected LoginResult doInBackground(LoginRegInfo... i) {
        info = i[0];
        ServerProxy proxy = new ServerProxy(info.getHostName(), info.getPort());

        if(!info.isLogin()){
            RegisterRequest regRequest = new RegisterRequest(info.getUserName(), info.getPassword(),
                                                             info.getEmail(), info.getFirstName(),
                                                             info.getLastName(), info.getGender());
            RegisterResult regResult = proxy.register(regRequest);

            if(!regResult.isSuccess()){
                return new LoginResult(null, null, null,
                                       "Error with registering.", false);
            }
        }

        LoginRequest loginRequest = new LoginRequest(info.getUserName(), info.getPassword());
        LoginResult loginResult = proxy.login(loginRequest);

        return loginResult;
    }
}