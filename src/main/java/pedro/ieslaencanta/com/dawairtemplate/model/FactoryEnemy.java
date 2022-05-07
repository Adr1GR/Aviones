/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author adria
 */
public class FactoryEnemy {

    private static HashMap<String, Supplier<AEnemy>> enemigos;
    private static ArrayList<String> names;

    static {
	enemigos = new HashMap<>();
	names = new ArrayList<>();
    }

    public static void addEnemy(String name, Supplier< AEnemy> s) {
	FactoryEnemy.enemigos.put(name, s);
	FactoryEnemy.names.add(name);
    }

    public static AEnemy get(Supplier<? extends AEnemy> s) {
	return s.get();
    }

    public static List<String> getKeyNames() {
	return FactoryEnemy.names;
	// return new ArrayList<String>(FactoryEnemigos.enemigos.keySet());
    }

    public static AEnemy create(String nombre) {
	if (FactoryEnemy.enemigos.get(nombre) != null) {
	    return FactoryEnemy.enemigos.get(nombre).get();
	} else {
	    return null;
	}
    }
    public static String getNext(String actual) {
	int indice = names.lastIndexOf(actual) + 1;
	if (indice == names.size()) {
	    indice = 0;
	}
	return names.get(indice);
    }
}
