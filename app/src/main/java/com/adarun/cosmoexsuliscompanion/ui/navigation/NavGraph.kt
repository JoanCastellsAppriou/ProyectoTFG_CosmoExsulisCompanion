package com.adarun.cosmoexsuliscompanion.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.adarun.cosmoexsuliscompanion.data.database.AppDatabase
import com.adarun.cosmoexsuliscompanion.data.repository.ActionRepository
import com.adarun.cosmoexsuliscompanion.data.repository.CharacterActionXrefRepository
import com.adarun.cosmoexsuliscompanion.data.repository.CharacterRepository
import com.adarun.cosmoexsuliscompanion.data.repository.CombatInstanceRepository
import com.adarun.cosmoexsuliscompanion.data.repository.EquipmentRepository
import com.adarun.cosmoexsuliscompanion.data.repository.InstanceRepository
import com.adarun.cosmoexsuliscompanion.ui.screens.CharacterDetailScreen
import com.adarun.cosmoexsuliscompanion.ui.screens.CreateCharacterScreen
import com.adarun.cosmoexsuliscompanion.ui.screens.InstanceDetailScreen
import com.adarun.cosmoexsuliscompanion.ui.screens.InstanceListScreen
import com.adarun.cosmoexsuliscompanion.ui.viewmodel.CharacterDetailViewModel
import com.adarun.cosmoexsuliscompanion.ui.viewmodel.CreateCharacterViewModel
import com.adarun.cosmoexsuliscompanion.ui.viewmodel.InstanceDetailViewModel
import com.adarun.cosmoexsuliscompanion.ui.viewmodel.InstanceListViewModel
import com.adarun.cosmoexsuliscompanion.ui.viewmodel.SimpleFactory

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val db = AppDatabase.getDatabase(context)
    val instanceRepo = InstanceRepository(db.instanceDao())

    NavHost(
        navController = navController,
        startDestination = NavRoutes.InstanceList.route
    ) {
        composable (NavRoutes.InstanceList.route) {
            val viewModel: InstanceListViewModel = viewModel(
                factory = SimpleFactory { InstanceListViewModel(instanceRepo) }
            )

            InstanceListScreen(
                viewModel = viewModel,
                onInstanceClick = { id ->
                    navController.navigate(
                        NavRoutes.InstanceDetail.creationRoute(id)
                    )
                }
            )
        }

        composable (NavRoutes.InstanceDetail.route) { backStackEntry ->
            val context = LocalContext.current
            val db = AppDatabase.getDatabase(context)

            val instanceId = backStackEntry.arguments
                ?.getString("instanceId")!!
                .toInt()

            val characterRepo = CharacterRepository(db.characterDao())
            val combatRepo = CombatInstanceRepository(
                db.combatInstanceDao(),
                db.characterCombatSaveDao(),
                characterRepo
            )

            val viewModel: InstanceDetailViewModel = viewModel(
                factory = SimpleFactory {
                    InstanceDetailViewModel(instanceId, characterRepo, combatRepo)
                }
            )

            InstanceDetailScreen (
                viewModel = viewModel,
                onCharacterClick = { id ->
                    navController.navigate(
                        NavRoutes.CharacterDetail.creationRoute(id)
                    )
                },
                onCreateCharacter = {
                    navController.navigate(
                        NavRoutes.CreateCharacter.creationRoute(instanceId)
                    )
                },
                onOpenCombat = { combatId ->
                    android.util.Log.d("NAV", "Abrir combate $combatId")
                }
            )
        }

        composable (NavRoutes.CharacterDetail.route) { backStackEntry ->
            val context = LocalContext.current
            val db = AppDatabase.getDatabase(context)

            val characterId = backStackEntry.arguments
                ?.getString("characterId")!!
                .toInt()

            val characterRepo = CharacterRepository(db.characterDao())
            val combatRepo = CombatInstanceRepository(
                db.combatInstanceDao(),
                db.characterCombatSaveDao(),
                characterRepo
            )

            val viewModel: CharacterDetailViewModel = viewModel(
                factory = SimpleFactory {
                    CharacterDetailViewModel(characterId, characterRepo, combatRepo)
                }
            )

            CharacterDetailScreen(
                viewModel = viewModel,
                onEditCharacter = { charId ->
                    navController.navigate(
                        NavRoutes.CreateCharacter.creationRoute(charId)
                    )
                },
                onOpenCombat = { combatId ->
                    android.util.Log.d("NAV", "Abrir combate $combatId")
                }
            )
        }

        composable (NavRoutes.CreateCharacter.route) { backStackEntry ->
            val context = LocalContext.current
            val db = AppDatabase.getDatabase(context)

            val instanceId = backStackEntry.arguments!!
                .getString("instanceId")!!
                .toInt()

            val repo = CharacterRepository(db.characterDao())
            val equipmentRepo = EquipmentRepository(db.equipmentDao())
            val actionRepo = ActionRepository(db.actionDao())
            val chAcCrossReference = CharacterActionXrefRepository(db.characterActionXrefDao())

            val viewModel: CreateCharacterViewModel = viewModel (
                factory = SimpleFactory {
                    CreateCharacterViewModel(instanceId, repo, equipmentRepo, actionRepo, chAcCrossReference)
                }
            )

            CreateCharacterScreen(
                viewModel = viewModel,
                onDone = {
                    navController.popBackStack()
                }
            )
        }
    }
}