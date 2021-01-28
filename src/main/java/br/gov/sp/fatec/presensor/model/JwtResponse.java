package br.gov.sp.fatec.presensor.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    @Getter
    @NonNull
    private final String jwttoken;

    public String getToken() {
        return getJwttoken();
    }

}
