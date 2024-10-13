package dev.jeep.Lookpay.services;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import dev.jeep.Lookpay.models.tables.*;
import dev.jeep.Lookpay.repositories.tables.*;

@Service
public class TablesService {

    @Autowired
    private EmissionTypeRepository emissionTypeRepository;

    @Autowired
    private EnvironmentTypeRepository environmentTypeRepository;

    @Autowired
    private IceTypeRepository iceTypeRepository;

    @Autowired
    private IdentificationTypeRepository identificationTypeRepository;

    @Autowired
    private IrbpTypeRepository irbpTypeRepository;

    @Autowired
    private IvaTypeRepository ivaTypeRepository;

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private ReceiptTypeRepository receiptTypeRepository;

    @Autowired
    private TaxTypeRepository taxTypeRepository;

    @Transactional(readOnly = true)
    public List<String> getTaxTypes() throws Exception {
        try {
            List<TaxTypeModel> taxes = taxTypeRepository.findAll();
            List<String> taxesTypes = new ArrayList<>();
            for (TaxTypeModel tax : taxes) {
                taxesTypes.add(tax.getType());
            }
            return taxesTypes;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<String> getIvaTypes() throws Exception {
        try {
            List<IvaTypeModel> ivas = ivaTypeRepository.findAll();
            List<String> ivaTypes = new ArrayList<>();
            for (IvaTypeModel iva : ivas) {
                ivaTypes.add(iva.getType());
            }
            return ivaTypes;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<String> getIceTypes() throws Exception {
        try {
            List<IceTypeModel> ices = iceTypeRepository.findAll();
            List<String> iceTypes = new ArrayList<>();
            for (IceTypeModel ice : ices) {
                iceTypes.add(ice.getType());
            }
            return iceTypes;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<String> getIrbpTypes() throws Exception {
        try {
            List<IrbpTypeModel> irbps = irbpTypeRepository.findAll();
            List<String> irbpTypes = new ArrayList<>();
            for (IrbpTypeModel irbp : irbps) {
                irbpTypes.add(irbp.getType());
            }
            return irbpTypes;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<String> getIdentificationTypes() throws Exception {
        try {
            List<IdentificationTypeModel> identifications = identificationTypeRepository.findAll();
            List<String> identificationTypes = new ArrayList<>();
            for (IdentificationTypeModel identification : identifications) {
                identificationTypes.add(identification.getType());
            }
            return identificationTypes;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<String> getReceiptTypes() throws Exception {
        try {
            List<ReceiptTypeModel> receipts = receiptTypeRepository.findAll();
            List<String> receiptTypes = new ArrayList<>();
            for (ReceiptTypeModel receipt : receipts) {
                receiptTypes.add(receipt.getType());
            }
            return receiptTypes;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<String> getEmissionTypes() throws Exception {
        try {
            List<EmissionTypeModel> emissions = emissionTypeRepository.findAll();
            List<String> receiptTypes = new ArrayList<>();
            for (EmissionTypeModel emission : emissions) {
                receiptTypes.add(emission.getType());
            }
            return receiptTypes;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<String> getEnvironmentTypes() throws Exception {
        try {
            List<EnvironmentTypeModel> environments = environmentTypeRepository.findAll();
            List<String> environmentTypes = new ArrayList<>();
            for (EnvironmentTypeModel environment : environments) {
                environmentTypes.add(environment.getType());
            }
            return environmentTypes;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<String> getPaymentTypes() throws Exception {
        try {
            List<PaymentTypeModel> payments = paymentTypeRepository.findAll();
            List<String> paymentTypes = new ArrayList<>();
            for (PaymentTypeModel payment : payments) {
                paymentTypes.add(payment.getType());
            }
            return paymentTypes;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
