package com.mndev.spring.service;

import com.mndev.spring.database.repository.CompanyRepository;
import com.mndev.spring.dto.CompanyReadDto;
import com.mndev.spring.listner.entity.AccessType;
import com.mndev.spring.listner.entity.EntityEvent;
import com.mndev.spring.mapper.CompanyReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final CompanyReadMapper companyReadMapper;


    //метод отслеживания события для Listener
    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id)
                .map(entity -> {
                    eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return companyReadMapper.mapFirstToSecond(entity);
                });
    }

    public List<CompanyReadDto> findAll() {
        return companyRepository.findAll().stream()
                .map(companyReadMapper::mapFirstToSecond)
                .collect(Collectors.toList());
    }
}
