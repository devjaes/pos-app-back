package dev.jeep.Lookpay.services;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import dev.jeep.Lookpay.dtos.*;
import dev.jeep.Lookpay.models.*;
import dev.jeep.Lookpay.repositories.*;
import dev.jeep.Lookpay.utils.Utility;

@Service
public class SellingProductService {

    @Autowired
    private SellingProductRepository sellingProductRepository;

    @Autowired
    private SellingProductLogService sellingProductLogService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ProductService productService;

    @Transactional(readOnly = true)
    public List<SellingProductResponseDTO> getAll() throws Exception {
        try {
            List<SellingProductModel> sellingProducts = sellingProductRepository.findAll();
            List<SellingProductResponseDTO> sellingProductResponseDTOs = new ArrayList<>();
            for (SellingProductModel sellingProduct : sellingProducts) {
                sellingProductResponseDTOs.add(convertModeltoResponse(sellingProduct));
            }
            return sellingProductResponseDTOs;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public SellingProductResponseDTO getOne(Long id) throws Exception {
        try {
            return convertModeltoResponse(sellingProductRepository.findById(id).get());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<SellingProductResponseDTO> register(List<SellingProductEntranceDTO> sellingProducts) throws Exception {
        try {
            List<SellingProductResponseDTO> sellingProductResponseDTOs = new ArrayList<>();
            for (SellingProductEntranceDTO sp : sellingProducts) {
                productService.updateStock(sp.getMainCode(), sp.getQuantity());
                SellingProductModel model = sellingProductRepository.save(convertEntranceToModel(sp));
                sellingProductLogService.register(model);
                sellingProductResponseDTOs
                        .add(convertModeltoResponse(model));
            }
            return sellingProductResponseDTOs;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<SellingProductModel> getAllByInvoiceId(Long id) throws Exception {
        try {
            return sellingProductRepository.getAllByInvoiceId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) throws Exception {
        try {
            if (sellingProductRepository.existsById(id)) {
                sellingProductRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // Converters
    public SellingProductResponseDTO convertModeltoResponse(SellingProductModel sellingProduct) throws Exception {
        try {
            SellingProductResponseDTO sellingProductResponseDTO = new SellingProductResponseDTO();
            sellingProductResponseDTO.setId(sellingProduct.getId());
            sellingProductResponseDTO.setInvoiceId(sellingProduct.getInvoice().getId());
            sellingProductResponseDTO.setProduct(productService.convertModeltoResponse(sellingProduct.getProduct()));
            sellingProductResponseDTO.setDiscount(sellingProduct.getDiscount());
            sellingProductResponseDTO.setSubtotal(sellingProduct.getSubtotal());
            sellingProductResponseDTO.setIva(sellingProduct.getIva());
            sellingProductResponseDTO.setIvaValue(sellingProduct.getIvaValue());
            sellingProductResponseDTO.setIce(sellingProduct.getIce());
            sellingProductResponseDTO.setIceValue(sellingProduct.getIceValue());
            sellingProductResponseDTO.setIrbp(sellingProduct.getIrbp());
            sellingProductResponseDTO.setIrbpValue(sellingProduct.getIrbpValue());
            sellingProductResponseDTO.setQuantity(sellingProduct.getQuantity());
            return sellingProductResponseDTO;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private SellingProductModel convertEntranceToModel(SellingProductEntranceDTO sellingProduct) throws Exception {
        try {
            ProductModel productModel = productRepository.getProductByMainCode(sellingProduct.getMainCode());
            InvoiceModel invoiceModel = invoiceRepository.findById(sellingProduct.getInvoiceId()).get();
            SellingProductModel sellingProductModel = new SellingProductModel();
            sellingProductModel.setInvoice(invoiceModel);
            sellingProductModel.setProduct(productModel);
            if (sellingProduct.getDiscount() == null) {
                sellingProductModel.setDiscount(0.0);
            } else {
                sellingProductModel.setDiscount(Utility.round(sellingProduct.getDiscount()));
            }
            double subtotal = productModel.getUnitPrice() * sellingProduct.getQuantity();
            sellingProductModel.setSubtotal(Utility.round(subtotal));

            double ivaValue = 0;
            double iceValue = 0;
            double irbpValue = 0;
            if (productModel.getIvaVariable() != null) {
                ivaValue = subtotal * (Double.parseDouble(productModel.getIvaVariable()) / 100);
                sellingProductModel.setIva(Integer.parseInt(productModel.getIvaVariable()));
            } else {
                ivaValue = subtotal * productModel.getIvaType().getTax();
                sellingProductModel.setIva((int) (productModel.getIvaType().getTax() * 100));
            }
            sellingProductModel.setIvaValue(Utility.round(ivaValue));
            if (productModel.getIceType() != null) {
                iceValue = subtotal * productModel.getIceType().getTax();
                sellingProductModel.setIce((int) (productModel.getIceType().getTax() * 100));
                sellingProductModel.setIceValue(Utility.round(iceValue));
            }
            if (productModel.getIrbpType() != null) {
                irbpValue = subtotal * productModel.getIrbpType().getTax();
                sellingProductModel.setIrbp((int) (productModel.getIrbpType().getTax() * 100));
                sellingProductModel.setIrbpValue(Utility.round(irbpValue));
            }
            sellingProductModel.setQuantity(sellingProduct.getQuantity());
            return sellingProductModel;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
