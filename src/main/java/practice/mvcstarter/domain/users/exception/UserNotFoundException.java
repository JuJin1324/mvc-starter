package practice.mvcstarter.domain.users.exception;


import practice.mvcstarter.global.error.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(Long target) {
        super(target + " is not found");
    }
}
