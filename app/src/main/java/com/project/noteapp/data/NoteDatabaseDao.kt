package com.project.noteapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.noteapp.model.Note


@Dao
interface NoteDatabaseDao {

    @Query("SELECT * from notes_tbl")
    fun getNotes(): List<Note>

    @Query("SELECT * from notes_tbl where id =:id")
    fun getNotesById(id: String) : Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(note: Note)

    @Query("DELETE from notes_tbl")
    fun deleteAll()

    @Delete
    fun deleteNotes(note: Note)

}