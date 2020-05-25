package dse.grp20.statustracking.receiver;

import dse.grp20.common.dto.NearCrashEventDTO;
import dse.grp20.statustracking.service.INearCrashEventService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class NearCrashEventReceiver {

    @Autowired
    INearCrashEventService nearCrashEventService;

    @RabbitListener(queues = "nearcrashevent.emit")
    public void updateTrafficLight (NearCrashEventDTO nearCrashEventDTO) {
        this.nearCrashEventService.registerNearCrashEvent(nearCrashEventDTO);
    }
}
