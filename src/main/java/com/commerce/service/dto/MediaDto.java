package com.commerce.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data class for media.
 */
public class MediaDto {

    private Long id;
    @NotNull
    @Size(max = 100)
    private String code;
    @NotNull
    @Size(max = 100)
    private String title;
    @NotNull
    private byte[] image;
    private String imageContentType;
    private Long productId;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
