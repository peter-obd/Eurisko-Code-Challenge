package database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [SignUpEntity::class], version = 3, exportSchema = false)
abstract class SignUpDatabase : RoomDatabase() {

    abstract val registerDatabaseDao: SignUpDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: SignUpDatabase? = null


        fun getInstance(context: Context): SignUpDatabase {
            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SignUpDatabase::class.java,
                        "user_details_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}