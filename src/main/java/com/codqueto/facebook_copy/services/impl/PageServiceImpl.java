package com.codqueto.facebook_copy.services.impl;

import com.codqueto.facebook_copy.dtos.request.PageRequest;
import com.codqueto.facebook_copy.dtos.request.PostRequest;
import com.codqueto.facebook_copy.dtos.response.PageResponse;
import com.codqueto.facebook_copy.repositories.PageRepository;
import com.codqueto.facebook_copy.services.PageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
        noRollbackFor = Exception.class,
        isolation = Isolation.READ_COMMITTED
)
@Slf4j
@AllArgsConstructor
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;

    @Override
    public PageResponse create(PageRequest page) {
        return null;
    }

    @Override
    public PageResponse readByTitle(String title) {
        return null;
    }

    @Override
    public PageResponse update(PageRequest page, String title) {
        return null;
    }

    @Override
    public void delete(String title) {

    }

    @Override
    public PageResponse createPost(PostRequest post) {
        return null;
    }

    @Override
    public PageResponse deletePost(Long idPost) {
        return null;
    }
}
