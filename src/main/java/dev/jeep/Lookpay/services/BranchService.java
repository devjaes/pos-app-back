package dev.jeep.Lookpay.services;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import dev.jeep.Lookpay.dtos.*;
import dev.jeep.Lookpay.models.*;
import dev.jeep.Lookpay.repositories.*;
import dev.jeep.Lookpay.utils.*;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Transactional(readOnly = true)
    public List<BranchResponseDTO> getAll() throws Exception {
        try {
            List<BranchModel> branches = branchRepository.findAll();
            List<BranchResponseDTO> branchResponseDTOs = new ArrayList<>();
            for (BranchModel branch : branches) {
                branchResponseDTOs.add(convertModeltoResponse(branch));
            }
            return branchResponseDTOs;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BranchResponseDTO getOne(Long id) throws Exception {
        try {
            return convertModeltoResponse(branchRepository.findById(id).get());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public BranchResponseDTO register(BranchEntranceDTO branch) throws Exception {
        try {
            if (branchRepository.getBranchByName(branch.getName()) != null) {
                return null;
            }
            return convertModeltoResponse(branchRepository.save(convertEntranceToModel(branch)));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public BranchResponseDTO update(Long id, BranchEntranceDTO branch) throws Exception {
        try {
            BranchModel branchModel = branchRepository.findById(id).get();
            if (branch.getName() != null) {
                if (branchRepository.getBranchByName(branch.getName()) != null) {
                    return null;
                }
                branchModel.setName(branch.getName());
            }
            if (branch.getAddress() != null) {
                branchModel.setAddress(branch.getAddress());
            }
            return convertModeltoResponse(branchRepository.save(branchModel));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) throws Exception {
        try {
            if (branchRepository.existsById(id)) {
                branchRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // Converters
    private BranchResponseDTO convertModeltoResponse(BranchModel branch) throws Exception {
        try {
            BranchResponseDTO branchResponseDTO = new BranchResponseDTO();
            branchResponseDTO.setId(branch.getId());
            branchResponseDTO.setKey(branch.getKey());
            branchResponseDTO.setName(branch.getName());
            branchResponseDTO.setAddress(branch.getAddress());
            return branchResponseDTO;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private BranchModel convertEntranceToModel(BranchEntranceDTO branch) throws Exception {
        try {
            BranchModel branchModel = new BranchModel();
            String lastKey = branchRepository.getLastKey();
            if (lastKey == null) {
                branchModel.setKey("001");
            } else {
                branchModel.setKey(Utility.increaseId(lastKey));
            }
            branchModel.setName(branch.getName());
            branchModel.setAddress(branch.getAddress());
            return branchModel;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
