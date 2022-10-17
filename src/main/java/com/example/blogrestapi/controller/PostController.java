package com.example.blogrestapi.controller;

import com.example.blogrestapi.payload.PostDto;
import com.example.blogrestapi.payload.PostDtoV2;
import com.example.blogrestapi.payload.PostResponse;
import com.example.blogrestapi.service.PostService;
import com.example.blogrestapi.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

//    @GetMapping(value = "/api/posts/{id}", params = "version=1") //url?version=1
//    @GetMapping(value = "/api/posts/{id}", headers = "X-API-VERSION=1") //HEADER version
//    @GetMapping(value = "/api/posts/{id}", produces = "application/vdn/javaguides.v1+json") //add to headers: Accept values=application/vdn/javaguides.v1+json
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

//    @GetMapping(value = "/api/posts/{id}", params = "version=2")
//    @GetMapping(value = "/api/posts/{id}", headers = "X-API-VERSION=2")
//    @GetMapping(value = "/api/posts/{id}", produces = "application/vdn.javaguides.v2+json")
//    public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable(name = "id") long id){
//        PostDto postDto = postService.getPostById(id);
//        PostDtoV2 postDtoV2 = new PostDtoV2();
//        postDtoV2.setId(postDto.getId());
//        postDtoV2.setTitle(postDto.getTitle());
//        postDtoV2.setComments(postDto.getComments());
//        postDtoV2.setDescription(postDto.getDescription());
//        List<String> tags = new ArrayList<>();
//        tags.add("Java");
//        tags.add("Spring Boot");
//        tags.add("AWS");
//        postDtoV2.setTags(tags);
//
//        return ResponseEntity.ok(postDtoV2);
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        PostDto postDtoResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postDtoResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
}
