package com.example.capstone1.Service;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class ServiceUser {

    private final ServiceProduct serviceProduct;
    private final ServiceMerchantStock serviceMerchantStock;


    ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> GetAllUser() {
        return users;
    }

    public void AddUser(User user) {
        users.add(user);
    }

    public boolean updateUser(String id, User user) {
        for (int i = 0; i < users.size(); i++) {

            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    public boolean DeleteUser(String id) {
        for (int i = 0; i < users.size(); i++) {

            if (users.get(i).getId().equalsIgnoreCase(id)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public User getUserById(String id) {
        for (User u : users) {
            if (u.getId().equalsIgnoreCase(id)) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<User> getUsersWhoPurchased() {
        ArrayList<User> buyers = new ArrayList<>();
        for (User u : users) {
            if (u.isHasPurchased()) {
                buyers.add(u);
            }
        }
        return buyers;
    }

    public boolean buyProduct(String userId, String productId, String merchantId) {
        User user = getUserById(userId);
        if (user == null)
            return false;

        Product product = null;
        for (Product p : serviceProduct.products) {
            if (p.getId().equalsIgnoreCase(productId)) {
                product = p;
                break;
            }
        }

        MerchantStock stock = null;
        for (MerchantStock ms : serviceMerchantStock.GetAllMerchantStock()) {
            if (ms.getProductId().equalsIgnoreCase(productId) && ms.getMerchantId().equalsIgnoreCase(merchantId)) {
                stock = ms;
                break;
            }
        }

        if (product == null || stock == null)
            return false;
        if (stock.getStock() <= 0)
            return false;

        double finalPrice = product.getPrice();


        if (user.getBalance() < finalPrice)
            return false;

        user.setBalance(user.getBalance() - finalPrice);
        stock.setStock(stock.getStock() - 1);

        product.setSoldCount(product.getSoldCount() + 1);

        int earnedPoints = (int) (finalPrice / 10);
        user.setLoyaltyPoints(user.getLoyaltyPoints() + earnedPoints);
        //
        user.setHasPurchased(true);
        //

        return true;
    }

    public boolean rateProduct(String productId, double rating, String userId) {

        for (int i = 0 ; i<users.size();i++) {
            if (users.get(i).getId().equalsIgnoreCase(userId)) ;
            return true;
        }

        boolean boughtThisProduct = false;
        for (MerchantStock ms : serviceMerchantStock.GetAllMerchantStock()) {
            if (ms.getProductId().equalsIgnoreCase(productId)) {
                    boughtThisProduct = true;
                    break;
                }
            }


        if (!boughtThisProduct) {
            return true;
        }


        for (Product p : serviceProduct.products) {
            if (p.getId().equalsIgnoreCase(productId)) {
                p.setTotalRating(p.getTotalRating() + rating);
                p.setRatingCount(p.getRatingCount() + 1);
                return true;
            }
        }

        return false;
    }


    //endPoint 2
            public boolean redeemPoints (String userId,int pointsToRedeem){

                User user = getUserById(userId);
                if (user == null) {
                    return false;
                }

                if (pointsToRedeem <= 0 || pointsToRedeem > user.getLoyaltyPoints()) {
                    return false;
                }


                double amountToAdd = (pointsToRedeem / 100.0) * 10;

                user.setBalance(user.getBalance() + amountToAdd);

                user.setLoyaltyPoints(user.getLoyaltyPoints() - pointsToRedeem);

                return true;
            }

        }