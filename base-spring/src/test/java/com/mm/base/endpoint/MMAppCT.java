package com.mm.base.endpoint;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.mm.base.endpoint.config.AppConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class MMAppCT {

    @Autowired
    private WebApplicationContext context;

    @Before
    public void init() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    @After
    public void shutdown() {
        RestAssuredMockMvc.reset();
    }

    @Test
    public void canRunApp() {
        new Thread(() -> {
            MMApp.run("app", AppConfig.class);
        }
        ).start();
    }

    @Test
    public void canRunAppFromMain() {
        new Thread(() -> {
            MMApp.main(new String[]{"app", "AppConfig"});
        }
        ).start();
    }

    @Test
    public void canAcessController() {
        when().get("/status").
                then().
                statusCode(200).
                body(equalTo("ok")).log();
    }

}