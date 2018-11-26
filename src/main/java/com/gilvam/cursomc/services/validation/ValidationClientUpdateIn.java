package com.gilvam.cursomc.services.validation;

import com.gilvam.cursomc.domain.Client;
import com.gilvam.cursomc.dto.ClientDTO;
import com.gilvam.cursomc.repositories.ClientRepository;
import com.gilvam.cursomc.resources.exception.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValidationClientUpdateIn implements ConstraintValidator<ValidationClientUpdate, ClientDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void initialize(ValidationClientUpdate ann) {
    }

    @Override
    public boolean isValid(ClientDTO dto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        Map<String, String> map = (Map<String, String>) this.request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        Client aux = this.clientRepository.findByEmail(dto.getEmail());
        if(aux != null && !aux.getId().equals(uriId)){
              list.add(new FieldMessage("email", "Email já existe"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}