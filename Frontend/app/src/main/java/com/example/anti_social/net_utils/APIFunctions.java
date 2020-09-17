package com.example.anti_social.net_utils;

/**
 * Class used to store string constants for urls for use with volley requests
 */
public class APIFunctions {
    public static final String POSTMAN_URL = "https://postman-echo.com/get?foo1=bar1&foo2=bar2";
    public static final String LOCAL_HOST = "http://10.0.2.2";
    public static final String GETALLPOSTS = "http://coms-309-sk-4.cs.iastate.edu:8080/api/post/getAllPosts";
    public static final String CREATEUSER = "http://coms-309-sk-4.cs.iastate.edu:8080/api/user/createUser";
    public static final String VERIFY_TOKEN = "http://coms-309-sk-4.cs.iastate.edu:8080/api/token";

    /**
     * NOT IN USE DUE TO WEBSOCKET IMPLEMENTATION
     * Get the correct url to create a comment based on user and post
     * @param userID the ID of the user creating the comment
     * @param postID the ID of the post the comment should be added to
     * @return the url for the server to create a new comment on a post
     */
    public static String createComment(String userID, int postID){
        return "http://coms-309-sk-4.cs.iastate.edu:8080/api/post/" + userID + "/" + postID + "/comment";
    }

    /**
     * NOT IN USE DUE TO OAUTH IMPLEMENTATION
     * get the correct url to get user ID based on name
     * @param name the name of the user
     * @return the url for the server to access the user
     */
    public static String getUserByName(String name){
        return "http://coms-309-sk-4.cs.iastate.edu:8080/api/user/getUserByUserName/"+name;
    }

    /**
     * Get the correct url to get number of upvotes on a pist
     * @param id the id of the post you want to know the number of upvotes for
     * @return the url for the server to get the number of upvotes for the post
     */
    public static String getPostUpvotes(int id){
        return "http://coms-309-sk-4.cs.iastate.edu:8080/api/post/"+id+"/likesByPostId";
    }

    /**
     * Get the correct url to create a new post
     * @param id the ID of the user creating the post
     * @return the url for the server create the new post
     */
    public static String createPost(String id){
        return "http://coms-309-sk-4.cs.iastate.edu:8080/api/post/"+id+"/createPost";
    }

    /**
     * Get the correct url to get the comments on a post
     * @param id the ID of the post to get the comments for
     * @return the url for the server to get the comments for the specific post
     */
    public static String getPostComments(int id){
        return "http://coms-309-sk-4.cs.iastate.edu:8080/api/post/"+id+"/commentsByPostId";
    }

    /**
     * Get the correct url for the ID of the upvote created by a user, used to check if a user has upvoted a post
     * @param userID the ID of the user
     * @param postID the ID of the post being checked
     * @return the url for the server to get the ID of the upvote
     */
    public static String getUpvoteID(String userID, int postID){
        return "http://coms-309-sk-4.cs.iastate.edu:8080/api/post/" + userID + "/" + postID + "/getLikeId";
    }

    /**
     * Get the correct url for the ID of the downvote created by a user, used to check if a user has downvoted a post
     * @param userID the ID of the user
     * @param postID the ID of the post being checked
     * @return the url for the server to get the ID of the downvote
     */
    public static String getDownvoteID(String userID, int postID){
        return "http://coms-309-sk-4.cs.iastate.edu:8080/api/post/" + userID + "/" + postID + "/getDislikeId";
    }

    /**
     * Get the correct url to allow user to upvote a post
     * @param userID the ID of the user attempting to upvote
     * @param postID the ID of the post the user is upvoting
     * @return the url for the server to add the upvote to the database
     */
    public static String upvotePost(String userID, int postID){
        return "http://coms-309-sk-4.cs.iastate.edu:8080/api/post/" + userID + "/" + postID + "/likePost";
    }

    /**
     * Get the correct url to allow user to un-upvote a post
     * @param userID the ID of the user attempting to un-upvote
     * @param postID the ID of the post the user is un-upvoting
     * @return the url for the server to remove the upvote to the database
     */
    public static String unUpvotePost(String userID, int postID){
        return "http://coms-309-sk-4.cs.iastate.edu:8080/api/post/" + userID + "/" + postID + "/unLike";
    }

    /**
     * Get the correct url to allow user to downvote a post
     * @param userID the ID of the user attempting to downvote
     * @param postID the ID of the post the user is downvoting
     * @return the url for the server to add the downvote to the database
     */
    public static String downvotePost(String userID, int postID){
        return "http://coms-309-sk-4.cs.iastate.edu:8080/api/post/" + userID + "/" + postID + "/dislikePost";
    }

    /**
     * Get the correct url to allow user to un-downvote a post
     * @param userID the ID of the user attempting to un-downvote
     * @param postID the ID of the post the user is un-downvoting
     * @return the url for the server to remove the downvote to the database
     */
    public static String unDownvotePost(String userID, int postID){
        return "http://coms-309-sk-4.cs.iastate.edu:8080/api/post/" + userID + "/" + postID + "/unDislike";
    }

    /**
     * Get the correct url to open a websocket to allow for comments
     * @param userID the ID of the user opening the socket
     * @param postID the ID of the post the user is trying to view
     * @return the url for the server to establish a websocket connection
     */
    public static String commentWebSocket(String userID, int postID){
        return "ws://coms-309-sk-4.cs.iastate.edu:8080/comment/" + postID + "/" + userID;
    }
}
