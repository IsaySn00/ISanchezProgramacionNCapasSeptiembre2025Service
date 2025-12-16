package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;


public class Result {
    public boolean correct;
    public String errorMessage;
    public Object object;
    @JsonIgnore
    public String redirectLink;
    public List<Object> objects;
    public Exception ex;
    @JsonIgnore
    public int status;
}
