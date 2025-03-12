package com.company.article;

import com.company.category.CategoryEntity;
import com.company.component.BaseEntity;
import com.company.photo.PhotoEntity;
import com.company.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "articles")
public class ArticleEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private CategoryEntity category;

    @Column(name = "category_id")
    private UUID categoryId;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Long viewsCount;

    @Enumerated(EnumType.STRING)
    private ArticleType type;

    @Column
    private String tags;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id",
    insertable = false, updatable = false)
    private PhotoEntity photo;

    @Column(name = "photo_id")
    private UUID photo_id;

    public enum ArticleType {
        NEWS, AD, MOTIVATION, INTERVIEW, PROMOTION
    }
}