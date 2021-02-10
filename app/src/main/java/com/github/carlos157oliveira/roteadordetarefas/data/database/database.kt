package com.github.carlos157oliveira.roteadordetarefas.data.database

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.carlos157oliveira.roteadordetarefas.data.dao.TarefaDAO
import com.github.carlos157oliveira.roteadordetarefas.data.converters.DateConverter
import com.github.carlos157oliveira.roteadordetarefas.data.dao.PessoaDAO
import com.github.carlos157oliveira.roteadordetarefas.data.dao.TarefaPessoasDAO
import com.github.carlos157oliveira.roteadordetarefas.data.model.Pessoa
import com.github.carlos157oliveira.roteadordetarefas.data.model.Tarefa
import java.util.*
import java.util.concurrent.Executors


@Database(entities = [Tarefa::class, Pessoa::class], version = 2)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val tarefaDAO: TarefaDAO
    abstract val pessoaDAO: PessoaDAO
    abstract val tarefaPessoasDAO: TarefaPessoasDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "tarefas_db_test3")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //db.execSQL("INSERT INTO tarefas VALUES(0, \"Teste 0\", 0)")
                        //db.execSQL("INSERT INTO tarefas VALUES(1, \"Teste 1\", 0)")
                    }
                })
                .build()
        }

    }

}