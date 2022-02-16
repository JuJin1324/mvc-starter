package practice.mvcstarter.domain.members.exception;


import practice.mvcstarter.global.error.exception.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {

    public MemberNotFoundException(Long target) {
        super(target + " is not found");
    }
}
