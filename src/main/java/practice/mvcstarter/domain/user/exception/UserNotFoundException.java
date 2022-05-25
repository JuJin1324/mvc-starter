package practice.mvcstarter.domain.user.exception;


import practice.mvcstarter.global.error.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(Long target) {
        super(target + " is not found");
    }
}
