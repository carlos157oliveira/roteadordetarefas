package com.github.carlos157oliveira.roteadordetarefas.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.carlos157oliveira.roteadordetarefas.data.dao.TarefaDAO
import com.github.carlos157oliveira.roteadordetarefas.data.converters.DateConverter
import com.github.carlos157oliveira.roteadordetarefas.data.dao.TarefaPessoasDAO
import com.github.carlos157oliveira.roteadordetarefas.data.model.Pessoa
import com.github.carlos157oliveira.roteadordetarefas.data.model.Tarefa

@Database(entities = [Tarefa::class, Pessoa::class], version = 2)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val tarefaDAO: TarefaDAO
    abstract val tarefaPessoasDAO: TarefaPessoasDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance: AppDatabase? = INSTANCE
                if (instance == null) {

                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                }
                return instance
            }
        }
    }
}