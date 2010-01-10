package Fix;

import java.sql.Types;

import org.hibernate.dialect.MySQLDialect;

public class MySQLDialectBitFix extends MySQLDialect {

	public MySQLDialectBitFix()
	{
		super();
		registerColumnType(Types.BIT, "tinyint(1)");
	}
}
