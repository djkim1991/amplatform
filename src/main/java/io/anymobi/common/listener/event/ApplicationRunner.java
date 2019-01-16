package io.anymobi.common.listener.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements ApplicationListener<MyEvent> {

    @Override
    public void onApplicationEvent(MyEvent event) {

    }
}
