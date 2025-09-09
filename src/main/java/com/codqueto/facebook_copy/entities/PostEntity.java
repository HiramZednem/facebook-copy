package com.codqueto.facebook_copy.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "Post")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PostEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime dateCreation;

    private String content;

    private String img;

}
