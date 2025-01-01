package com.jsp.food.delivery.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.jsp.food.delivery.dto.Customer;
import com.jsp.food.delivery.dto.Restaurant;

import jakarta.mail.internet.MimeMessage;

@Service
public class MyEmailSender {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	TemplateEngine templateEngine;

	public void sendOtp(Customer customer) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom("sujithkumar762002@gmail.com", "Food Delivery App");
			helper.setTo(customer.getEmail());
			helper.setSubject("Otp for Creating Account with Us");

			Context context = new Context();
			context.setVariable("x", customer);

			helper.setText(templateEngine.process("otp-mail.html", context), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mailSender.send(message);

	}

	public void sendOtp(Restaurant restaurant) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom("sujithkumar762002@gmail.com", "Food Delivery App");
			helper.setTo(restaurant.getEmail());
			helper.setSubject("Otp for Creating Account with Us");

			Context context = new Context();
			context.setVariable("x", restaurant);

			helper.setText(templateEngine.process("otp-mail.html", context), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mailSender.send(message);
	}

}
