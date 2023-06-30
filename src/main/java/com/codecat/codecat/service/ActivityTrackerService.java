package com.codecat.codecat.service;

import com.codecat.codecat.model.ActivityTracker;
import com.codecat.codecat.repository.ActivityTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityTrackerService {

    @Autowired
    ActivityTrackerRepository activityTrackerRepository;

    public ActivityTracker createActivity(ActivityTracker activityTracker) {
        return activityTrackerRepository.save(activityTracker);
    }

    public List<ActivityTracker> getActivityTrackerList() {
        return activityTrackerRepository.findAll();
    }
}
