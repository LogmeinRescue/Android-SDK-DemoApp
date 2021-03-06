package com.logmein.rescuesdkdemo.devicescreenstreamingapp.eventhandler;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.logmein.rescuesdk.api.chat.ChatClient;
import com.logmein.rescuesdk.api.chat.event.ChatConnectedEvent;
import com.logmein.rescuesdk.api.chat.event.ChatDisconnectedEvent;
import com.logmein.rescuesdk.api.eventbus.Subscribe;

/**
 * Manipulates the chat message view based on the related events.
 */
public class ChatMessagePresenter {
    private EditText chatMessage;
    private TextWatcher typingSenderWatcher;

    public ChatMessagePresenter(EditText chatMessage) {
        this.chatMessage = chatMessage;
    }

    @Subscribe
    public void onChatConnected(ChatConnectedEvent event) {
        final ChatClient chatClient = event.getChatClient();
        chatMessage.setEnabled(true);

        typingSenderWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                chatClient.sendTyping();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        chatMessage.addTextChangedListener(typingSenderWatcher);
    }

    @Subscribe
    public void onChatDisconnected(ChatDisconnectedEvent event) {
        chatMessage.setEnabled(false);
        chatMessage.removeTextChangedListener(typingSenderWatcher);
    }
}
