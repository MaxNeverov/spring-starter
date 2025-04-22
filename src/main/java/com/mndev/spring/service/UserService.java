package com.mndev.spring.service;

import com.mndev.spring.database.entity.User;
import com.mndev.spring.database.querydsl.QPredicates;
import com.mndev.spring.database.repository.CompanyRepository;
import com.mndev.spring.database.repository.UserRepository;
import com.mndev.spring.dto.UserCreateEditDto;
import com.mndev.spring.dto.UserFilter;
import com.mndev.spring.dto.UserReadDto;
import com.mndev.spring.mapper.UserCreateEditMapper;
import com.mndev.spring.mapper.UserReadMapper;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mndev.spring.database.entity.QUser.user;

@Service
@RequiredArgsConstructor
//перед каждым методом открывает транзакцию и после закрывает ее(во избежании Lazy объектов)
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final ImageService imageService;

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::mapFirstToSecond).collect(Collectors.toList());
    }

    public Page<UserReadDto> findAll(UserFilter filter, Pageable pageable) {

        Predicate predicate = QPredicates.builder()
                //указанное значение входит в состав имени не зависимо от регистра
                .add(filter.getFirstname(), user.firstname::containsIgnoreCase)
                //указанное значение входит в состав фамилии не зависимо от регистра
                .add(filter.getLastname(), user.lastname::containsIgnoreCase)
                //идет до указанной даты
                .add(filter.getBirthDate(), user.birthDate::before)
                .build();

        return userRepository.findAll(predicate, pageable)
                .map(userReadMapper::mapFirstToSecond);

    }

    public List<UserReadDto> findAll(UserFilter filter) {

        return userRepository.findAllByFilter(filter).stream()
                .map(userReadMapper::mapFirstToSecond).collect(Collectors.toList());
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::mapFirstToSecond);
    }

    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userCreateEditDto) {
        //Если не получиться создать сущность, то пробрасывает ошибку
        return Optional.of(userCreateEditDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return userCreateEditMapper.mapFirstToSecond(dto);
                })
                .map(userRepository::save)
                .map(userReadMapper::mapFirstToSecond)
                .orElseThrow();
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if(!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userCreateEditDto) {
        return userRepository.findById(id)
                .map(entity -> {
                    uploadImage(userCreateEditDto.getImage());
                    return userCreateEditMapper.mapSecondToFirst(userCreateEditDto, entity);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::mapFirstToSecond);
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    //Для Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }
}
