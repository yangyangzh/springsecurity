package com.yyzhai.resource.server.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

	@RequestMapping("/api/resource")
	public Object getUserInfo() {

		String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = user + "@test.com";
		return email;
	}

}
