package com.codecat.codecat.controller;

import com.codecat.codecat.model.ActivityTracker;
import com.codecat.codecat.service.ActivityTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activityTracker")
@CrossOrigin("*")
public class ActivityTrackerController {

    @Autowired
    ActivityTrackerService activityTrackerService;

    @PostMapping("create")
    private ResponseEntity<ActivityTracker> createActivityTracker(@RequestBody ActivityTracker activityTracker) {
        ActivityTracker createdActivityTracker = activityTrackerService.createActivity(activityTracker);
        return ResponseEntity.ok(createdActivityTracker);
    }

    @GetMapping("/list")
    private ResponseEntity<List<ActivityTracker>> getActivityTrackerList() {
        List<ActivityTracker> activityTrackerList = activityTrackerService.getActivityTrackerList();
        return ResponseEntity.ok(activityTrackerList);
    }

//    @PutMapping("/markTestCaseAsAnswered")
//    private ResponseEntity<String> markTestCaseAsAnswered(@RequestBody ActivityTrackerDto request) {
//        activityTrackerService.markTestCaseAsAnswered(request);
//        return ResponseEntity.ok("Changed to true with user ID: " + request.getEmail() + " test case ID: " + request.getTestCaseId());
//    }
//
//    @GetMapping
//    public ResponseEntity<ActivityTracker> getActivityTracker(@RequestParam("email") String email, @RequestParam("testCaseId") Long testCaseId) {
//        ActivityTracker activityTracker = activityTrackerService.getActivityTrackerByUserIdAndTestCase(email, testCaseId);
//        return ResponseEntity.ok(activityTracker);
//    }
}
