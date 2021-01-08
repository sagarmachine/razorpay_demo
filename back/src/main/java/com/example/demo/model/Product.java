package com.example.demo.model;

import java.util.HashMap;

public class Product implements  ProdStruct{




    static HashMap<String, Integer> discounts;
    static Integer id;
    String productId;
    String productName;
    String productType;
    Double productPrice;
    Integer productQuantity;


    static {
        id=100;
        discounts.put("ELECTRONIC GADGETS",12);
        discounts.put("CLOTHINGS",8);
        discounts.put("GROCERIES",8);
        discounts.put("FOOTWEARS",10);
        discounts.put("Furniture",15);

    }

    public Product(String productName, String productType, Double productPrice, Integer productQuantity) {
        this.productId = "P"+(++id);
        this.productName = productName;
        this.productType = productType;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;

    }



    public static Integer getId() {
        return id;
    }

    public static void setId(Integer id) {
        Product.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }


    public float calDiscount(){

        if(!validateProductType(this.productType))
            throw  new RuntimeException("Invalid Product Type");

        return  ((this.productPrice.floatValue())*discounts.get(this.productType.toUpperCase()))/100;

    }

    public boolean validateProductType(String type){

        for(String t:productList){
            if(type.equalsIgnoreCase(t))
                return true;
        }

        return false;

    }


    @Override
    public Double calculateTotalPrice() {

        float discount=calDiscount();
        return (this.productPrice-discount)*this.productQuantity;

    }
}
