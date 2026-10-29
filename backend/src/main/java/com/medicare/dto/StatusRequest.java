package com.medicare.dto;
import com.medicare.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
public record StatusRequest(@NotNull OrderStatus status) {}
