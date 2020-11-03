package es.iessaladillo.pedrojoya.intents.ui.battle

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.intents.R
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.data.local.Database.getPokemonById
import es.iessaladillo.pedrojoya.intents.data.local.Database.getRandomPokemon
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.BattleActivityBinding
import es.iessaladillo.pedrojoya.intents.ui.selection.SelectionActivity
import es.iessaladillo.pedrojoya.intents.ui.winner.WinnerActivity

private const val RC_POKEMON_SELECTION: Int = 1
private const val RC_POKEMON_FIGHT: Int = 1

class BattleActivity : AppCompatActivity() {

    private lateinit var pokemonA : Pokemon
    private lateinit var pokemonB : Pokemon

    private lateinit var binding:  BattleActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()

        pokemonA = getRandomPokemon()
        pokemonB = getRandomPokemon()

        binding.imageViewFightOne.setImageDrawable(getDrawable(pokemonA.idIcon))
        binding.imageViewFightTwo.setImageDrawable(getDrawable(pokemonB.idIcon))


        binding.textFightOne.text = getText(pokemonA.idName)


        binding.textFightTwo.text = getText(pokemonB.idName)






    }

    private fun setupViews() {
        binding = BattleActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.buttonFight.setOnClickListener { navigateToFightActivity() }

        binding.linearLayoutA.setOnClickListener { navigateToSelectionActivity(pokemonA, 1) }

        binding.linearLayoutB.setOnClickListener { navigateToSelectionActivity(pokemonB, 2) }

    }

    private fun navigateToFightActivity() {
        val winner : Pokemon
        if(pokemonA.forsa >= pokemonB.forsa){
            winner = pokemonA
        }else{
            winner=pokemonB
        }
        val intent = WinnerActivity.newIntent(this, winner.id)
        startActivityForResult(intent, RC_POKEMON_SELECTION)
    }

    private fun navigateToSelectionActivity(pokemon : Pokemon, screen : Int) {
        val intent = SelectionActivity.newIntent(this, pokemon.id, screen)
        startActivityForResult(intent, RC_POKEMON_FIGHT)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK && requestCode == RC_POKEMON_SELECTION && intent != null) {

            extractResult(intent)




        }

    }

    private fun extractResult(intent: Intent) {
        if (!intent.hasExtra(SelectionActivity.EXTRA_POKEMON_ID) || !intent.hasExtra(SelectionActivity.EXTRA_SCREEN) ) {
            throw RuntimeException(
                "BattleActivity must receive pokemonId and screen in result intent")
        }

        if (intent.getIntExtra(SelectionActivity.EXTRA_SCREEN, 1)==1){
            pokemonA = getPokemonById(intent.getLongExtra(SelectionActivity.EXTRA_POKEMON_ID,0))!!
            binding.imageViewFightOne.setImageDrawable(getDrawable(pokemonA.idIcon))
            binding.textFightOne.text = getText(pokemonA.idName)

        }else{
            pokemonB = getPokemonById(intent.getLongExtra(SelectionActivity.EXTRA_POKEMON_ID,0))!!
            binding.imageViewFightTwo.setImageDrawable(getDrawable(pokemonB.idIcon))
            binding.textFightTwo.text = getText(pokemonB.idName)
        }


    }

}