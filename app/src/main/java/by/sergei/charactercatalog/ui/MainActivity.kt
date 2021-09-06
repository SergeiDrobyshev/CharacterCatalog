package by.sergei.charactercatalog.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import by.sergei.charactercatalog.R
import by.sergei.charactercatalog.Screens
import by.sergei.charactercatalog.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewBindingActivity by viewBinding(ActivityMainBinding::class.java)

    private val router: Router by inject()
    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator: Navigator by lazy {
        object : AppNavigator(this, viewBindingActivity.fragmentMainContainer.id) {
            override fun applyCommands(commands: Array<out Command>) {
                super.applyCommands(commands)
                supportFragmentManager.executePendingTransactions()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            router.newRootScreen(Screens.charactersList())
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}