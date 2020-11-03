package es.iessaladillo.pedrojoya.intents.data.local

import android.content.res.Resources
import es.iessaladillo.pedrojoya.intents.R
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import android.content.Context

import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

// TODO: Haz que Database implemente DataSource
object Database : DataSource{
    val pikasho: Pokemon = Pokemon(1, R.drawable.pikachu, R.string.pikachu_name, 8)

    val giratina: Pokemon = Pokemon(2, R.drawable.giratina, R.string.giratina_name, 10)

    val cubone: Pokemon = Pokemon(3, R.drawable.cubone, R.string.cubone_name, 5)

    val gyarados: Pokemon = Pokemon(4, R.drawable.gyarados, R.string.gyarados_name, 7)

    val feebas: Pokemon = Pokemon(5, R.drawable.feebas, R.string.feebas_name, 1)

    val bulbasur: Pokemon = Pokemon(6, R.drawable.bulbasur, R.string.bulbasur_name, 6)

    val lista: List<Pokemon> = listOf(pikasho, giratina, cubone, gyarados, feebas, bulbasur)


    override fun getRandomPokemon(): Pokemon {
        var pokemon : Pokemon
        var random = nextInt(6)

        return lista[random]

    }

    override fun getAllPokemons(): List<Pokemon> {
        return lista
    }

    override fun getPokemonById(id: Long): Pokemon? {
        lista.forEach(){
            if (it.id == id){
                return it
            }

        }
        return null
    }






}