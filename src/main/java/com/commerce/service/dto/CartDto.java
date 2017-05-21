package com.commerce.service.dto;

import com.commerce.domain.User;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Data class for cart.
 */
public class CartDto {

    private Long id;
    @Size(max = 70)
    private String code;
    private User user;
    private List<OrderEntryDto> entries = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderEntryDto> getEntries() {
        return entries;
    }

    public void setEntries(List<OrderEntryDto> entries) {
        this.entries = entries;
    }
}
