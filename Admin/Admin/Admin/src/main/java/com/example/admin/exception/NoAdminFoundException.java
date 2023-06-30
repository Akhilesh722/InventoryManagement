package com.example.admin.exception;

@SuppressWarnings("serial")
public class NoAdminFoundException extends RuntimeException{
 public NoAdminFoundException(String msg ) {
	 super(msg);
 }
}
