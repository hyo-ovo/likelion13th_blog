package likelion13th.blog.exception;

import jakarta.persistence.EntityNotFoundException;
import likelion13th.blog.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //추가
    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ApiResponse> handleArticleNotFoundException(ArticleNotFoundException ex){
        return buildErrorResponse(HttpStatus.NOT_FOUND,ex.getMessage());
    }

    //추가
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ApiResponse> handleCommentNotFoundException(CommentNotFoundException ex){
        return buildErrorResponse(HttpStatus.NOT_FOUND,ex.getMessage());
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ApiResponse> handlePermissionDeniedException(PermissionDeniedException ex){
        return buildErrorResponse(HttpStatus.FORBIDDEN,ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerEntityNotFoundException(EntityNotFoundException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    private ResponseEntity<ApiResponse> buildErrorResponse(HttpStatus status,String message){
        ApiResponse response=new ApiResponse(false,status.value(),message);
        return ResponseEntity
                .status(status)
                .body(response);
    }
}