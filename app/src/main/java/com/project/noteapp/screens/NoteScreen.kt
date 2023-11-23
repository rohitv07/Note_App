package com.project.noteapp.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.noteapp.R
import com.project.noteapp.components.NoteButton
import com.project.noteapp.components.NoteInputText
import com.project.noteapp.data.NotesDataSource
import com.project.noteapp.model.Note
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NoteScreen(
    notes : List<Note>,
    onAddNote : (Note) -> Unit,
    onRemoveNote : (Note) -> Unit
) {

    var title = remember {
        mutableStateOf("")
    }

    var description = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = {
            Text(text = stringResource(id = R.string.app_name))
        },
            actions = {
                Icon(imageVector = Icons.Rounded.Notifications,
                    contentDescription = "Icon",
                    modifier = Modifier.padding(7.dp))
            },
            colors = TopAppBarDefaults.topAppBarColors(Color(0xFFDADFE3)))
        }
    ) {
        //Content
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(it)){

            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {

                NoteInputText(
                    modifier = Modifier.padding(
                        top = 9.dp,
                        bottom = 8.dp),
                    text = title.value,
                    label = "Title",
                    onTextChange = {
                        if (it.all { char ->
                                char.isLetter() || char.isWhitespace()
                            }) title.value = it
                    } )

                NoteInputText(
                    modifier = Modifier.padding(
                        top = 9.dp,
                        bottom = 8.dp),
                    text = description.value,
                    label = "Add a note",
                    onTextChange = {
                        if (it.all { char ->
                                char.isLetter() || char.isWhitespace()
                            }) description.value = it
                    })

                NoteButton(text = "Save",
                    onClick = {
                        if (title.value.isNotEmpty() && description.value.isNotEmpty()){
                            // Add to list and Save

                            onAddNote(
                                Note(title = title.value,
                                description = description.value)
                            )

                            title.value = ""
                            description.value = ""

                            Toast.makeText(context,"Note Added", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                Divider(modifier = Modifier.padding(10.dp))
                LazyColumn{
                    items(notes){note ->
                        NoteRow(note = note,
                            onNoteClicked = {
                                onRemoveNote(it)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note:Note,
    onNoteClicked: (Note) -> Unit) {

    Surface(
        modifier = modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp))
            .fillMaxWidth(),
        color = Color(0xFFDFE6EB),
        shadowElevation = 6.dp
    ) {
        Column(
            modifier = modifier
                .clickable {
                    onNoteClicked(note)
                }
                .padding(
                    horizontal = 14.dp,
                    vertical = 6.dp
                ),
            horizontalAlignment = Alignment.Start
        ) {

            Text(text = note.title,
                style = MaterialTheme.typography.titleSmall)

            Text(text = note.description,
                style = MaterialTheme.typography.titleMedium)

            Text(text = note.entryDate.format(DateTimeFormatter.ofPattern("EEE, d MMM")),
                style = MaterialTheme.typography.bodySmall)

        }

    }
}


@Preview
@Composable
fun NoteScreenView() {
    NoteScreen(
        notes = NotesDataSource().loadNotes(),
        onAddNote = {},
        onRemoveNote = {}
    )

}