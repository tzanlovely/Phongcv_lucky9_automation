package io;

import org.sikuli.script.Image;

import java.util.List;

public interface Word {
    public void write(String fileName, List<String> content);
    public void printImage(String fileName, Image image);
}
