package com.slack.controller;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.slack.model.User;
import com.slack.service.NotificationService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/notification-service")
public class NotificationController {
	long ttlInMilliSeconds = 60000*24;
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private DirectExchange directExchange;
	private String tokenString;
	

	
	@GetMapping("/receive-notification")
	public String receiveNotificaion() {
		tokenString = new String(rabbitTemplate.receive("directQueue").getBody());
		Claims tokenData =NotificationService.decodeJWT(tokenString);

		
		String userMail = (tokenData.get("iss").toString());
		//User user = notificationService.getUserByUserMail(userMail);
		System.out.println(tokenString);
		return userMail;
	}
	
	
	

}
