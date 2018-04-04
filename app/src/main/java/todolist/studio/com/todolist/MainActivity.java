package todolist.studio.com.todolist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private EditText txtInput;
    private Button btnAdd;
    private ListView listaTarefas;

    //Banco de dados
    private SQLiteDatabase dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            txtInput = findViewById(R.id.textoID);
            btnAdd = findViewById(R.id.btnAddID);
            listaTarefas = findViewById(R.id.lstViewID);

//        gerar banco
            dados = openOrCreateDatabase("tarefas",MODE_PRIVATE,null);

//        gerar tabelas

            dados.execSQL("CREATE TABLE IF NOT EXISTS tarefas(id INTEGER PRIMARY KEY AUTOINCREMENT, task VARCHAR)");

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String nometarefa = txtInput.getText().toString();
                    dados.execSQL("INSERT INTO tarefas(task) VALUES ('" + nometarefa +"')");

                }
            });
            // Recuperar as tarefas cadastradas
            Cursor cursor = dados.rawQuery("SELECT * FROM tarefas",null);
            int indice = cursor.getColumnIndex("id");
            int indiceTarefa = cursor.getColumnIndex("task");

            cursor.moveToFirst();

            while (cursor != null){

                Log.i("Resultado - ", "Tarefa: " + cursor.getString(indiceTarefa));

                cursor.moveToNext();
            }


        }catch (Exception e){
            e.printStackTrace();
        }




    }
}
