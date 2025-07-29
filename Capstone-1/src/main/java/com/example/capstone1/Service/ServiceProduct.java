package com.example.capstone1.Service;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class ServiceProduct {

    private final ServiceCategory serviceCategory;

    ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> GetAllProduct() {
        return products;
    }

    public boolean AddProduct(Product product) {

        if (!isCategoryExist(product.getCategoryID())) {
            return false;
        }

        products.add(product);
        return true;
    }

    public boolean isCategoryExist(String categoryId) {
        for (Category c : serviceCategory.GetAllCategory()) {
            if (c.getId().equalsIgnoreCase(categoryId)) {
                return true;
            }
        }
        return false;
    }

    public boolean updateProduct(String id, Product product) {
        for (int i = 0; i < products.size(); i++) {

            if (products.get(i).getId().equals(id)) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    public boolean DeleteProduct(String id) {
        for (int i = 0; i < products.size(); i++) {

            if (products.get(i).getId().equalsIgnoreCase(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public Product getProductById(String id) {
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    // end point 1

    public boolean rateProduct(String productId, double rating) {

        if (rating < 0 || rating > 5)
            return false;

        for (Product p : products) {
            if (p.getId().equals(productId)) {
                p.setTotalRating(p.getTotalRating() + rating);
                p.setRatingCount(p.getRatingCount() + 1);
                return true;
            }
        }
        return false;
    }

    public Double getAverageRating(String productId) {
        for (Product p : products) {
            if (p.getId().equals(productId)) {
                if (p.getRatingCount() == 0) return 0.0;
                return p.getTotalRating() / p.getRatingCount();
            }
        }
        return null;
    }

    //endPoint5

    public Product getRandomProduct() {
        if (products == null || products.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(products.size());
        return products.get(index);
    }

}
