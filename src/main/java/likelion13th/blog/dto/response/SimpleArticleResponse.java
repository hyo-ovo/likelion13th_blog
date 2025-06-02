package likelion13th.blog.dto.response;

import likelion13th.blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder @AllArgsConstructor
public class SimpleArticleResponse {

    private final Long id;
    private final String title;
    private final String author;
    private final LocalDateTime createdAt;
    private final int commentCount;


    public static SimpleArticleResponse of(Article article) {
        return SimpleArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .author(article.getAuthor())
                .createdAt(article.getCreatedAt())
                .commentCount(article.getCommentCount())
                .build();
    }
}