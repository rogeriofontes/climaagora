package climaagora.psi.com.br.climaagora.infra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * Implementacao de SQLiteOpenHelper
 * 
 * Classe utilit???ria para abrir, criar, e atualizar o banco de dados.
 *
 * @author Rog??rio Fontes - rogerio.fontes@pressystem.com.br
 */
class SQLiteHelper extends SQLiteOpenHelper {

	/** The Constant CATEGORIA. */
	private static final String CATEGORIA = "student";

	/** The script sql create. */
	private String[] scriptSQLCreate;
	
	/** The script sql delete. */
	private String scriptSQLDelete;

	/**
	 * Cria uma inst???ncia de SQLiteHelper.
	 *
	 * @param context the context
	 * @param nomeBanco nome do banco de dados
	 * @param versaoBanco vers???o do banco de dados (se for diferente ??? para atualizar)
	 * @param scriptSQLCreate SQL com o create table..
	 * @param scriptSQLDelete SQL com o drop table...
	 */
	SQLiteHelper(Context context, String nomeBanco, int versaoBanco, String[] scriptSQLCreate, String scriptSQLDelete) {
		super(context, nomeBanco, null, versaoBanco);
		this.scriptSQLCreate = scriptSQLCreate;
		this.scriptSQLDelete = scriptSQLDelete;
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	// Criar novo banco...
	public void onCreate(SQLiteDatabase db) {
		Log.i(CATEGORIA, "Criando banco com sql");
		int qtdeScriptsCreate = scriptSQLCreate.length;
		
		// Executa cada sql passado como par???metro dependendo dos scripts
		if (qtdeScriptsCreate > 0) {
			for (int i = 0; i < qtdeScriptsCreate; i++) {
				String sqlCreate = scriptSQLCreate[i];
				Log.i(CATEGORIA, sqlCreate);
				// Cria o banco de dados executando o script de cria??????o
				db.execSQL(sqlCreate);
			} 
		}
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	// Mudou a vers???o...
	public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
		Log.w(CATEGORIA, "Atualizando da vers???o " + versaoAntiga + " para " + novaVersao + ". Todos os registros ser???o deletados.");
		Log.i(CATEGORIA, scriptSQLDelete);
		// Deleta as tabelas...
		db.execSQL(scriptSQLDelete);
		// Cria novamente...
		onCreate(db);
	}
}