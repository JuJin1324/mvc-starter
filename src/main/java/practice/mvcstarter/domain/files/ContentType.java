package practice.mvcstarter.domain.files;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

    private final String value;

    public static String getExtention(ContentType contentType) {
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
}
