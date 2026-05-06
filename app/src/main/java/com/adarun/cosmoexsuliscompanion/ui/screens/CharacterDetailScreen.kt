package com.adarun.cosmoexsuliscompanion.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adarun.cosmoexsuliscompanion.data.relation.CombatWithSaves
import com.adarun.cosmoexsuliscompanion.ui.viewmodel.CharacterDetailViewModel
import com.adarun.cosmoexsuliscompanion.ui.viewmodel.enums.CharacterTab

@Composable
fun CharacterDetailScreen (
    viewModel: CharacterDetailViewModel,
    onEditCharacter: (Int, Int) -> Unit,
    onOpenCombat: (Int) -> Unit
) {
    val loadout by viewModel.loadout.collectAsState()
    val actions by viewModel.actions.collectAsState()
    val abilities by viewModel.abilities.collectAsState()
    val combats by  viewModel.combatsWithSaves.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()

    if (loadout == null) {
        CircularProgressIndicator()
    } else {
        val currentLoadout = loadout ?: run {
            Scaffold { padding ->
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            return
        }

        Scaffold(
            topBar = {
                Column {
                    Text(
                        text = currentLoadout.character.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )

                    TabRow(selectedTabIndex = currentTab.ordinal) {
                        Tab(
                            selected = currentTab == CharacterTab.DETAIL,
                            onClick = { viewModel.setTab(CharacterTab.DETAIL) },
                            text = { Text("Details") }
                        )
                        Tab(
                            selected = currentTab == CharacterTab.COMBATS,
                            onClick = { viewModel.setTab(CharacterTab.COMBATS) },
                            text = { Text("Ongoing Combats") }
                        )
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onEditCharacter(currentLoadout.character.instanceId, currentLoadout.character.charId) }
                ) {
                    Text("✏️")
                }
            }

        ) { padding ->
            when (currentTab) {
                CharacterTab.DETAIL -> {
                    LazyColumn (
                        modifier = Modifier.padding(padding)
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(24.dp)
                            ) {

                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text("Strength: ${currentLoadout.character.baseSkills.strength}")
                                    Text("Resilience: ${currentLoadout.character.baseSkills.resilience}")
                                    Text("Agility: ${currentLoadout.character.baseSkills.agility}")
                                }

                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text("Grit: ${currentLoadout.character.baseSkills.grit}")
                                    Text("Conviction: ${currentLoadout.character.baseSkills.conviction}")
                                    Text("Logic: ${currentLoadout.character.baseSkills.logic}")
                                }
                            }
                        }
                        item { HorizontalDivider() }
                        item {
                            Text("Actions", style = MaterialTheme.typography.titleMedium)
                            Column (
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                actions.forEach { action ->
                                    Text(action.name, style = MaterialTheme.typography.titleSmall)
                                    Text("${action.diceThrows}d${action.skill} ${action.behavior}")
                                }
                            }

                            HorizontalDivider()

                            Text("Abilities", style = MaterialTheme.typography.titleMedium)
                            Column (
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                abilities.forEach { ability ->
                                    Text(ability.name, style = MaterialTheme.typography.titleSmall)
                                    Text("${ability.diceThrows}d${ability.skill} ${ability.behavior}")
                                }
                            }

                            HorizontalDivider()

                            Text("Equipment", style = MaterialTheme.typography.titleMedium)
                            Text("Weapon: ${currentLoadout.weapon?.name ?: "None"}")
                            Text("Armor: ${currentLoadout.armor?.name ?: "None"}")
                        }
                    }
                }

                CharacterTab.COMBATS -> {
                    ChCombatList(
                        combats = combats,
                        onCombatClick = onOpenCombat,
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChCombatList (
    combats: List<CombatWithSaves>,
    onCombatClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn (modifier = modifier.fillMaxSize()) {
        items (combats) { combatWithParticipants ->
            val combat = combatWithParticipants.combat
            val saves = combatWithParticipants.savesWithCharacter

            ListItem(
                headlineContent = { Text("Combat ${combat.combatId}") },
                supportingContent = { Text (
                    saves.joinToString (
                        separator = ", "
                    ) { it.character.name }
                ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background (MaterialTheme.colorScheme.background)
                    .clickable {
                        onCombatClick(combat.combatId)
                    }
            )
            HorizontalDivider()
        }
    }
}