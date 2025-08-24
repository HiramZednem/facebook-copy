package com.codqueto.facebook_copy.services;

import com.codqueto.facebook_copy.dtos.request.PageRequest;
import com.codqueto.facebook_copy.dtos.request.PostRequest;
import com.codqueto.facebook_copy.dtos.response.PageResponse;
import com.codqueto.facebook_copy.dtos.response.PostResponse;

public interface PageService {

    PageResponse create (PageRequest page);
    PageResponse readByTitle(String title);
    PageResponse update(PageRequest page, String title);
    void delete(String title);


    PageResponse createPost(PostRequest post);
    PageResponse deletePost(Long idPost);
}
