package practice.mvcstarter.domain.boards;

import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/13
 */
public class BoardTopicConverter implements AttributeConverter<BoardTopic, String> {
    @Override
    public String convertToDatabaseColumn(BoardTopic attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public BoardTopic convertToEntityAttribute(String dbData) {
        if (!StringUtils.hasText(dbData)) {
            return null;
        }
        return Stream.of(BoardTopic.values())
                .filter(c -> c.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
