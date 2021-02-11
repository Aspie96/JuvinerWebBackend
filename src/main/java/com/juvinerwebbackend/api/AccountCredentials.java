/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juvinerwebbackend.api;

public class AccountCredentials {
    private String username;
    private String password;
    
    public AccountCredentials() {
        
    }
    
    public AccountCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
}
