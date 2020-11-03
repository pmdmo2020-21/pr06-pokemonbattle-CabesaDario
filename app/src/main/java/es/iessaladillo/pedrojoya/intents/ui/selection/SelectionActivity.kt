package es.iessaladillo.pedrojoya.intents.ui.selection

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.loader.ResourcesProvider
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.get
import es.iessaladillo.pedrojoya.intents.R
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.data.local.Database.getAllPokemons

import es.iessaladillo.pedrojoya.intents.data.local.Database.getPokemonById
import es.iessaladillo.pedrojoya.intents.data.local.Database.getRandomPokemon
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.BattleActivityBinding
import es.iessaladillo.pedrojoya.intents.databinding.SelectionActivityBinding

class SelectionActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_POKEMON_ID = "EXTRA_POKEMON_ID"
        const val EXTRA_SCREEN = "EXTRA_SCREEN"

        fun newIntent(context: Context, id: Long, screen: Int): Intent =
            Intent(context, SelectionActivity::class.java)
                .putExtras(bundleOf(
                    EXTRA_POKEMON_ID to id, EXTRA_SCREEN to screen))

    }
    private  var lista: List<Pokemon> = getAllPokemons()
    private   var pokemon : Pokemon? = null

    private var screen = 1

    private lateinit var binding: SelectionActivityBinding

    private lateinit var radioSearch : String


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding = SelectionActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)


        getIntentData()


        binding.radioGroup.clearCheck()

        binding.radioGroup2.clearCheck()

        var nameOfresoruce="radio"+getString(pokemon!!.idName)



        var identifier = resources.getIdentifier(nameOfresoruce, "id", this.packageName)


        binding.radioGroup.check(identifier)

        binding.radioGroup2.check(identifier)


        binding.radioGroup.setOnCheckedChangeListener {L, B -> clearCheck(L, B, binding.radioGroup2)}

        binding.radioGroup2.setOnCheckedChangeListener {L, B -> clearCheck(L, B, binding.radioGroup)}



    }



    private fun clearCheck(group: RadioGroup, checkedId: Int, radioGroup: RadioGroup) {

        var name : String

        radioGroup.setOnCheckedChangeListener(null);

        name = getText(checkedId).toString()

        var radioCheckedButton : RadioButton = findViewById(checkedId)

        Database.lista.forEach(){
            if (getString(it.idName) == radioCheckedButton.text){
                pokemon = getPokemonById(it.id)
            }

        }
        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener { group1, checkedId ->
            clearCheck(group1,
                checkedId,
                group)
        };












    }


    override fun onBackPressed() {


        val result = Intent().putExtras(bundleOf(EXTRA_POKEMON_ID to pokemon!!.id, EXTRA_SCREEN to screen))
        setResult(RESULT_OK, result)
        super.onBackPressed()










    }




    private fun getIntentData() {
        if (intent == null || !intent.hasExtra(EXTRA_POKEMON_ID)) {
            throw RuntimeException(
                "SelectionActivity needs to receive pokemon id as extra")
        }
        screen=intent.getIntExtra(EXTRA_SCREEN,1)
        pokemon = getPokemonById(intent.getLongExtra(EXTRA_POKEMON_ID,0))

    }

}