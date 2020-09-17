package com.example.anti_social;


import android.app.Activity;
import android.util.Log;

import com.example.anti_social.net_utils.APIFunctions;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * class used to create and handle websockets for use in comments for postActivity
 */
public class commentSocket extends Activity {

    private WebSocketClient cc;

    private int postID;
    private String userID;
    private android.os.Handler postHandler;

    /**
     * Constructor for a comment socket
     * @param postID The ID of the post that the websocket is for
     * @param userID The ID of the user accessing the post
     * @param postHandler The posts handler used to help websocket interact with UI thread
     */
    public commentSocket(int postID, String userID, android.os.Handler postHandler){
        this.postID = postID;
        this.userID = userID;
        this.postHandler = postHandler;
    }

    public void setUpSocket(){

        Draft[] drafts ={new Draft_6455()};

        String w = APIFunctions.commentWebSocket(userID, postID);
        try {
            cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {

                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d("OPEN", "onOpen() returned: " + "is connecting");
                }

                @Override
                public void onMessage(String message) {
                    Log.d("MESSAGE", "onMessage() returned: " + message);

                    postHandler.sendEmptyMessage(0);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    Log.d("Exception:", ex.toString());
                }
            };
        }
        catch (URISyntaxException e){
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        cc.connect();
    }

    /**
     * sends message when user clicks to send a comment
     * @param comment the comment sent by the user
     */
    public void send(String comment){
        try{
            cc.send(comment);
        }
        catch (Exception e){
            Log.d("ExceptionSendessage:", e.getMessage().toString());
        }
    }

    /**
     * closes web socket called when post activity has ended
     */
    public void close(){
        cc.close();
    }
}
