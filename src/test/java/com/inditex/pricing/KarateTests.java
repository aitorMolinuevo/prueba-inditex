package com.inditex.pricing;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class KarateTests {

    @Karate.Test
    Karate testAll() {
        return Karate.run("classpath:karate");
    }
}
