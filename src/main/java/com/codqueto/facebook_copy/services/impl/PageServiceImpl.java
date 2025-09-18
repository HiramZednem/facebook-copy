package com.codqueto.facebook_copy.services.impl;

import com.codqueto.facebook_copy.dtos.request.PageRequest;
import com.codqueto.facebook_copy.dtos.request.PostRequest;
import com.codqueto.facebook_copy.dtos.response.PageResponse;
import com.codqueto.facebook_copy.dtos.response.PostResponse;
import com.codqueto.facebook_copy.entities.PageEntity;
import com.codqueto.facebook_copy.entities.PostEntity;
import com.codqueto.facebook_copy.exceptions.TitleNotValidException;
import com.codqueto.facebook_copy.repositories.PageRepository;
import com.codqueto.facebook_copy.repositories.UserRepository;
import com.codqueto.facebook_copy.services.PageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional(
//        noRollbackFor = Exception.class,
//        isolation = Isolation.READ_COMMITTED
)
@Slf4j
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;
    private final UserRepository userRepository;
    private final HashSet<String> badWords = new HashSet<>();

    public PageServiceImpl(PageRepository pageRepository, UserRepository userRepository) throws FileNotFoundException {
        this.pageRepository = pageRepository;
        this.userRepository = userRepository;

        Scanner s = new Scanner(new File("src/main/resources/bad-words.txt"));

        while (s.hasNextLine()) {
            badWords.add(s.nextLine());
        }
    }

    @Override
    public PageResponse create(PageRequest page) {
        final var entity = new PageEntity();
        BeanUtils.copyProperties(page, entity);

        this.validateTitle(entity.getTitle());

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
    public PageResponse findByTitle(String title) {
        var pageEntity = pageRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("Title not found"));

        final List<PostResponse> postResponses = pageEntity.getPosts().stream().map(post -> {
            return PostResponse
                    .builder()
                    .img(post.getImg())
                    .content(post.getContent())
                    .dateCreation(post.getDateCreation())
                    .id(post.getId())
                    .build();

        }).toList();
        final var response = new PageResponse();
        BeanUtils.copyProperties(pageEntity, response);
        response.setPosts(postResponses);

        return response;
    }

    @Override
    public PageResponse update(PageRequest page, String title) {

        final var entityFromDB = pageRepository.findByTitle(title)
                .orElseThrow(()-> new IllegalArgumentException("Page not found"));

        this.validateTitle(page.getTitle());

        entityFromDB.setTitle(page.getTitle());

        final var pageUpdated = pageRepository.save(entityFromDB);

        final var response = new PageResponse();
        BeanUtils.copyProperties(pageUpdated, response);
        return response;
    }

    @Override
    public void delete(String title) {
        final var entityFromDB = pageRepository.findByTitle(title)
                .orElseThrow(()-> new IllegalArgumentException("Page not found"));

        if(pageRepository.existsByTitle(title)) {
            pageRepository.deleteByTitle(entityFromDB.getTitle());
        }
//        pageRepository.delete(entityFromDB);
//        pageRepository.deleteById(entityFromDB.getId());

    }

    @Override
    public PageResponse createPost(PostRequest post, String title) {
        final var pageToUpdate = pageRepository.findByTitle(title)
                .orElseThrow(()-> new IllegalArgumentException("Page not found"));

        PostEntity postEntity = new PostEntity();

        BeanUtils.copyProperties(post, postEntity);
        postEntity.setDateCreation(LocalDateTime.now());
        pageToUpdate.addPost(postEntity);

        final PageEntity pageUpdated= this.pageRepository.save(pageToUpdate);

        final PageResponse response = new PageResponse();
        BeanUtils.copyProperties(pageUpdated, response);

        final List<PostResponse> postResponses = pageUpdated.getPosts().stream().map(p -> {
            return PostResponse
                    .builder()
                    .img(p.getImg())
                    .content(p.getContent())
                    .dateCreation(p.getDateCreation())
                    .id(p.getId())
                    .build();

        }).toList();

        response.setPosts(postResponses);

        return response;
    }

    @Override
    public void deletePost(Long idPost, String title) {
        final PageEntity pageToUpdate = this.pageRepository.findByTitle(title)
                .orElseThrow( () -> new IllegalArgumentException("Page not found"));

        final PostEntity postTodelete = pageToUpdate.getPosts()
                .stream()
                .filter( post ->  post.getId() == idPost)
                .findFirst()
                .orElseThrow( () -> new IllegalArgumentException("Post not found") );

        pageToUpdate.removePost(postTodelete);

        this.pageRepository.save(pageToUpdate);
    }

    private void validateTitle(String title) {
        if (title.isEmpty()) {
            throw new TitleNotValidException("Title cannot be empty");
        }

        Arrays.stream(title.trim().split("-")).forEach(word -> {
            if (badWords.contains(word)) {
                throw new TitleNotValidException("The title can't contain bad words");
            }
        });
    }
}
