package org.nurim.nurim.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curation_id")
    private int postId;

    @Column(name = "title", nullable = false)
    private String postTitle;

    @Column(name = "content", nullable = false)
    private String postContent;

    @Column(name = "category", nullable = false)
    private String postCategory;

    @Column(name = "registerDate", nullable = false)
    private LocalDate postRegisterDate;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true) // 어떤 Entity의 속성으로 매핑하는지 // CurationImage의 curation// 변수
    @Builder.Default
    private Set<PostImage> imageSet = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    public void addPostImage(String uuid, String fileName) {

        PostImage postImage = PostImage.builder()
                .image_detail(uuid)  // 파일 uuid 저장
                .image_thumb(fileName)   // 파일 이름 저장
                .build();

        this.imageSet.add(postImage);
        postImage.changePost(this);

    }


}