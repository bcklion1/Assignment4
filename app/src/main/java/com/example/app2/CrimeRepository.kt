package com.example.app2

import android.content.Context
import androidx.room.Room
import database.CrimeDatabase
import kotlinx.coroutines.flow.Flow
import java.util.UUID

private const val DATABASE_NAME = "crime-database"
class CrimeRepository private constructor(context: Context) {
    private val database:CrimeDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            CrimeDatabase::class.java,
            DATABASE_NAME
        )
        .createFromAsset(DATABASE_NAME)
        .build()

    fun getCrimes(): Flow<List<Crime>> = database.crimeDao().getCrimes()

    suspend fun getCrime(id : UUID): Crime = database.crimeDao().getCrime(id)

    companion object{
        private var INSTANCE: CrimeRepository? = null

        fun initalize(context:Context){
            if (INSTANCE == null){
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository{
            return INSTANCE?:
            throw java.lang.IllegalStateException("CrimeRepository must be initalized")
        }
    }
}