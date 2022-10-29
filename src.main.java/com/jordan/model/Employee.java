package com.jordan.model;

import lombok.Data;

//Model -> has POJO and business logic

public class Employee {

	public Employee(int id, String name, String password) {
		// TODO Auto-generated constructor stub
		this.empId = id;
		this.empName = name;
		this.password = password;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private int empId;
	private String empName;
	private String password;

	public boolean validate() {
		if (empName.equals("Jordan") && password.equals("123")) {
			return true;
		} else {
			return false;
		}
	}

}