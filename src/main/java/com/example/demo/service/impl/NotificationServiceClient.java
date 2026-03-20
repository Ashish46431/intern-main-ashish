package com.example.demo.service.impl;

import com.example.demo.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceClient {

    private final RestTemplate restTemplate;

    @Value("${external.service.url}")
    private String baseUrl;

    public NotificationDto createNotificationSms(String channel,Long employeeId) {
        Map<String,String> map = new HashMap<>();
        map.put("tovalue","user");
        map.put("message","notification testing");
        map.put("channel",channel);
        map.put("from",employeeId.toString());
        ResponseEntity<NotificationDto> result= restTemplate.postForEntity(baseUrl+"/notification/notify/sms",map,NotificationDto.class);
        return result.getBody();
    }

    public NotificationDto createNotificationPush(String channel, Long employeeId) {
        Map<String, String> map = new HashMap<>();
        map.put("tovalue", "user");
        map.put("message", "notification testing");
        map.put("channel", channel);
        map.put("from", employeeId.toString());
        ResponseEntity<NotificationDto> result = restTemplate.postForEntity(baseUrl + "/notification/notify/push", map, NotificationDto.class);
        return result.getBody();
    }
}
