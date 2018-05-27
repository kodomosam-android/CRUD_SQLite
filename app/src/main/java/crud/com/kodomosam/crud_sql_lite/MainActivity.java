package crud.com.kodomosam.crud_sql_lite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnAtualizar, btnDeletar, btnBuscar;

    EditText textId, textNome, textApelido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = (Button)findViewById(R.id.insert);
        btnAtualizar = (Button)findViewById(R.id.atualizar);
        btnDeletar = (Button)findViewById(R.id.deletar);
        btnBuscar = (Button)findViewById(R.id.buscar);

        textId = (EditText)findViewById(R.id.id);
        textNome = (EditText)findViewById(R.id.nome);
        textApelido = (EditText)findViewById(R.id.apelido);

        final BBDD_Helper helper = new BBDD_Helper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Gets the data repository in write mode
                SQLiteDatabase db = helper.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(Estrutura_BBDD.NOME_COLUNA1, textId.getText().toString());
                values.put(Estrutura_BBDD.NOME_COLUNA2, textNome.getText().toString());
                values.put(Estrutura_BBDD.NOME_COLUNA3, textApelido.getText().toString());

                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(Estrutura_BBDD.TABLE_NAME, null, values);

                if(newRowId == -1){
                    Toast.makeText(getApplicationContext(),"O registro ja existe " + newRowId, Toast.LENGTH_LONG).show();
                    textId.setText("");
                    textNome.setText("");
                    textApelido.setText("");
                }


            }
        });

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = helper.getWritableDatabase();

                // New value for one column
                String title = "MyNewTitle";
                ContentValues values = new ContentValues();
                values.put(Estrutura_BBDD.NOME_COLUNA2, textNome.getText().toString());
                values.put(Estrutura_BBDD.NOME_COLUNA3, textApelido.getText().toString());

                // Which row to update, based on the title
                String selection = Estrutura_BBDD.NOME_COLUNA1 + " LIKE ?";
                String[] selectionArgs = { textId.getText().toString() };

                int count = db.update(
                        Estrutura_BBDD.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);

                Toast.makeText(getApplicationContext(),"Registro atualizado ", Toast.LENGTH_LONG).show();

            }
        });

        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Gets the data repository in write mode
                SQLiteDatabase db = helper.getWritableDatabase();
                // Define 'where' part of query.
                String selection = Estrutura_BBDD.NOME_COLUNA1 + " LIKE ?";
                // Specify arguments in placeholder order.
                String[] selectionArgs = { textId.getText().toString() };
                // Issue SQL statement.
                int deletedRows = db.delete(Estrutura_BBDD.TABLE_NAME, selection, selectionArgs);

                Toast.makeText(getApplicationContext(),"Registro numero " + textId.getText().toString() + " apagado", Toast.LENGTH_LONG).show();

            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = helper.getReadableDatabase();

                // Define a projection that specifies which columns from the database
                // you will actually use after this query.
                String[] projection = {
                        //BaseColumns._ID,
                        Estrutura_BBDD.NOME_COLUNA2,
                        Estrutura_BBDD.NOME_COLUNA3
                };

                // Filter results WHERE "title" = 'My Title'
                String selection = Estrutura_BBDD.NOME_COLUNA1 + " = ?";
                String[] selectionArgs = { textId.getText().toString() };

                // How you want the results sorted in the resulting Cursor
                /*String sortOrder =
                        FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";*/

                try {

                    Cursor cursor = db.query(
                            Estrutura_BBDD.TABLE_NAME,   // The table to query
                            projection,             // The array of columns to return (pass null to get all)
                            selection,              // The columns for the WHERE clause
                            selectionArgs,          // The values for the WHERE clause
                            null,                   // don't group the rows
                            null,                   // don't filter by row groups
                            null               // The sort order
                    );

                    cursor.moveToFirst();

                    textNome.setText(cursor.getString(0));
                    textApelido.setText(cursor.getString(1));

                }catch (Exception e){

                    Toast.makeText(getApplicationContext(),"Sem registros ", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}
