package com.kreyzon.jobsmanagement.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kreyzon.jobsmanagement.utils.Constant;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse {
    private String result;
    private String message;
    private Object object;

    public GenericResponse() {}

    public GenericResponse(String result, String message, Object object) {
        this.result = result;
        this.message = message;
        this.object = object;
    }

    public GenericResponse(String result, String message) {
        this.result = result;
        this.message = message;
    }

    @JsonIgnore
    public Boolean isResultNOK() {
        if (this.getResult().equals(Constant.RESULT_NOK))
            return true;

        return false;
    }
}

