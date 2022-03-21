package practice.mvcstarter.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/14
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LikedPostSaveDto {
    @NotNull
    private Boolean isLiked;
}
