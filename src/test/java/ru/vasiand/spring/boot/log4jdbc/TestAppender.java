package ru.vasiand.spring.boot.log4jdbc;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.OutputStreamAppender;

import java.io.ByteArrayOutputStream;

public class TestAppender extends OutputStreamAppender<ILoggingEvent> {

    private volatile ByteArrayOutputStream baos;

    @Override
    public void start() {
        baos = new ByteArrayOutputStream();
        setOutputStream(baos);
        super.start();
    }

    @Override
    public ByteArrayOutputStream getOutputStream() {
        return baos;
    }
}
