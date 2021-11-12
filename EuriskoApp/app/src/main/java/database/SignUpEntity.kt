package database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Signup_users_table")
data class SignUpEntity(@PrimaryKey(autoGenerate = true)
                        var userId: Int = 0,

                        @ColumnInfo(name = "first_name")
                        var firstName: String,

                        @ColumnInfo(name = "last_name")
                        var lastName: String,

                        @ColumnInfo(name = "email")
                        var email: String,

                        @ColumnInfo(name = "password_text")
                        var passwrd: String,

                        @ColumnInfo(name = "logged")
                        var logged: Boolean,)
{

}