package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED) //외부에서의 생성을 열어 둘 필요가 없을 때 / 보안적으로 권장
@Entity
@Getter
@Table(name="board")
public class Board extends Time{

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 10, nullable = false)
    String writer;

    @Column(length = 100, nullable = false)
    String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Board(Long id, String writer, String title, String content, User user){
        Assert.hasText(writer, "writer must not be empty");
        Assert.hasText(title, "title must not be empty");
        Assert.hasText(content, "content must not be empty");

        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.user = user;
    }
}
