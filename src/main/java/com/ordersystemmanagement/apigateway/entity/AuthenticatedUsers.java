package com.ordersystemmanagement.apigateway.entity;


import java.util.ArrayList;

public class AuthenticatedUsers {
   public static ArrayList<User> authenticatedUsers;

   public static User findByUserName(String userName){

       for (User user:
           authenticatedUsers ) {
           if (user.getEmail().equals(userName)){
               return user;
           }

       };
       return null;
   }
   }



