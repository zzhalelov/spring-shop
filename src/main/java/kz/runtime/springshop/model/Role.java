package kz.runtime.springshop.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    USER("Пользователь", "user"),
    MODER("Модератор", "moder"),
    ADMIN("Администратор", "admin");

    private final String displayName;
    private final String serviceName;
}