package org.example.capstone1update.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone1update.Model.Category;
import org.example.capstone1update.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {


    private final CategoryRepository categoryRepository;



        public List<Category> getCategories(){

            return categoryRepository.findAll();
        }

        public void addCategory(Category category) {

            categoryRepository.save(category);
        }


        public Boolean updateCategory(Integer id, Category category){

            Category oldCategory = categoryRepository.getById(id);

            if(oldCategory == null)
                return false;


            oldCategory.setName(category.getName());

            categoryRepository.save(oldCategory);
            return true;


        }

        public Boolean deleteCategory(Integer id){


            Category oldCategory = categoryRepository.getById(id);

            if(oldCategory == null)
                return false;

            categoryRepository.delete(oldCategory);
            return true;

        }












    }













