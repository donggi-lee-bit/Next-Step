package http.response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.junit.jupiter.api.Test;

class HttpResponseTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    void responseForward() throws FileNotFoundException {
        // Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");
    }

    @Test
    void responseRedirect() throws FileNotFoundException {
        // Http_Redirect.txt 결과는 응답 header에
        // Location 정보가 /index.html로 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");
    }

    @Test
    void responseCookies() throws FileNotFoundException {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "Logined=true");
        response.sendRedirect("/index.html");
    }

    private FileOutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}
