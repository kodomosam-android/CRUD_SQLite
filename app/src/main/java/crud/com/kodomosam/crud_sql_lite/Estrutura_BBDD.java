package crud.com.kodomosam.crud_sql_lite;

import android.provider.BaseColumns;

/**
 * Created by kodom on 02/04/2018.
 */

public class Estrutura_BBDD {
        // To prevent someone from accidentally instantiating the contract class,
        // make the constructor private.
        private Estrutura_BBDD() {}

        /* Inner class that defines the table contents */
        //public static class FeedEntry implements BaseColumns {

            public static final String TABLE_NAME = "dadosPersonales";
            public static final String NOME_COLUNA1 = "Id";
            public static final String NOME_COLUNA2 = "Nome";
            public static final String NOME_COLUNA3 = "Apelido";

        //}
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Estrutura_BBDD.TABLE_NAME + " (" +
                        Estrutura_BBDD.NOME_COLUNA1 + " INTEGER PRIMARY KEY," +
                        Estrutura_BBDD.NOME_COLUNA2 + " TEXT," +
                        Estrutura_BBDD.NOME_COLUNA3 + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estrutura_BBDD.TABLE_NAME;

    }


