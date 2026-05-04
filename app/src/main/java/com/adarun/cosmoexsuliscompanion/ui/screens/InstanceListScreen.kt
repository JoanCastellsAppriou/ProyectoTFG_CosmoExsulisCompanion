package com.adarun.cosmoexsuliscompanion.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adarun.cosmoexsuliscompanion.data.model.Instance
import com.adarun.cosmoexsuliscompanion.ui.viewmodel.InstanceListViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun InstanceListScreen(
    viewModel: InstanceListViewModel,
    onInstanceClick: (Int) -> Unit
){
    val instances by viewModel.instances.collectAsState()
    val selected by viewModel.selectedInstances.collectAsState()
    val hasSelection by viewModel.hasSelection.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Column {
                Text (
                    text = "Cosmo Exsulis: RPG Companion",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        },
        floatingActionButton = {
            if (hasSelection) {
                FloatingActionButton(
                    onClick = {viewModel.deleteSelection()}
                ) {
                    Text("\uD83D\uDDD1")
                }
            }
            else {
                FloatingActionButton(
                    onClick = {showDialog = true}
                ) {
                    Text("+")
                }
            }
        }
    ) { padding ->
        InstanceList(
            instances = instances,
            selected = selected,
            onClick = { id ->
                if (selected.isEmpty()) {
                    onInstanceClick(id)
                }
                else {
                    viewModel.toggleSelection(id)
                }
            },
            onLongClick = { id -> viewModel.toggleSelection(id) },
            modifier = Modifier.padding(padding)
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Nueva Instancia") },
            text = {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Nombre") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newName.isNotBlank()) {
                            viewModel.createInstance(newName)
                            newName = ""
                            showDialog = false
                        }
                    },
                    enabled = newName.isNotBlank()
                ) {
                    Text("Crear")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InstanceList (
    instances: List<Instance>,
    selected: Set<Int>,
    onClick: (Int) -> Unit,
    onLongClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier
            .fillMaxSize()
    ) {
        items (instances) { instance ->
            val isSelected = selected.contains(instance.instanceId)

            ListItem(
                headlineContent = { Text (instance.name) },
                supportingContent = {
                    Text("ID: ${instance.instanceId} | ${instance.createdAt.dayOfMonth}/${instance.createdAt.monthValue}/${instance.createdAt.year}")
                },
                colors = ListItemDefaults.colors(
                    containerColor =
                        if (isSelected) {
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        } else {
                            MaterialTheme.colorScheme.background
                        }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = { onClick(instance.instanceId) },
                        onLongClick = { onLongClick(instance.instanceId) }
                    )
                    .padding(horizontal = 8.dp)
            )
            HorizontalDivider()
        }
    }
}