package com.udacity.jwdnd.superduperdrive.model;


import org.springframework.web.multipart.MultipartFile;

public class FileForm {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
