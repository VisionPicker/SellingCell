package com.nju.software.SellingCell.controller.vo;

import lombok.Data;

public @Data class LoginResult {
    boolean success=false;
    String name;
    String role;
    Integer id;
}
