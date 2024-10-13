package dev.jeep.Lookpay.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import dev.jeep.Lookpay.dtos.*;
import dev.jeep.Lookpay.models.*;
import dev.jeep.Lookpay.repositories.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getAll() throws Exception {
        try {
            List<CategoryModel> categories = categoryRepository.findAll();
            List<CategoryResponseDTO> categoriesDTOs = new ArrayList<>();
            for (CategoryModel category : categories) {
                categoriesDTOs.add(convertModeltoResponse(category));
            }
            return categoriesDTOs;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO getOne(Long id) throws Exception {
        try {
            return convertModeltoResponse(categoryRepository.findById(id).get());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public CategoryResponseDTO register(CategoryEntranceDTO category) throws Exception {
        try {
            if (categoryRepository.getCategoryByType(category.getCategory()) != null) {
                return null;
            }
            return convertModeltoResponse(categoryRepository.save(convertEntranceToModel(category)));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public CategoryResponseDTO update(Long id, CategoryEntranceDTO category) throws Exception {
        try {
            CategoryModel categoryModel = categoryRepository.findById(id).get();
            if (categoryRepository.getCategoryByType(category.getCategory()) != null) {
                return null;
            }
            categoryModel.setCategory(category.getCategory());
            return convertModeltoResponse(categoryRepository.save(categoryModel));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) throws Exception {
        try {
            if (categoryRepository.existsById(id)) {
                List<ProductModel> products = categoryRepository.findById(id).get().getProducts();
                for (ProductModel product : products) {
                    product.setCategory(null);
                    productRepository.save(product);
                }
                categoryRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(
                    categoryRepository.findById(id).get().getProducts().size() + "ttttttttttttttttttttttttttttttttt");
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    // Converters
    private CategoryResponseDTO convertModeltoResponse(CategoryModel catergory) throws Exception {
        try {
            CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
            categoryResponseDTO.setId(catergory.getId());
            categoryResponseDTO.setCategory(catergory.getCategory());
            return categoryResponseDTO;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private CategoryModel convertEntranceToModel(CategoryEntranceDTO category) throws Exception {
        try {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setCategory(category.getCategory());
            return categoryModel;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
