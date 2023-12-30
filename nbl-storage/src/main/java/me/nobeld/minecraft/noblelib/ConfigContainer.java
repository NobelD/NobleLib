package me.nobeld.minecraft.noblelib;

public record ConfigContainer<T>(String path, T def) {
}