import io.vertx.core.Vertx;

/**
 * @Author: JiuBuKong
 * @Date: 2018/9/20 下午5:19
 */
public class HelloWorldEmbedded {


    public static void main(String[] args) {
        // Create an HTTP server which simply returns "Hello World!" to each request.
//        Vertx.vertx().createHttpServer().requestHandler(req -> req.response().end("Hello World!")).listen(8080);

        Vertx vertx=Vertx.vertx();
        //部署verticle
        vertx.deployVerticle(PushTrailVerticle.class.getName());
    }
}
