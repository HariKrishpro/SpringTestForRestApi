package com.SpringTest.data.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class EndResponse {
    @Value("OUTPUT")
    private Object OutPut;
    private HttpStatus StaTusCode;

    public void okStatus(Object object) {
        this.OutPut = object;
        this.StaTusCode = HttpStatus.OK;
    }

    public void forbiddenStatus(Object object) {
        this.OutPut = object;
        this.StaTusCode = HttpStatus.FORBIDDEN;
    }

}
