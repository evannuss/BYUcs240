package ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.familymap.R;

import net.DataRetrievalTask;
import net.LoginRegisterTask;
import net.LoginRequest;
import net.RegisterRequest;

import org.w3c.dom.Text;

public class LoginFragment extends Fragment implements LoginRegisterTask.Listener, DataRetrievalTask.Listener {

    private EditText hostEdit;
    private EditText portEdit;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText emailEdit;
    private RadioGroup mfRadioGroup;
    private RadioButton maleButton;
    private RadioButton femaleButton;
    private Button loginButton;
    private Button registerButton;

    private int[] registerChecker = new int[7];
    private int[] loginChecker = new int[4];

    private String hostName;
    private int port;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;

    public String getHostName() { return hostName; }

    public int getPort() { return port; }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate){

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        hostEdit = (EditText) view.findViewById(R.id.hostEditText);
        hostEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hostName = s.toString();
                int currIndex = 0;

                if(s.length() == 0){
                    loginChecker[currIndex] = 0;
                    registerChecker[currIndex] = 0;
                }
                else{
                    loginChecker[currIndex] = 1;
                    registerChecker[currIndex] = 1;
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        portEdit = (EditText) view.findViewById(R.id.portEditText);
        portEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int currIndex = 1;
                if(s.toString().length() == 0){
                    port = 0;
                    loginChecker[currIndex] = 0;
                    registerChecker[currIndex] = 0;
                }
                else{
                    port = Integer.parseInt(s.toString());
                    loginChecker[currIndex] = 1;
                    registerChecker[currIndex] = 1;
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        usernameEdit = (EditText) view.findViewById(R.id.usernameEditText);
        usernameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userName = s.toString();
                int currIndex = 2;

                if(s.length() == 0){
                    loginChecker[currIndex] = 0;
                    registerChecker[currIndex] = 0;
                }
                else{
                    loginChecker[currIndex] = 1;
                    registerChecker[currIndex] = 1;
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        passwordEdit = (EditText) view.findViewById(R.id.passwordEditText);
        passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s.toString();
                int currIndex = 3;

                if(s.length() == 0){
                    loginChecker[currIndex] = 0;
                    registerChecker[currIndex] = 0;
                }
                else{
                    loginChecker[currIndex] = 1;
                    registerChecker[currIndex] = 1;
                }

                boolean enable = true;
                for (int value : loginChecker) {
                    if (value == 0) {
                        enable = false;
                        break;
                    }
                }

                loginButton.setEnabled(enable);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        firstNameEdit = (EditText) view.findViewById(R.id.firstNameEditText);
        firstNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firstName = s.toString();
                int currIndex = 4;

                if(s.length() == 0){
                    registerChecker[currIndex] = 0;
                }
                else{
                    registerChecker[currIndex] = 1;
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        lastNameEdit = (EditText) view.findViewById(R.id.lastNameEditText);
        lastNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lastName = s.toString();
                int currIndex = 5;

                if(s.length() == 0){
                    registerChecker[currIndex] = 0;
                }
                else{
                    registerChecker[currIndex] = 1;
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        emailEdit = (EditText) view.findViewById(R.id.emailEditText);
        emailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email = s.toString();
                int currIndex = 6;

                if(s.length() == 0){
                    registerChecker[currIndex] = 0;
                }
                else{
                    registerChecker[currIndex] = 1;
                }

                boolean enable = true;
                for(int value : registerChecker){
                    if(value == 0){
                        enable = false;
                        break;
                    }
                }

                registerButton.setEnabled(enable);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        mfRadioGroup = (RadioGroup) view.findViewById(R.id.mfRadioGroup);
        maleButton = (RadioButton) view.findViewById(R.id.maleRadioButton);
        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "m";
            }
        });

        femaleButton = (RadioButton) view.findViewById(R.id.femaleRadioButton);
        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "f";
            }
        });

        loginButton = (Button) view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonClicked();
            }
        });

        registerButton = (Button) view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerButtonClicked();
            }
        });

        registerButton.setEnabled(false);
        loginButton.setEnabled(false);
        maleButton.setChecked(true);
        gender = "m";

        return view;
    }

    public LoginRegInfo getRegisterInfo(){
        return new LoginRegInfo(false, hostName, port, userName, password,
                                firstName, lastName, email, gender);
    }

    public LoginRegInfo getLoginInfo(){
        return new LoginRegInfo(true, hostName, port, userName, password,
                                null, null, null, null);
    }

    private void loginButtonClicked(){
        LoginRegisterTask task = new LoginRegisterTask(this);

        task.execute(getLoginInfo());
    }

    private void registerButtonClicked(){
        LoginRegisterTask task = new LoginRegisterTask(this);

        task.execute(getRegisterInfo());
    }

    @Override
    public void onError(Error e){

    }

    @Override
    public void happyToast(String firstName, String lastName){
        Toast.makeText(getContext(), "First name: " + firstName + ". Last name: "
                                     + lastName + ".", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sadToast(){
        Toast.makeText(getContext(), R.string.failure_toast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void switchFragment(){
        ((MainActivity) getActivity()).switchFragment();
    }
}
