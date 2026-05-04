package com.adarun.cosmoexsuliscompanion.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adarun.cosmoexsuliscompanion.data.model.Character
import com.adarun.cosmoexsuliscompanion.data.relation.CombatWithSaves
import com.adarun.cosmoexsuliscompanion.ui.viewmodel.InstanceDetailViewModel
import com.adarun.cosmoexsuliscompanion.ui.viewmodel.enums.InstanceTab

@Composable
fun InstanceDetailScreen(
    viewModel: InstanceDetailViewModel,
    onCharacterClick: (Int) -> Unit,
    onCreateCharacter: () -> Unit,
    onOpenCombat: (Int) -> Unit
) {
    val characters by viewModel.characters.collectAsState()
    val combats by viewModel.combats.collectAsState()

    val currentTab by viewModel.currentTab.collectAsState()
    val selectedCharacters by viewModel.selectedCharacters.collectAsState()
    val selectedCombats by viewModel.selectedCombats.collectAsState()
    val hasSelection by viewModel.hasSelection.collectAsState()

    Scaffold(
        topBar = {
            Column {
                Text (
                    text = "Instance Detail",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )

                TabRow (selectedTabIndex = currentTab.ordinal) {
                    Tab(
                        selected = currentTab == InstanceTab.CHARACTERS,
                        onClick = { viewModel.setTab(InstanceTab.CHARACTERS) },
                        text = { Text("Characters") }
                    )
                    Tab(
                        selected = currentTab == InstanceTab.COMBATS,
                        onClick = { viewModel.setTab(InstanceTab.COMBATS) },
                        text = { Text("Combats") }
                    )
                }
            }
        },
        floatingActionButton = {
            when (currentTab) {
                InstanceTab.CHARACTERS -> {
                    if (hasSelection) {
                        Column (
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            FloatingActionButton(
                                onClick = {viewModel.createCombat()}
                            ) {
                                Text("⚔")
                            }
                            FloatingActionButton(
                                onClick = { viewModel.deleteSelection() }
                            ) {
                                Text("\uD83D\uDDD1")
                            }
                        }
                    }
                    else {
                        FloatingActionButton(
                            onClick = onCreateCharacter
                        ) {
                            Text("+")
                        }
                    }
                }
                InstanceTab.COMBATS -> {
                    if (hasSelection) {
                        FloatingActionButton(
                            onClick = { viewModel.deleteSelection() }
                        ) {
                            Text("\uD83D\uDDD1")
                        }
                    }
                }
            }
        }
    ) { padding ->
        when (currentTab) {
            InstanceTab.CHARACTERS -> {
                CharacterList (
                    characters = characters,
                    selected = selectedCharacters,
                    onClick = { id ->
                        if (selectedCharacters.isEmpty()) {
                            onCharacterClick(id)
                        }
                        else {
                            viewModel.toggleSelection(id)
                        }
                    },
                    onLongClick = { id -> viewModel.toggleSelection(id) },
                    modifier = Modifier.padding(padding)
                )
            }

            InstanceTab.COMBATS -> {
                CombatList (
                    combats = combats,
                    selected = selectedCombats,
                    onClick = { id ->
                        if (selectedCombats.isEmpty()) {
                            onOpenCombat(id)
                        }
                        else {
                            viewModel.toggleSelection(id)
                        }
                    },
                    onLongClick = { id ->
                        viewModel.toggleSelection(id)
                    },
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterList (
    characters: List<Character>,
    selected: Set<Int>,
    onClick: (Int) -> Unit,
    onLongClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier
            .fillMaxSize()
    ) {
        items (characters) { char ->
            val isSelected = selected.contains(char.charId)

            ListItem(
                headlineContent = { Text (char.name) },
                supportingContent = {
                    Text("Level ${char.baseSkills.getLevel()}")
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
                        onClick = { onClick(char.charId) },
                        onLongClick = { onLongClick(char.charId) }
                    )
                    .padding(horizontal = 8.dp)
            )
            HorizontalDivider()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CombatList (
    combats: List<CombatWithSaves>,
    selected: Set<Int>,
    onClick: (Int) -> Unit,
    onLongClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn (modifier = modifier.fillMaxSize()) {
        items (combats) { combatWithParticipants ->
            val combat = combatWithParticipants.combat
            val saves = combatWithParticipants.savesWithCharacter

            val isSelected = selected.contains(combat.combatId)

            ListItem(
                headlineContent = { Text("Combat ${combat.combatId}") },
                supportingContent = { Text (
                    saves.joinToString (
                        separator = ", "
                    ) { it.character.name }
                ) },
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
                        onClick = { onClick(combat.combatId) },
                        onLongClick = { onLongClick(combat.combatId) }
                    )
            )
            HorizontalDivider()
        }
    }
}