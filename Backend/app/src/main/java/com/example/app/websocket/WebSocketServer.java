package com.example.app.websocket;


import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.validation.Valid;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.example.app.model.comment;
import com.example.app.repo.commentRepo;
import com.example.app.repo.postRepo;
import com.example.app.repo.userRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Vamsi Krishna Calpakkam
 * @author Nick Battani
 * @author Amir Hamza
 */
@ServerEndpoint("/comment/{postId}/{userId}")
@Component
public class WebSocketServer {


    private static commentRepo commentRepository;

    private static postRepo postRepo;

    private static userRepo userRepo;

    @Autowired
    public void setCommentRepository(commentRepo repo) {
        commentRepository = repo;  // we are setting the static variable
    }

    @Autowired
    public void setPostRepository(postRepo repo) {
        postRepo = repo;  // we are setting the static variable
    }

    @Autowired
    public void setUserRepository(userRepo repo) {
        userRepo = repo;  // we are setting the static variable
    }

    // Store all socket session and their corresponding username.
    private static Map<Session, Long> sessionUsernameMap = new Hashtable<>();
    private static Map<Long, Session> usernameSessionMap = new Hashtable<>();
    private static Map<Session, Long> sessionPostMap = new Hashtable<>();
    private static Map<Long, Session> postSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("postId") Long postId, @PathParam("userId") Long userId)
            throws IOException {
        logger.info("Entered into Open");

        sessionUsernameMap.put(session, userId);
        usernameSessionMap.put(userId, session);
        sessionPostMap.put(session, postId);
        postSessionMap.put(postId, session);

    }

    @OnMessage
    public void onMessage(Session session, String comment) throws IOException {
        // Handle new messages
        logger.info("Entered into Message: Got Message:" + comment);
        Long userId = sessionUsernameMap.get(session);
        Long postId = sessionPostMap.get(session);

        broadcast(userId + ": " + comment);
        comment temp = new comment();
        temp.setText(comment);
        temp.setUser(userRepo.findByID(userId));
        temp.setPost(postRepo.findByID(postId));
        commentRepository.save(temp);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        Long userId = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(userId);

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
    }

    private void sendMessageToPArticularUser(Long userId, String message) {
        try {
            usernameSessionMap.get(userId).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }

        });

    }
}
