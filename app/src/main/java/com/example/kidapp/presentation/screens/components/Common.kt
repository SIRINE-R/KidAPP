package com.example.kidapp.presentation.screens.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.kidapp.R


@Composable
fun ProfileImage(
    profileImage: Any?,
    name: String? = "",
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.Transparent)
                .clickable(
                    onClick = onClick
                )
        ) {
           Image(
               painter = painterResource(profileImage as Int),
               contentDescription = null,
               modifier = modifier.size(80.dp) ,
               contentScale = ContentScale.Crop
           )
        }
        if (name != null) {
            Text(
                text = name,
                color = Color.Black ,
                style = MaterialTheme.typography.titleMedium ,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
@Composable
fun KidTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholder: String = "",
    isReadOnly: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Box(
        modifier = Modifier
            .clickable{
                onClick()
            }
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            label = {
                Text(
                    label,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = keyboardOptions,
            shape = RoundedCornerShape(25.dp),
            leadingIcon = leadingIcon,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            readOnly = isReadOnly,
            enabled = enabled,
            keyboardActions = keyboardActions,
            isError = isError,
            visualTransformation = visualTransformation
        )
    }
}
@Composable
fun KidButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(16.dp) ,
        shape = RoundedCornerShape(25.dp)

    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium ,
            textAlign = TextAlign.Center
        )
    }
}
@Composable
fun SectionEditing(
    title: String,
    isUpdateSelected: Boolean,
    isDeleteSelected: Boolean,
    onUpdateSelected: () -> Unit = {},
    onDeleteSelected: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(25.dp))
        ) {
            Column {
                NavigationBar(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(25.dp))
                ) {
                    CustomNavigationBarItem(
                        selected = isUpdateSelected,
                        onClick = onUpdateSelected,
                        label = "Update" ,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                    CustomNavigationBarItem(
                        selected = isDeleteSelected,
                        onClick = onDeleteSelected,
                        label = "Delete"

                    )
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    content()
                }
            }
        }
    }
}
@Composable
fun CustomNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    label: String ,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(25.dp))
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium ,
                    modifier = modifier.align(Alignment.CenterHorizontally) ,
                    color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
        }
    }
}
@Composable
fun KidWatchedVideoCard(
    modifier: Modifier = Modifier,
    title: String = "",
    description: String = "",
    thumbnail: Int = R.drawable.panda,
    onClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Card(
            modifier = modifier
                .size(height = 100.dp, width = 100.dp)
                .clickable(onClick = onClick)
                .clip(RoundedCornerShape(25.dp)) ,
        ) {
            Image(
                painter = painterResource(thumbnail),
                contentDescription = null ,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }

    }
}
@Composable
fun VideoCard(
    modifier: Modifier = Modifier,
    title: String =  "",
    description: String = "",
    thumbnail: String = "",
    onClick: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable(onClick = onClick)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(25.dp)),
        ) {
            Image(
                painter = painterResource(R.drawable.panda),
                contentDescription = null ,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (title.isNotBlank()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (description.isNotBlank()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Composable
fun SearchContent(
    text: String = "" ,
    timeDate : String = "" ,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)

    ) {
        Text(
            text = text ,
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = timeDate ,
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.padding(10.dp)
        )
    }
}


@Composable
fun SearchBar(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onClick: () -> Unit = {},
    painter: Int? = R.drawable.panda,
    isReadOnly: Boolean = false,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onSearch: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        ) {
            painter?.let {
                Image(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp) ,
                    contentScale = ContentScale.Crop
                )
            }
        }
        KidTextField(
            value = value,
            onValueChange = onValueChange,
            label = stringResource(R.string.search),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search_interface_symbol),
                    modifier = Modifier.size(24.dp),
                    contentDescription = null
                )
            },
            isReadOnly = isReadOnly,
            onClick = onClick,
            enabled = enabled,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onSearch = { onSearch() }
            )
        )
    }
}
@Composable
fun ActivityContainer(
    content: @Composable () -> Unit = {},
    title: String = ""
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title ,
            style = MaterialTheme.typography.titleLarge
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.onPrimaryContainer)
        ) {
            content()
        }
    }
}