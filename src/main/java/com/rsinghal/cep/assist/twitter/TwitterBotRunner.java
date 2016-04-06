package com.rsinghal.cep.assist.twitter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TwitterBotRunner {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");

		TwitterBot obj12 = (TwitterBot) context
				.getBean("twitterbot");
		obj12.start();
	}

}
