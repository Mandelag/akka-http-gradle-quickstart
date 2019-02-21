/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.example.app;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.io.IOException;
import java.util.concurrent.CompletionStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static akka.http.javadsl.server.Directives.*;

public class App {

  private static final Logger logger = LoggerFactory.getLogger("Main");

  public static void main(String[] args) throws IOException {

    final String BIND_ADDRESS = System.getenv("BIND_ADDRESS");
    final int BIND_PORT = Integer.parseInt(System.getenv("BIND_PORT"));

    ActorSystem system = ActorSystem.create("routes");

    final Http http = Http.get(system);
    final ActorMaterializer materializer = ActorMaterializer.create(system);

    final Flow<HttpRequest, HttpResponse, NotUsed> routes = appRoute().flow(system, materializer);
    final CompletionStage<ServerBinding> binding = http.bindAndHandle(routes, ConnectHttp.toHost(BIND_ADDRESS, BIND_PORT), materializer);

    logger.info("Server started at {}:{}.\r\nPress any key to stop the server.", BIND_ADDRESS, BIND_PORT);

    System.in.read();

    binding.thenCompose(ServerBinding::unbind)
        .thenAccept(unbound -> system.terminate());
  }

  public static Route appRoute() {
    return path("", () ->
        get(() -> complete("Hello, World!"))
    );
  }
}
