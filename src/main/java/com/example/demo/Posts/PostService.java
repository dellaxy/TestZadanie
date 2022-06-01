package com.example.demo.Posts;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class PostService {
    private final PostRepository postRepository;
    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    public Post getPostById(int id){
        Optional<Post> findPostById = postRepository.findById(id);
        if (findPostById.isPresent()) {
            return findPostById.get();
        }
        else{
            addNewPost(id);
            return postRepository.findById(id).get();
        }
    }
    public List<Post> getPostByUserId(int uid) {
        List<Post> posts = postRepository.findByUserId(uid);
        return posts;
    }
    public void addNewPost(int id) {
        JSONObject json = getData("https://jsonplaceholder.typicode.com/posts/"+id);
        if(json != null) {
            int userId = json.getInt("userId");
            JSONObject jsonUser = getData("https://jsonplaceholder.typicode.com/users/"+userId);
            if (jsonUser != null) {
                Post post = new Post(json.getInt("id"),  json.getInt("userId"), json.getString("title"), json.getString("body"));
                postRepository.save(post);
            }
        }
        else {
            throw new IllegalStateException("Post with id " + id + " does not exist");
        }
    }
    public void deletePost(int id) {
        if(postRepository.existsById(id))
            postRepository.deleteById(id);
        else {
            throw new IllegalStateException("Post with id " + id + " not found");
        }
    }
    @Transactional
    public void updatePost(int postid, String title, String body) {
        if(postRepository.existsById(postid)) {
            Post post = postRepository.findById(postid).get();
            if(title != null && title.length() > 0 && !title.equals(post.getTitle())) {
                post.setTitle(title);
            }
            if(body != null && body.length() > 0 && !body.equals(post.getBody())) {
                post.setBody(body);
            }
        }
        else {
            throw new IllegalStateException("Post with id " + postid + " not found");
        }
    }
    public JSONObject getData(String u){
        String temp = "";
        try {
            URL url = new URL(u);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(conn.getResponseCode() == 200) {
                Scanner scan = new Scanner(url.openStream());
                while(scan.hasNext()) {
                    temp += scan.nextLine();
                }
            }
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(temp.isEmpty()) {
            return null;
        }
        else {
            JSONObject json = new JSONObject(temp);
            return json;
        }
    }

}
