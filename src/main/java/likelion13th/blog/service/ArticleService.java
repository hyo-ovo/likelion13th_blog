package likelion13th.blog.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import likelion13th.blog.domain.Article;
import likelion13th.blog.dto.*;
import likelion13th.blog.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    //게시글 생성
    public ArticleResponse addArticle(AddArticleRequest request){
        //1. request 객체의 .toEntity()를 통해 Article 객체 생성
        Article article=request.toEntity();

        //2. Article객체를 JPA의 save() 를 사용하여 DB에 저장
        articleRepository.save(article);

       /* 3. article 객체를 response DTO 생성하여 반환
          response 클래스의 정작 팩토리 메서드 of() 통해 API 응답객체 생성 */
        return ArticleResponse.of(article);
    }


    //전체 글 조회
    public List<SimpleArticleResponse> getAllArticles(){
        /*1. JPA의 findAll() 을 사용하여 DB에 저장된 전체 Article을 List 형태로 가져오기*/
        List<Article> articleList = articleRepository.findAll();

        /*2. Article -> SimpleArticleResponse : 엔티티를 DTO로 변환*/
        List<SimpleArticleResponse> articleResponseList = articleList.stream()
                .map(article -> SimpleArticleResponse.of(article))
                .toList();

        /*3. articleResponseList (DTO 리스트) 반환 */
        return articleResponseList;
    }

    //단일 글 조회
    public ArticleResponse getArticle(Long id){
        /* 1. JPA의 findById()를 사용하여 DB에서 id가 일치하는 게시글 찾기.
              id가 일치하는 게시글이 DB에 없으면 에러 반환*/
        Article article=articleRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 ID의 게시글을 찾을 수 없습니다. ID: "+id));

        /*2. ArticleResponse DTO 생성하여 반환 */
        return ArticleResponse.of(article);
    }

    // 글 수정
    @Transactional
    public ArticleResponse updateArticle(Long id, UpdateArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다."));

        if(!article.getPassword().equals(request.getPassword())){
            throw new RuntimeException("해당 글에 대한 수정 권한이 없습니다.");
        }

        article.update(request.getTitle(), request.getContent());
        article = articleRepository.save(article);

        return ArticleResponse.of(article);
    }

    // 글 삭제
    @Transactional
    public void deleteArticle(Long id, DeleteRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 id의 게시글을 찾을 수 없습니다."));

        if(!request.getPassword().equals(article.getPassword())){
            throw new RuntimeException("해당 글에 대한 삭제 권한이 없습니다.");
        }
        articleRepository.deleteById(id);
    }
}