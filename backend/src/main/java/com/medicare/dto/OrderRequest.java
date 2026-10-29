package com.medicare.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Min;
import java.util.List;
public record OrderRequest(@NotBlank String shippingAddress,@NotEmpty List<@Valid Item> items) {
 public record Item(@Min(1) Long medicineId,@Min(1) int quantity) {}
}
