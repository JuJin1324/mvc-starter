package practice.mvcstarter.external.service.send;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/25
 */
public interface EmailSendClient {

    /**
     * HTML 전송
     */
    void sendHtml(String toAddress, String html);
}
