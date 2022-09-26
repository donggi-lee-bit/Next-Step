package http.request;

import java.util.HashMap;
import java.util.Map;
import util.HttpRequestUtils;

public class RequestParams {

    private Map<String, String> params = new HashMap<>();

    public void addBody(String body) {
        putParams(body);
    }

    public String getParameter(String name) {
        return params.get(name);
    }

    public void addQueryString(String queryString) {
        putParams(queryString);
    }

    private void putParams(String data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        params.putAll(HttpRequestUtils.parseQueryString(data));
    }
}
