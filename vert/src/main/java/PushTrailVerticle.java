import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;
import io.vertx.ext.web.impl.RouterImpl;

import java.util.Date;

/**
 * @Author: JiuBuKong
 * @Date: 2018/10/11 下午4:51
 */
public class PushTrailVerticle extends AbstractVerticle {


    @Override
    public void start() throws Exception {
        SockJSHandlerOptions sockjsopt = new SockJSHandlerOptions().setHeartbeatInterval(25000);
        //默认25秒，具体查看SockJSHandlerOptions类
        SockJSHandler sockJSHandler = SockJSHandler.create(vertx, sockjsopt);
        //创建路由规则
        Router router = new RouterImpl(vertx);
        BridgeOptions opt = new BridgeOptions();
        opt.setPingTimeout(100000);//默认10秒，具体查看BridgeOptions类
        //设置从客户端发送消息到服务端的地址，根据自己的需要可以创建任意个
        opt.addInboundPermitted(new PermittedOptions().setAddress("chat.to.server"));
        //解决跨域问题
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET).allowedMethod(HttpMethod.OPTIONS)
                .allowedMethod(HttpMethod.POST).allowedHeader("X-PINGARUNER").allowedHeader("Content-Type"));
        router.route().handler(BodyHandler.create().setBodyLimit(-1));
        router.route("/eventbus/*").handler(sockJSHandler.bridge(opt, bridgeEvent -> {
            switch (bridgeEvent.type()) {
                case SOCKET_CREATED:
                    System.out.println(new Date() + ":This event will occur when a new SockJS socket is created.");
                    break;
                case SOCKET_IDLE:
                    System.out.println(new Date() + ":This event will occur when SockJS socket is on idle for longer period of time than initially configured.");
                    break;
                case SOCKET_PING:
                    System.out.println(new Date() + ":This event will occur when the last ping timestamp is updated for the SockJS socket.");
                    break;
                case SOCKET_CLOSED:
                    System.out.println(new Date() + ":This event will occur when a SockJS socket is closed.");
                    break;
                case SEND:
                    System.out.println(new Date() + ":This event will occur when a message is attempted to be sent from the client to the server.");
                    break;
                case PUBLISH:
                    System.out.println(new Date() + ":This event will occur when a message is attempted to be published from the client to the server.");
                    break;
                case RECEIVE:
                    System.out.println(new Date() + ":This event will occur when a message is attempted to be delivered from the server to the client.");
                    break;
                case REGISTER:
                    System.out.println(new Date() + ":This event will occur when a client attempts to register a handler.");
                    break;
                case UNREGISTER:
                    System.out.println(new Date() + ":This event will occur when a client attempts to unregister a handler.");
                    break;
                default:
                    break;
            }
            //设置为true，可以处理任何在eventbus上的事件
            bridgeEvent.complete(true);
        }));
        String data = "212121";
        //创建一个eventbus，用来数据通讯
        EventBus eventBus = vertx.eventBus();
        //HttpServerOptions httpopt=new HttpServerOptions().setMaxWebsocketFrameSize(Integer.parseInt(PropertiesUtil.getProperties("common", "maxWebsocketFrameSize")));//设置数据量的大小，在数据量小的时候，这个值可以不用设置
        HttpServerOptions httpopt = new HttpServerOptions();//设置数据量的大小，在数据量小的时候，这个值可以不用设置
        HttpServer server = vertx.createHttpServer(httpopt);
        server.requestHandler(router::accept).listen(8080, res -> {
            if (res.succeeded()) {
                System.out.println("服务开启成功！");
            } else {
                System.out.println("服务开启失败");
            }
        });
        //设置数据推送出去的时间限制
        DeliveryOptions deliveryOptions = new DeliveryOptions(new JsonObject().put("timeout", 10000));
        //注册地址,然后对接收到客户端来的消息进行处理
        eventBus.consumer("chat.to.server", message -> {
            System.out.println(new Date() + ":客户端发往服务端的消息内容为:" + message.body().toString());

            eventBus.publish("chat.to.server", data, deliveryOptions);
            System.out.println(new Date() + ":数据发布出去的时间");
        });
        //周期性推送数据
        vertx.setPeriodic(10000, timerID -> {
            eventBus.publish("chat.to.server", data, deliveryOptions);
            System.out.println(new Date() + ":推送完毕");
        });

    }
}
