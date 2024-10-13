package dev.jeep.Lookpay.services;

import java.nio.file.Files;
import java.sql.Date;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import dev.jeep.Lookpay.dtos.*;
import dev.jeep.Lookpay.models.*;
import dev.jeep.Lookpay.models.tables.*;
import dev.jeep.Lookpay.repositories.*;
import dev.jeep.Lookpay.repositories.tables.*;
import dev.jeep.Lookpay.utils.Utility;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceLogService invoiceLogService;

    @Autowired
    private EnvironmentTypeRepository environmentTypeRepository;

    @Autowired
    private EmissionTypeRepository emissionTypeRepository;

    @Autowired
    private ReceiptTypeRepository receiptTypeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private BoxService boxService;

    @Autowired
    private SellingProductService sellingProductService;

    @Transactional(readOnly = true)
    public List<InvoiceResponseDTO> getAll() throws Exception {
        try {
            List<InvoiceModel> invoices = invoiceRepository.findAll();
            List<InvoiceResponseDTO> invoiceDTOs = new ArrayList<>();
            for (InvoiceModel invoice : invoices) {
                invoiceDTOs.add(convertModeltoResponse(invoice));
            }
            return invoiceDTOs;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public InvoiceResponseDTO getOne(Long id) throws Exception {
        try {
            return convertModeltoResponse(invoiceRepository.findById(id).get());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public InvoiceResponseDTO register(InvoiceEntranceDTO customer) throws Exception {
        try {
            InvoiceModel invoiceModel = invoiceRepository.save(convertEntranceToModel(customer));
            return convertModeltoResponse(invoiceModel);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public InvoiceResponseDTO updateValues(Long id)
            throws Exception {
        try {
            InvoiceModel invoiceModel = invoiceRepository.findById(id).get();
            List<SellingProductModel> sellingProducts = sellingProductService.getAllByInvoiceId(id);

            Double totalWithoutTax = 0.0;
            Double totalDiscount = 0.0;
            Double total = 0.0;

            for (SellingProductModel product : sellingProducts) {
                totalWithoutTax += product.getSubtotal();
                totalDiscount += product.getDiscount();

                total += product.getSubtotal() + product.getIvaValue() + product.getIceValue() + product.getIrbpValue()
                        - product.getDiscount();
            }

            invoiceModel.setTotalWithoutTax(Utility.round(totalWithoutTax));
            invoiceModel.setTotalDiscount(Utility.round(totalDiscount));
            invoiceModel.setTotal(Utility.round(total));
            invoiceModel.setTip(0.0);

            invoiceLogService.register(invoiceModel);
            return convertModeltoResponse(invoiceRepository.save(invoiceModel));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Resource invoice(Long id) throws Exception {
        try {
            StoreModel storeModel = storeRepository.findById(1L).get();
            InvoiceModel invoiceModel = invoiceRepository.findById(id).get();
            List<SellingProductModel> sellingProducts = sellingProductService.getAllByInvoiceId(id);

            byte[] finalInvoice = Files
                    .readAllBytes(Utility.invoice(storeModel, invoiceModel, sellingProducts).toPath());
            boxService.updateSequential(invoiceModel.getBox().getId());

            ByteArrayResource resource = new ByteArrayResource(finalInvoice);

            return resource;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // Converters
    private InvoiceResponseDTO convertModeltoResponse(InvoiceModel invoice) throws Exception {
        try {
            InvoiceResponseDTO invoiceResponseDTO = new InvoiceResponseDTO();
            invoiceResponseDTO.setId(invoice.getId());
            invoiceResponseDTO.setEnvironmentType(invoice.getEnvironmentType().getType());
            invoiceResponseDTO.setEmissionType(invoice.getEmissionType().getType());
            invoiceResponseDTO.setAccessKey(invoice.getAccessKey());
            invoiceResponseDTO.setReceiptType(invoice.getReceiptType().getType());
            invoiceResponseDTO.setCustomerId(invoice.getCustomer().getId());
            invoiceResponseDTO.setEmissionDate(invoice.getEmissionDate());
            invoiceResponseDTO.setRemissionGuide(invoice.getRemissionGuide());
            invoiceResponseDTO.setTotalWithoutTax(invoice.getTotalWithoutTax());
            invoiceResponseDTO.setTotalDiscount(invoice.getTotalDiscount());
            invoiceResponseDTO.setTip(invoice.getTip());
            invoiceResponseDTO.setPaymentType(invoice.getPaymentType().getType());
            invoiceResponseDTO.setTotal(invoice.getTotal());
            invoiceResponseDTO.setCurrency(invoice.getCurrency());
            invoiceResponseDTO.setBoxId(invoice.getBox().getId());
            invoiceResponseDTO.setBoxKey(invoice.getBox().getKey());
            invoiceResponseDTO.setBranchName(invoice.getBox().getBranch().getName());

            List<SellingProductResponseDTO> sellingProducts = new ArrayList<>();
            List<SellingProductModel> sellingProductsModel = sellingProductService.getAllByInvoiceId(invoice.getId());
            for (SellingProductModel sellingProduct : sellingProductsModel) {
                sellingProducts.add(sellingProductService.convertModeltoResponse(sellingProduct));
            }
            invoiceResponseDTO.setSellingProducts(sellingProducts);
            return invoiceResponseDTO;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private InvoiceModel convertEntranceToModel(InvoiceEntranceDTO invoice) throws Exception {
        try {
            BoxModel boxModel = boxRepository.findById(invoice.getBoxId()).get();
            BranchModel branchModel = boxModel.getBranch();
            StoreModel storeModel = storeRepository.findById(1L).get();

            Date emissionDate = new Date(new java.util.Date().getTime());
            String[] date = emissionDate.toString().split("-");

            String emissionDateInvoice = date[2] + date[1] + date[0];
            ReceiptTypeModel receiptType = receiptTypeRepository.getReceiptByType(invoice.getReceiptType());
            String ruc = storeModel.getRuc();
            EnvironmentTypeModel environmentType = environmentTypeRepository
                    .getEnvironmentByType(invoice.getEnvironmentType());
            String serie = branchModel.getKey() + boxModel.getKey();
            String sequential = boxModel.getSequential();
            String numericalCode = "12345678";
            EmissionTypeModel emissionType = emissionTypeRepository.getEmissionByType(invoice.getEmissionType());

            String module11 = emissionDateInvoice + receiptType.getCodeSri() + ruc +
                    environmentType.getCodeSri() + serie + sequential + numericalCode + emissionType.getCodeSri();
            String accessKey = module11 + Utility.module11(module11);

            InvoiceModel invoiceModel = new InvoiceModel();
            invoiceModel
                    .setEnvironmentType(environmentType);
            invoiceModel.setEmissionType(emissionType);
            invoiceModel.setAccessKey(accessKey);
            invoiceModel.setReceiptType(receiptType);
            invoiceModel
                    .setCustomer(customerRepository.getCustomerByIdentification(invoice.getCustomerIdentification()));
            invoiceModel.setEmissionDate(emissionDate);
            invoiceModel.setRemissionGuide(serie + boxModel.getSequential());
            invoiceModel.setPaymentType(paymentTypeRepository.getPaymentByType(invoice.getPaymentType()));
            invoiceModel.setCurrency("DOLAR");
            invoiceModel.setBox(boxModel);

            return invoiceModel;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
