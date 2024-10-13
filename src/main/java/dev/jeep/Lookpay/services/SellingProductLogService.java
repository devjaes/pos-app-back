package dev.jeep.Lookpay.services;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import dev.jeep.Lookpay.models.*;
import dev.jeep.Lookpay.repositories.*;

@Service
public class SellingProductLogService {

    @Autowired
    private SellingProductLogRepository sellingProductLogRepository;

    @Transactional(readOnly = true)
    public List<SellingProductLogModel> getAll() throws Exception {
        try {
            return sellingProductLogRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public SellingProductLogModel register(SellingProductModel model) throws Exception {
        try {
            return sellingProductLogRepository.save(convertModelToLog(model));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // Converters
    private SellingProductLogModel convertModelToLog(SellingProductModel sellingProduct) throws Exception {
        try {
            SellingProductLogModel log = new SellingProductLogModel();
            log.setProductName(sellingProduct.getProduct().getName());
            log.setProductUnitPrice(sellingProduct.getProduct().getUnitPrice());
            log.setIvaApplied(sellingProduct.getIva());
            log.setIvaValue(sellingProduct.getIvaValue());
            if (sellingProduct.getProduct().getIceType() != null) {
                log.setIceApplied(sellingProduct.getIce());
                log.setIceValue(sellingProduct.getIceValue());
            }
            if (sellingProduct.getProduct().getIrbpType() != null) {
                log.setIrbpApplied(sellingProduct.getIrbp());
                log.setIrbpValue(sellingProduct.getIrbpValue());
            }
            log.setDiscount(sellingProduct.getDiscount());
            log.setSubtotal(sellingProduct.getSubtotal());
            log.setQuantity(sellingProduct.getQuantity());
            log.setInvoiceAccessKey(sellingProduct.getInvoice().getAccessKey());
            return log;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
