package todolist.studio.com.todolist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText txtInput;
    private Button btnAdd;
    private ListView listaTarefas;

    //Banco de dados
    private SQLiteDatabase dados;

    private ArrayAdapter<String> itensadaptador;
    private ArrayList<String> itens;

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

                    salvarTarefa(nometarefa);

                }
            });

            recuperarTarefas();


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void salvarTarefa(String texto){

        try{
            if(texto.equals("")){
                Toast.makeText(MainActivity.this,"Digite uma tarefa",Toast.LENGTH_SHORT).show();
            }else{
                dados.execSQL("INSERT INTO tarefas(task) VALUES ('" + texto +"')");
                Toast.makeText(MainActivity.this,"Tarefa salva",Toast.LENGTH_SHORT).show();
                recuperarTarefas();
                txtInput.setText("");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void recuperarTarefas(){

        try{
            // Recuperar as tarefas cadastradas
            Cursor cursor = dados.rawQuery("SELECT * FROM tarefas ORDER BY id DESC",null);

            int indice = cursor.getColumnIndex("id");
            int indiceTarefa = cursor.getColumnIndex("task");

            itens = new ArrayList<String>();
            itensadaptador = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_2,
                    android.R.id.text1,
                    itens);
            listaTarefas.setAdapter(itensadaptador);

            cursor.moveToFirst();

            while (cursor != null){

                int total = cursor.getCount();
                Log.i("Resultado - ", "Tarefa: " + cursor.getString(indiceTarefa));
                itens.add(cursor.getString(indiceTarefa));

                cursor.moveToNext();
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
