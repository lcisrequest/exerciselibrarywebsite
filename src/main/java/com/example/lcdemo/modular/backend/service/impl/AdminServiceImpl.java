package com.example.lcdemo.modular.backend.service.impl;


import com.example.lcdemo.modular.backend.dao.AdminMapper;
import com.example.lcdemo.modular.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    AdminMapper adminMapper;
}
