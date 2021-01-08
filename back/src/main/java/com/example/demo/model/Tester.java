package com.example.demo.model;

public class Tester {

    public static void main(String[] args) {

    }

   void workHere(String name , String type, Double price, Integer quantity){

        Product product1 = new Product(name, type, price, quantity);

       try{
           System.out.println("Total Price - "+( product1.calculateTotalPrice()));
           System.out.println("Product Id - "+product1.getProductId());
       }catch (Exception exception){
           System.out.println(exception.getMessage());
       }

   }
}
