package com.sossgrid.datastore.schemarepo;

import com.sossgrid.datastore.Schema;

public abstract class AbstractSchemaRepo {
	public abstract Schema Get(String schemaGroup, String name);
	public abstract void Create(String schemaGroup, String name);
	public abstract boolean Check(String schemaGroup, String name);
}
