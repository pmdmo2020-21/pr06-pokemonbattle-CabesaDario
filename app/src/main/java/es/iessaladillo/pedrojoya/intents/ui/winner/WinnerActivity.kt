package es.iessaladillo.pedrojoya.intents.ui.winner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.BattleActivityBinding
import es.iessaladillo.pedrojoya.intents.databinding.WinnerActivityBinding
import es.iessaladillo.pedrojoya.intents.ui.selection.SelectionActivity

class WinnerActivity : AppCompatActivity() {
    private   var pokemon : Pokemon? = null
    private lateinit var binding: WinnerActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WinnerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        binding.imageGanador.setImageDrawable(getDrawable(pokemon!!.idIcon))
        binding.winnerText.text = getText(pokemon!!.idName)
    }

    companion object {

        const val EXTRA_POKEMON_ID = "EXTRA_POKEMON_ID"


        fun newIntent(context: Context, id: Long): Intent =
            Intent(context, WinnerActivity::class.java)
                .putExtras(
                    bundleOf(
                    EXTRA_POKEMON_ID to id)
                )

    }
    private fun getIntentData() {
        if (intent == null || !intent.hasExtra(WinnerActivity.EXTRA_POKEMON_ID)) {
            throw RuntimeException(
                "FighrActivity needs to receive pokemon id as extra")
        }
        pokemon = Database.getPokemonById(intent.getLongExtra(WinnerActivity.EXTRA_POKEMON_ID, 0))

    }


}