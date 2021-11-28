package practice.mvcstarter.web.controllers.teams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/17
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSingleTeamResBody {
    private Long   teamId;
    private String teamName;
}
