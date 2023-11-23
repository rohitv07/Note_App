package com.project.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.noteapp.data.NotesDataSource
import com.project.noteapp.model.Note
import com.project.noteapp.screens.NoteScreen
import com.project.noteapp.screens.NoteViewModel
import com.project.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                val noteViewModel: NoteViewModel by viewModels()
                NotesApp(noteViewModel = noteViewModel)
            }
        }
    }
}



@Composable
fun NotesApp(noteViewModel: NoteViewModel = viewModel()) {

    val notesList = noteViewModel.getAllNotes()

    NoteScreen(
        notes = notesList,
        onAddNote = {
            noteViewModel.addNote(it)
        },
        onRemoveNote = {
            noteViewModel.removeNote(it)
        }
    )
}


@Composable
fun MyApp(content : @Composable () -> Unit) {
    NoteAppTheme {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MyApp {
        NoteScreen(
            notes = emptyList(),
            onAddNote = {},
            onRemoveNote = {}
        )
    }
}

