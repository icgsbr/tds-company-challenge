package br.com.tds.challenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlDTO {
    //region ATTRIBUTES
    @NotBlank
    String longUrl;
    //endregion
}
