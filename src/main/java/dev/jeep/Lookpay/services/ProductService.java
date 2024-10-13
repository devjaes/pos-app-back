package dev.jeep.Lookpay.services;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.jeep.Lookpay.dtos.*;
import dev.jeep.Lookpay.models.*;
import dev.jeep.Lookpay.repositories.*;
import dev.jeep.Lookpay.repositories.tables.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private IvaTypeRepository ivaTypeRepository;

    @Autowired
    private IceTypeRepository iceTypeRepository;

    @Autowired
    private IrbpTypeRepository irbpTypeRepository;

    @Autowired
    private S3Service s3Service;

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAll() throws Exception {
        try {
            List<ProductModel> products = productRepository.findAll();
            List<ProductResponseDTO> productsDTOs = new ArrayList<>();
            for (ProductModel product : products) {
                productsDTOs.add(convertModeltoResponse(product));
            }
            return productsDTOs;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO getOne(Long id) throws Exception {
        try {
            return convertModeltoResponse(productRepository.findById(id).get());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductResponseDTO register(String product, MultipartFile image) throws Exception {
        try {
            ProductEntranceDTO productDTO = convertProductStringToDTO(product);
            if (productRepository.getProductByMainCode(productDTO.getMainCode()) != null) {
                return null;
            }
            ProductModel productModel = convertEntranceToModel(productDTO);
            productModel = productRepository.save(productModel);

            if (image != null) {
                HashMap<String, String> imagesRes = s3Service.setProductImages(productModel.getId(), image);
                productModel.setImageKey(imagesRes.get("imageKey"));
                productModel.setImageUrl(imagesRes.get("imageUrl"));
                productModel = productRepository.save(productModel);
            }

            return convertModeltoResponse(productModel);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductResponseDTO update(Long id, String product, MultipartFile image) throws Exception {
        try {
            ProductEntranceDTO productDTO = convertProductStringToDTO(product);
            ProductModel productModel = productRepository.findById(id).get();

            if (productDTO.getMainCode() != null) {
                if (productRepository.getProductByMainCode(productDTO.getMainCode()) != null) {
                    return null;
                }
                productModel.setMainCode(productDTO.getMainCode());
            }
            if (productDTO.getName() != null) {
                productModel.setName(productDTO.getName());
            }
            if (productDTO.getAuxCode() != null) {
                productModel.setAuxCode(productDTO.getAuxCode());
            }
            if (productDTO.getDescription() != null) {
                productModel.setDescription(productDTO.getDescription());
            }
            if (productDTO.getCategory() != null) {
                productModel.setCategory(categoryRepository.getCategoryByType(productDTO.getCategory()));
            }
            if (productDTO.getStock() != null) {
                productModel.setStock(productDTO.getStock());
            }
            if (productDTO.getUnitPrice() != null) {
                productModel.setUnitPrice(productDTO.getUnitPrice());
            }
            if (productDTO.getIvaVariable() != null) {
                productModel.setIvaVariable(productDTO.getIvaVariable());
            }
            if (productDTO.getIvaType() != null) {
                productModel.setIvaType(ivaTypeRepository.getIvaByType(productDTO.getIvaType()));
            }
            if (productDTO.getIceType() != null) {
                productModel.setIceType(iceTypeRepository.getIceByType(productDTO.getIceType()));
            }
            if (productDTO.getIrbpType() != null) {
                productModel.setIrbpType(irbpTypeRepository.getIrbpByType(productDTO.getIrbpType()));
            }
            if (image != null) {
                HashMap<String, String> imagesRes = s3Service.setProductImages(productModel.getId(), image);
                productModel.setImageKey(imagesRes.get("imageKey"));
                productModel.setImageUrl(imagesRes.get("imageUrl"));
            }

            return convertModeltoResponse(productRepository.save(productModel));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ProductResponseDTO> updateIvaByCategory(String category, String ivaVariable) throws Exception {
        try {
            List<ProductModel> products = productRepository
                    .getProductsByCategory(categoryRepository.getCategoryByType(category).getId());
            List<ProductResponseDTO> productsDTOs = new ArrayList<>();
            for (ProductModel product : products) {
                product.setIvaVariable(ivaVariable);
                productsDTOs.add(convertModeltoResponse(productRepository.save(product)));
            }
            return productsDTOs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) throws Exception {
        try {
            if (productRepository.existsById(id)) {
                String imageKey = productRepository.findById(id).get().getImageKey();
                if (imageKey != null) {
                    s3Service.deleteObject(imageKey);
                }
                productRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStock(String mainCode, Integer amount) throws Exception {
        try {
            ProductModel product = productRepository.getProductByMainCode(mainCode);
            product.setStock(product.getStock() - amount);
            productRepository.save(product);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // Converters
    public ProductResponseDTO convertModeltoResponse(ProductModel product) throws Exception {
        try {
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setId(product.getId());
            productResponseDTO.setName(product.getName());
            productResponseDTO.setMainCode(product.getMainCode());
            productResponseDTO.setAuxCode(product.getAuxCode());
            productResponseDTO.setDescription(product.getDescription());
            if (product.getCategory() != null) {
                productResponseDTO.setCategory(product.getCategory().getCategory());
            }
            productResponseDTO.setStock(product.getStock());
            productResponseDTO.setUnitPrice(product.getUnitPrice());
            if (product.getIvaVariable() != null) {
                productResponseDTO.setIvaVariable(product.getIvaVariable());
            }
            productResponseDTO.setImageUrl(product.getImageUrl());
            if (product.getIvaType() != null) {
                productResponseDTO.setIvaType(product.getIvaType().getType());
            }
            if (product.getIceType() != null) {
                productResponseDTO.setIceType(product.getIceType().getType());
            }
            if (product.getIrbpType() != null) {
                productResponseDTO.setIrbpType(product.getIrbpType().getType());
            }
            return productResponseDTO;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private ProductModel convertEntranceToModel(ProductEntranceDTO product) throws Exception {
        try {
            ProductModel productModel = new ProductModel();
            productModel.setName(product.getName());
            productModel.setMainCode(product.getMainCode());
            if (product.getAuxCode() == null) {
                productModel.setAuxCode(product.getMainCode());
            } else {
                productModel.setAuxCode(product.getAuxCode());
            }
            productModel.setDescription(product.getDescription());
            productModel.setStock(product.getStock());
            productModel.setUnitPrice(product.getUnitPrice());
            if (product.getIvaVariable() != null) {
                productModel.setIvaVariable(product.getIvaVariable());
            }
            productModel.setCategory(categoryRepository.getCategoryByType(product.getCategory()));
            if (product.getIvaType() != null) {
                productModel.setIvaType(ivaTypeRepository.getIvaByType(product.getIvaType()));
            }
            if (product.getIceType() != null) {
                productModel.setIceType(iceTypeRepository.getIceByType(product.getIceType()));
            }
            if (product.getIrbpType() != null) {
                productModel.setIrbpType(irbpTypeRepository.getIrbpByType(product.getIrbpType()));
            }
            return productModel;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public ProductEntranceDTO convertProductStringToDTO(String product) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(product, ProductEntranceDTO.class);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
