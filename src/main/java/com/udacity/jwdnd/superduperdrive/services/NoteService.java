package com.udacity.jwdnd.superduperdrive.services;

import com.udacity.jwdnd.superduperdrive.mapper.NoteMapper;
import com.udacity.jwdnd.superduperdrive.model.Note;
import com.udacity.jwdnd.superduperdrive.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final AuthenticationService authenticationService;
    private final NoteMapper noteMapper;

    public NoteService( AuthenticationService authenticationService, NoteMapper noteMapper) {
        this.authenticationService = authenticationService;
        this.noteMapper = noteMapper;
    }

    public void addOrUpdateNote(NoteForm noteForm) {
        Note note = new Note();

        note.setNoteTitle(noteForm.getTitle());
        note.setNoteDescription(noteForm.getDescription());
        note.setUserId(authenticationService.getUserId());

        if(noteForm.getNoteId().isEmpty()){
            noteMapper.insert(note);
        } else {
            note.setNoteId(Integer.parseInt(noteForm.getNoteId()));
            noteMapper.updateNote(note);
        }

    }

    public List<Note> getNoteListings() {
        return noteMapper.getNotesForUser(authenticationService.getUserId());
    }

    public Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }

}

