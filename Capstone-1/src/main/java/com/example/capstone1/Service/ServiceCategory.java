package com.example.capstone1.Service;
import com.example.capstone1.Model.Category;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class ServiceCategory {

    ArrayList<Category> categories = new ArrayList<>();

    public void addCategory(Category category) {
        categories.add(category);
    }

    public ArrayList<Category> GetAllCategory() {
        return categories;
    }

    public boolean updateCategory(String id, Category category) {
        for (int i = 0; i < categories.size(); i++) {

            if (categories.get(i).getId().equalsIgnoreCase(id)) {
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String id) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equalsIgnoreCase(id)) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }
}
