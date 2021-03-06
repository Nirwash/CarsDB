package data

import android.provider.BaseColumns

object DatabaseContract {
    object DatabaseEntry: BaseColumns {
        const val TABLE_NAME = "cars"
        const val KEY_ID = "id"
        const val KEY_NAME = "name"
        const val KEY_PRICE = "price"

        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                    "$KEY_ID INTEGER PRIMARY KEY," +
                    "$KEY_NAME TEXT," +
                    "$KEY_PRICE TEXT)"

        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}