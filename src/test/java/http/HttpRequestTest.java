package http;

import static org.assertj.core.api.Assertions.assertThat;

import http.request.HttpMethod;
import http.request.HttpRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    void request_GET() throws FileNotFoundException {
        InputStream in = new FileInputStream(
            new File(testDirectory + "Http_GET.txt"));
        HttpRequest httpRequest = new HttpRequest(in);
        assertThat(HttpMethod.GET).isEqualTo(httpRequest.getMethod());
        assertThat("/user/create").isEqualTo(httpRequest.getPath());
        assertThat("keep-alive").isEqualTo(httpRequest.getHeader("Connection"));
        assertThat("javajigi").isEqualTo(httpRequest.getParameter("userId"));
    }

    @Test
    void request_POST() throws FileNotFoundException {
        InputStream in = new FileInputStream(
            new File(testDirectory + "Http_POST.txt"));
        HttpRequest httpRequest = new HttpRequest(in);
        assertThat(HttpMethod.POST).isEqualTo(httpRequest.getMethod());
        assertThat("/user/create").isEqualTo(httpRequest.getPath());
        assertThat("keep-alive").isEqualTo(httpRequest.getHeader("Connection"));
        assertThat("javajigi").isEqualTo(httpRequest.getParameter("userId"));
    }
}
