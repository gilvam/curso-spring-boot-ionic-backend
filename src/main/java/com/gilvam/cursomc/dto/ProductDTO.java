package com.gilvam.cursomc.dto;

import com.gilvam.cursomc.domain.Client;
import com.gilvam.cursomc.domain.Product;
import com.gilvam.cursomc.services.validation.ValidationClientUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ValidationClientUpdate
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Double value;

    public ProductDTO() {
    }

    public ProductDTO(Product ob){
        this.id = ob.getId();
        this.name = ob.getName();
        this.value = ob.getValue();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
