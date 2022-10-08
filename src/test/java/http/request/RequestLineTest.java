package http.request;

import static http.request.HttpMethod.GET;
import static http.request.HttpMethod.POST;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RequestLineTest {

    @Test
    void create_get_method() {
        RequestLine line = new RequestLine("GET /index.html HTTP/1.1");
        assertThat(GET).isEqualTo(line.getMethod());
        assertThat("/index.html").isEqualTo(line.getPath());
    }

    @Test
    void create_post_method() {
        RequestLine line = new RequestLine("POST /index.html HTTP/1.1");
        assertThat(POST).isEqualTo(line.getMethod());
        assertThat("/index.html").isEqualTo(line.getPath());
    }

    @Test
    void create_path_and_params() {
        RequestLine line = new RequestLine(
            "GET /user/create?userId=donggi&password=test HTTP/1.1");

        assertThat(GET).isEqualTo(line.getMethod());
        assertThat("/user/create").isEqualTo(line.getPath());
        assertThat("userId=donggi&password=test").isEqualTo(line.getQueryString());
    }
}
