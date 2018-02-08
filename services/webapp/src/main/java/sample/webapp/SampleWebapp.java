package sample.webapp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

/**
 * @author Thomas Segismont
 */
public class SampleWebapp extends AbstractVerticle {

  private static final String GREETER_SERVICE_HOST = System.getenv().getOrDefault("GREETER_SERVICE_HOST", "localhost");
  private static final String GREETER_SERVICE_PORT = System.getenv().getOrDefault("GREETER_SERVICE_PORT", "8081");
  private static final String GREETER_SERVICE_PATH = System.getenv().getOrDefault("GREETER_SERVICE_PATH", "");
  private static final String NAME_SERVICE_HOST = System.getenv().getOrDefault("NAME_SERVICE_HOST", "localhost");
  private static final String NAME_SERVICE_PORT = System.getenv().getOrDefault("NAME_SERVICE_PORT", "8082");
  private static final String NAME_SERVICE_PATH = System.getenv().getOrDefault("NAME_SERVICE_PATH", "");

  private WebClient webClient;

  @Override
  public void start() throws Exception {
    webClient = WebClient.create(vertx);

    vertx.createHttpServer().requestHandler(request -> {
      String greeter = "http://"
          + GREETER_SERVICE_HOST
          + ':' + GREETER_SERVICE_PORT
          + '/' + GREETER_SERVICE_PATH
          + "?greet=" + request.getParam("greet");
      String name = "http://"
          + NAME_SERVICE_HOST
          + ':' + NAME_SERVICE_PORT
          + '/' + NAME_SERVICE_PATH
          + "?id=" + request.getParam("id");
      System.out.println("greeter = " + greeter);
      System.out.println("name = " + name);

      CompositeFuture.all(get(greeter), get(name)).setHandler(ar -> {
        if (ar.succeeded()) {
          CompositeFuture future = ar.result();
          request.response().end(future.<String>resultAt(0) + ' ' + future.<String>resultAt(1));
        } else {
          ar.cause().printStackTrace();
          request.response().setStatusCode(500).end();
        }
      });

    }).listen(8080);
  }

  private Future<String> get(String url) {
    Future<HttpResponse<String>> future = Future.future();
    webClient.getAbs(url).as(BodyCodec.string()).send(future);
    return future.map(HttpResponse::body);
  }
}
