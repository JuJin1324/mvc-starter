package practice.mvcstarter.external.service.send;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/25
 */
public interface SmsSendClient {

    /**
     * 메시지 전송
     */
    void sendMessage(String phoneNumber, String message);
}
