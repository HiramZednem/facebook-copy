package com.codqueto.facebook_copy.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Page")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private LocalDateTime dateCreation;

    @OneToOne()
    @JoinColumn(name = "id_User", unique = true)
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_page")
    private List<PostEntity> posts = new ArrayList<>();

    public void addPost(PostEntity post) {
       posts.add(post);
    }

    public void removePost(PostEntity post) {
        posts.remove(post);
    }
}
