package com.udacity.jwdnd.superduperdrive.controller;

import com.udacity.jwdnd.superduperdrive.services.NoteService;
import com.udacity.jwdnd.superduperdrive.model.Note;
import com.udacity.jwdnd.superduperdrive.model.NoteForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @PostMapping("add-note")
    public String addOrUpdateNote(@ModelAttribute("newNote") NoteForm noteForm, Model model) {

        noteService.addOrUpdateNote(noteForm);

        model.addAttribute("notes", noteService.getNoteListings());
        model.addAttribute("result", "success");

        return "result";
    }

    @GetMapping(value = "/get-note/{noteId}")
    public Note getNote(@PathVariable Integer noteId) {
        return noteService.getNote(noteId);
    }

    @GetMapping(value = "/delete-note/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Model model) {
        noteService.deleteNote(noteId);
        model.addAttribute("notes", noteService.getNoteListings());
        model.addAttribute("result", "success");

        return "result";
    }
}
