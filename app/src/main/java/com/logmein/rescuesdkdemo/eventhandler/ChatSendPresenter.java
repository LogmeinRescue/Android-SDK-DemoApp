package com.logmein.rescuesdkdemo.eventhandler;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.logmein.rescuesdk.api.chat.ChatClient;
import com.logmein.rescuesdk.api.chat.event.ChatConnectedEvent;
import com.logmein.rescuesdk.api.chat.event.ChatDisconnectedEvent;
import com.logmein.rescuesdk.api.eventbus.Subscribe;

/**
 * Created by bbanyai on 15/10/15.
 */
public class ChatSendPresenter {
    private Button chatSend;
    private EditText chatMessage;

    public ChatSendPresenter(Button chatSend, EditText chatMessage) {
        this.chatSend = chatSend;
        this.chatMessage = chatMessage;
    }

    @Subscribe
    public void onChatConnected(ChatConnectedEvent event) {
        final ChatClient chatClient = event.getChatClient();
        chatSend.setEnabled(true);
        chatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatClient.sendMessage(chatMessage.getText().toString());
                chatMessage.setText("");
            }
        });
    }

    @Subscribe
    public void onChatDisconnected(ChatDisconnectedEvent event) {
        chatSend.setEnabled(false);
        chatSend.setOnClickListener(null);
    }

}
