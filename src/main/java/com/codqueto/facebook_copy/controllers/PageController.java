package com.codqueto.facebook_copy.controllers;

import com.codqueto.facebook_copy.dtos.request.PageRequest;
import com.codqueto.facebook_copy.dtos.request.PostRequest;
import com.codqueto.facebook_copy.dtos.response.PageResponse;
import com.codqueto.facebook_copy.entities.PageEntity;
import com.codqueto.facebook_copy.services.PageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(path = "page")
@AllArgsConstructor
public class PageController {

    private final PageService pageService;

    @GetMapping(path = "{title}")
    public ResponseEntity<PageResponse> getPage(
            @PathVariable String title
    ) {

        return ResponseEntity.ok(this.pageService.findByTitle(title));

    }

    @PostMapping
    public ResponseEntity<?> postPage(@RequestBody PageRequest request) {
        request.setTitle(this.normalizeTitle(request.getTitle()));

        final var uri = this.pageService.create(request).getTitle();
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @PutMapping(path = "{title}")
    public ResponseEntity<PageResponse> updatePage(
            @PathVariable String title,
            @RequestBody PageRequest request
    ) {
        request.setTitle(this.normalizeTitle(request.getTitle()));
        return ResponseEntity.ok(pageService.update(request, title));

    }

    @DeleteMapping(path = "{title}")
    public ResponseEntity<Void> deletePage(@PathVariable String title) {
        this.pageService.delete(title);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "{title}/post")
    public ResponseEntity<PageResponse> createPost(
            @PathVariable String title,
            @RequestBody PostRequest request
    ) {
       return ResponseEntity.ok(pageService.createPost(request, title));
    }

    @DeleteMapping(path = "{title}/post/{id}")
    public ResponseEntity<Void> deletePost (
        @PathVariable String title,
        @PathVariable Long id
    ) {
       this.pageService.deletePost(id, title);
       return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "img/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam(value = "file") MultipartFile file
    ) {
        try {

            String pathUrl = "C:\\Users\\hculebro\\Documents\\learning\\facebook-copy\\src\\main\\resources\\static\\img";
            Path path = Paths.get(pathUrl);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            final var destination = new File(pathUrl);
            file.transferTo(destination);

            return ResponseEntity.ok().body("Img uploaded successfully");
        }catch (IOException e) {
            return ResponseEntity.internalServerError().body("Can not upload the image");
        }


    }

    private String normalizeTitle(String title) {
        if (title.contains(" ")) {
            return title.replaceAll(" ","-");
        }
        return title;
    }
}
