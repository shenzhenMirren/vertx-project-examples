package github.mirrentools.app.test;

import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import github.mirrentools.core.enums.ResultState;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ExtendWith(VertxExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClassesTest {
  WebClient webClient;
  /**
   * 测试的id
   */
  String testId = "1";

  @BeforeEach
  @DisplayName("Start test init...")
  void start(VertxTestContext testContext) {
    Vertx vertx = Vertx.vertx();
    try {
      String root = System.getProperty("user.dir");
      Path path = Paths.get(root, "data", "config.json");
      byte[] bs = Files.readAllBytes(path);
      JsonObject config = new JsonObject(new String(bs));
      webClient = WebClient.create(vertx, new WebClientOptions().setDefaultHost("127.0.0.1").setDefaultPort(config.getInteger("httpPort")));
      testContext.completeNow();
    } catch (Exception e) {
      testContext.failNow(e);
    }
  }

  @Test
  @Order(1)
  @DisplayName("Test find")
  void testFind(VertxTestContext testContext) {
    Future<HttpResponse<Buffer>> future = webClient.get("/private/api/Classes/find")
      .addQueryParam(Constant.USER_ID_KEY, Constant.USER_ID_VALUE)
      .addQueryParam(Constant.USER_TOKEN_KEY, Constant.USER_TOKEN_VALUE)
      .send();
    future
      .onSuccess(resp -> {
        try {
          JsonObject body = resp.bodyAsJsonObject();
          System.out.println("test find response: ");
          System.out.println(body);
          if (body.getInteger("code") == ResultState.SUCCESS.getCode()) {
            JsonArray data = body.getJsonArray("data");
            testContext.verify(() -> {
              if (data.size() >= 0) {
                testContext.completeNow();
              } else {
                testContext.failNow("获取所有数据-->测试失败: 返回的结果无效");
              }
            });
          } else {
            testContext.failNow("无效的响应状态码");
          }
        } catch (Exception e) {
          testContext.failNow("响应数据无效");
        }
      })
      .onFailure(testContext::failNow);
  }

  @Test
  @Order(2)
  @DisplayName("Test save")
  void testSave(VertxTestContext testContext) {
    MultiMap multiMap = MultiMap.caseInsensitiveMultiMap();
    multiMap.add("id", "1");
    multiMap.add("classRooms", "1");
    Future<HttpResponse<Buffer>> future = webClient.post("/private/api/Classes/save")
      .addQueryParam(Constant.USER_ID_KEY, Constant.USER_ID_VALUE)
      .addQueryParam(Constant.USER_TOKEN_KEY, Constant.USER_TOKEN_VALUE)
      .sendForm(multiMap);
    future
      .onSuccess(resp -> {
        try {
          JsonObject body = resp.bodyAsJsonObject();
          System.out.println("test save response: ");
          System.out.println(body);
          if (body.getInteger("code") == ResultState.SUCCESS.getCode()) {
            int data = body.getInteger("data");
            testContext.verify(() -> {
              if (data == 1) {
                testContext.completeNow();
              } else {
                testContext.failNow("保存数据-->测试失败: 返回的结果不等于 1");
              }
            });
          } else {
            testContext.failNow("无效的响应状态码");
          }
        } catch (Exception e) {
          testContext.failNow("响应数据无效");
        }
      })
      .onFailure(testContext::failNow);
  }


  @Test
  @Order(3)
  @DisplayName("Test Get")
  void testGet(VertxTestContext testContext) {
    Future<HttpResponse<Buffer>> future = webClient.get("/private/api/Classes/get")
      .addQueryParam(Constant.USER_ID_KEY, Constant.USER_ID_VALUE)
      .addQueryParam(Constant.USER_TOKEN_KEY, Constant.USER_TOKEN_VALUE)
      .addQueryParam("id",testId)
      .send();
    future
      .onSuccess(resp -> {
        try {
          JsonObject body = resp.bodyAsJsonObject();
          System.out.println("test get response: ");
          System.out.println(body);
          if (body.getInteger("code") == ResultState.SUCCESS.getCode()) {
            JsonObject data = body.getJsonObject("data");
            testContext.verify(() -> {
              if (!data.isEmpty()) {
                testContext.completeNow();
              } else {
                testContext.failNow("通过id获取指定数据-->测试失败: 返回的结果为空");
              }
            });
          } else {
            testContext.failNow("无效的响应状态码");
          }
        } catch (Exception e) {
          testContext.failNow("响应数据无效");
        }
      })
      .onFailure(testContext::failNow);
  }

  @Test
  @Order(4)
  @DisplayName("Test delete")
  void testDelete(VertxTestContext testContext) {
    MultiMap multiMap = MultiMap.caseInsensitiveMultiMap();
    multiMap.add("id", testId);
    Future<HttpResponse<Buffer>> future = webClient.post("/private/api/Classes/delete")
      .addQueryParam(Constant.USER_ID_KEY, Constant.USER_ID_VALUE)
      .addQueryParam(Constant.USER_TOKEN_KEY, Constant.USER_TOKEN_VALUE)
      .sendForm(multiMap);
    future
      .onSuccess(resp -> {
        try {
          JsonObject body = resp.bodyAsJsonObject();
          System.out.println("test delete response: ");
          System.out.println(body);
          if (body.getInteger("code") == ResultState.SUCCESS.getCode()) {
            int data = body.getInteger("data");
            testContext.verify(() -> {
              if (data == 1) {
                testContext.completeNow();
              } else {
                testContext.failNow("通过id删除-->测试失败: 返回的结果不等于 1");
              }
            });
          } else {
            testContext.failNow("无效的响应状态码");
          }
        } catch (Exception e) {
          testContext.failNow("响应数据无效");
        }
      })
      .onFailure(testContext::failNow);
  }
}

