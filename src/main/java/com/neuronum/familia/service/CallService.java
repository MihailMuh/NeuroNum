package com.neuronum.familia.service;

import com.neuronum.familia.repository.CallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CallService {
    private final CallRepository callRepository;

    public boolean existsById(String id) {
        return callRepository.existsById(id);
    }
}
