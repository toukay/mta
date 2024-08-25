package pja.mas.youssef.travelagency;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @EventListener
    public void atStart(ContextRefreshedEvent event) {
        System.out.println("DataInitializer.atStart");
    }
}
