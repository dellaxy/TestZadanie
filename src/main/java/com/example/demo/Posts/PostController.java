package com.example.demo.Posts;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Post-editor", description = "Manažovanie príspevkov")
@RequestMapping(path = "post")
public class PostController {
    private final PostService postsService;

    @Autowired
    public PostController(PostService postsService) {
        this.postsService = postsService;
    }

    @GetMapping
    @Operation(summary = "Všetky príspevky")
    public List<Post> getPosts(){
        return postsService.getAllPosts();
    }

    @GetMapping(path = "id/{id}")
    @Operation(summary = "Príspevok podľa ID")
    public Post getPostbyId(@PathVariable("id") int id){
        return postsService.getPostById(id);
    }

    @GetMapping(path = "userid/{userId}")
    @Operation(summary = "Príspevky podľa userID")
    public List<Post> getPostByuId(@PathVariable("userId") int userId){
        return postsService.getPostByUserId(userId);
    }

    @PostMapping(path = "{postId}")
    @Operation(summary = "Nový príspevok")
    public void addPost(@PathVariable("postId") int postId){
        postsService.addNewPost(postId);
    }

    @DeleteMapping(path = "{postId}")
    @Operation(summary = "Vymazanie príspevku podľa ID")
    public void deletePost(@PathVariable("postId") int postId){
        postsService.deletePost(postId);
    }

    @PutMapping(path = "{postId}")
    @Operation(summary = "Úprava title a body príspevsku")
    public void updatePost(@PathVariable("postId") int postId, @RequestParam(required = false) String title, @RequestParam(required = false) String body){
        postsService.updatePost(postId, title, body);
    }

}
