package com.udacity.jwdnd.superduperdrive.controller;

import com.udacity.jwdnd.superduperdrive.model.CredentialForm;
import com.udacity.jwdnd.superduperdrive.model.File;
import com.udacity.jwdnd.superduperdrive.model.FileForm;
import com.udacity.jwdnd.superduperdrive.model.NoteForm;
import com.udacity.jwdnd.superduperdrive.services.CredentialService;
import com.udacity.jwdnd.superduperdrive.services.EncryptionService;
import com.udacity.jwdnd.superduperdrive.services.FileService;
import com.udacity.jwdnd.superduperdrive.services.NoteService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(
            FileService fileService, NoteService noteService,
            CredentialService credentialService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("newFile") FileForm fileForm,
                              @ModelAttribute("newNote") NoteForm noteForm, @ModelAttribute("newCredential") CredentialForm credentialForm,
                              Model model) {

        model.addAttribute("userFiles", this.fileService.getFileListings());
        model.addAttribute("userNotes", noteService.getNoteListings());
        model.addAttribute("userCredentials", credentialService.getCredentialListings());
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

    @PostMapping
    public String newFile(@ModelAttribute("newFile") FileForm fileForm, Model model) throws IOException {

        MultipartFile multipartFile = fileForm.getFile();
        String fileName = multipartFile.getOriginalFilename();

        boolean fileIsDuplicate = false;
        File fExist = fileService.getFile(fileName);
        if(fExist != null) {
            fileIsDuplicate = true;
        }

        if (!fileIsDuplicate) {
            fileService.addFile(multipartFile);
            model.addAttribute("result", "success");
        } else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "You are trying to add file with Same Name");
        }
        model.addAttribute("files", fileService.getFileListings());

        return "result";
    }

    @GetMapping(value = "/get-file/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public byte[] getFile(@PathVariable String fileName) {
        return fileService.getFile(fileName).getFileData();
    }


    @GetMapping(value = "/delete-file/{fileName}")
    public String deleteFile(@PathVariable String fileName, @ModelAttribute("newFile") FileForm newFile,
                             @ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("newCredential") CredentialForm newCredential,
                             Model model) {
        fileService.deleteFile(fileName);

        model.addAttribute("files", fileService.getFileListings());
        model.addAttribute("result", "success");

        return "result";
    }

    @ExceptionHandler()
    public String handle(IOException ex) {
        return "error";
    }
}
