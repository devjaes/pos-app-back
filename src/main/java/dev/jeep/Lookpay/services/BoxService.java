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
public class BoxService {

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Transactional(readOnly = true)
    public List<BoxResponseDTO> getAll() throws Exception {
        try {
            List<BoxModel> boxes = boxRepository.findAll();
            List<BoxResponseDTO> boxResponseDTOs = new ArrayList<>();
            for (BoxModel box : boxes) {
                boxResponseDTOs.add(convertModeltoResponse(box));
            }
            return boxResponseDTOs;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BoxResponseDTO getOne(Long id) throws Exception {
        try {
            return convertModeltoResponse(boxRepository.findById(id).get());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public BoxResponseDTO register(BoxEntranceDTO box) throws Exception {
        try {
            return convertModeltoResponse(boxRepository.save(convertEntranceToModel(box)));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) throws Exception {
        try {
            if (boxRepository.existsById(id)) {
                boxRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateSequential(Long id) throws Exception {
        try {
            BoxModel box = boxRepository.findById(id).get();
            box.setSequential(Utility.increaseSequential(box.getSequential()));
            boxRepository.save(box);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<BoxModel> getAllByBranchId(Long id) throws Exception {
        try {
            return boxRepository.getAllByBranchId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // Converters
    public BoxResponseDTO convertModeltoResponse(BoxModel box) throws Exception {
        try {
            BoxResponseDTO boxResponseDTO = new BoxResponseDTO();
            boxResponseDTO.setId(box.getId());
            boxResponseDTO.setKey(box.getKey());
            boxResponseDTO.setSequential(box.getSequential());
            boxResponseDTO.setBranchName(box.getBranch().getName());
            return boxResponseDTO;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private BoxModel convertEntranceToModel(BoxEntranceDTO box) throws Exception {
        try {
            BoxModel boxModel = new BoxModel();
            BranchModel branch = branchRepository.findById(box.getBranchId()).get();
            String lastKey = boxRepository.getLastKey(branch.getId());
            if (lastKey == null) {
                boxModel.setKey("001");
            } else {
                boxModel.setKey(Utility.increaseId(lastKey));
            }
            boxModel.setBranch(branch);
            boxModel.setSequential("00000001");
            return boxModel;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
