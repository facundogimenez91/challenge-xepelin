package com.xepelin.challenge.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xepelin.challenge.core.HandlerResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO implements HandlerResponse {

  private String errorDetail;

  private int httpStatusCode;

}
