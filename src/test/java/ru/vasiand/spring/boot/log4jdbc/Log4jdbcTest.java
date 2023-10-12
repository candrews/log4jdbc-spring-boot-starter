package ru.vasiand.spring.boot.log4jdbc;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
    classes = TestConfig.class,
    properties = {
        "log4jdbc.dump.sql.addsemicolon=true",
        "log4jdbc.trim.sql.extrablanklines=false",
    }
)
@EnableAutoConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class Log4jdbcTest {

    @Autowired
    private TestRepository testRepository;

    @Test
    @DisplayName("sql statements are logged by log4jdbc")
    public void sqlLogged() {
        final var testAppender = setupTestLogger();
        testRepository.save(new TestEntity("256"));
        final var actual = testAppender.getOutputStream().toString(StandardCharsets.UTF_8);
        assertEquals("INFO select t1_0.id from test_entity t1_0 where t1_0.id='256' ;\nINFO insert into test_entity (id) values ('256') ;\n", actual);
    }

    private TestAppender setupTestLogger() {
        final var lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        final var ple = new PatternLayoutEncoder();
        ple.setPattern("%level %msg%n");
        ple.setContext(lc);
        ple.start();

        final var testAppender = new TestAppender();
        testAppender.setEncoder(ple);
        testAppender.setContext(lc);
        testAppender.start();

        final var sqlonlyLogger = (Logger) LoggerFactory.getLogger("jdbc.sqlonly");
        sqlonlyLogger.addAppender(testAppender);
        sqlonlyLogger.setLevel(Level.INFO);
        sqlonlyLogger.setAdditive(false);

        return testAppender;
    }
}
