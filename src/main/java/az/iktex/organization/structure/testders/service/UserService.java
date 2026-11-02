package az.iktex.organization.structure.testders.service;

import az.iktex.organization.structure.testders.entity.Passport;
import az.iktex.organization.structure.testders.entity.User;
import az.iktex.organization.structure.testders.enums.Gender;
import az.iktex.organization.structure.testders.mapper.PassportMapper;
import az.iktex.organization.structure.testders.mapper.UserMapper;
import az.iktex.organization.structure.testders.repository.PassportRepository;
import az.iktex.organization.structure.testders.repository.UserRepository;

import az.iktex.organization.structure.testders.request.PassportRequest;
import az.iktex.organization.structure.testders.request.UserRequest;
import az.iktex.organization.structure.testders.response.PassportResponse;
import az.iktex.organization.structure.testders.response.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PassportRepository passportRepository;
    private final PassportMapper passportMapper;


    @Transactional
    public UserResponse create(UserRequest  userRequest) {
        if (userRepository.existsByEmail(userRequest.email()))
            throw  new IllegalStateException("Email already exists");
        User user = userMapper.toEntity(userRequest);
        User saved=userRepository.save(user);
        return userMapper.toResponse(saved);

    }


    public UserResponse getById(Long id) {
        User user=userRepository.findByIdWithPassport(id).orElseThrow(()->new EntityNotFoundException("User not found"));

        return userMapper.toResponse(user);

    }

    public  UserResponse getByEmail(String email) {
        User user=userRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("User not found"));
        return userMapper.toResponse(user);
    }

    public List<UserResponse> getAll(Gender gender){
        if (gender==null)
            return userMapper.toResponseList(userRepository.findAllWithPassport());
        List<UserResponse> users=userMapper.toResponseList(userRepository.findAllByGender(gender));
        return users;

    }

    public List<UserResponse> getWithoutPassport(){
        List<UserResponse> users=userMapper.toResponseList(userRepository.findAllByPassportIsNull());
        return users;
    }


    @Transactional
    public UserResponse update(Long id,UserRequest userRequest){

        User user=userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found"));
        if (userRepository.existsByEmailAndIdNot(userRequest.email(),id)){
            throw  new IllegalStateException("Email already exists");}
        userMapper.updateEntity(userRequest,user);
        return userMapper.toResponse(user);



    }
    @Transactional
    public void delete(Long id){
        User user=userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found"));
        userRepository.delete(user);

    }


    @Transactional
    public PassportResponse addPassport(Long userId, PassportRequest  passportRequest){
        User user=userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found"));
        if(passportRepository.existsByUserId(userId)){
            throw  new IllegalStateException("User already has a passport");}
        if(passportRepository.existsBySerialNumber(passportRequest.serialNumber())){
            throw  new IllegalStateException("Passport serial number already exists");}


        Passport passport=passportMapper.toEntity(passportRequest);
        user.setPassport(passport);
        Passport saved = passportRepository.save(passport);
        return passportMapper.toResponse(saved);
    }

    public PassportResponse getPassport(Long userId){

        if(!userRepository.existsById(userId)){
            throw  new EntityNotFoundException("User not found");
        }
        Passport passport=passportRepository.findByUserId(userId).orElseThrow(()->new EntityNotFoundException("Passport not found for user:"+userId));
        return passportMapper.toResponse(passport);
    }
    @Transactional
    public void deletePassport(Long userId){
        User user=userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found"));
        if(user.getPassport()==null){
            throw  new EntityNotFoundException("Passport not found");
        }
        user.setPassport(null);

    }

}