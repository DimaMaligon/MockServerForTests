package utils;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SimpleResponseInterceptor implements Interceptor {
    private MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private String body = "";
    private int code = 200;
    private String header = "";

    public SimpleResponseInterceptor(String body, int code, String header) {
        this.body = body;
        this.code = code;
        this.header = header;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) {
        try {
            Response response = chain.proceed(chain.request());
            return response.newBuilder()
                    .header("User-agent", header)
                    .body(ResponseBody.create(body, JSON))
                    .code(code)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("The error with response interception", e);
        }
    }
}
