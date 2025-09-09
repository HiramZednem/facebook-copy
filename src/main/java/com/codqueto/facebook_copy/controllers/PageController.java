package com.codqueto.facebook_copy.controllers;

import com.codqueto.facebook_copy.dtos.request.PageRequest;
import com.codqueto.facebook_copy.dtos.response.PageResponse;
import com.codqueto.facebook_copy.entities.PageEntity;
import com.codqueto.facebook_copy.services.PageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
    public ResponseEntity<?> deletePage(@PathVariable String title) {
        this.pageService.delete(title);
        return ResponseEntity.noContent().build();
    }

    private String normalizeTitle(String title) {
        if (title.contains(" ")) {
            return title.replaceAll(" ","-");
        }
        return title;
    }
}
