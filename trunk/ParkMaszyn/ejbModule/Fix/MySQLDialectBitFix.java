package Fix;

import java.sql.Types;

import org.hibernate.dialect.MySQL5InnoDBDialect;

public class MySQLDialectBitFix extends MySQL5InnoDBDialect {

	public MySQLDialectBitFix()
	{
		super();
		registerColumnType(Types.BIT, "tinyint(1)"); 
	}
}
