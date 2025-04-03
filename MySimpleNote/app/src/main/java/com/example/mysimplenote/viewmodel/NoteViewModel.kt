package com.example.mysimplenote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysimplenote.data.Note
import com.google.firebase.database.*

class NoteViewModel : ViewModel() {
    private val notesLiveData = MutableLiveData<List<Note>>()
    private val notesRef = FirebaseDatabase.getInstance().getReference("notes")

    init {
        notesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notes = snapshot.children.mapNotNull { it.getValue(Note::class.java) }
                notesLiveData.value = notes
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi
            }
        })
    }

    fun getNotes(): LiveData<List<Note>> {
        return notesLiveData
    }

    fun addNote(note: Note) {
        val id = notesRef.push().key
        note.id = id
        if (id != null) {
            notesRef.child(id).setValue(note)
        }
    }

    fun deleteNote(id: String) {
        notesRef.child(id).removeValue()
    }
}