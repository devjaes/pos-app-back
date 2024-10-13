package dev.jeep.Lookpay.services;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.jeep.Lookpay.dtos.*;
import dev.jeep.Lookpay.models.*;
import dev.jeep.Lookpay.models.vm.AssetModel;
import dev.jeep.Lookpay.repositories.*;
import dev.jeep.Lookpay.utils.Utility;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private S3Service s3Service;

    @Transactional(readOnly = true)
    public StoreResponseDTO getStore() throws Exception {
        try {
            return convertModeltoResponse(storeRepository.findById(1L).get());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public StoreResponseDTO update(String storeString, MultipartFile electronicSignature) throws Exception {
        try {
            StoreEntranceDTO store = convertStoreStringToDTO(storeString);

            StoreModel storeModel = storeRepository.findById(1L).get();
            if (store.getRuc() != null) {
                storeModel.setRuc(store.getRuc());
            }
            if (store.getStoreName() != null) {
                storeModel.setStoreName(store.getStoreName());
            }
            if (store.getTradeName() != null) {
                storeModel.setTradeName(store.getTradeName());
            }
            if (store.getAddress() != null) {
                storeModel.setAddress(store.getAddress());
            }
            if (store.getEspecialTaxPayer() != null) {
                storeModel.setEspecialTaxPayer(store.getEspecialTaxPayer());
            }
            if (store.getForcedAccounting() != null) {
                storeModel.setForcedAccounting(store.getForcedAccounting());
            }
            if (electronicSignature != null) {
                String signatureKey = s3Service.setStoreSignature(storeModel, electronicSignature);

                if (signatureKey == null) {
                    throw new Exception("Error al subir la firma electrónica");
                }
                storeModel.setElectronicSignatureKey(signatureKey);

                AssetModel asset = s3Service.getObject(signatureKey);

                if (asset == null) {
                    throw new Exception("Error al obtener la firma electrónica");
                }

                Utility.saveElectronicSignatureFile(signatureKey, asset);

            }
            return convertModeltoResponse(storeRepository.save(storeModel));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public StoreResponseDTO deleteElectronicSignature() throws Exception {
        try {
            StoreModel storeModel = storeRepository.findById(1L).get();
            s3Service.deleteObject(storeModel.getElectronicSignatureKey());
            Utility.deleteElectronicSignatureFile(storeModel.getElectronicSignatureKey());

            storeModel.setElectronicSignatureKey(null);

            return convertModeltoResponse(storeRepository.save(storeModel));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // Converters
    private StoreResponseDTO convertModeltoResponse(StoreModel storeModel) throws Exception {
        try {
            StoreResponseDTO responseDto = new StoreResponseDTO();
            responseDto.setId(storeModel.getId());
            responseDto.setStoreName(storeModel.getStoreName());
            responseDto.setTradeName(storeModel.getTradeName());
            responseDto.setRuc(storeModel.getRuc());
            responseDto.setAddress(storeModel.getAddress());
            responseDto.setEspecialTaxPayer(storeModel.getEspecialTaxPayer());
            responseDto.setForcedAccounting(storeModel.getForcedAccounting());
            responseDto.setElectronicSignatureKey(storeModel.getElectronicSignatureKey());
            return responseDto;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public StoreEntranceDTO convertStoreStringToDTO(String store) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(store, StoreEntranceDTO.class);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
