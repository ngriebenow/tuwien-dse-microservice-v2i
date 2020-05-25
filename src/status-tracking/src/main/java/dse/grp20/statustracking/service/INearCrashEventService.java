package dse.grp20.statustracking.service;

import dse.grp20.common.dto.NearCrashEventDTO;

public interface INearCrashEventService {

    void registerNearCrashEvent(NearCrashEventDTO nce);
}
