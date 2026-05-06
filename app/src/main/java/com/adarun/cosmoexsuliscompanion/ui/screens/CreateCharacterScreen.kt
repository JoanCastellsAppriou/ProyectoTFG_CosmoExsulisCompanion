package com.adarun.cosmoexsuliscompanion.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adarun.cosmoexsuliscompanion.data.model.Equipment
import com.adarun.cosmoexsuliscompanion.ui.viewmodel.CreateCharacterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCharacterScreen (
    viewModel: CreateCharacterViewModel,
    onDone: () -> Unit
) {
    val name = viewModel.name
    val stats by viewModel.skills.collectAsState()
    val weapons by viewModel.weapons.collectAsState()
    val armors by viewModel.armors.collectAsState()
    val isEquipmentReady by viewModel.isEquipmentReady.collectAsState()
    val defaultActions by viewModel.defaultActions.collectAsState()
    val availableAbilities by viewModel.availableAbilities.collectAsState()
    val chosenAbilities = viewModel.chosenAbilities.collectAsState()
    val isFormValid by viewModel.isFormValid.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Character Creation") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (isFormValid) viewModel.saveCharacter (onDone)
                },
                containerColor =
                    if (isFormValid) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text("✔")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            HorizontalDivider()

            Text("Skills", style = MaterialTheme.typography.titleMedium)
            Text("Points Spent (min. 30): ${stats.totalPoints()}")

            StatEditor(
                label = "Strength",
                value = stats.strength,
                onChange = { newValue ->
                    viewModel.updateStat { stats ->
                        stats.copy(strength = newValue)
                    }
                }
            )

            StatEditor(
                label = "Resilience",
                value = stats.resilience,
                onChange = { newValue ->
                    viewModel.updateStat { stats ->
                        stats.copy(resilience = newValue)
                    }
                }
            )

            StatEditor(
                label = "Agility",
                value = stats.agility,
                onChange = { newValue ->
                    viewModel.updateStat { stats ->
                        stats.copy(agility = newValue)
                    }
                }
            )

            StatEditor(
                label = "Grit",
                value = stats.grit,
                onChange = { newValue ->
                    viewModel.updateStat { stats ->
                        stats.copy(grit = newValue)
                    }
                }
            )

            StatEditor(
                label = "Conviction",
                value = stats.conviction,
                onChange = { newValue ->
                    viewModel.updateStat { stats ->
                        stats.copy(conviction = newValue)
                    }
                }
            )

            StatEditor(
                label = "Logic",
                value = stats.logic,
                onChange = { newValue ->
                    viewModel.updateStat { stats ->
                        stats.copy(logic = newValue)
                    }
                }
            )

            HorizontalDivider()

            Text ("Unlocked Actions", style = MaterialTheme.typography.titleMedium)

            if (defaultActions.isEmpty()) {
                Text ("No Unlocked Actions")
            }
            else {
                defaultActions.forEach { action ->
                    Text (action.name, style = MaterialTheme.typography.titleSmall)
                    Text ( "${action.diceThrows}d${action.skill} ${action.behavior}" )
                }
            }

            HorizontalDivider()

            Text ("Learnable Abilities", style = MaterialTheme.typography.titleMedium)
            if (availableAbilities.isEmpty()) {
                Text ("No Available Abilities")
            }
            else {
                availableAbilities.forEach { ability ->
                    val isSelected = chosenAbilities.value.contains(ability.actionCode)

                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text (ability.name, style = MaterialTheme.typography.titleSmall)

                        Checkbox (
                            checked = isSelected,
                            onCheckedChange = {
                                viewModel.toggleAbility(ability)
                            }
                        )
                    }
                }
            }

            HorizontalDivider()

            Text("Equipment", style = MaterialTheme.typography.titleMedium)

            if (isEquipmentReady) {
                EquipmentDropdown(
                    label = "Weapon",
                    items = weapons,
                    selectedCode = viewModel.weapon,
                    onSelect = viewModel::selectWeapon
                )

                EquipmentDropdown(
                    label = "Armor",
                    items = armors,
                    selectedCode = viewModel.armor,
                    onSelect = viewModel::selectArmor
                )
            } else {
                Text("Loading...", modifier = Modifier.padding(8.dp))
                CircularProgressIndicator()
            }

        }
    }
}

@Composable
fun StatEditor (
    label: String,
    value: Int,
    onChange: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(label)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button (
                onClick = { onChange(value - 1) }
            ) {
                Text("-")
            }

            Text(value.toString())

            Button (
                onClick = { onChange(value + 1) }
            ) {
                Text("+")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentDropdown (
    label: String,
    items: List<Equipment>,
    selectedCode: String?,
    onSelect: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val selectedItem = items.find { it.equipmentCode == selectedCode }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedItem?.name ?: "None",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem (
                text = { Text("None") },
                onClick = {
                    onSelect(null)
                    expanded = false
                }
            )

            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.name) },
                    onClick = {
                        onSelect (item.equipmentCode)
                        expanded = false
                    }
                )
            }
        }
    }
}