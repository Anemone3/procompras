package com.procompras.engine;

public record ProductScrapping(String name, String description, String price, String realPrice, String image,
                               String category, String market, Boolean haveDiscount, String discountPercentage,
                               String volumes) {
}