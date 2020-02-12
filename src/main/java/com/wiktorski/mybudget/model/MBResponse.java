package com.wiktorski.mybudget.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MBResponse<T> implements Serializable {

    @Builder.Default
    private boolean isOk = true;
    private T data;
    private String message;


}
