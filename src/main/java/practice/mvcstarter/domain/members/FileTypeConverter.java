package practice.mvcstarter.domain.members;

import org.springframework.util.StringUtils;
import practice.mvcstarter.domain.files.ContentType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/13
 */
public class FileTypeConverter implements AttributeConverter<FileType, String> {
    @Override
    public String convertToDatabaseColumn(FileType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public FileType convertToEntityAttribute(String dbData) {
        if (!StringUtils.hasText(dbData)) {
            return null;
        }
        return Stream.of(FileType.values())
                .filter(c -> c.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
