package com.example.capstone1.Service;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
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



    //end point 4
    public ArrayList<Product> getTop3SellingProducts() {


        ArrayList<Product> copy = new ArrayList<>(products);


        for (int i = 0; i < copy.size() - 1; i++) {
            for (int j = i + 1; j < copy.size(); j++) {
                if (copy.get(j).getSoldCount() > copy.get(i).getSoldCount()) {

                    Product temp = copy.get(i);
                    copy.set(i, copy.get(j));
                    copy.set(j, temp);
                }
            }
        }

        if (copy.size() <= 3) {
            return copy;
        }

        ArrayList<Product> top3 = new ArrayList<>();
        top3.add(copy.get(0));
        top3.add(copy.get(1));
        top3.add(copy.get(2));

        return top3;
    }
    public Double getAverageRating(String productId) {
        for (Product p : products) {
            if (p.getId().equals(productId)) {
                if (p.getRatingCount() == 0)
                    return 0.0;
                return p.getTotalRating() / p.getRatingCount();
            }
        }
        return null;
    }

    //endpoint 5
    public ArrayList<Product> getProductsByCategoryAndPrice(String categoryId, double maxPrice) {
        ArrayList<Product> result = new ArrayList<>();

        for (Product p : products) {
            if (p.getCategoryID().equalsIgnoreCase(categoryId) && p.getPrice() <= maxPrice) {
                result.add(p);
            }
        }

        return result;
    }

}
