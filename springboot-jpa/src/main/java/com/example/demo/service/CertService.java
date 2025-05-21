package com.example.demo.service;

import com.example.demo.exception.CertException;
import com.example.demo.model.dto.UserCert;

public interface CertService {
	UserCert getCert(String username, String password) throws CertException;
}
