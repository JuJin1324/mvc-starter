package practice.mvcstarter.domain.base.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import practice.mvcstarter.domain.base.exception.InvalidAccessTimeException;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class TimeBaseEntity {
    @Column(name = "created_date")
    private LocalDateTime createdDateUTC;

    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDateUTC;

    @PrePersist
    public void prePersist() {
        createdDateUTC = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastModifiedDateUTC = LocalDateTime.now();
    }

    public LocalDateTime getCreatedDateKST() {
        if (createdDateUTC == null) {
            throw new InvalidAccessTimeException();
        }
        return ZonedDateTime.of(createdDateUTC, ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
    }

    public LocalDateTime getLastModifiedDateKST() {
        if (lastModifiedDateUTC == null) {
            throw new InvalidAccessTimeException();
        }
        return ZonedDateTime.of(lastModifiedDateUTC, ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
    }
}
