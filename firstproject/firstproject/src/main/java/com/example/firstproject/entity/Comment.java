package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 해당 댓글 엔티티 여러개가, 하나의 Article의 대표값을 저장
    @JoinColumn(name = "article_id") // "articleid" 컬럼에 Article의 대표값을 저장
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        // 예외 처리
        if( dto.getId() !=null) throw new IllegalArgumentException("댓글 생성 실패, 댓글의 id가 있어야 합니다");
        if (!Objects.equals(dto.getArticleId(), article.getId())) throw new IllegalArgumentException("댓글 생성 실패, 게시글의 id가 잘못되었습니다 ");

        // 엔티티 생성 및 반환
        return new Comment(dto.getId(), article,dto.getNickname(), dto.getBody());
    }

    public void patch(CommentDto dto) {
        // 예외 발생
        if(this.id != dto.getId())throw new IllegalArgumentException("댓글 수정 실패! 잘못된 아이디가 입력되었습니다");

        // 객체 갱신
        if(dto.getNickname() != null) this.nickname = dto.getNickname();
        if(dto.getBody() != null) this.body = dto.getBody();
    }
}