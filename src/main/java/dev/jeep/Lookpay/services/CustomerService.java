package dev.jeep.Lookpay.services;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import dev.jeep.Lookpay.dtos.*;
import dev.jeep.Lookpay.models.*;
import dev.jeep.Lookpay.repositories.*;
import dev.jeep.Lookpay.repositories.tables.*;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private IdentificationTypeRepository identificationTypeRepository;

    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getAll() throws Exception {
        try {
            List<CustomerModel> customers = customerRepository.findAll();
            List<CustomerResponseDTO> customersDTOs = new ArrayList<>();
            for (CustomerModel customer : customers) {
                customersDTOs.add(convertModeltoResponse(customer));
            }
            return customersDTOs;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public CustomerResponseDTO getOne(Long id) throws Exception {
        try {
            return convertModeltoResponse(customerRepository.findById(id).get());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public CustomerResponseDTO register(CustomerEntranceDTO customer) throws Exception {
        try {
            if (customerRepository.getCustomerByIdentification(customer.getIdentification()) != null
                    || customerRepository.getCustomerByEmail(customer.getEmail()) != null) {
                return null;
            }
            return convertModeltoResponse(customerRepository.save(convertEntranceToModel(customer)));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public CustomerResponseDTO update(Long id, CustomerEntranceDTO customer) throws Exception {
        try {
            CustomerModel customerModel = customerRepository.findById(id).get();
            if (customer.getIdentification() != null) {
                if (customerRepository.getCustomerByIdentification(customer.getIdentification()) != null) {
                    return null;
                }
                customerModel.setIdentification(customer.getIdentification());
            }
            if (customer.getEmail() != null) {
                if (customerRepository.getCustomerByEmail(customer.getEmail()) != null) {
                    return null;
                }
                customerModel.setEmail(customer.getEmail());
            }
            if (customer.getIdentificationType() != null) {
                customerModel.setIdentificationType(
                        identificationTypeRepository.getIdentificationByType(customer.getIdentificationType()));
            }
            if (customer.getName() != null) {
                customerModel.setName(customer.getName());
            }
            if (customer.getLastName() != null) {
                customerModel.setLastName(customer.getLastName());
            }
            if (customer.getBusinessName() != null) {
                customerModel.setBusinessName(customer.getBusinessName());
            }
            if (customer.getAddress() != null) {
                customerModel.setAddress(customer.getAddress());
            }
            return convertModeltoResponse(customerRepository.save(customerModel));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) throws Exception {
        try {
            if (customerRepository.existsById(id)) {
                customerRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // Converters
    private CustomerResponseDTO convertModeltoResponse(CustomerModel customer) throws Exception {
        try {
            CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
            customerResponseDTO.setId(customer.getId());
            customerResponseDTO.setName(customer.getName());
            customerResponseDTO.setLastName(customer.getLastName());
            customerResponseDTO.setEmail(customer.getEmail());
            customerResponseDTO.setBusinessName(customer.getBusinessName());
            customerResponseDTO.setIdentification(customer.getIdentification());
            customerResponseDTO.setAddress(customer.getAddress());
            customerResponseDTO.setIdentificationType(customer.getIdentificationType().getType());
            return customerResponseDTO;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private CustomerModel convertEntranceToModel(CustomerEntranceDTO customer) throws Exception {
        try {
            CustomerModel customerModel = new CustomerModel();
            customerModel.setName(customer.getName());
            customerModel.setLastName(customer.getLastName());
            customerModel.setEmail(customer.getEmail());
            customerModel.setBusinessName(customer.getBusinessName());
            customerModel.setIdentification(customer.getIdentification());
            customerModel.setAddress(customer.getAddress());
            customerModel.setIdentificationType(
                    identificationTypeRepository.getIdentificationByType(customer.getIdentificationType()));
            return customerModel;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
