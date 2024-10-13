package dev.jeep.Lookpay.services;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import dev.jeep.Lookpay.models.*;
import dev.jeep.Lookpay.repositories.*;

@Service
public class InvoiceLogService {

    @Autowired
    private InvoiceLogRepository invoiceLogRepository;

    @Transactional(readOnly = true)
    public List<InvoiceLogModel> getAll() throws Exception {
        try {
            return invoiceLogRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public InvoiceLogModel register(InvoiceModel model) throws Exception {
        try {
            return invoiceLogRepository.save(convertModeltoLog(model));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // Converters
    private InvoiceLogModel convertModeltoLog(InvoiceModel model) throws Exception {
        try {
            InvoiceLogModel log = new InvoiceLogModel();
            log.setEnvironmentType(model.getEnvironmentType().getType());
            log.setEmissionType(model.getEmissionType().getType());
            log.setAccessKey(model.getAccessKey());
            log.setReceiptType(model.getReceiptType().getType());
            log.setCustomerIdentification(model.getCustomer().getIdentification());
            log.setEmissionDate(model.getEmissionDate());
            log.setRemissionGuide(model.getRemissionGuide());
            log.setTotalWithoutTax(model.getTotalWithoutTax());
            log.setTotalDiscount(model.getTotalDiscount());
            log.setTip(model.getTip());
            log.setPaymentType(model.getPaymentType().getType());
            log.setTotal(model.getTotal());
            log.setCurrency(model.getCurrency());
            log.setBoxKey(model.getBox().getKey());
            log.setBranchName(model.getBox().getBranch().getName());
            return log;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
