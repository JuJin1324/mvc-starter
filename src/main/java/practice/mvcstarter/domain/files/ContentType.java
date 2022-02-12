package practice.mvcstarter.domain.files;

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
    ETC("*/*")
    ;

    private final String value;

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
