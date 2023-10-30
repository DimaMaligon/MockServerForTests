import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import utils.SimpleDispatcher;
import utils.SimpleResponseInterceptor;

import java.io.IOException;
import java.net.InetAddress;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExampleTest {
    Dispatcher simpleDispatcher;
    MockWebServer mockServer;

    @BeforeAll
    void start() throws IOException {
        simpleDispatcher = new SimpleDispatcher();
        mockServer = new MockWebServer();
        InetAddress ip = InetAddress.getByName("127.0.0.2");
        int port = 8088;
        mockServer.setDispatcher(simpleDispatcher);
        mockServer.start(ip, port);
    }

    @Test
    public void testMockResponseGet() {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .url(mockServer.url("http://127.0.0.2:8088/get"))
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.code());
            System.out.println(response.body().string());
        } catch (NullPointerException e) {
            throw new NullPointerException("The response is empty");
        } catch (IOException e) {
            throw new RuntimeException("The error with request from testMockResponseGet", e);
        }

    }

    @Test
    public void testReplaceResponseGet() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SimpleResponseInterceptor(" body request", 200, "HEAD"))
                .build();

        Request request = new Request.Builder()
                .url("https://reqres.in/api/users")
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.code());
            System.out.println(response.body().string());
        } catch (NullPointerException e) {
            throw new NullPointerException("The response is empty");
        } catch (IOException e) {
            throw new RuntimeException("The error with request from testReplaceResponseGet", e);
        }
    }

    @AfterAll
    void finish() throws IOException {
        mockServer.shutdown();
    }
}
