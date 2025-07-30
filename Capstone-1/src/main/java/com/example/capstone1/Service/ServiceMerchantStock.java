package com.example.capstone1.Service;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
@RequiredArgsConstructor
@Service
public class ServiceMerchantStock {

private final ServiceMerchant serviceMerchant ;
private final ServiceProduct serviceProduct ;

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public void AddStock(MerchantStock merchantStock) {
        merchantStocks.add(merchantStock);
    }
    public boolean addMerchantStock(MerchantStock merchantStock) {
        // product
        for (int i = 0; i < serviceProduct.products.size(); i++) {

            Product p = serviceProduct.products.get(i);

            if (p.getId().equalsIgnoreCase(merchantStock.getProductId())) {

               //Merchant is found ?
                for (int j = 0; j < serviceMerchant.merchants.size(); j++) {

                    Merchant m = serviceMerchant.merchants.get(j);

                    if (m.getId().equalsIgnoreCase(merchantStock.getMerchantId())) {

                        //product Merchant ?
                        for (int k = 0; k < merchantStocks.size(); k++) {
                            MerchantStock existingStock = merchantStocks.get(k);

                            if (existingStock.getProductId().equalsIgnoreCase(merchantStock.getProductId()) &&
                                    existingStock.getMerchantId().equalsIgnoreCase(merchantStock.getMerchantId())) {

                                //add product
                                existingStock.setStock(existingStock.getStock() + merchantStock.getStock());
                                return true;
                            }
                        }

                        //add if not found
                        merchantStocks.add(merchantStock);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //end point 3

    public ArrayList<Merchant> getMerchantsWithLowStock(int threshold) {
        ArrayList<Merchant> result = new ArrayList<>();

        for (MerchantStock ms : merchantStocks) {
            if (ms.getStock() < threshold) {

                for (Merchant m : serviceMerchant.merchants) {
                    if (m.getId().equalsIgnoreCase(ms.getMerchantId())) {

                        boolean alreadyAdded = false;
                        for (Merchant existing : result) {
                            if (existing.getId().equalsIgnoreCase(m.getId())) {
                                alreadyAdded = true;
                                break;
                            }
                        }

                        if (!alreadyAdded) {
                            result.add(m);
                        }
                    }
                }
            }
        }

        return result;
    }

    public ArrayList<MerchantStock> GetAllMerchantStock() {
        return merchantStocks;
    }

    public boolean updateMerchantStock(String id, MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equalsIgnoreCase(id)) {
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchantStock(String id) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equalsIgnoreCase(id)) {
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }
}

