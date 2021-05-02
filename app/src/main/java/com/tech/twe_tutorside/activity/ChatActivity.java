package com.tech.twe_tutorside.activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.SessionManager;
import com.tech.twe_tutorside.adapter.Chat_conversasion_Adapter;
import com.tech.twe_tutorside.model.GetChat;
import com.tech.twe_tutorside.model.GetChatData;
import com.tech.twe_tutorside.utils.RetrofitClients;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    ImageView iv_back;
    ArrayList<GetChatData> arrayList_conversation = new ArrayList<>();
    Chat_conversasion_Adapter mAdapter;
    RecyclerView recycler_view_chat;

    ProgressBar progressBar;
    private SessionManager sessionManager;

    EditText edt_message;
    ImageView img_send_message;
    TextView txt_name;
    private final int SPLASH_TIME = 5000;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);}
        setContentView(R.layout.activity_chat);

        recycler_view_chat =findViewById(R.id.recycler_view_chat);
        img_send_message =findViewById(R.id.img_send_message);
        edt_message =findViewById(R.id.edt_message);

        iv_back=findViewById(R.id.iv_back);
        txt_name=findViewById(R.id.txt_name);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                onBackPressed();
            }
        });

        progressBar = findViewById(R.id.progressBar);
        sessionManager = new SessionManager(ChatActivity.this);


        img_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ChatMessage =edt_message.getText().toString();

                if(ChatMessage.equalsIgnoreCase(""))
                {
                    Toast.makeText(ChatActivity.this, "Please Enter Message.", Toast.LENGTH_SHORT).show();
                }else
                {
                    if (sessionManager.isNetworkAvailable()) {
                        progressBar.setVisibility(View.VISIBLE);
                        insertChat(ChatMessage);
                    }else {
                        Toast.makeText(ChatActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        if (sessionManager.isNetworkAvailable()) {
            progressBar.setVisibility(View.VISIBLE);
            getChat();
        }else {
            Toast.makeText(ChatActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

        String StudentName = Preference.get(ChatActivity.this, Preference.KEY_student_Name);
        txt_name.setText(StudentName);
    }


    @Override
    protected void onResume() {
        super.onResume();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getChat();
            }
        }, 0, 5000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    private void setAdapter(ArrayList<GetChatData> arrayList_conversation) {
        mAdapter = new Chat_conversasion_Adapter(ChatActivity.this,arrayList_conversation);
        recycler_view_chat.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        recycler_view_chat.setLayoutManager(linearLayoutManager);
        recycler_view_chat.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new Chat_conversasion_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetChatData model) {

            }
        });
    }

    private void getChat() {

        String sender_id = Preference.get(ChatActivity.this,Preference.KEY_SenderId);;
        String receiver_id = Preference.get(ChatActivity.this,Preference.KEY_ReceiverId);;

        Call<GetChat> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_chat(sender_id,receiver_id);
        call.enqueue(new Callback<GetChat>() {
            @Override
            public void onResponse(Call<GetChat> call, Response<GetChat> response) {

                progressBar.setVisibility(View.GONE);

                try {

                    GetChat finallyPr = response.body();

                    String status   = finallyPr.getStatus ();
                    String message = finallyPr.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                      arrayList_conversation = (ArrayList<GetChatData>) finallyPr.getResult();

                     setAdapter(arrayList_conversation);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetChat> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void insertChat(String chat_message) {

        String sender_id = Preference.get(ChatActivity.this,Preference.KEY_SenderId);;
        String receiver_id = Preference.get(ChatActivity.this,Preference.KEY_ReceiverId);;

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .insert_chat(sender_id,receiver_id,chat_message);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");

                    if (status.equalsIgnoreCase("1")) {
                        edt_message.setText("");
                        Toast.makeText(ChatActivity.this, message, Toast.LENGTH_SHORT).show();
                        getChat();
                    }
                } catch (Exception e) {
                    Toast.makeText(ChatActivity.this, "Please Check Network", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Please Check Network", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void handlerMethod() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getChat();
            }
        }, SPLASH_TIME);
    }

}