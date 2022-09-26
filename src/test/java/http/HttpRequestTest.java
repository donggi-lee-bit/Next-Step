package http;

import static org.assertj.core.api.Assertions.assertThat;

import http.request.HttpMethod;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

    private String testDirectory = "./src/test/resources/";

    private HttpRequest httpRequest;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        InputStream in = new FileInputStream(
            new File(testDirectory + "Http_GET.txt"));
        httpRequest = new HttpRequest(in);
    }

    @Test
    void request_GET() throws FileNotFoundException {
        assertThat(HttpMethod.GET).isEqualTo(httpRequest.getMethod());
        assertThat("/user/create").isEqualTo(httpRequest.getPath());
        assertThat("keep-alive").isEqualTo(httpRequest.getHeader("Connection"));
        assertThat("javajigi").isEqualTo(httpRequest.getParameter("userId"));
    }
}
