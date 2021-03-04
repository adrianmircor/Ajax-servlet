package pe.igh.app.service;

import java.util.ArrayList;
import java.util.List;

import pe.igh.app.model.TablaItem;

public class TablaService {
	
	public List<TablaItem> multiplicar(int num){
		List<TablaItem> lista = new ArrayList<>();
		int rpta;
		for(int i = 1; i <= 12; i++){
			rpta = i * num;
			TablaItem item = new TablaItem();
			item.setNum1(i);
			item.setNum2(num);
			item.setRpta(rpta);
			item.setOperador("X");
			lista.add(item);
		}
		return lista;
	}

}
