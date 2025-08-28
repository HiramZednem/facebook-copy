package com.codqueto.facebook_copy.services.impl;

import com.codqueto.facebook_copy.dtos.request.PageRequest;
import com.codqueto.facebook_copy.dtos.request.PostRequest;
import com.codqueto.facebook_copy.dtos.response.PageResponse;
import com.codqueto.facebook_copy.entities.PageEntity;
import com.codqueto.facebook_copy.repositories.PageRepository;
import com.codqueto.facebook_copy.repositories.UserRepository;
import com.codqueto.facebook_copy.services.PageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Transactional(
        noRollbackFor = Exception.class,
        isolation = Isolation.READ_COMMITTED
)
@Slf4j
@AllArgsConstructor
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;
    private final UserRepository userRepository;

    @Override
    public PageResponse create(PageRequest page) {
        final var entity = new PageEntity();
        BeanUtils.copyProperties(page, entity);

        final var user = userRepository.findById(page.getUserId())
                        .orElseThrow();

        entity.setDateCreation(LocalDateTime.now());
        entity.setUser(user);
        entity.setPosts(new ArrayList<>());

        final var pageCreated = this.pageRepository.save(entity);

        final var response = new PageResponse();
        BeanUtils.copyProperties(pageCreated, response);
        return response;
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
