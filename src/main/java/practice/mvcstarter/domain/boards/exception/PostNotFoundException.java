package practice.mvcstarter.domain.boards.exception;


import practice.mvcstarter.global.error.exception.EntityNotFoundException;

public class PostNotFoundException extends EntityNotFoundException {

    public PostNotFoundException(Long target) {
        super(target + " is not found");
    }
}
