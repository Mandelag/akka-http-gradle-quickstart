/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.example.app;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.Assert;
import org.junit.Test;

public class AppTest extends JUnitRouteTest {

  @Test
  public void testhello() {
    TestRoute appRoute = testRoute(App.appRoute());
    appRoute.run(HttpRequest.GET("/"))
        .assertStatusCode(200)
        .assertEntity("Hello, World!");
//    Assert.assertEquals(5, 5);
  }
}
