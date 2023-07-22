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

    public List<ActivityTracker> getActivityTrackerByAssessmentId(Long assessmentId) {
        return activityTrackerRepository.findByAssessmentId(assessmentId);
    }

    public List<ActivityTracker> getActivityTrackerStudent(String email) {
        return activityTrackerRepository.findByEmail(email);
    }

    public ActivityTracker updateActivityTracker(Long id, ActivityTracker activityTracker) {
        ActivityTracker activityTrackerExist = activityTrackerRepository.findById(id).orElseThrow(null);
        if (activityTrackerExist != null) {
            activityTrackerExist.setFeedback(activityTracker.getFeedback());
            activityTrackerRepository.save(activityTrackerExist);
        }
        return activityTrackerExist;
    }

    public List<ActivityTracker> getActivityTrackerListByAssessmentIdAndEmail(Long assessmentId, String email){
        return activityTrackerRepository.findByAssessmentIdAndEmail(assessmentId, email);
    }
}
