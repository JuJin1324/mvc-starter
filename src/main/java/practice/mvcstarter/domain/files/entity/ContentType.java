package practice.mvcstarter.domain.files.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/19
 */

@AllArgsConstructor
@Getter
public enum ContentType {
    IMAGE_PNG("image/png"),
    IMAGE_JPEG("image/jpeg"),
    PLAIN_TEXT("plain/text"),
    ETC("*/*");

    @JsonValue
    private final String value;

    public static String getExtension(ContentType contentType) {
        if (contentType == IMAGE_PNG) {
            return "png";
        } else if (contentType == IMAGE_JPEG) {
            return "jpg";
        } else if (contentType == PLAIN_TEXT) {
            return "txt";
        } else {
            return "etc";
        }
    }

    public static ContentType getValueOf(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("value is empty.");
        }
        switch (value) {
            case "image/png":
                return IMAGE_PNG;
            case "image/jpeg":
                return IMAGE_JPEG;
            case "plain/text":
                return PLAIN_TEXT;
            default:
                return ETC;
        }
    }
}
