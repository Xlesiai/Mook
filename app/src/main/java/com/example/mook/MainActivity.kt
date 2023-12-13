package com.example.mook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.mook.database.LibraryBook
import com.example.mook.database.LibraryDatabase
import com.example.mook.database.LibraryViewModel
import com.example.mook.dialogs.BookDialog
import com.example.mook.fragments.AddBooks
import com.example.mook.fragments.Library
import com.example.mook.fragments.Settings
import com.example.mook.fragments.Trending
import com.example.mook.navigation.Drawer
import com.example.mook.ui.theme.MookTheme


class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            LibraryDatabase::class.java,
            "library.db"
        ).build()
    }
    private val viewModel by viewModels<LibraryViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return LibraryViewModel(db.dao) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MookTheme {
                val state by viewModel.state.collectAsState()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            Drawer(navController, scope, drawerState)
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "library"
                    ) {
                        composable("library")   { Library(state, navController)}
                        composable("trending")  { Trending() }
                        composable("settings")  { Settings() }
                        composable("add books") { AddBooks(state, viewModel)}
                    }
                }

            }
        }
    }
}

// ---------------------------Preview---------------------------
@Preview(showBackground = true)
@Composable
fun prev() {
    MookTheme {
        BookDialog(LibraryBook("Harry", "Rowling", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Eget egestas purus viverra accumsan in. Blandit cursus risus at ultrices. Eget est lorem ipsum dolor sit amet consectetur adipiscing elit. Mattis nunc sed blandit libero volutpat sed. Viverra tellus in hac habitasse platea dictumst vestibulum rhoncus est. Lacus viverra vitae congue eu consequat. Elementum integer enim neque volutpat ac tincidunt vitae semper quis. Mattis ullamcorper velit sed ullamcorper morbi. Dictum varius duis at consectetur lorem. Mattis pellentesque id nibh tortor id. Nisl purus in mollis nunc sed. Integer malesuada nunc vel risus commodo.\n" + "\n" + "Amet mattis vulputate enim nulla aliquet porttitor. Risus nullam eget felis eget nunc lobortis mattis aliquam faucibus. Velit egestas dui id ornare arcu odio ut sem nulla. Velit ut tortor pretium viverra suspendisse. Scelerisque purus semper eget duis at tellus at urna condimentum. Cras sed felis eget velit aliquet sagittis id. Bibendum neque egestas congue quisque egestas diam in arcu. Turpis egestas maecenas pharetra convallis posuere morbi leo. Phasellus faucibus scelerisque eleifend donec pretium. Sem integer vitae justo eget magna fermentum iaculis. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc. Vitae congue mauris rhoncus aenean vel elit. Consectetur libero id faucibus nisl tincidunt. Morbi enim nunc faucibus a pellentesque sit. Orci phasellus egestas tellus rutrum tellus pellentesque. Vel quam elementum pulvinar etiam non quam. Consequat id porta nibh venenatis cras. Mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus et. Ut morbi tincidunt augue interdum velit euismod in pellentesque. Eget lorem dolor sed viverra ipsum nunc.\n" + "\n" + "Hendrerit gravida rutrum quisque non tellus orci ac auctor. Scelerisque eu ultrices vitae auctor eu augue. Convallis convallis tellus id interdum velit laoreet id. Id venenatis a condimentum vitae sapien pellentesque habitant morbi. Facilisis leo vel fringilla est ullamcorper eget nulla facilisi. Augue neque gravida in fermentum et sollicitudin ac orci. Pellentesque id nibh tortor id aliquet lectus proin nibh. Eu sem integer vitae justo eget magna. Metus dictum at tempor commodo. Eget gravida cum sociis natoque penatibus et. Urna nunc id cursus metus aliquam eleifend mi. Commodo viverra maecenas accumsan lacus. Lectus urna duis convallis convallis. Egestas diam in arcu cursus euismod quis viverra nibh.\n" + "\n" + "Vitae tempus quam pellentesque nec nam aliquam. Tincidunt eget nullam non nisi est sit amet facilisis. Eu feugiat pretium nibh ipsum consequat nisl vel pretium. Scelerisque purus semper eget duis at tellus at urna condimentum. Habitasse platea dictumst quisque sagittis purus sit amet volutpat consequat. Donec ultrices tincidunt arcu non sodales neque. Risus pretium quam vulputate dignissim suspendisse in est ante. Mauris sit amet massa vitae tortor. Nisi scelerisque eu ultrices vitae auctor eu augue. Dui ut ornare lectus sit amet est. Viverra accumsan in nisl nisi scelerisque. Velit aliquet sagittis id consectetur purus ut faucibus. Odio euismod lacinia at quis risus.\n" + "\n" + "Eget nulla facilisi etiam dignissim diam quis enim lobortis. Phasellus egestas tellus rutrum tellus. Lectus magna fringilla urna porttitor. Velit sed ullamcorper morbi tincidunt ornare massa eget. Nibh tortor id aliquet lectus proin nibh nisl condimentum id. Eget mauris pharetra et ultrices. Diam phasellus vestibulum lorem sed risus ultricies. Ipsum nunc aliquet bibendum enim facilisis. Vulputate ut pharetra sit amet aliquam id. Maecenas volutpat blandit aliquam etiam erat velit scelerisque in dictum. Nulla at volutpat diam ut venenatis tellus in. Dignissim convallis aenean et tortor. Nulla pellentesque dignissim enim sit amet venenatis urna cursus eget.\n" + "\n" + "Aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque. Placerat vestibulum lectus mauris ultrices eros in cursus turpis. Eros donec ac odio tempor. Nisl tincidunt eget nullam non nisi est sit amet. Fringilla phasellus faucibus scelerisque eleifend donec pretium vulputate sapien. Pellentesque dignissim enim sit amet. Diam vulputate ut pharetra sit amet. Et netus et malesuada fames ac turpis egestas sed. Sit amet cursus sit amet dictum sit amet justo. Risus nullam eget felis eget.\n" + "\n" + "Ornare arcu dui vivamus arcu felis bibendum ut tristique. Volutpat est velit egestas dui id ornare arcu odio ut. Lacinia quis vel eros donec ac odio tempor orci. Ut placerat orci nulla pellentesque dignissim enim sit amet venenatis. Vel elit scelerisque mauris pellentesque. Interdum velit laoreet id donec ultrices. Eleifend quam adipiscing vitae proin sagittis nisl rhoncus. Risus at ultrices mi tempus imperdiet. Consectetur adipiscing elit ut aliquam purus sit. Sit amet mauris commodo quis imperdiet massa. Blandit massa enim nec dui nunc. Vel quam elementum pulvinar etiam non quam lacus. Neque viverra justo nec ultrices dui sapien eget mi proin. Consequat interdum varius sit amet mattis vulputate enim. Diam maecenas ultricies mi eget. Sit amet aliquam id diam maecenas ultricies.\n" + "\n" + "Proin nibh nisl condimentum id. Lobortis mattis aliquam faucibus purus in massa tempor. Magna eget est lorem ipsum. Scelerisque fermentum dui faucibus in. Sem integer vitae justo eget magna fermentum. Nulla pellentesque dignissim enim sit amet venenatis urna cursus eget. Quam adipiscing vitae proin sagittis. Aliquam eleifend mi in nulla posuere sollicitudin aliquam. Bibendum neque egestas congue quisque egestas diam in arcu cursus. Aliquam etiam erat velit scelerisque. Nunc sed augue lacus viverra vitae. Mi tempus imperdiet nulla malesuada pellentesque elit eget gravida. Ac ut consequat semper viverra nam libero justo laoreet. Viverra tellus in hac habitasse platea. Posuere sollicitudin aliquam ultrices sagittis.\n" + "\n" + "Vestibulum rhoncus est pellentesque elit. Ac tortor vitae purus faucibus ornare suspendisse. At risus viverra adipiscing at in. Nunc aliquet bibendum enim facilisis gravida neque. Pellentesque nec nam aliquam sem et. Id porta nibh venenatis cras sed felis. Sed turpis tincidunt id aliquet risus. At quis risus sed vulputate odio ut enim. Convallis posuere morbi leo urna molestie at elementum eu. Enim neque volutpat ac tincidunt vitae semper.\n" + "\n" + "Ut sem viverra aliquet eget sit. Mi proin sed libero enim sed. Egestas tellus rutrum tellus pellentesque eu tincidunt tortor aliquam. Rutrum tellus pellentesque eu tincidunt tortor aliquam nulla facilisi. Tempus iaculis urna id volutpat lacus. Mi tempus imperdiet nulla malesuada pellentesque elit. Nibh cras pulvinar mattis nunc sed blandit libero volutpat sed. Lobortis scelerisque fermentum dui faucibus in ornare quam viverra orci. Tincidunt eget nullam non nisi est sit. Ut tristique et egestas quis ipsum suspendisse ultrices gravida. Enim eu turpis egestas pretium aenean pharetra magna. Dignissim sodales ut eu sem integer vitae justo. Nisl rhoncus mattis rhoncus urna neque viverra justo. Cursus vitae congue mauris rhoncus aenean vel. Cras ornare arcu dui vivamus arcu felis bibendum ut tristique. Ornare massa eget egestas purus viverra accumsan in.\n" + "\n" + "Ut morbi tincidunt augue interdum velit euismod in. Quis eleifend quam adipiscing vitae proin sagittis nisl. Purus ut faucibus pulvinar elementum. Posuere lorem ipsum dolor sit amet consectetur. Ornare arcu odio ut sem nulla pharetra. Feugiat nibh sed pulvinar proin. Molestie nunc non blandit massa enim nec. Id nibh tortor id aliquet lectus proin nibh nisl. Dolor sit amet consectetur adipiscing. Phasellus faucibus scelerisque eleifend donec pretium. At risus viverra adipiscing at in tellus integer feugiat. Maecenas ultricies mi eget mauris pharetra et ultrices neque ornare. Quam vulputate dignissim suspendisse in est ante in nibh mauris. Vitae auctor eu augue ut lectus arcu bibendum. Neque viverra justo nec ultrices dui sapien. Sagittis nisl rhoncus mattis rhoncus.\n" + "\n" + "In nulla posuere sollicitudin aliquam ultrices sagittis. Aliquet eget sit amet tellus cras adipiscing enim eu turpis. Velit laoreet id donec ultrices tincidunt arcu non. Ultrices eros in cursus turpis. Adipiscing vitae proin sagittis nisl rhoncus mattis. Aliquet eget sit amet tellus cras adipiscing enim eu. Cras tincidunt lobortis feugiat vivamus. Fames ac turpis egestas integer. Felis donec et odio pellentesque diam volutpat. Lectus proin nibh nisl condimentum id venenatis a condimentum. Blandit massa enim nec dui nunc. Pharetra vel turpis nunc eget lorem dolor. Eget dolor morbi non arcu risus quis varius quam quisque. Egestas pretium aenean pharetra magna ac placerat. Tincidunt eget nullam non nisi.\n" + "\n" + "Aliquam faucibus purus in massa tempor. Gravida quis blandit turpis cursus in. Mi sit amet mauris commodo quis imperdiet massa tincidunt. Aliquet nibh praesent tristique magna. Ac odio tempor orci dapibus ultrices in. Adipiscing elit duis tristique sollicitudin nibh. Sagittis eu volutpat odio facilisis mauris sit. Enim eu turpis egestas pretium aenean. Neque aliquam vestibulum morbi blandit cursus risus at. Placerat vestibulum lectus mauris ultrices eros in cursus turpis. Amet luctus venenatis lectus magna fringilla. Platea dictumst quisque sagittis purus. Rutrum quisque non tellus orci ac. Integer eget aliquet nibh praesent tristique magna sit amet purus. Vitae tempus quam pellentesque nec nam aliquam sem et tortor. At erat pellentesque adipiscing commodo elit at. Vestibulum sed arcu non odio euismod.\n" + "\n" + "Et pharetra pharetra massa massa ultricies mi quis hendrerit dolor. Aliquam faucibus purus in massa. Dignissim sodales ut eu sem integer vitae justo eget magna. Viverra suspendisse potenti nullam ac tortor. Sapien nec sagittis aliquam malesuada bibendum arcu vitae. Fringilla phasellus faucibus scelerisque eleifend donec pretium vulputate sapien nec. Id ornare arcu odio ut. Mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque. Fermentum odio eu feugiat pretium nibh ipsum consequat. Nisl nunc mi ipsum faucibus vitae aliquet nec ullamcorper. Consequat mauris nunc congue nisi vitae suscipit tellus. Vel quam elementum pulvinar etiam non. A pellentesque sit amet porttitor. Donec ultrices tincidunt arcu non sodales neque sodales ut. Est ultricies integer quis auctor elit sed. Libero nunc consequat interdum varius sit amet mattis vulputate enim. Nulla pharetra diam sit amet nisl suscipit adipiscing bibendum est. Etiam tempor orci eu lobortis elementum nibh tellus molestie.\n" + "\n" + "At consectetur lorem donec massa sapien faucibus et molestie ac. In hac habitasse platea dictumst quisque. Sagittis id consectetur purus ut faucibus pulvinar elementum integer enim. Vitae elementum curabitur vitae nunc sed velit dignissim sodales. Vitae congue mauris rhoncus aenean. At imperdiet dui accumsan sit amet. Mi ipsum faucibus vitae aliquet. At erat pellentesque adipiscing commodo elit at. Lacus vestibulum sed arcu non odio euismod lacinia at. Id interdum velit laoreet id donec ultrices tincidunt. Et sollicitudin ac orci phasellus. Rutrum tellus pellentesque eu tincidunt tortor aliquam nulla. Iaculis eu non diam phasellus. Sit amet mattis vulputate enim nulla.\n" + "\n" + "Est lorem ipsum dolor sit. Aliquet porttitor lacus luctus accumsan tortor. Lacus sed turpis tincidunt id aliquet risus. Feugiat in fermentum posuere urna nec tincidunt praesent. Nibh sit amet commodo nulla facilisi nullam vehicula ipsum. Consectetur adipiscing elit ut aliquam purus sit amet. Lorem ipsum dolor sit amet consectetur adipiscing elit. Volutpat commodo sed egestas egestas fringilla phasellus faucibus. Faucibus scelerisque eleifend donec pretium vulputate sapien nec. Venenatis tellus in metus vulputate eu scelerisque felis imperdiet. Ornare quam viverra orci sagittis eu volutpat odio facilisis.\n" + "\n" + "Amet mattis vulputate enim nulla. Lacus luctus accumsan tortor posuere ac. Dictum varius duis at consectetur. Egestas erat imperdiet sed euismod nisi. Ac odio tempor orci dapibus ultrices in iaculis. Quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. At tellus at urna condimentum mattis. Ac turpis egestas maecenas pharetra convallis posuere morbi leo. Tellus elementum sagittis vitae et leo duis ut. Parturient montes nascetur ridiculus mus mauris vitae. Eget aliquet nibh praesent tristique magna sit amet purus. Purus in massa tempor nec. Est placerat in egestas erat imperdiet. Suscipit tellus mauris a diam maecenas. Euismod lacinia at quis risus sed vulputate. Dictum varius duis at consectetur lorem donec massa sapien. Id porta nibh venenatis cras. Natoque penatibus et magnis dis parturient montes nascetur. Mollis aliquam ut porttitor leo a diam sollicitudin. Egestas congue quisque egestas diam in arcu.\n" + "\n" + "Lacinia quis vel eros donec ac odio tempor orci dapibus. Sed libero enim sed faucibus turpis in eu mi bibendum. Nulla pellentesque dignissim enim sit amet venenatis. Adipiscing at in tellus integer. Auctor urna nunc id cursus metus aliquam. In nisl nisi scelerisque eu ultrices vitae auctor eu. Lobortis mattis aliquam faucibus purus in massa. Sem integer vitae justo eget magna fermentum iaculis. Lacus laoreet non curabitur gravida arcu. Dui nunc mattis enim ut tellus elementum sagittis vitae.\n" + "\n" + "Cras adipiscing enim eu turpis egestas pretium. Nisl rhoncus mattis rhoncus urna neque viverra justo. In arcu cursus euismod quis viverra nibh cras pulvinar mattis. Habitasse platea dictumst vestibulum rhoncus est pellentesque elit ullamcorper. Senectus et netus et malesuada fames ac. Ullamcorper velit sed ullamcorper morbi tincidunt ornare massa eget egestas. Malesuada fames ac turpis egestas integer eget. Sit amet volutpat consequat mauris nunc congue nisi. Ipsum dolor sit amet consectetur adipiscing elit. Luctus accumsan tortor posuere ac. Vel quam elementum pulvinar etiam non quam lacus suspendisse. Mauris a diam maecenas sed enim.\n" + "\n" + "Amet nisl suscipit adipiscing bibendum est ultricies integer. Diam vel quam elementum pulvinar etiam non quam lacus. Morbi tincidunt augue interdum velit euismod in. Nulla facilisi etiam dignissim diam quis enim. Tellus orci ac auctor augue mauris augue neque gravida. Urna porttitor rhoncus dolor purus non enim praesent. Nunc consequat interdum varius sit amet mattis. Eget felis eget nunc lobortis mattis aliquam faucibus. Lorem mollis aliquam ut porttitor leo a diam sollicitudin tempor. Morbi non arcu risus quis varius quam quisque id. Venenatis tellus in metus vulputate eu scelerisque felis imperdiet. Quam viverra orci sagittis eu volutpat odio facilisis. Non tellus orci ac auctor augue mauris augue. Nunc scelerisque viverra mauris in aliquam sem fringilla."), onEvent = {}, modifier = Modifier)
    }
}