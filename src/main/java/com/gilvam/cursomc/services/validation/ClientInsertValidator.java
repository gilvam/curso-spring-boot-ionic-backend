package com.gilvam.cursomc.services.validation;

import com.gilvam.cursomc.dto.ClientNewDTO;
import com.gilvam.cursomc.enums.TypeClient;
import com.gilvam.cursomc.resources.exception.FieldMessage;
import com.gilvam.cursomc.services.validation.utils.BR;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator personalizado para a anotação CustomValidationClientInsert
 */
public class ClientInsertValidator implements ConstraintValidator<CustomValidationClientInsert, ClientNewDTO> {
    @Override
    public void initialize(CustomValidationClientInsert ann) {
    }

    @Override
    public boolean isValid(ClientNewDTO dto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(dto.getType().equals(TypeClient.PERSONINDIVIDUAL.getCod()) && !BR.isValidCPF(dto.getCpfOrCnpj())){
            list.add(new FieldMessage("cpfOrCnpj", "CPF inválido"));
        }
        if(dto.getType().equals(TypeClient.PERSONCORPORATION.getCod()) && !BR.isValidCNPJ(dto.getCpfOrCnpj())){
            list.add(new FieldMessage("cpfOrCnpj", "CNPJ inválido"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}