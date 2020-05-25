package dse.grp20.statustracking.service.impl;

import dse.grp20.common.dto.NearCrashEventDTO;
import dse.grp20.statustracking.entities.NearCrashEvent;
import dse.grp20.statustracking.service.INearCrashEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class NearCrashEventService implements INearCrashEventService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void registerNearCrashEvent(NearCrashEventDTO nce) {
        this.mongoTemplate.save(new NearCrashEvent(nce), "NearCrashEvents");
    }

}
