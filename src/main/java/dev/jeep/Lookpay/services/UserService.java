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
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Transactional(readOnly = true)
    public UserResponseDTO login(String email, String password) throws Exception {
        try {
            UserModel user = userRepository.getUserByEmail(email);
            if (!this.checkPassword(password, user.getPassword())) {
                return null;
            }
            return convertModelToResponse(user);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAll() throws Exception {
        try {
            List<UserModel> users = userRepository.findAll();
            List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
            for (UserModel user : users) {
                userResponseDTOs.add(convertModelToResponse(user));
            }
            return userResponseDTOs;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getOne(Long id) throws Exception {
        try {
            return convertModelToResponse(userRepository.findById(id).get());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public UserResponseDTO register(UserEntranceDTO user) throws Exception {
        try {
            if (userRepository.getUserByEmail(user.getEmail()) != null) {
                return null;
            }
            return convertModelToResponse(userRepository.save(convertEntranceToModel(user)));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public UserResponseDTO update(Long id, UserEntranceDTO user) throws Exception {
        try {
            UserModel userModel = userRepository.findById(id).get();
            UserEntranceDTO userUpdate = user;
            if (userUpdate.getEmail() != null) {
                if (userRepository.getUserByEmail(userUpdate.getEmail()) != null) {
                    return null;
                }
                userModel.setEmail(userUpdate.getEmail());
            }
            if (userUpdate.getName() != null) {
                userModel.setName(userUpdate.getName());
            }
            if (userUpdate.getLastName() != null) {
                userModel.setLastName(userUpdate.getLastName());
            }
            if (userUpdate.getPassword() != null) {
                userModel.setPassword(Utility.encryptPassword(userUpdate.getPassword()));
            }
            return convertModelToResponse(userRepository.save(userModel));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) throws Exception {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private boolean checkPassword(String loginPassword, String userPassword) {
        if (Utility.argon2.verify(userPassword, loginPassword.toCharArray())) {
            return true;
        }
        return false;
    }

    // Converters
    private UserResponseDTO convertModelToResponse(UserModel userModel) throws Exception {
        try {
            UserResponseDTO responseDto = new UserResponseDTO();
            responseDto.setId(userModel.getId());
            responseDto.setName(userModel.getName());
            responseDto.setLastName(userModel.getLastName());
            responseDto.setEmail(userModel.getEmail());
            responseDto.setRol(userModel.getRol().getType());
            return responseDto;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private UserModel convertEntranceToModel(UserEntranceDTO userEntrance) throws Exception {
        try {
            UserModel userModel = new UserModel();
            userModel.setName(userEntrance.getName());
            userModel.setLastName(userEntrance.getLastName());
            userModel.setEmail(userEntrance.getEmail());
            userModel.setPassword(Utility.encryptPassword(userEntrance.getPassword()));
            userModel.setRol(rolRepository.getRolByType(userEntrance.getRol()));
            return userModel;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
