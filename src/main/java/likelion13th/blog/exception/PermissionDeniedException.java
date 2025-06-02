package likelion13th.blog.exception;

public class PermissionDeniedException extends RuntimeException{

    //생성자
    public PermissionDeniedException(String message) {
        super(message);
    }

}