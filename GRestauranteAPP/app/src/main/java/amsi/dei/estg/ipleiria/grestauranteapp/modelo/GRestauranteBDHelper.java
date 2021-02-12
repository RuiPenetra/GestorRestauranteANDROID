package amsi.dei.estg.ipleiria.grestauranteapp.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GRestauranteBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "gestorrestauranteBD";
    private static final int DB_VERSION = 1;
    private static final String TABLE_PRODUTO = "produtos";
    private static final String ID_PRODUTO = "id_produto";
    private static final String NOME_PRODUTO = "nome";
    private static final String INGREDIENTES_PRODUTO = "ingredientes";
    private static final String PRECO_PRODUTO = "preco";
    private static final String ID_CATEGORIA = "id_categoria";
    private static final String TABLE_CARRINHO = "carrinho";
    private static final String ID_UTILIZADOR = "id_utilizador";
    private static final String QUANTIDADE = "quantidade";
    private static final String PRECO_TOTAL = "preco_total";
    private static final String ID_ITEM = "id_item";


    private final SQLiteDatabase db;

    public GRestauranteBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //código sql de criação de tabela
        String createTableProduto = "CREATE TABLE " + TABLE_PRODUTO + "( " +
                ID_PRODUTO + " INTEGER PRIMARY KEY, " +
                NOME_PRODUTO + " TEXT NOT NULL, " +
                INGREDIENTES_PRODUTO + " TEXT NOT NULL, " +
                PRECO_PRODUTO + " TEXT NOT NULL, " +
                ID_CATEGORIA + " INTEGER NOT NULL );";

        String createTableCarrinho = "CREATE TABLE " + TABLE_CARRINHO + "( " +
                ID_ITEM + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ID_UTILIZADOR + " INTEGER NOT NULL, " +
                ID_PRODUTO + " INTEGER NOT NULL, " +
                ID_CATEGORIA + " INTEGER NOT NULL, " +
                NOME_PRODUTO + " TEXT NOT NULL, " +
                QUANTIDADE + " INTEGER NOT NULL, " +
                PRECO_TOTAL + " TEXT NOT NULL );";

        db.execSQL(createTableProduto);
        db.execSQL(createTableCarrinho);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String deleteTableProduto = "DROP TABLE IF EXISTS " + TABLE_PRODUTO;
        String deleteTableCarrinho = "DROP TABLE IF EXISTS " + TABLE_CARRINHO;

        db.execSQL(deleteTableProduto);
        db.execSQL(deleteTableCarrinho);
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
        values.put(ID_CATEGORIA, produto.getCategoria());

        this.db.insert(TABLE_PRODUTO, null, values);

        return null;
    }

    public void removerAllProdutosBD() {
        this.db.delete(TABLE_PRODUTO,null, null);
    }

    public ArrayList<Produto> getAllProdutosBD() {
        ArrayList<Produto> produtos = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_PRODUTO, new String[]{ID_PRODUTO, NOME_PRODUTO, INGREDIENTES_PRODUTO, PRECO_PRODUTO, ID_CATEGORIA},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Produto auxProduto = new Produto(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));

                    produtos.add(auxProduto);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return produtos;
    }

    public ArrayList<Produto> getProdutosCategoriaBD(int id) {
        ArrayList<Produto> produtos = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_PRODUTO, new String[]{ID_PRODUTO, NOME_PRODUTO, INGREDIENTES_PRODUTO, PRECO_PRODUTO, ID_CATEGORIA},
                ID_CATEGORIA+"=?", new String[] {id+""}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Produto auxProduto = new Produto(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));

                //if (auxProduto.getCategoria() ==id) {

                    produtos.add(auxProduto);
                //}

            } while (cursor.moveToNext());
        }
        cursor.close();

        return produtos;
    }


    public ArrayList<Carrinho> getItemsCarrinhoBD(int id_utilizador) {
        ArrayList<Carrinho> itemsCarrinho = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_CARRINHO, new String[]{ID_ITEM,ID_UTILIZADOR, ID_PRODUTO,ID_CATEGORIA, NOME_PRODUTO, QUANTIDADE, PRECO_TOTAL},
                ID_UTILIZADOR +"=?", new String[] {id_utilizador+""}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Carrinho auxCarrinho = new Carrinho(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6));

                itemsCarrinho.add(auxCarrinho);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return itemsCarrinho;
    }

    public Carrinho adicionarItemCarrinhoBD(Carrinho item) {
        ContentValues values = new ContentValues();
        values.put(ID_UTILIZADOR, item.getId_utilizador());
        values.put(ID_PRODUTO, item.getId_produto());
        values.put(ID_CATEGORIA, item.getId_categoria());
        values.put(NOME_PRODUTO, item.getNome_produto());
        values.put(QUANTIDADE, item.getQuantidade());
        values.put(PRECO_TOTAL, item.getPreco());

        this.db.insert(TABLE_CARRINHO, null, values);

        return null;
    }

    public boolean removerAllItemsCarrinhoBD(int id_utilizador) {
/*        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = this.db.query(TABLE_CARRINHO, new String[]{ID_UTILIZADOR, ID_PRODUTO, NOME_PRODUTO, QUANTIDADE, PRECO_TOTAL,ID_CATEGORIA},
                ID_UTILIZADOR+"=?", new String[] {id+""}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                db.execSQL("DELETE FROM "+ TABLE_CARRINHO + " WHERE " + ID_UTILIZADOR + " = "+id+"");

            } while (cursor.moveToNext());
        }
        cursor.close();*/

        int nRows=this.db.delete(TABLE_CARRINHO,ID_UTILIZADOR + " = ?", new String[]{id_utilizador+""});
        return (nRows>0);
    }

    public boolean removerItemCarrinhoBD(Carrinho item){
        int nRows=this.db.delete(TABLE_CARRINHO,"id_item = ?" , new String[]{item.getId()+""});
        return (nRows>0);
    }

    public boolean editarItemCarrinhoBD(Carrinho item) {
        ContentValues values=new ContentValues();
        values.put(ID_UTILIZADOR, item.getId_utilizador());
        values.put(ID_PRODUTO, item.getId_produto());
        values.put(NOME_PRODUTO, item.getNome_produto());
        values.put(QUANTIDADE, item.getQuantidade());
        values.put(PRECO_TOTAL, item.getPreco());
        values.put(ID_CATEGORIA, item.getId_categoria());

        int nRows=this.db.update(TABLE_CARRINHO,values, "id_item = ?", new String[]{item.getId()+""});

        return (nRows>0);
    }

}
