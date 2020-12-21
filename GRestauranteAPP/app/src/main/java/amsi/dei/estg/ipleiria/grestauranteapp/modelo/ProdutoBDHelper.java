package amsi.dei.estg.ipleiria.grestauranteapp.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProdutoBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "produtosBD";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "produtos";
    private static final String ID_PRODUTO = "id";
    private static final String NOME_PRODUTO = "nome";
    private static final String INGREDIENTES_PRODUTO = "ingredientes";
    private static final String PRECO_PRODUTO = "preco";
    private static final String CATEGORIA_PRODUTO = "id_categoria";

    private final SQLiteDatabase db;

    public ProdutoBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //código sql de criação de tabela
        String createTableProduto = "CREATE TABLE " + TABLE_NAME + "( " +
                ID_PRODUTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOME_PRODUTO + " TEXT NOT NULL, " +
                INGREDIENTES_PRODUTO + " TEXT NOT NULL, " +
                PRECO_PRODUTO + " TEXT NOT NULL, " +
                CATEGORIA_PRODUTO + " INTEGER NOT NULL );";

        db.execSQL(createTableProduto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String deleteTableProduto = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(deleteTableProduto);
        this.onCreate(db);
    }

    /*********************************************CRUD******************************************/

    /**
     * INSERT
     *
     * @param
     * @return
     */
    public Produto adicionarProdutoBD(Produto produto) {
        ContentValues values = new ContentValues();
        values.put(ID_PRODUTO, produto.getId());
        values.put(NOME_PRODUTO, produto.getNome());
        values.put(INGREDIENTES_PRODUTO, produto.getIngredientes());
        values.put(PRECO_PRODUTO, produto.getPreco());
        values.put(CATEGORIA_PRODUTO, produto.getCategoria());

        long id = this.db.insert(TABLE_NAME, null, values);

     /*   if(id>=1){
            produto.setID((int) id);----------------------------------------------------------------------------------
            return livro;
        }*/
        return null;
    }

    public void removerAllProdutosBD() {
        this.db.delete(TABLE_NAME, null, null);
    }

    public ArrayList<Produto> getAllProdutosBD() {
        ArrayList<Produto> produtos = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_NAME, new String[]{ID_PRODUTO, NOME_PRODUTO, INGREDIENTES_PRODUTO, PRECO_PRODUTO, CATEGORIA_PRODUTO},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Produto auxProduto = new Produto(cursor.getInt(0), cursor.getString(4), cursor.getString(5), cursor.getString(1), cursor.getInt(2));

                produtos.add(auxProduto);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return produtos;
    }

    public ArrayList<Produto> getProdutosCategoriaBD(int id) {
        ArrayList<Produto> produtos = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_NAME, new String[]{ID_PRODUTO, NOME_PRODUTO, INGREDIENTES_PRODUTO, PRECO_PRODUTO, CATEGORIA_PRODUTO},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Produto auxProduto = new Produto(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));

                if (auxProduto.getCategoria() == id) {

                    if (id<=6) {
                        produtos.add(auxProduto);
                    }else{
                        produtos.add(auxProduto);
                    }

                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        return produtos;
    }
}
