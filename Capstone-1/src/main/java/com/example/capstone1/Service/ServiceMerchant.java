package com.example.capstone1.Service;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@RequiredArgsConstructor
@Service
public class ServiceMerchant {

    ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> GetAllMerchant() {
        return merchants;
    }

    public void AddMerchant(Merchant merchant) {
        merchants.add(merchant);
    }

    public boolean updateMerchant(String id, Merchant merchant) {
        for (int i = 0; i < merchants.size(); i++) {

            if (merchants.get(i).getId().equals(id)) {
                merchants.set(i, merchant);
                return true;
            }
        }
        return false;
    }

    public boolean DeleteMerchant(String id) {
        for (int i = 0; i < merchants.size(); i++) {

            if (merchants.get(i).getId().equalsIgnoreCase(id)) {
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }

}
