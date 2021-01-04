package com.genx.request;

import java.util.Set;
import lombok.Data;

@Data
public class SignUpForm {

	private String name;

	private String username;

	private String email;

	private Set<String> role;

	private String password;

	private String salary;

	private String age;

}